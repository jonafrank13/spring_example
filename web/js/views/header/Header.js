define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/header/header.html'
], function($,_, Backbone, HeaderTemplate){

    var View_header = Backbone.View.extend({
        el: $("header"),

        render: function(){
            var tHeaderTemplate = _.template(HeaderTemplate);
            this.$el.html(tHeaderTemplate);        
        }
    });

    return View_header;
});
