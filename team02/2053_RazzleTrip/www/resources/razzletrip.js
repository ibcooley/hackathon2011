
/*
    RazzleTrip v0.1

    Josh Skidmore <josh.skidmore@sparcedge.com>
    Bryan Gilbert <bryan.gilbert@sparcedge.com>
    Taylor Brockman <taylor.brockman@gmail.com>
*/


(function(){

    var map = null,
        firstTime = false,
        timeLocations = [],
        lastLocationId = null,
        lastMarker = null,
        trafficLayer = new google.maps.TrafficLayer();
    
    var toggles = {
        "traffic":      false
    };

    var pref = {
        "serviceURI":       "http://ec2-184-72-67-174.compute-1.amazonaws.com:8080/routeInfo"
        //http://ec2-50-17-20-41.compute-1.amazonaws.com:8080/routeInfo?startLoc=33.638283,-81.407183&endLoc=32.863714,-79.915068&callback=blah  
    };


    /*$.mobile.changePage($("#dash"),{
                "transition":   "fade"
           }); */
    
    var updateCourse = function(arg) {
        if (arg.locationId == null)
            return;

        var locationId = arg.locationId;
            dataBundle = timeLocations[locationId];

        if (lastLocationId != null && locationId == lastLocationId)
            return;
        
        if (lastMarker != null)
            lastMarker.setMap(null);

        lastLocationId = locationId;

        

        // update time
            var time = parseInt( dataBundle.duration / 60 );
            $("#time-remaining").text(time + " minutes");
        
        var latlng = new google.maps.LatLng(dataBundle.lat, dataBundle.lng);
        var marker = new google.maps.Marker({                                                                                              
            "position":     latlng                                                                                                      
        });



        
        marker.setMap(map);
        lastMarker = marker;

        centerMap({
            "lat":  dataBundle.lat,
            "lng":  dataBundle.lng
        });

    };

    var updateServiceData = function(res) {
        
        timeLocations = res.timeLocations;

        $("#time-slider-input")
            .attr({
                "max": res.timeLocations.length
            })
            .change(function(){
                updateCourse({
                    "locationId":   parseInt( $("#time-slider-input").val() )
                });
            })
            .slider("refresh");
        
        updateCourse({
            "lat":  timeLocations[0].lat,
            "lng":  timeLocations[0].lng
        });
    };

    var centerMap = function(arg) {
        if (arg.lat == null || arg.lng == null)
            return;
        
        var lat = arg.lat,
            lng = arg.lng;

        var point = new google.maps.LatLng(lat,lng);
        map.setCenter(point);
    };


    var updateRoute = function(arg) {
        if (arg.source == null || arg.destination == null)
            return;
        
        var source = arg.source,
            destination = arg.destination;
        
        var url = pref.serviceURI + "?startLoc=" + source + "&endLoc=" + destination + "&callback=?";

        $.ajax({
            "url":          url,
            "dataType":     "json",
            "success":      updateServiceData
        });

        mapRoute({
            "source":       source,
            "destination":  destination
        });
    
        $.mobile.changePage($("#dash"),{
            "transition":   "fade"
        });

        $("#time-slider-input")
            .val(0) 
            .slider("refresh");

    };
    
    var mapRoute = function(arg) {
        if (arg.source == null || arg.destination == null)
            return;
        
        var source = arg.source,
            destination = arg.destination;

        //console.log("SOURCE: " + source);
        //console.log("DEST: " + destination);

        var directionsDisplay;
        var directionsService = new google.maps.DirectionsService();

        directionsDisplay = new google.maps.DirectionsRenderer({
            "map":              map,
            "preserveViewport": false,
            "draggable":        false,
            "hideRouteList":    true,
            "markerOptions":    {
                "visible":  false
            }
        });
       
        var request = {
            "origin":           source,
            "destination":      destination,
            "travelMode":       google.maps.DirectionsTravelMode.DRIVING
        };

        directionsService.route(request, function(res,status) {

            if (status == google.maps.DirectionsStatus.OK)
                directionsDisplay.setDirections(res);

        });

    };


    var saveRoute = function(ev) {
            
        var source = $("#source").val(),
            destination = $("#destination").val();


        if (source.match(/\[Current Position\]/i) ) {
            
            // process current location using phonegap
            var geoSuccess = function(res) {
                var coords = res.coords;
                
                updateRoute({
                    "source":       coords.latitude + "," + coords.longitude,
                    "destination":  destination
                });

            };

            var geoFail = function(error) {
                alert("FAIL: " + error.code + ": " + error.message);
            };

            var watchId = navigator.geolocation.getCurrentPosition(geoSuccess, geoFail);

        }
        else {
            // no geo

            updateRoute({
                "source":       source,
                "destination":  destination
            });
        }

    };

    var recentDestinationUpdate = function() {
        
        $("#destination")
            .val($("#recentDestinations").val());

    };


    var toggleTrafficLayer = function() {
        
        var status = $("#trafficSlider").val();

        if (status.toLowerCase() === "on")
            trafficLayer.setMap(map);
        else
            trafficLayer.setMap(null);

    };



    onPageLoad = function() {

        $("#route-save-button")
            .live("click",saveRoute);
        
        $("#trafficSlider")
            .live("change",toggleTrafficLayer);
        
        $("#recentDestinations")
            .live("change",recentDestinationUpdate);

        console.log( $('#route').html() );
        
        $('#route').live('pagecreate',function(event){
          alert('This page was just enhanced by jQuery Mobile!');
        });
        

        var latlng = new google.maps.LatLng(32.900074,-79.915409);
        var opts = {
          zoom: 6,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        map = new google.maps.Map(document.getElementById("map-canvas"),opts);

        

            var lat = 34.5;                                                                                                                      
            var lng = -79.5;
            var xZoom = 3.35;                                                                                                                    
            var yZoom = 2.5;                                                                                                                     
            var imageBounds = new google.maps.LatLngBounds(                                                                                      
            new google.maps.LatLng(lat-yZoom/2,lng-xZoom/2),                                                                                     
            new google.maps.LatLng(lat+yZoom/2,lng+xZoom/2)
        );                                                                                    
                                                                                                                                               
        // var imgUrl = "http://resize.wunderground.com/cgi-bin/resize_convert?ox=gif&url=radblast/cgi-bin/radar/WUNIDS_composite%3Fcenterlat=34.5\1694489%26centerlon=-79.47250366%26radius=75%26newmaps=1%26smooth=1%26newmaps=1%26reproj.automerc=1%26api_key=011a8b43fd34622f";           
        // cached                                                                                                                                  
        var  imgUrl = "http://jonx.org/~brockman/maps/radar.jpg";                                                                                  
                                                                                                                                           
        var radarMap = new google.maps.GroundOverlay( imgUrl, imageBounds );                                                                 
        //radarMap.setMap(map);

        // wunderground
        // key: 011a8b43fd34622f    
        // basic radar image > 

        var latlng = new google.maps.LatLng(lat, lng);
        var marker = new google.maps.Marker({                                                                                              
            position: latlng,                                                                                                                  
            title:"Hello World!"                                                                                                               
        });     

        //marker.setMap(map);

    }

})();