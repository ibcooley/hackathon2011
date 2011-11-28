package com.hackathon

import java.net.URL
import org.scalatra._
import java.io.{InputStream, InputStreamReader, BufferedReader}
import java.sql.{DriverManager, ResultSet};
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

class HackathonScalatra extends ScalatraServlet with UrlSupport {

	before {
		contentType = "application/json"
	}

	get("/routeInfo") {
		
		request.getParameter("callback") + "(" + DirectionsService.getRouteJson(request.getParameter("startLoc"), request.getParameter("endLoc")) + ")"
	}

	protected def contextPath = request.getContextPath
}

class GoogMapStep (val distance: BigInt, val duration: BigInt, val startLat: Double, val startLng: Double, val endLat: Double, val endLng: Double) {
	var events: List[Event] = List[Event]()
}

class Event (val eventType: String, val message: String, val icon: String) {
	
}

class Route (var steps: List[GoogMapStep]) {
	
}

object DirectionsService {

	def getRouteJson(startLoc: String, endLoc: String): String = {
		val route = DirectionsService.getRoute(startLoc, endLoc)
		return DirectionsService.createRouteJson(route)
	}

	def getRoute(startLoc: String, endLoc: String): Route = {
		var mysql = new MySqlRetsDatabase()
		var serviceUrlStr = "http://maps.googleapis.com/maps/api/directions/json?origin=" + startLoc + "&destination=" + endLoc + "&sensor=true"
		val json = JsonParser.parse(retrieveUrlText(serviceUrlStr))
		var steps = for { 
			JObject(step) <- json
			JField("distance", JObject(distance)) <- step
			JField("duration", JObject(duration)) <- step
			JField("end_location", JObject(endLocation)) <- step
			JField("start_location", JObject(startLocation)) <- step
			JField("travel_mode", JString(travelMode)) <- step
			JField("value", JInt(distanceVal)) <- distance
			JField("value", JInt(durationVal)) <- duration
			JField("lat", JDouble(endLocationLat)) <- endLocation
			JField("lng", JDouble(endLocationLng)) <- endLocation
			JField("lat", JDouble(startLocationLat)) <- startLocation
			JField("lng", JDouble(startLocationLng)) <- startLocation
			if travelMode == "DRIVING"
		} yield (distanceVal,durationVal,endLocationLat,endLocationLng,startLocationLat,startLocationLng)
		
		var stepList = List[GoogMapStep]()
		steps.foreach { step =>
			var stepObj = new GoogMapStep(step._1,step._2,step._5,step._6,step._3,step._4)
			stepList ::= stepObj
			val events = mysql.lookupEvents(stepObj.startLat.toString().substring(0,5),stepObj.startLng.toString().substring(0,5))
			events.foreach { event =>
				stepObj.events ::= new Event(event("type"),event("message"),event("icon"))
				println("event added")
			}
		}

		return new Route(stepList.reverse)
	}

	def createRouteJson(route: Route): String = {
		var i = 1
		var duration = BigInt(0)

		route.steps.foreach { step =>
			duration = duration + step.duration
		}

		val json = ("timeLocations" -> route.steps.map { step =>
			var jobj = new JObject(List[JField]())
			jobj = jobj ~ ("location" -> JInt(i))
			i = i+1
			jobj = jobj ~ ("lat" -> JDouble(step.startLat))
			jobj = jobj ~ ("lng" -> JDouble(step.startLng))
			jobj = jobj ~ ("duration" -> JInt(duration))
			duration = duration - step.duration
			jobj = jobj ~ ("distance" -> JInt(step.distance))
			jobj = jobj ~ ("timestamp" -> JInt(System.currentTimeMillis()))
			jobj = jobj ~ ("eventsNow" -> DirectionsService.getNowJsonEvents(step))
	//		jobj = jobj ~ ("eventsNext" -> DirectionsService.getNextJsonEvents(i))
			jobj
		})
		return compact(JsonAST.render(json))
	}

	def getNowJsonEvents(step: GoogMapStep): JArray = {
		var eventList = List[JObject]()
		step.events.foreach { event =>
			var jobj = new JObject(List[JField]())
			jobj = jobj ~ ("type" -> JString(event.eventType))
			jobj = jobj ~ ("message" -> JString(event.message))
			jobj = jobj ~ ("icon" -> JString(event.icon))
			eventList ::= jobj
		}
		JArray(eventList)
	}

/*
	def getNextJsonEvents(location: int): JArray = {
		
	}
*/
	def retrieveUrlText(urlString: String): String = {
		println(urlString)
        val url = new URL(urlString.replaceAll(" ","%20"))
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
}

class MySqlRetsDatabase() {
    val conn_str = "jdbc:mysql://localhost:3306/EventData?user=root&password=root"

    classOf[com.mysql.jdbc.Driver]
    Class.forName("com.mysql.jdbc.Driver").newInstance
    val conn = DriverManager.getConnection(conn_str)


    def lookupEvents(lat: String, lng: String): List[Map[String,String]] = {
    	println("query, lat: " + lat + " lng: " + lng)
    	val statement = conn.createStatement ();
        var eventList = List[Map[String,String]]()
        val rs = statement.executeQuery("SELECT * FROM event WHERE lat = " + lat + " AND lng = " + lng);

        while(rs.next) {
             eventList ::= convertResultSetToMap(rs)
        }

        return eventList
    }

    def convertResultSetToMap(rs: ResultSet): Map[String,String] = {
        var event = Map[String,String]()
        val colCount = rs.getMetaData.getColumnCount
        for(i <- 1 to colCount) {
                val colName = rs.getMetaData.getColumnName(i)
                event += ((colName, rs.getString(colName)))
        }
        return event
    }

    def close(): Unit = {
        conn.close
    }
}
