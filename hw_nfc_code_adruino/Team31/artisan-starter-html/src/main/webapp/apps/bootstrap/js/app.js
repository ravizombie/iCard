/**
 * App
 */

var App = (function (App, $) {

    App.global = {}

    /*
     * Function to generate global configuration object - Apptains integrity of original for copying
     * objects - the data structures configured by this App
     * tabs - the top level tabs for the App
     */

    var globalFactory = function (key) {
        var global = {
            info:{
                timestart:new Date().getTime(), 
                model_name:"", 
                serial_number:"", 
                assetId:"",
                validJson:"", 
                holder:"",
                newRow:true
            }, 
            dataItems:[], 
            helper:{
                refreshToggle:true
            }
        }
        if (!key) {
            return global
        }
        else {
            return global[key]
        }
    }

    /**
     * App.init()
     */
    App.init = function () {
        // put initial processing here
        App.global = globalFactory()             
        App.Fetch.callScriptoFetchData()
    }

    /***************************************************************************
     * App.resetGlobal()
     *
     * Reset the global variable
     */
    App.resetGlobal = function (key) {
        if (key) {
            return App.global = globalFactory(key)
        }
        else return App.global = globalFactory()
    }
    

    



    return App;
}(App || {}, jQuery));
