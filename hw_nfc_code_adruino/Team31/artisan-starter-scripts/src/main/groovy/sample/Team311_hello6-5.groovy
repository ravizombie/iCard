import static com.axeda.sdk.v2.dsl.Bridges.*
import com.axeda.services.v2.Model
import com.axeda.services.v2.ModelCriteria

/****************
 * Hello 6.5!
 *
 * 1_hello6-5.groovy
 *
 * You'll notice a big difference from the old SDK, as we're using a modelBridge for our model creation.
 *
 * All objects now use a bridge to create, read, and update.
 *
 * Here we create a model using a map.  The parameters required by the map are "name" and "modelNumber."
 *
 * The parameter modelNumber is the unique identifier for the model.
 *
 * Before creating the model, we use a find with Model Criteria to check if the model exists.  Model Criteria operates
 * on multiple fields and returns a Find Model Result.
 *
 * If you perform the dump method on the Find Model Result, you will get a complete description of its properties.
 *
 * Sample Output:
 INFO: com.axeda.services.v2.FindModelResult
 INFO: <com.axeda.services.v2.FindModelResult@1b13c62 criteria=com.axeda.services.v2.ModelCriteria@f36ab3 models=[] totalCount=0>
 INFO: com.axeda.services.v2.ExecutionResult
 INFO: <com.axeda.services.v2.ExecutionResult@1639628 successful=true totalCount=1 succeeded=[com.axeda.services.v2.SuccessfulOperation@19725e3] failures=[]>
 DEBUG: ...Script complete.  Return value: [Content-Type:application/text, Content:FooModel]
 *
 * **************/

// check to see find the model using
ModelCriteria modelCriteria = new ModelCriteria()

modelCriteria.modelNumber = "FooModel"

// modelBridge is statically invoked from the Bridges import
def result = modelBridge.find(modelCriteria)

// the result of a find using Criteria is a Find Model Result
logger.info(result.class.name)
logger.info(result.dump())

Model model

if (result.totalCount == 0){
    // instantiate a new model using a map - not persisted to database until .create is called
    model = new Model([name:"FooModel",modelNumber:"FooModel"])
    def executionResult = modelBridge.create(model)
    logger.info(executionResult.class.name)
    logger.info(executionResult.dump())
    result = model


}
else {
// the bridge can also be used with a simple .find and a string to return an object instead of an Execution Result
 result = modelBridge.find("FooModel")
 // this is a Model
 logger.info(result.class.name)

}
 def response = result.name
return ['Content-Type': 'application/text', 'Content': response]
