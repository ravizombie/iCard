function callScripto2(params, runme) {


   // var sessionId = $.cookie("axedasession");
    //var script = '/services/v1/rest/Scripto/execute/ExtendedMapService';
    //if(sessionId !=null){
    //    script = script+"?sessionid="+sessionId;
    //}
    //$.get(script, params, function (r) {
        //runme(r);
    //});

    axeda.callScripto("GET",'Team31ExtendedMapService',params,1,runme,false);

}


function saveMapValue() {
    var map = $("select#color").val();
    var key = $("input#newkey").val();
    var value = $("input#newvalue").val();

    callScripto2({action:'addValue', map:map, key:key, value:value}, function (r) {
        getMapData(map)

    });


}

function updateMapValue() {
    var map = $("select#color").val();
    var key = $("select#key").val();
    var value = $("input#value").val();

    callScripto2({action:'addValue', map:map, key:key, value:value}, function (r) {
        getMapData(map)

    });


}

function addMap() {
    var map = $("input#newMapName").val();
    var key = $("input#newMapAndkey").val();
    var value = $("input#newMapAndValue").val();

    callScripto2({action:'addValue', map:map, key:key, value:value}, function (r) {
        getMaps()

    });

}

function deleteMap() {
    var map = $("select#color").val();


    callScripto2({action:'deleteMap', map:map}, function (r) {
        getMaps()


    });


}

function deleteMapValue() {
    var key = $("select#key").val();
    var map = $("select#color").val();


    callScripto2({action:'deleteValue', map:map, key:key}, function (r) {
        getMapData(map)

    });


}


function getMaps() {

    callScripto2({action:'getMaps'}, function (r) {
        $("select#color").empty();
        var options = '<option value="-1">Select a Map</option>';

        $.each(r, function (k, v) {
            options += '<option value="' + v.label + '">' + v.label + '</option>';
        });
        $("select#color").append(options);
        $("select#color").trigger("liszt:updated");
        $("select#color").change(function () {

            var selected = $("select#color").find('option').eq(this.selectedIndex);
            getMapData(selected.val());
        });

    });

}
function getMapData(selected) {
    $("select#key").attr("disabled", "disabled");

    callScripto2({action:'getMap', map:selected}, function (r) {
        var options = '<option value="-1">Select a Key</option>';
        $.each(r[0], function (k, v) {
            options += '<option value="' + k + '">' + k + '</option>';
        });
        $("select#key").empty();

        $("select#key").append(options);

        $("select#key").change(function () {

            var key = $("select#key").find('option').eq(this.selectedIndex);
            getKeyData(selected, key.val());
        });
        $("select#key").removeAttr("disabled")
        $("select#key").trigger("liszt:updated");


    });
}

function getKeyData(selected, key) {
    callScripto2({action:'getMap', map:selected, value:key}, function (r) {
        $("input#value").attr("value", r[0][key]);

    });
}
