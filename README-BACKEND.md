//**************************************************************************************
//**************************************************************************************
//*************************@Author : JONA FRANK*****************************************
//**************************************************************************************
//**************************************************************************************

Hi,

Greetings from Enchai(Chennai). I have tried making the code as modular as possible, 
reusing components where ever needed and i have also hard-coded in the runner classes. 
Basically took this as a challenge to complete the entire code in a day from scratch, 
and managed to complete it in a day and a half.

I have taken up the problem statement given in the problem set 1 as i found it 
interesting.

Giving what is asked is just mediocre, thus decided to take the problem statement 1 step 
further and make it a WEB APP.

I have designed the entire problem statement as a WEB APP, The back-end supplies the REST
API calls for getting the match output as well as the CRUD operation for the players in 
the game.

Me and my friend decided to do this together, Me taking over the back-end and she decided
to take over the front end.

Now i shall explain the project structure.

QUICK RUN PROGRAM    : 1. Extract game.zip
					   2. From command line invoke java -cp game.jar com.matchapi.runner.Runner
					   
					   All dependencies have been bundled in the game.jar,players.json contains
					   sample entries, also the command line has an optional parameter to pass a
					   different target, Default is 40, 
					   eg java -cp game.jar com.matchapi.runner.Runner 45
					   This is specifically for cmd line output.  

RUN PROGRAM    		 : 1. Extract the MatchApi.zip file
					   2. Load the project onto the IDE (Created using NETBEANS)
					   3. Add dependencies (JARs), as given in the zip if required
					   4. Add players.json to e:\players.json {Pre loaded values} {If 
					      path needs to be changed edit in Utils.java}
					   4. Run the Runner.java program to get the output in the console
					   5. Run the Project and visit the url 
					      http://localhost:8080/MatchApi/runGame.jsp to view the output 
					      as a web-page

URL FOR TESTING      : http://localhost:8080/MatchApi/runGame.jsp?target=<user_defined>
					   This URL can be called to test the game simulation (both problem 
					   1 & 2) The parameter target is optional, Default target is set 
					   as 40.

REST API COLLECTION  : https://www.getpostman.com/collections/83bf390ef45669c7f226
                       The API URL collection can be loaded on POSTMAN app in chrome
                       and tested

PROGRAMMING LANGUAGE : JAVA (v8.x)

FRAMEWORK USED       : SPRING 4.X

DEVELOPED IN         : NETBEANS (v8.1) 

PROJECT STRUCTURE    :

MatchApi
	Web Pages
		WEB-INF
			rest-servlet.xml
			web.xml
		runGame.jsp
	Source Packages
		com.matchapi.controller
			MatchApiController.java
		com.matchapi.model
			Player.java
			Players.java
		com.matchapi.simulator
			MatchSimulator.java
		com.matchapi.util
			Utils.java
		com.matchapi.runner
			Runner.java
	Library
	Configuration Files
		web.xml

<path_defined>:\players.json

DEPENDENCIES        : 1.jackson-mapper-asl-1.8.4.jar <FOR SPRING AUTO PARSING HTML RESPONSE AS JSON>
                      2.json_simple-1.1.jar          <FOR JSONObject AND JSONArray OBJECTS>          
                      {BOTH JARS BUNDLED IN THE ZIP USE IT WHEN DEPLOYING TO IDE/RUNNING}

EXPLAINATION        :

Starting point : Player.java
							This class is the basic model for the Player, It has the getters and setters 
							for the following fields :
							id
							name
							dot
							one
							two
							three
							four
							five
							six
							out

							Id and name are self explanatory
							dot,one,two,three,four,five,six,out corresponds to the probability percentages
							of the players for each of the above score. For example If kirat boli has 20% 
							probability for getting a 6, six will have the integer value 20.

							This class also has the getAsJSON function to get the player data as a json 
							object.

Class          : Players.java
							This is the wrapper class for an individual player, All the CRUD operations
							for loading/viewing/deleting Player data can be found here.

Class          : Utils.java
							Basically the helper class for loading data from file or for saving data to file

Class          : MatchSimulator.java
							THIS IS WHERE THE MAGIC HAPPENS, this is the main class which simulates the entire game
							It helps in getting the ball by ball update for a player and returning the match results

Class          : MatchApiController.java
							This is the spring controller for mapping the API calls to the internal methods, It is 
							autowired with existing methods in the MatchSimulator and Players class

Class          : Runner.java
							This is the main runner class for the project, this can be invoked to simulate the
							game, and for running tests.

File           : players.json
							This file contains the players data in JsonArray format. Default location as mapped in the Utils
							class is E:\

JSP            : runGame.jsp
							It is a clone of the runner class for viewing the output as a webpage rather than the console


I have also given sufficient comments in each file for all the methods defined/used.

TROUBLESHOOTING      :

1. Empty game result : 
						Please check if players.json file is dropped in e:\ or change the path in Utils.java and recompile,
						run the project. Else the player data can be populated via API/UI

2. Dependencies missing :
						I have documented and packaged the dependent jar files, Please add them to the project when running it.	


Things that could have been done :

1. Re-factor/Optimize the runner.java and runGame.jsp, I just put it there to quickly view the output, Focus was given on the API 
   part of the code
2. Add logger,JDBC support... That would have been over kill for this problem i suppose
3. Instead of a REST API, thought about giving the output as a WEBSOCKET stream, again thought it would have been over kill for 
   the project
4. Error passing in API, runner classes(Integer parsing for input 'target' etc), I focussed on finishing it quickly thus skipped it.


CONTACT :

JONA FRANK S
9445544689
jona.mailbox@gmail.com