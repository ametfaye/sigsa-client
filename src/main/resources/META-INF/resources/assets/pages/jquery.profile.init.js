/**
 * Theme: Dastyle - Responsive Bootstrap 4 Admin Dashboard
 * Author: Mannatthemes
 * Profile Js
 */



var cities = L.layerGroup();

	var longitude = document.getElementById('longitude-input').value;
	var latitude = document.getElementById('latitude-input').value;
	
	L.marker([latitude, longitude]).bindPopup('Lieu de la mission').addTo(cities);


	var mbAttr = 'Website - <a href="https://mannatthemes.com/" target="_blank">Mannatthemes</a> '
		mbUrl = 'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw';

	var grayscale   = L.tileLayer(mbUrl, {id: 'mapbox/light-v9', tileSize: 512, zoomOffset: -1, attribution: mbAttr}),
		streets  = L.tileLayer(mbUrl, {id: 'mapbox/streets-v11', tileSize: 512, zoomOffset: -1, attribution: mbAttr});


	
	var map = L.map('user_map', {
		center: [latitude, longitude],
		zoom: 10,
		layers: [streets, cities]
	});

	var baseLayers = {
		"Grayscale": grayscale,
		"Streets": streets
	};

	var overlays = {
		"Cities": cities
	};

	L.control.layers(baseLayers, overlays).addTo(map);


	

   // light_datepick
new Lightpick({
	field: document.getElementById('light_datepick'),
	inline: true,                
  });


