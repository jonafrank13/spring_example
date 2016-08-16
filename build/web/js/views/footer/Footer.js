/*
FileName : Footer.js
Function : Renders the footer of the Page 

*/

define([

    'jquery',
    'underscore',
    'backbone',
    'text!templates/footer/footer.html'

], function($,_, Backbone, FooterTemplate){

    var View_footer = Backbone.View.extend({
        el: $("footer"),

        render: function(){
            var tFooterTemplate = _.template(FooterTemplate);
            this.$el.html(tFooterTemplate);
        }
    });

    return View_footer;
});
