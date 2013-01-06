import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.services.v2.ExtendedMapCriteria
import com.axeda.services.v2.FindExtendedMapResult
import net.sf.json.JSONArray
 import net.sf.json.JSONObject
 
String action = parameters.action;
String map = parameters.map;
String key = parameters.key;
String value = parameters.value;
 
logger.debug(parameters)
 
def extendedMapBridge = Bridges.extendedMapBridge;
 
def returnval = null;
try {
    logger.info "Inside: Team31ExtendedMapService"
    logger.info "Action: ${action}"
    switch (action) {
        case "getMaps":
            ExtendedMapCriteria elc = new ExtendedMapCriteria()
            elc.name = "Team31_*"
            FindExtendedMapResult resultList = extendedMapBridge.find(elc);
            def list = resultList.maps
 
            returnval =  JSONArray.fromObject(list).toString()
            break;
 
        case "getMap":
            def aMap = extendedMapBridge.find(map);
            def realMap = new LinkedHashMap<String,String>();
            aMap.map.each{
                realMap.put(it.name,it.value)
            }
            returnval = JSONArray.fromObject(realMap).toString()
            break;
 
        case "deleteMap":
            def extMap = extendedMapBridge.find(map);
 
            if (extMap!=null) {
 
                extendedMapBridge.delete(extMap);
            }
    
            returnval = JSONArray.fromObject([returnstatus:"success"]).toString()
            break;
 
        case "addValue":
            extendedMapBridge.append(map, key, value);
            returnval = JSONArray.fromObject([returnstatus:"success"]).toString()
            break;
 
        case "deleteValue":
            def extMap = extendedMapBridge.find(map);
 
            if (extMap.map.size() > 1) {
                extendedMapBridge.remove(map, key)
            returnval = JSONArray.fromObject([returnstatus:"success"]).toString()
            } else {
                extendedMapBridge.delete(extMap);
            returnval = JSONArray.fromObject([returnstatus:"success"]).toString()
            }
 
            break;
    }
} catch (Exception e) {
    returnval = JSONArray.fromObject(e.getMessage()).toString()
 
}
 
return ['Content-Type': 'application/json', 'Content': returnval]
