define([

    'jquery',
    'underscore',
    'backbone',
    'views/home/MatchSimulator',
    'text!templates/home/home.html',
    'text!templates/home/commentaryTable.html',
    'text!templates/home/commentary.html',
    'text!templates/home/players.html',

], function($,_, Backbone, Match, HomeTemplate, CommentaryTable,CommentaryBox,PlayersBox){

    var match = undefined;
    var View_home = Backbone.View.extend({
        el: $("content"),

        initialize: function() {
            var base = this;
            $(document).ready(function(){
              ui.initialize();

              //Changes in score is bound, and is updated in the screen
              match.matchstats.bind('change:balls', ui.updateScreen, this);

              //When the winningTeam is modified, the results are shown
              match.matchstats.bind('change:winningTeam', ui.showResults, this);

              base.bindFastForwardButton();
            });
        },

        render: function(){

            // A new match is started
            match = new Match();

            //A new template with match stats is rendered
            var tHomeTemplate = _.template(HomeTemplate);
            this.$el.html(tHomeTemplate(match.matchstats.toJSON()));
            scoreTable.initialize();
        },

        bindFastForwardButton : function () {
          $("#forward").bind('click',function() {
              //fastForward button takes you directly to the results
              match.fastForward();
          });
        }
    });
     var ui = {
       initialize : function (){
         this.ballUpdates = [];
         this.$scoreCard  =  $("#score-card");
         this.$overs      =  $("#overs");
         this.$reqRuns    =  $("#req-runs");
       },
       updateScreen : function(matchstats){
         var score    = matchstats.get('score');
         var wickets  = matchstats.get('wickets');
         var overs    = matchstats.get('overs');
         var recquiredRuns = matchstats.get('recquiredRuns');

         ui.updateScoreCards(score,wickets,overs,recquiredRuns);
         ui.updateCommentary(score,overs, recquiredRuns,matchstats);
       },

       updateScoreCards : function(score,wickets,overs,recquiredRuns){
         /* 'score', 'overs' and 'recquiredRuns' is updated for every ball */

         var base = this;
         base.$scoreCard.text(score+" - "+wickets);
         base.$overs.text(overs);
         var reqRuns = parseInt(recquiredRuns) > 0 ? recquiredRuns : "-";
         base.$reqRuns.text(reqRuns);
         (base.$scoreCard.parent()).add(base.$overs.parent()).add(base.$reqRuns.parent()).toggleClass('flip');
       },

       updateCommentary : function(score,overs, recquiredRuns,matchstats){
         /* Commentary has ball updates - The commentary table is updated after every over */
         var currentPlayerName = matchstats.get('currentPlayer').name;
         var runs              = matchstats.get('runs')
         var totalOvers        = matchstats.get('totalOvers')
         var players           = matchstats.get('players')

         this.ballUpdates.push({'overs': overs, 'playerName': currentPlayerName,'runs' : runs, 'score' :score});

         if(overs === parseInt(overs)){
           /* The commentary table is updated after every over */
           scoreTable.updateCommentary(this.ballUpdates, totalOvers - overs ,recquiredRuns);
           this.ballUpdates = [];
           scoreTable.updatePlayerInfo(players);
        }
       },

       showResults : function(matchstats){

         var winningTeam = matchstats.get('winningTeam');
         var battingTeam = matchstats.get('battingTeam');
         var bowlingTeam = matchstats.get('bowlingTeam');
         var str = "";
         if(winningTeam.name == battingTeam.name){
           str = battingTeam.name+" won by "+(11-matchstats.get('wickets')+" wickets");
         }else{
           str = bowlingTeam.name +" won by "+matchstats.get('recquiredRuns')+" runs";
         }
         var totalOvers = matchstats.get('totalOvers');
         var overs      = matchstats.get('overs');
         var recquiredRuns = matchstats.get('recquiredRuns');
         var players    = matchstats.get('players');
         scoreTable.updateCommentary(ui.ballUpdates, totalOvers - overs, recquiredRuns,true);
         scoreTable.updatePlayerInfo(players);

         $('body').css('background-image',"url("+winningTeam.logo+")");
         $("#cards").css("opacity","0.90");

         scoreTable.displayWinner(str);
       }
     };

     var scoreTable = {

       initialize : function(){
         var base = this;
         base.$table    = $("#scoreTable");
         base.$players  = $("#players");
         base.$stats    = $("#stats");

         base.navBar    = $("#navBar");
         base.navBarChildren  = base.navBar.children();

         base.render();
         base.bindEvents();
       },

       render : function () {
         var base = this;
         var tCommentaryTable = _.template(CommentaryTable);
         base.$stats.html(tCommentaryTable({'oversLeft':4,'recquiredRuns':40}));
         base.$commentaryTable =  $("#commentaryTable");
         base.$playersInfo     =  $("<span id= 'playersInfo' class=' text-center playersInfo'></span>")
       },

       updateCommentary : function (ballUpdate,oversLeft,recquiredRuns,scrollToTop) {
         var tCommentaryBox = _.template(CommentaryBox);
         this.$commentaryTable.append(tCommentaryBox({'ballUpdate':ballUpdate ,'oversLeft':oversLeft,'recquiredRuns':recquiredRuns}));
            var scrollValue = (scrollToTop == true) ? 0 : this.$commentaryTable[0].scrollHeight;
         this.$commentaryTable.parent().scrollTop(scrollValue);
       },

       updatePlayerInfo : function (players) {
         var tPlayersBox = _.template(PlayersBox);
         console.trace("player details",players)
         this.$playersInfo.html(tPlayersBox({ 'playersDetails' : players}));
       },

       displayWinner : function (str) {
         this.$commentaryTable.prepend("<span class='font35'>"+str+"</span><br>");
       },

       bindEvents : function(){
         var base = this;
         (base.$table).add(base.$players).bind('click',function(){
           base.toggleClassActive($(this).parent());
           var boxContent = $(this).attr('id') == "players" ? base.$playersInfo : base.$commentaryTable;
           base.$stats.html(boxContent);
         });
       },

       toggleClassActive : function(element){
         $.each(this.navBarChildren,function(i,child){
           (child == element[0]) ? $(child).addClass("active") : $(child).removeClass("active");
         });
       },
     };

    return View_home;
});
