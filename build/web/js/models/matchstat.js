define([
    'jquery',
    'backbone',
], function($,  Backbone){

  var MatchStat = Backbone.Model.extend({
    defaults : {
      balls       : 0,
      score       : 0,
      wickets     : 0,
      players     : [],
      currentPlayer : undefined,
      recquiredRuns : 40,
      overs       : 0,
      totalOvers  : 4,
      teams       : [{'name':'Lengaburu','score':0,'logo':'img/loyal.png'},{'name':'Enchai','score':0,'logo':'img/queens.png'}],
      battingTeam : undefined,
      bowlingTeam : undefined,
      winningTeam : undefined,
    },
  });

  return MatchStat;
});
