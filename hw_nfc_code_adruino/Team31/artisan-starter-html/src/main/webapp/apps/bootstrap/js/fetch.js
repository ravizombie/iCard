/**
 * App.Fetch
 *
 * Module for fetching pours.
 */
var App = App || {};
App.Fetch = (function (Fetch, $) {

    /***************************************************************************
     * All of your event handlers go here.
     * We generally have ~1 per page to start.
     */

    /* Groovy Log Events */
    // The "refresh" button lives on our Groovy Logs page
    $('#refresh').die().live('click',function(){
        Fetch.callScriptoFetchData()
    })
    // the accordion handler for the Groovy Logs page
    $('.title span').die().live('click',function(){
        var obj = this.parentNode.parentNode
        var indents = $('.indent',obj)
            indents.toggle()
            if (this.innerHTML == "+"){
                this.innerHTML = "-"
            }
            else{
                this.innerHTML = "+"
            }
    })

    /* SMS Events */
    // Just one event, the send button
    $('#send').die().live('click', function () {
        if ($('#target').val() == "") {
            alert('please enter a valid MSISDN')
            return false
        }
        else if ($('#message').val() == "") {
            alert('please enter a valid Message')
            return false
        }
        Fetch.callScriptoFetchSMSData()
    })

    /* YourApp Events */
    $('#getDeviceData').die().live('click', function () {
        Fetch.callScriptoFetchDeviceData()
    })

    /***************************************************************************
     * Fetch.callScriptoFetchData(model_name: string, serial_number: string)
     *
     * Calls the Groovy scripts, then Fetch.setPours
     */
    Fetch.callScriptoFetchData = function () {
        axeda.callScripto("GET", "Team31TailLog", {}, 2,
        function (json) {
            Fetch.processJson(json[0]) 
        },
        {
            localstoreoff:"yes", 
            contentType: "application/json; charset=utf-8"
        })
    }

    Fetch.processJson = function(json){
       var list = ""
       var data = ""
       for (var i in json){
           list += '<div class="object">'
           list += '<div class="title">'+i+'<span style="toggle">+</span></div>'

           data = Fetch.getData(json[i])
           list += data.replace(/\n/g, '<br />');
           list += '</div>'
       }
       $('#container').html(list)
    }
    
    Fetch.getData = function(data){
        var arr = ""
        var swit = 1
        for(i in data){
            var clas = (swit == 1)?"odd":"even"
            arr += '<div class="indent '+clas+'">'+data[i]+'</div>'
            swit = swit*-1
        }
        return arr
    }


/**
 * SMS Fetch Scripts
 **/

    Fetch.callScriptoFetchSMSData = function () {
            var target = $('#target').val()
            var mesg = $('#message').val()
            axeda.callScripto("GET", "Team31SendSMS",
            {
                target: target,
                message: mesg
            }, 2,
            function (json) {
                console.log(json)
                Fetch.processSMSJson(json)
            },
            {
                localstoreoff:"yes",
                contentType: "application/json; charset=utf-8"
            })
        }


    Fetch.processSMSJson = function (json) {
        console.log(json)
        var myValue = Fetch.readProperty(json, ['Error']);
        if (typeof(myValue) !== 'undefined') {
            alert(json["Error"])
            return false
        }
        else {
            var text = ""
            text += "Id : " + json["Id"]
            text += "<br/>"
            text += "Resource Url : " + json["ResourceReference"]["ResourceUrl"]
            $("#container").html(text)
        }
    }

    Fetch.readProperty = function(json, properties) {
      // Breaks if properties isn't an array of at least 1 item
      if (properties.length == 1)
        return json[properties[0]];
      else {
        var property = properties.shift();
        if (typeof(json[property]) !== "undefined")
          return readProperty(json[property], properties);
        else
          return; // returns undefined
      }
    }

/**
 * YourApp Fetch Scripts
 **/

    Fetch.callScriptoFetchDeviceData = function () {
            console.log("Inside callScriptoFetchDeviceData")
            axeda.callScripto("GET", "Team31YourApp", {}, 2,
            function (json) {
                console.log(json)
                Fetch.processDeviceJson(json)
            },
            {
                localstoreoff:"yes",
                contentType: "application/json; charset=utf-8"
            })
    }

    Fetch.processDeviceJson = function (json) {
        console.log(json)
        var myValue = Fetch.readProperty(json, ['Exception']);
        if (typeof(myValue) !== 'undefined') {
            alert(json['Exception'])
            return false
        }
        else {
            var serialNumber = json["Device"]["serialNumber"]
            $("#serialNumber").html(serialNumber)
            var modelNumber = json["Device"]["modelNumber"]
            $("#modelNumber").html(modelNumber)
            var lastContact = json["Device"]["lastContact"]
            $("#lastContact").html(lastContact)
            var iccid = json["Device"]["iccid"]
            $("#iccid").html(iccid)
        }
    }


    return Fetch;
}(App.Fetch || {}, jQuery));
