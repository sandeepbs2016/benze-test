# Getting Started

**Autonomous electric vehicles**

**PROBLEM STATEMENT**


Design and implement a microservice for an autonomous electric vehicle system with the following features:

    Check if the vehicle can reach the destination without charging
    Find charging stations - If the destination can not be reached with current charging level. 
    Appropriate handling to be done if the destination cannot be reached even with charging

**Build tool**

    Apache Maven

**Language**

    Java 1.8

**Frameworks**

    Spring Boot
    Spring web
 
 **Service API**

	API		:	http://localhost:8080/check_charge_stations
	
	Request	: { "vin": "W1K2062161F0017", "source": "Home", "destination": "zoo" }
	
	Response :	
		{
		    "transactionId": "1633763109814",
		    "vin": "W1K2062161F0017",
		    "source": "Home",
		    "destination": "zoo",
		    "distance": 140,
		    "currentChargeLevel": 39,
		    "chargingStations": [
		        "S1",
		        "S2"
		    ],
		    "errors": [],
		    "chargingRequired": true
		}
		
    

# mbrd
