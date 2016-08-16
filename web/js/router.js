// Filename: router.js
define([
    'jquery',
    'backbone',
    'views/home/Home',
    'views/footer/Footer',
    'views/header/Header',
], function($, Backbone , Home, Footer, Header) {

    var AppRouter = Backbone.Router.extend({
        routes: {
            'index': 'showIndex',
            'register': 'showRegister',
            '*actions': 'defaultAction'
        }
    });

    var initialize = function(){

        var app_router = new AppRouter;

        app_router.on('route:defaultAction', function (actions) {
            var tHome = new Home();
            tHome.render();
            var tHeader = new Header();
            tHeader.render();
            var tFooter = new Footer();
            tFooter.render();
        });

        app_router.on('route:showIndex', function (actions){
            var tHome = new Home();
            tHome.render();
            var tHeader = new Header();
            tHeader.render();
            var tFooter = new Footer();
            tFooter.render();
        });



        Backbone.history.start();

    };
    return {
        initialize: initialize
    };
});
