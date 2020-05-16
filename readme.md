This is a sample backend application named **BisKit** for a simple e-commerce website.
It is built using Spring Boot framework and uses MySql as a relational
database.
This project uses JWT based authentication system and so requires the 
bearer token (Bearer jwt_token) in the **Authorization** header for 
each request made. Only authentication and sign up end points don't 
require the token and are open to all. For any other request, the user
must be logged in.
The application is configured with role based access to the end points.
For the sake of simplicity, currently the system has only two roles :
1) ROLE_ADMIN
2) ROLE_CUSTOMER

which are stored as string but will be better if some _id_ is used 
for this purpose.

The database has mainly 4 tables :
1) user
2) items
3) orders
4) order_items

**ADMIN** can only access _/admin_ end points where he/she can perform
CRUD operations on the items in the store.
**CUSTOMER** can only access _/customer_ end points where he/she can
view the items in the store, create single or bulk order, view their
respective orders, cancel order and cancel any particular item of their order.
Both, after ordering and cancellation, the stock count gets updated
based on the units ordered. 

For running the application on your local machine, clone this
project. Open the project, preferrably in IntelliJ IDEA (as this was
used to develop this application), as a **gradle** project.
For setting up the database, create a database named _biskit_
on your MySql server and import the _.sql_ file from the _db_ folder
inside the **resources** package for creating the tables and getting
them populated with some data. Now run the application from
**BisKitApplication.java** file. For every account in the **user**
table, the password is _123456_.
The project is also integrated with **Swagger** for api documentation
which can be accessed by hitting 
_http://localhost:8080/swagger-ui.html_
from your browser once the project start to run successfully.
You can also get the postman collection of the api's by importing below
link in postman 
_https://www.getpostman.com/collections/9305640e2424f5df133e_

**NOTE : ** Make sure to set the database credentials in the 
_application.properties_ file according to your local credentials.

Please reach out to me on _asperanza23@gmail.com_ if you face any 
issue in setting up the project or you have any questions.
