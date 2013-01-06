import com.axeda.services.v2.Model

/****************
 * Hello Implicit Verbs!
 *
 * 2_helloImplicitVerbs.groovy
 *
 * Scripts now start off with CRUD access to domain objects with Implicit Verbs.
 * These verbs - create, find, update and delete are available to the script
 * without being imported.  They are implicitly available, just like implicit
 * objects are in SDK v1.
 *
 * Unfortunately, you still have to import the object (such as the Model in this case)
 * in order to create it.  Not that much typing, is it?
 *
 * **************/

 def response = ""

 try {
     def result = find.model("FooModel")
  //   logger.info(result.class.name)
    result.description = "description"
     update.model(result)

     def model_name = result.name

     if (result){
         delete.model(result)
     }
     create.model(new Model([name: model_name, modelNumber: model_name]))

     result = find.model(model_name)
     response = result.name

 }
 catch (Exception e){

response = e.localizedMessage
}

return ['Content-Type': 'text/plain', 'Content': response]

