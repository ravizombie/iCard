package sample.bridges

import com.axeda.sdk.v2.dsl.Bridges

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 9/27/12
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */

def testCredential = Bridges.externalCredentialBridge.find("WebinarCred")

def response = new StringBuilder()

response.append "Name: ${testCredential.username}, Pass: ${testCredential.password}, Custom 1: ${testCredential.getParameters().get(0).name}"

return ["Content-Type":"application/text","Content":response.toString()]
