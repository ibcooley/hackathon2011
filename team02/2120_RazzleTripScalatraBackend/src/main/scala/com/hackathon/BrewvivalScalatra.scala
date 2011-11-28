package com.hackathon

import java.net.URL
import java.io.{InputStream, InputStreamReader, BufferedReader}
import java.sql.{Connection, DriverManager, ResultSet};
import scala.util.Sorting
import scala.collection.mutable.HashMap
import org.scalatra._
import net.liftweb.json._

class BrewvivalScalatra extends ScalatraServlet with UrlSupport {

    before {
        contentType = "text/csv"
    }

    get("/") {
        BrewvivalBeerReporter.createCSV()
    }

    protected def contextPath = request.getContextPath
}

class Beer(val name: String, val style: String, val abv: String, val brewery: String, val baUrl: String, val rbUrl: String) extends Ordered[Beer] {
    var baNumRating = 0
    var rbNumRating = 0
    var baRatingString = ""
    var rbRatingString = ""
    var index = 0

    def compare(that: Beer): Int = {
        this.index - that.index
    }
}

object BrewvivalBeerReporter {

    val baRating = """(?s).*?BA\sOVERALL(?s).*?BAscore_big(?s).*?>(.*?)<(?s).*?""".r
    val rbRating = """(?s).*?performance\srelative(?s).*?>(.*?)<(?s).*""".r

    val nameClass = """views-field\sviews-field-title"""
    val styleClass = """views-field\sviews-field-field-style-value"""
    val abvClass = """views-field\sviews-field-field-abv-value"""
    val breweryClass = """views-field\sviews-field-field-brewery-value"""
    val baUrlClass = """views-field\sviews-field-field-beeradvocate-url"""
    val rbUrlClass = """views-field\sviews-field-field-ratebeer-url"""

    val brewvivalBeer = ("""<td\sclass=""" + "\"" + nameClass + "\"" + """>(?s).*?>((?s).*?)<""" +
                        """(?s).*?<td\sclass=""" + "\"" + styleClass + "\"" + """>((?s).*?)<""" +
                        """(?s).*?<td\sclass=""" + "\"" + abvClass + "\"" + """>((?s).*?)<""" +
                        """(?s).*?<td\sclass=""" + "\"" + breweryClass + "\"" + """>((?s).*?)<""" +
                        """(?s).*?<td\sclass=""" + "\"" + baUrlClass + "\"" + """>(?s).*?<(a href=\"(.*?)\"|/(td))""" +
                        """(?s).*?<td\sclass=""" + "\"" + rbUrlClass + "\"" + """>(?s).*?<(a href=\"(.*?)\"|/(td))""").r
    val brewvivalUrl = "http://brewvival.com/beers"

    def retrieveAndCacheRating(url: String): String = {
        val conn_str = "jdbc:mysql://localhost:3306/Brewvival?user=root&password=root"
        var rating = ""

        classOf[com.mysql.jdbc.Driver]
        val conn = DriverManager.getConnection(conn_str)

        try {
            val statement = conn.createStatement()
            val rs = statement.executeQuery("SELECT * FROM beer_ratings WHERE url = '" + url.trim() + "'")
            if(rs.next) {
                rating = rs.getString("rating")
            } else {
                if(url.contains("ratebeer")) {
                    rating = scrapeRateBeerRating(url)
                } else if(url.contains("beeradvocate")) {
                    rating = scrapeBeerAdvocateRating(url)
                }
                statement.executeUpdate("INSERT INTO beer_ratings (url, rating) VALUES ('" + url.trim() + "','" + rating + "')")
            }
        }
        finally {
            conn.close
        }

        return rating
    }

    def retrieveUrlText(urlString: String): String = {
        val url = new URL(urlString)
        val bReader = new BufferedReader(new InputStreamReader(url.getContent().asInstanceOf[InputStream]))

        def readAll(reader: BufferedReader, sb: StringBuilder): String = {
            val line = reader.readLine()
            if(line != null) {
                readAll(reader, sb.append(line + "\n"))
            }
            sb.toString()
        }
        return readAll(bReader, new StringBuilder())
    }

