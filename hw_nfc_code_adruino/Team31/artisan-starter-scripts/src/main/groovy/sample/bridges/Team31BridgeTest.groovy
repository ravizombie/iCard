package sample.bridges

import static com.axeda.sdk.v2.dsl.Bridges.*
import com.axeda.sdk.v2.dsl.*

import com.axeda.services.v2.Model
import com.axeda.services.v2.ModelCriteria
import com.axeda.services.v2.ModelType
import com.axeda.services.v2.FindModelResult
import com.axeda.services.v2.ExecutionResult
import com.axeda.services.v2.ModelReference

def response = new StringBuilder()
def serialNumber = "TestConfigDevice2"
def model = new ModelReference(id: "TestConfig")

try {

    def asset = Bridges.assetBridge.find("TestConfig||${serialNumber}")

    if (asset) {
        Bridges.assetConfigurationBridge.appendConfigurationItems()
    } else {
        response.append "Asset not found"
    }


} catch (Exception e) {
    response.append "Exception: ${e.message}\n"
}

foundModel = find.model modelNum
response.append "FirstFind: ${foundModel?.id}\n"

foundModel = null

foundModel = Bridges.modelBridge.find modelNum
response.append "SecondFind: ${foundModel?.id}\n"

foundModel = null

ModelCriteria criteria = new ModelCriteria(modelNumber: modelNum)
FindModelResult findResult = Bridges.modelBridge.find criteria

response.append "ThirdFind: ${findResult?.models?.get(0)?.id}\n"

return ['Content-Type': 'application/text', 'Content': response.toString()]
