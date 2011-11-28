import java.net.URL
import java.io.{InputStream, InputStreamReader, BufferedReader}
import BigInt._

import net.liftweb.json._
import net.liftweb.json.JsonDSL._

class GoogMapStep (val distance: BigInt, val duration: BigInt, val startLat: Double, val startLng: Double, val endLat: Double, val endLng: Double) {

}

class Route (var steps: List[GoogMapStep]) {
	
}

object DirectionsService {
	
	def getRoute(startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double): Route = {
		var startLatLng = startLatitude + "," + startLongitude
		var endLatLng = endLatitude + "," + endLongitude
		var serviceUrlStr = "http://maps.googleapis.com/maps/api/directions/json?origin=" + startLatLng + "&destination=" + endLatLng + "&sensor=true"
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
			stepList ::= new GoogMapStep(step._1,step._2,step._5,step._6,step._3,step._4)
		}

		return new Route(stepList.reverse)
	}

	def createRouteJson(route: Route): String = {
		var i = 1
		var duration = BigInt(0)
		val json = ("timeLocations" -> route.steps.map { step =>
			var jobj = new JObject(List[JField]())
			jobj = jobj ~ ("location" -> JInt(i))
			i = i+1
			jobj = jobj ~ ("lat" -> JDouble(step.startLat))
			jobj = jobj ~ ("lng" -> JDouble(step.startLng))
			jobj = jobj ~ ("duration" -> JInt(step.duration))
			duration = duration + step.duration
			jobj = jobj ~ ("distance" -> JInt(step.distance))
			jobj = jobj ~ ("timestamp" -> JInt(System.currentTimeMillis()))
			jobj
		})
		return compact(JsonAST.render(json))
	}

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

val startLat = 33.638283
val startLng = -81.407183
val endLat = 32.863714
val endLng = -79.915068

val route = DirectionsService.getRoute(startLat, startLng, endLat, endLng)
println(DirectionsService.createRouteJson(route))
