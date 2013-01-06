function callScripto(params, runme) {
    axeda.callScripto("GET",'Team31ExtendedListService',params,1,runme,false);
}


function saveListValue() {
    var list = $("select#listname").val();
    var value = $("input#newlistvalue").val();

    callScripto( {action:'addValue', list:list,  value:value}, function (r) {
        getListData(list)
    });
}



function addList() {
    var map = $("input#newListName").val();
    var value = $("input#newListAndValue").val();

    callScripto( {action:'addValue', list:map, value:value}, function (r) {
        getLists()

    });

}

function deleteList() {
    var map = $("select#listname").val();


    callScripto( {action:'deleteList', list:map}, function (r) {
        getLists()


    });


}

function deleteListValue() {
    var key = $("select#listvalue option:selected").text();
    var map = $("select#listname").val();


    callScripto({action:'deleteValue', list:map, value:key}, function (r) {
        getListData(map)

    });


}


function getLists() {





    callScripto({action:'getLists'}, function (r) {
        $("select#listname").empty();
        var options = '<option value="-1">Select a List</option>';

        $.each(r, function (k, v) {
            options += '<option value="' + v.label + '">' + v.label + '</option>';
        });
        $("select#listname").append(options);
        $("select#listname").change(function () {

            var selected = $("select#listname").find('option').eq(this.selectedIndex);
            getListData(selected.val());
        });

    });

}
function getListData(selected) {
    $("select#listvalue").attr("disabled", "disabled");

    callScripto({action:'getList', list:selected}, function (r) {
        var options = '<option value="-1">Select a Key</option>';
        $.each(r, function (k,v) {
            options += '<option value="' + k + '">' + v + '</option>';
        });
        $("select#listvalue").empty();

        $("select#listvalue").append(options);


        $("select#listvalue").removeAttr("disabled")
        $("select#listvalue").trigger("liszt:updated");


    });

}



