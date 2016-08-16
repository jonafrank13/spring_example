/*
Filename : MatchSimulator.js
function : Simulates the match using the match results given by 'runMatch' api - by Jona The Frank
*/

define([
    'jquery',
    'underscore',
    'backbone',
    'models/matchstat'

], function($, _, Backbone,MatchStat){

    var URL = {
      GETRESULT   : "rest/run/",
      GETPLAYERS  : "rest/player/"
    };

    var matchstats=[],playersScores=[],teams=[];
    var interval = 1500;

    var Match = Backbone.View.extend({
      initialize: function() {
        /*The model is initialized  */
        matchstats = new MatchStat({balls : 0, score : 0, wickets : 0, currentPlayer : undefined, recquiredRuns : 40, overs : 0});
        teams = matchstats.get('teams');
        matchstats.set({'battingTeam':teams[0],'bowlingTeam':teams[1]});
        this.matchstats = matchstats;

        /* The matchResult is obtained and simulated  */
        request.send('GET',URL.GETPLAYERS,players.updatePlayerInfo,undefined);
        request.send('GET',URL.GETRESULT,result.updateScores,undefined);
      },
      fastForward : function(){
        interval = 1;
      }
    });

      var request = {
        send : function(type,url,callback,data) {

          $.ajax({
                type : type,
                url  : url,
                data : data,
                success: function(resp){
                    if(callback != undefined){
                        callback(resp);
                    }
                },
                error: function(resp){
                    var res = resp;
                }
         });
         
        },
      };


    var result = {

      ballUpdates : undefined,

      updateScores : function (resp){
        result.ballUpdates = resp  //JSON.parse(resp);
        result.setIntervalMethod();
      },

      /*  setInterval - with varying interval */
      /* Every 1500ms - a ball's detail is updated in the model data */

       setIntervalMethod : function () {
        setTimeout(function () {
          var ballUpdates = result.ballUpdates;
          var balls = matchstats.get('balls');
          (ballUpdates[balls])  ?   result.updateRuns(ballUpdates[balls]) : result.endMatch();
          if(ballUpdates[balls]){
            result.setIntervalMethod();
          }
        }, interval);
      },

      updateRuns : function(ballUpdate){
        //The scores and runs after each ball is updated to the model
        var balls = matchstats.get('balls')+1;
        var score = matchstats.get('score');
        var recquiredRuns = matchstats.get('recquiredRuns');

        matchstats.set({'overs': parseInt(balls/6)+(balls%6)/10 });

        var playerId = parseInt(_.keys(ballUpdate)[0]);
        var currentRuns = ballUpdate[playerId];
        matchstats.set({'runs' :  currentRuns != "out" ? parseInt(currentRuns) : "out" });
        players.updatePlayerScores(playerId,matchstats.get('runs'));

        if(currentRuns=="out"){
          matchstats.set({'wickets' : ++matchstats.attributes.wickets});
        }else{
          matchstats.set({'score' : score + parseInt(currentRuns)});
          matchstats.set({'recquiredRuns' :  recquiredRuns - parseInt(currentRuns)});
        }

        matchstats.set({'balls' : balls});
        console.log(matchstats.toJSON())
      },

      endMatch : function(){
        var recquiredRuns = matchstats.get('recquiredRuns');
        var winningTeam = (recquiredRuns <= 0 ) ? matchstats.get('battingTeam') : matchstats.get('bowlingTeam');
        matchstats.set('winningTeam',winningTeam);
      }
    };

    var players = {
      updatePlayerInfo : function(resp){
        var playersInfo = resp;//JSON.parse(resp);
        var players = [];
        $.each(playersInfo, function(i,playerInfo){
          players[playerInfo.id] = {'id' : playerInfo.id, 'name' : playerInfo.name, 'score':0, 'balls':0,'playing':false}
        });
        matchstats.set({'players':players})
      },
      updatePlayerScores : function(id,runs) {
        var currentPlayer = matchstats.get('players')[id];
        currentPlayer.score = (runs != "out") ? currentPlayer.score + runs : currentPlayer.score;
        currentPlayer.playing = (runs != "out") ?  true : false;
        currentPlayer.balls ++;
        matchstats.set({'currentPlayer': currentPlayer});
        matchstats.set('players'[id],currentPlayer)
      }
    };
    return Match;
});
