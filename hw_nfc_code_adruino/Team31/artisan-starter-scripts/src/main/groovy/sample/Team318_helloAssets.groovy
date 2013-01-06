import static com.axeda.sdk.v2.dsl.Bridges.*
import com.axeda.services.v2.Asset
import com.axeda.services.v2.AssetCriteria
import com.axeda.services.v2.Model
import com.axeda.services.v2.ModelCriteria
import com.axeda.sdk.v2.bridge.AssetConfigurationBridge
import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.services.v2.AssetConfigurationCriteria
import com.axeda.services.v2.ConfigurationItemCriteria
import static com.axeda.sdk.v2.dsl.FindImpl

/****************
 * Hello Assets!
 *
 * 8_helloAssets.groovy
 *
 * Here we use the model we created in 1_hello6-5 to create an Asset.
 *
 * The individual things we are concerned with are now called Assets, not Devices.
 *
 * You might notice that asset.model has a property of detail, not name.  This is because
 * asset.model returns a ModelReference, not a Model.  In many cases all that is needed from
 * the model of an Asset is the name or some other trivial data, as opposed to situations where
 * the actual object is needed.  For this reason, a shallow copy of the model is provided as a
 * ModelReference, which contains the systemId and detail as the name.
 *
 * You can find out more about any object by calling its dump() method, which prints out all its properties.
 *
 * **************/

def response = ""

ModelCriteria crit = new ModelCriteria()
crit.modelNumber = "Fo*"
def modelresults = modelBridge.find(crit)

Model model

if (modelresults.totalCount == 0){
    throw new Exception("No Model with that Name")
} else {
    model = modelresults.models[0]
}

// model = modelBridge.find("FooModel")  // this would have worked also
def asset = new Asset([name:"BarAsset",serialNumber:"BarAsset",model:model])
def result = assetBridge.create(asset)

response = "name is " + asset.serialNumber + ", model is " + asset.model

AssetConfigurationBridge abridge = Bridges.assetConfigurationBridge
AssetConfigurationCriteria acc = new AssetConfigurationCriteria()
ConfigurationItemCriteria cic = new ConfigurationItemCriteria()


find {}.

abridge.find()

// prints out all the properties of ModelReference
logger.info(asset.model.dump())

return ['Content-Type': 'application/text', 'Content': response]
