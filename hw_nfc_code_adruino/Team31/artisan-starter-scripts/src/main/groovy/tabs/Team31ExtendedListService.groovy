import com.axeda.sdk.v2.dsl.Bridges
import net.sf.json.JSONArray
import com.axeda.services.v2.ExtendedListCriteria
import com.axeda.services.v2.FindExtendedListResult

String action = parameters.action;
String list = parameters.list;
String value = parameters.value;

logger.debug(parameters)

def extendedListBridge = Bridges.extendedListBridge;

def returnval = null;
try {
    logger.info "Inside: Team31ExtendedListService"
    logger.info "Action: ${action}"
    switch (action) {

        case "getLists":
            ExtendedListCriteria elc = new ExtendedListCriteria()
            elc.name = "Team31_*"
            FindExtendedListResult resultList = extendedListBridge.find(elc);
            def alist = resultList.lists
            returnval = JSONArray.fromObject(alist).toString()
            break;

        case "getList":
            def aList = extendedListBridge.find(list);
            returnval = JSONArray.fromObject(aList.list).toString()
            break;

        case "deleteList":
            def extList = extendedListBridge.find(list);

            if (extList != null) {

                extendedListBridge.delete(extList);
            }

            returnval = JSONArray.fromObject([returnstatus: "success"]).toString()
            break;

        case "addValue":
            extendedListBridge.append(list, value);
            returnval = JSONArray.fromObject([returnstatus: "success"]).toString()
            break;

        case "deleteValue":
            def extList = extendedListBridge.find(list);

            if (extList.list.size() > 1) {
                extendedListBridge.remove(list, value)
                returnval = JSONArray.fromObject([returnstatus: "success"]).toString()
            } else {
                extendedListBridge.delete(extList);
                returnval = JSONArray.fromObject([returnstatus: "success"]).toString()
            }

            break;
    }
} catch (Exception e) {
    returnval = JSONArray.fromObject(e.getMessage()).toString()

}

return ['Content-Type': 'application/json', 'Content': returnval]
