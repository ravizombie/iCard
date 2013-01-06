/**
 * Regions are hierarchical in nature with no limit to depth, although two to
 * three levels of hierarchy should suffice for most applications.
 * 
 * This example starts off by demonstrating the parent/child relationships of Regions
 * and their possible association with Locations.
 * 
 * Beginning with with SDKv2, a Location must be associated with an Organization
 * and vice-versa, so in order to demonstrate associating a Location with a Region,
 * an Organization and its corresponding Location are created first.
 */
import static com.axeda.sdk.v2.dsl.Bridges.*

import com.axeda.sdk.v2.transformers.RegionTransformer;
import com.axeda.services.v2.ExecutionResult;
import com.axeda.services.v2.FindRegionResult;
import com.axeda.services.v2.Location;
import com.axeda.services.v2.LocationCriteria;
import com.axeda.services.v2.Organization;
import com.axeda.services.v2.OrganizationCriteria;
import com.axeda.services.v2.Region;
import com.axeda.services.v2.RegionCriteria;
import com.axeda.services.v2.RegionReference;

def response = "success"

def didItWork = { it ->
  assert it.successful, it.getFailures()[0].getMessage().replaceAll(/\$\{target\}/, it.getFailures()[0].getSourceOfFailure())
}

try {
  // CREATE a parent Region with two children
  def unitedStates = "United States"
  def northEastern = "Northeastern"
  def southern = "Southern"
  
  Region unitedStatesRegion = new Region(name:unitedStates)
  Region northEasternRegion = new Region(name:northEastern, parent:unitedStatesRegion)
  Region southernRegion = new Region(name:southern, parent:unitedStatesRegion)
  
  ExecutionResult create = regionBridge.create(unitedStatesRegion)
  didItWork(create)
  def unitedStatesRegionId = create.succeeded[0].id
  
  create = regionBridge.create(northEasternRegion)
  didItWork(create)
  def northEasternRegionId = create.succeeded[0].id
  
  create = regionBridge.create(southernRegion)
  didItWork(create)
  def southernRegionId = create.succeeded[0].id

  // READ and verify parent / child relationship
  Region unitedStatesById = regionBridge.findById(unitedStatesRegionId)
  Region northEasternById = regionBridge.findById(northEasternRegionId)
  Region southernById = regionBridge.findById(southernRegionId)

  // Note that the parent region got associated with its children by the platform implicitly when the children were created.
  assert unitedStatesById.children[0].id == northEasternById.id, "United States Region should know its child is Northeastern Region"
  assert unitedStatesById.children[1].id == southernById.id, "United States Region should know its child is Southern Region"
  assert northEasternById.parent.id == unitedStatesById.id, "Northeastern Region should know its parent is United States Region"
  assert southernById.parent.id == unitedStatesById.id, "Southern Region should know its parent is United States Region"
    
  // Now let's UPDATE a region, adding a description
  unitedStatesById.description = "A federal constitutional republic comprising fifty states and a federal district."
  regionBridge.update(unitedStatesById)
  Region unitedStatesByName = regionBridge.find(unitedStatesById.name) // Find (READ) by name this time.  Because alternateId for Region is name, this works.
  assert unitedStatesByName.description == unitedStatesById.description, "Description did not get updated"

  // Regions can be include specific locations, but Locations are tied to Organizations and vice versa and must be created as a composite object.
  // So we have to create an Organization with a Location first.
  Location bostonCityHall = new Location(name:"Boston City Hall", region:northEasternById, line1:"One City Hall Square", city:"Boston", state:"MA", postalCode:"02201", country:"USA")
  Organization massGov = new Organization(name:"Massachusetts Government", description:"www.mass.gov")
  massGov.locations.add(bostonCityHall)
  create = organizationBridge.create(massGov)
  didItWork(create)
  
  // Verify the Organization with Location can be READ.
  Organization massGovByName = organizationBridge.find("Massachusetts Government")
  Location bostonByName = locationBridge.findOne(new LocationCriteria(name:"Boston City Hall"))
  assert massGovByName != null, "Could not find Massachusetts Government by name"
  assert bostonByName != null, "Could not find Boston City Hall by name"
  assert massGovByName.locations[0].id == bostonByName.id, "Massachusetts Government Organization does not have the Boston City Hall Location"
  
  // Finally, add the Location of the newly created Organization to a Region
  Region northEastByCriteria = regionBridge.findOne(new RegionCriteria(name:northEastern))  // Another way to find (READ) using RegionCriteria
  northEastByCriteria.locations.add(bostonByName)
  regionBridge.update(northEastByCriteria)
  northEastByCriteria = regionBridge.findById(northEastByCriteria.id)
  assert northEastByCriteria.locations.get(0).id == bostonByName.id, "Boston City Hall was not successfully added to the Northeast region"
    
  // Now let's clean up with some DELETEs
  regionBridge.delete(unitedStatesById)
  regionBridge.delete(southernById)
  regionBridge.delete(northEasternById)
  organizationBridge.delete(massGovByName)
} catch (Exception e) {
  response = e.getLocalizedMessage()
} catch (AssertionError e) {
  response = e.getLocalizedMessage()
}

return response
