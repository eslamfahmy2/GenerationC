# GenerationC
demo base app using android best practices in, clean articulate, MVVM, Compose , Testing , Navigator , Hilt , Navigation , Single Activity , Material Theme 

# Overview

 use case :  search int list -cars- thats load data from local file data source that

# Technologies

The project attempt to adapt best practices in android development flowing SOILD principles wih,
t lates modern Android tools and frameworks such as Kotlin, Jetpack Compose, Coroutines, Flow, Hilt,
Clean code, Navigation ,
employs automatic state and error handling and wrappers to handle calls

## Testing

Junit, and Illustration integration with Espresso Testing

# Project Structure

The project uses a MVVM architecture, data is obtained form local data source, 
as the needed data is retrieved fresh from the file system,that contained in separate Data layer,
Business logic and use cases are placed in a separate Domain,
View Models are used to communicate with the UI layer,
which is primarily written with the Compose framework.

#Structure

adapting a Clean architecture. All UI related code can be found in the ".presentation"
package and is subdivided into packages according to view
".domain" package contains systems models , repositories, mappers..
".data" pancake contains implementation of data source "Retorfit" and date transfer objects. 

adapting MVVM as main beavioual design pattern , align with singlton object for as creations desin 

#Testing 

Junit test for local test
ex- Testing in local repository
Instruments test for testing Compose view and android related, 
ex- Testing Main Screen, Testing Details Sceen


![seach](https://github.com/eslamfahmy2/GenerationC/assets/74387512/b8dca797-622e-40f3-9df2-ed9f11f94cad)


![details](https://github.com/eslamfahmy2/GenerationC/assets/74387512/fd9a7cc0-74c9-4c03-9f88-8278cdbbe9bd)

