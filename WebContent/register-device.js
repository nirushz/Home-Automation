window.addEventListener('load', function(ev){
	getDevices();
	
}, false);


var devicesList = document.getElementById("deviceUL");

//sent http POST request to DevicesServlet 
function post() {	
	var companyName = $("#companyName").val();
	var deviceName = $("#deviceName").val();
	var deviceID = $("#deviceID").val();
	var userID = 1234;  /*TODO get userID from local storage or cookie*/
	var errorMsgPlaceHolder = document.getElementById("errorMsg");
	
	

	if (deviceName == "") {
		errorMsgPlaceHolder.innerHTML = "Device Name is requried";
		return;
	}
	
	if (deviceID == "" || isNaN(deviceID) ) {
		errorMsgPlaceHolder.innerHTML = "Device ID is requried and should be only number";
		return;
	}
	
	$.post("http://localhost:8080/DeviceRegister/getDevices",
			{companyName:companyName, deviceName:deviceName, deviceID:deviceID, userID:userID},
			function(data) {				
				//SQL error codes handeling;
				switch (data) {
				case '1062': errorMsgPlaceHolder.innerHTML = "Device Id is already used";
					return;
				}
			
				//window.location.replace(data);
				$('#deviceUL').empty();
				getDevices();
				$('#myModal').modal('hide')
				$("#deviceName").val("");
				$("#deviceID").val("");
				
				/* using 'noty' jquery plugin for notification*/
				new Noty({
				    text: deviceName + " succesfully added",
				    timeout: 2000,
				    type: 'error'
				}).show();
				
			}
	);	
	

}


//sent http GET request to DevicesServlet 
function getDevices(){
	var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            var data = xhr.responseText;
            var parsedJSON = JSON.parse(data);
            
         // Get all device names from the json array and show it on website left sidebar
            for (var i=0;i<parsedJSON.length;i++) {
            	var li = document.createElement("li");
            	li.appendChild(document.createTextNode(parsedJSON[i].deviceName));
            	li.setAttribute("deviceID", parsedJSON[i].deviceID);
            	li.className += " list-group-item";
            	li.addEventListener('click', getDeviceData, false);
            	devicesList.appendChild(li);
            
            	var trashSpan = document.createElement("span");
            	trashSpan.className += " glyphicon glyphicon-trash";
            	trashSpan.style.float = 'right';
            	trashSpan.addEventListener('click', deleteListItem, true);
            	li.appendChild(trashSpan);
            	
              }
        }
    }
    xhr.open('GET', 'http://localhost:8080/DeviceRegister/getDevices', true);
    xhr.send(null);
}


function deleteListItem(ev){
	ev.stopPropagation(); //prevent the parent click listener (li)
	ev.target.parentElement.parentElement.removeChild(ev.target.parentElement);
	
	var deviceID = ev.target.parentElement.getAttribute("deviceID");
	//DELETE request - delete the device data from the table
	$.ajax({
	    url: 'http://localhost:8080/DeviceRegister/getDevices' + '?' + $.param({"deviceID": deviceID}),
	    type: 'DELETE',
//	    data: {"deviceID":deviceID},
//	    success: function(result) {
//	    	console.log(result);
//	    }
	});
}


//sent http GET request to DataServlet 
function getDeviceData (ev){
	
	$.get("http://localhost:8080/DeviceRegister/getData",		// The servlet url
		  {deviceID:ev.target.getAttribute("deviceID")},		// deviceID of the device that was pressed
		  showData);											//f unction to run if the request succeeds
	
	function showData(data){
		var activeLI = document.getElementsByClassName("active");
		if (activeLI.length > 0) {
			activeLI[0].classList.remove("active");			
		}
		ev.target.className += " active";
		var parsedJSON = JSON.parse(data);
		var deviceDataPlaceholder = document.getElementById("deviceData");
		
		if (undefined === parsedJSON[0]){ 						//device with no data
			
			deviceDataPlaceholder.innerHTML = "This device doesn't have any data yet.";
			deviceDataPlaceholder.className = " text-danger";
			return;
		}
		
		document.getElementById("deviceData").innerHTML = "";
		
		// Get all data from the json array and show it on website
		for (var i=0;i<parsedJSON.length;i++) {
		    var deviceData =  parsedJSON[i].deviceData
		    deviceDataPlaceholder.className = " text-primary";
		    deviceDataPlaceholder.innerHTML += deviceData + "<br>";
		 }
	}
}

