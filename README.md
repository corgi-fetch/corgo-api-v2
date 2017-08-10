# corgo-api-v2 #
API developed in Java with Spring Boot. API URL is currently hosted at http://corgoapi-v2.azurewebsites.net/api.

## Currently exposed endpoints ##

GET: http://corgoapi-v2.azurewebsites.net/api/post

GET: http://corgoapi-v2.azurewebsites.net/api/post/{post_id} 

POST: http://corgoapi-v2.azurewebsites.net/api/post

	{
		"date" : int,
		"owner" : {
			"id" : string,
			"rating" : int,
			"name" : string
		},
		"title" : string,
		"description" : string,
		"payment" : double,
		"interestedQueue" : UserStubDTO[] (should be empty on init),
		"serviceGiven" : boolean,
		"serviceReceived" : boolean
	}

GET: http://corgoapi-v2.azurewebsites.net/api/user 

GET: http://corgoapi-v2.azurewebsites.net/api/user/{userid} 

POST: http://corgoapi-v2.azurewebsites.net/api/user

	{
		"rating" : int,
		"name" : string,
		"email" : string,
		"postHistory" : Post[] (should be empty on init),
		"currentPosts" : Post[] (should be empty on init),
		"currentJobs" : Post[] (should be empty on init),
		"creditCardNumber" : string,
		"bankAccount" : string
	}



