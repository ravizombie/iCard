package sample.bridges

import com.axeda.sdk.v2.dsl.Bridges

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 9/27/12
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */

def startString = "Here is our String!!! laskjdlaksjdlaksd"

def response = Bridges.customObjectBridge.execute("CustomObjectSyncCall",["inString":startString])

return ["Content-Type":"application/text","Content":response]
