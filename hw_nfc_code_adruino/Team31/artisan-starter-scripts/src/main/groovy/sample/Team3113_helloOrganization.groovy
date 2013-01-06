/**
 * An Organization consists of a name, an optional description and at least one mandatory {@link Location}.
 * 
 * Example:
 */

import static com.axeda.sdk.v2.dsl.Bridges.*

import com.axeda.services.v2.ExecutionResult;
import com.axeda.services.v2.FindLocationResult;
import com.axeda.services.v2.FindOrganizationResult;
import com.axeda.services.v2.Location
import com.axeda.services.v2.LocationCriteria;
import com.axeda.services.v2.Organization;
import com.axeda.services.v2.OrganizationCriteria;

def didItWork = { it ->
  assert it.successful, it.getFailures()[0].getMessage().replaceAll(/\$\{target\}/, it.getFailures()[0].getSourceOfFailure())
}

def response = "success"

try {
  ExecutionResult result
  Organization acme = new Organization(name:"Acme Corporation", description:"Wile E. Coyote's preferred vendor.")
  Location acmeMonumentValley = new Location(name:"Acme Monument Valley", line1:"P.O. Box A1", city:"Monument Valley", state:"UT", postalCode:"84536")
  acme.locations.add(acmeMonumentValley) // Organization requires a Location
  
  Organization petco = new Organization(name:"Petco", description:"Roadrunner's preferred vendor.")
  Location petcoFlagstaff = new Location(name:"Petco Flagstaff", line1:"5047 East Marketplace Drive", city:"Flagstaff", state:"AZ", postalCode:"86004")
  petco.locations.add(petcoFlagstaff)
  
  // CREATE
  result = organizationBridge.create(acme)
  didItWork(result)
  result = organizationBridge.create(petco)
  didItWork(result)

  // READ (Using five different methods on the Organization Bridge)
  OrganizationCriteria orgCriteria = new OrganizationCriteria(name:"Acme Corporation")
  
  Organization oneAcmeByCriteria = organizationBridge.findOne(orgCriteria)
  assert oneAcmeByCriteria.name.equals("Acme Corporation")
  FindOrganizationResult acmeAmongOthersByCriteria = organizationBridge.find(orgCriteria)
  Organization acmeById = organizationBridge.findById(oneAcmeByCriteria.id)
  Organization acmeByAltId = organizationBridge.find(oneAcmeByCriteria.name) // Alternate Id of an Organization is the name.
  Organization petcoByAltId = organizationBridge.find(petco.name)
  List<Organization> listOfOrgs = organizationBridge.find(Arrays.asList(oneAcmeByCriteria.id, petcoByAltId.id))
  
  def acmeByListOfId = listOfOrgs[0]
  def petcoByListOfId = listOfOrgs[1]

  assert oneAcmeByCriteria.description.contains("Wile"), "Did not find Acme Corporation by name."
  assert oneAcmeByCriteria.id == acmeAmongOthersByCriteria.organizations[0].id
  assert oneAcmeByCriteria.id == acmeById.id
  assert oneAcmeByCriteria.id == acmeByAltId.id
  assert oneAcmeByCriteria.id == acmeByListOfId.id

  assert petcoByAltId.id == petcoByListOfId.id, "Petco ids should match."

  // UPDATE
  def oldDesc = acmeById.description
  acmeById.description = oldDesc + " CHANGED"
  organizationBridge.update(acmeById)
  acmeById = organizationBridge.findById(acmeById.id)
  assert acmeById.description.equals(oldDesc + " CHANGED")
  
  // Let's change the address on a Location while we are here.
  Location acmeLocation = locationBridge.findById(acmeById.locations[0].id)
  acmeLocation.line1 = "PO Box AA1"
  locationBridge.update(acmeLocation)
  acmeLocation = locationBridge.findById(acmeById.locations[0].id)
  assert acmeLocation.line1.equals("PO Box AA1")

  // DELETE
  organizationBridge.delete(acmeById)
  organizationBridge.delete(petcoByAltId)
}
catch(Exception e) {
  response = e.getLocalizedMessage()
}
catch(AssertionError e) {
  response = e.getLocalizedMessage()
}

return response
