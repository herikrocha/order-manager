This is a simple exercise, a simple order manager. You should develop an API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it.

Specification

The system should be able to provide the following features:

- (OK) create, read, update and delete and list all entities;

- (OK) when an order is created, it should try to satisfy it with the current stock.;

- (OK) when a stock movement is created, the system should try to attribute it to an order that isn't complete;

- when an order is complete, send a notification by email to the user that created it;

- trace the list of stock movements that were used to complete the order, and vice-versa;

- show current completion of each order;

- Write a log file with: orders completed, stock movements, email sent and errors.

Entities

- Item

            ++ ID (PK)
            > name
            ++ stockAmount

- StockMovement

            ++ ID (PK)
            > creationDate
            > Item (FK)
            > quantity
            > type (INPUT/OUTPUT)

- Order

            ++ ID (PK)
            > creationDate
            > Item (FK)
            > quantity
            > User (FK) (who created the order)
            ++ status 

- User

            ++ ID (PK)
            > name
            > email

Requirements:

- The API should make by java 8 with Spring Boot + Spring JPA or Jave EE + Hibernate, PostgreSQL, GIT, log4j (or other);

- You should provide instructions on how to run the project and how to call the routes;


## ENDPOINTS

### Items
- (OK) POST    /items      (name)
- (OK) PUT     /items      (id, name)
- (OK) GET     /items
- (OK) DELETE  /items/{id} (id)

### Users
- (OK) POST    /user      (name, email)
- (OK) PUT     /user      (id, name, email))
- (OK) GET     /user
- (OK) DELETE  /user/{id} (id)

### StockMovement
- (OK) POST    /stock      (itemId, quantity, type)  -> Try to attribute it to an order that isn't complete;
- (OK) PUT     /stock      (id, itemId, quantity, type)
- (OK) GET     /stock      
- GET     /stock/{orderId}  -> List stockMovements were used to complete the orderId     
- (OK) DELETE  /stock/{id} (id)

### Orders
- (OK) POST    /orders      (itemId, quantity, userId) -> Try to satisfy it with the current stock
- (OK) PUT     /orders/cancel/{orderId}      (id, status)) -> Only cancel Orders
- (OK) GET     /orders
- (OK) DELETE  /orders/{id} (id)


