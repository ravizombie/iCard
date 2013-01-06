/**
 * AssetGroups are hierarchical in nature and contain {@link Asset}s.
 * 
 * An AssetGroup is implicitly created to correspond with any newly created {@link com.axeda.services.v2.Model}.
 * When a Model is deleted, its corresponding default AssetGroup is also deleted.
 * 
 * Here is an example of working with AssetGroups:
 */
import static com.axeda.sdk.v2.dsl.Bridges.*

import com.axeda.services.v2.AssetGroup;
import com.axeda.services.v2.AssetGroupCriteria
import com.axeda.services.v2.FindAssetGroupResult

def response = "success"

try {
  // First let's READ all the existing AssetGroups out of the Axeda Platform.
  FindAssetGroupResult existingAssetGroupsResult = assetGroupBridge.find(new AssetGroupCriteria())
  
  // For each AssetGroupReference, let's fetch the full AssetGroup object.
  existingAssetGroupsResult.assetGroups.each {
    AssetGroup assetGroup = assetGroupBridge.findById(it.id)
    
    if (assetGroup.parentAssetGroup == null) {
      logger.info("Found this AssetGroup: name: " + assetGroup.name + ", parent AssetGroup: none")
    }
    else {
      AssetGroup parentAssetGroup = assetGroupBridge.findById(assetGroup.parentAssetGroup.id)
      assert parentAssetGroup != null, "Parent AssetGroup not found with id: " + assetGroup.parentAssetGroup.id
      logger.info("Found this AssetGroup: name: " + assetGroup.name + ", parent AssetGroup: " + parentAssetGroup.name)
    }
  }

  // Now let's create our own AssetGroup
  def myAssetGroup = new AssetGroup(name:"My Asset Group")
  assetGroupBridge.create(myAssetGroup)
  
  // Let's retrieve the newly created AssetGroup by name
  def myAssetGroupFound = assetGroupBridge.findOne(new AssetGroupCriteria(name:"My Asset Group"))
  assert myAssetGroupFound != null
  assert myAssetGroupFound.parentAssetGroup != null
  assert myAssetGroupFound.parentAssetGroup.id != null
  
  // The default parent AssetGroup is Root.
  def myAssetGroupParentFound = assetGroupBridge.findById(myAssetGroupFound.parentAssetGroup.id)
  assert myAssetGroupParentFound.name == "Root Asset Group"

  // DELETE (clean up)
  assetGroupBridge.delete(myAssetGroupFound)
  
} catch(Exception e) {
  logger.error(e.getMessage())
  response = e.getLocalizedMessage()
} catch(AssertionError e) {
  logger.error(e.getMessage())
  response = e.getLocalizedMessage()
}

return ['Content-Type': 'application/text', 'Content': response]