    def retrieveFirstGoogleResult(unencodedUrlString: String): String = {
        val urlString = unencodedUrlString.replaceAll(" ","%20")
        val json = JsonParser.parse(retrieveUrlText(urlString))
        val JString(resUrl) = ((json \ "responseData" \ "results")(0) \ "url")
        return resUrl
    }

    def retrieveBeerUrls(beer: String): (String,String) = {
        val baUrlString = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=site:beeradvocate.com%20" + beer
        val rbUrlString = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=site:ratebeer.com%20" + beer

        val baUrl = retrieveFirstGoogleResult(baUrlString)
        val rbUrl = retrieveFirstGoogleResult(rbUrlString)

        return (baUrl,rbUrl)
    }

    def retrieveBeerListFromBrewvivial(): List[Beer] = {
        var bvBeerList = List[Beer]()
        (brewvivalBeer findAllIn retrieveUrlText(brewvivalUrl)).matchData.foreach { m => 
                bvBeerList = (new Beer (
                name = m.subgroups(0).trim().replaceAll("&#039;","'"),
                style = m.subgroups(1).trim(),
                abv = m.subgroups(2).trim(),
                brewery = m.subgroups(3).trim(),
                baUrl = (if(m.subgroups(5) != null) m.subgroups(5).trim() else ""),
                rbUrl = (if(m.subgroups(8) != null) m.subgroups(8).trim() else "")
            ) :: bvBeerList)
        }   
        return bvBeerList
    }

    def scrapeBeerAdvocateRating(baUrl: String): String = {
        try {
            val baRating(rating) = retrieveUrlText(baUrl)
            return rating
        } catch {
            case e: Exception => return ""
        }
    }

    def scrapeRateBeerRating(rbUrl: String): String = {
        try {
            val rbRating(rating) = retrieveUrlText(rbUrl)
            return rating
        } catch {
            case e: Exception => return ""
        }
    }

    def convertLetterRatingToNum(alpRating: String): Int = {
        (alpRating match {
            case "A+" =>
                return 98
            case "A" =>
                return 95
            case "A-" =>
                return 92
            case "B+" =>
                return 88
            case "B" =>
                return 85
            case "B-" => 
                return 82
            case "C+" =>
                return 78
            case "C" =>
                return 75
            case "C-" =>
                return 72
            case "D+" =>
                return 68
            case "D" =>
                return 65
            case "D-" =>
                return 62
            case _ =>
                return 0
             
        })
    }

    def createCSV(): String = {
        Class.forName("com.mysql.jdbc.Driver").newInstance
        val bvBeerList = retrieveBeerListFromBrewvivial()

        bvBeerList.foreach { beer: Beer =>
            var baLetterRating = ""
            var rbNumRatingString = ""
            var baNumRating = 0
            var rbNumRating = 0

            if(beer.baUrl != "") {
                beer.baRatingString = retrieveAndCacheRating(beer.baUrl)
                beer.baNumRating = convertLetterRatingToNum(beer.baRatingString)
            }
            if(beer.rbUrl != "") {
                beer.rbRatingString = retrieveAndCacheRating(beer.rbUrl)
                beer.rbNumRating = if(beer.rbRatingString != "") beer.rbRatingString.toInt else 0
            }
            if(beer.baNumRating > 0 && beer.rbNumRating > 0) {
                beer.index = (beer.baNumRating*2 + beer.rbNumRating*2) / 2
            } else {
                beer.index = (beer.baNumRating*2 + beer.rbNumRating*2)
            }
        }

        val bvBeerArray = bvBeerList.toArray
        Sorting.quickSort(bvBeerArray)
        val sb = new StringBuilder()

        sb.append("Beer,Style,ABV,Brewery,BA Score,RB Score\n")

        bvBeerArray.foreach { beer: Beer =>
            sb.append(
                beer.name.replaceAll(",","") + "," +
                beer.style.replaceAll(",","") + "," +
                beer.abv.replaceAll(",","") + "," +
                beer.brewery.replaceAll("¬∑", "").replaceAll("·", "").replaceAll(",","") + "," +
                beer.baRatingString.replaceAll(",","") + "," +
                beer.rbRatingString.replaceAll(",","") + "," + "\n"
            )
        }

        return sb.toString()
    }
}