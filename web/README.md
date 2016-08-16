#Problem1 - Front End
---------------------

All that the world wants is data, but beautiful and attractive presentation of the data is the only way that the world wants it.

So, we have designed a modular approach to present the data from the  matchApi in a exciting way.

(Due to time constraints, the UI design was stopped with a basic design, that was developed in less than a hour)

=========================================================================================================

The different modules in the front-end development are defined here :
---------------------------------------------------------------------

#models ----- matchstat.js

All the data that is required to be rendered on the screen, is stored here.


#Controller --- MatchSimulator.js

Gets the whole match result data from MatchApi.
Now for the simulation, every ball result is updated in the 'matchstat' data model, every 1.5s.

#Views ---- Home.js

A new match is started here.
The changes in the 'matchstat' model are bound here.
Whenever there's a change in the 'matchstat' model, the changes are updated on the screen.

#fastForward button
The button on the right bottom of the screen is for the people who are too eager to view the result of the match.

==============================================================================================================

Libraries used are,
-------------------

#backbone.js

The structure that it provides to the web application is so simply stupendous, that it made me want to use it. This js has helped me to develop the application, even while I was engaged with all the other time demanding projects.

#require.js

This is a js file that helps in module loading. It will greatly reduce the loading time of any project.

#underscore.js

While the primary reason for me to use this was, because backbone has hard dependency with this. It has greatly helped me with updating the html files quickly.

=============================================================================================================

Contact
-------

Divya Gupta M.S    

Mail me anytime at    divya261261@yahoo.com     
Or reach me at        9789921328
