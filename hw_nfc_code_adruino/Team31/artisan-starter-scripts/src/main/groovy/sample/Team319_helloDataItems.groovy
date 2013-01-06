import static com.axeda.sdk.v2.dsl.Bridges.*
import com.axeda.services.v2.Asset
import com.axeda.services.v2.AssetCriteria
import com.axeda.services.v2.CurrentDataItemValueCriteria
import com.axeda.services.v2.DataItem
c

/****************
 * Hello Data Items!
 *
 * 9_helloDataItems.groovy
 *
 *
 * **************/

def response = []

try {
  AssetCriteria assetCriteria = new AssetCriteria()

  assetCriteria.serialNumber = "BarAsset"

  def result = assetBridge.find(assetCriteria)

  def asset = result.totalCount > 0 ? result.assets[0] : assetBridge.find("BarAsset")

  CurrentDataItemValueCriteria criteria = new CurrentDataItemValueCriteria()

  if (asset){
    criteria.assetId = asset.id
  }

   List<DataItem> dItems = dataItemBridge.find criteria

logger.info(dItems.size())

response = [
        success: "succeeded"
    ]

}
catch (Exception e){

response = [error: e.localizedMessage]
}

return ['Content-Type': 'application/json', 'Content': JSONObject.fromObject(response).toString(2)]
