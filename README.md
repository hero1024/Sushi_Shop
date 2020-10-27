# Sushi_Shop
Backend Developer Interview Code Challenge<br>
As we strive to find the best of the best to join our team, we believe that one of the most <br>
effective ways to assess a person’s technical skills is to put them to practice.<br>
Code Challenge: Sushi Shop<br>
Overview:<br>
The challenge is to build a simulated sushi shop server-side program that takes orders from the <br>
customers, processes the orders in parallel, shows and updates the order status. <br>
The program should be built using the following frameworks/libraries/tools: • Spring boot<br>
• H2 database<br>
• Maven/Gradle<br>
• Any other libraries you feel you may need<br>
Requirements:<br>
1. The server should start on port 9000<br>
2. Use an embedded in-memory H2 database with the following SQL to initialize the <br>
database when the server starts:<br>
DROP TABLE IF EXISTS sushi;<br>
 <br>
CREATE TABLE sushi (<br>
 id INT AUTO_INCREMENT PRIMARY KEY,<br>
 name VARCHAR(30),<br>
 time_to_make INT DEFAULT NULL<br>
);<br>
DROP TABLE IF EXISTS sushi_order;<br>
CREATE TABLE sushi_order (<br>
 id INT AUTO_INCREMENT PRIMARY KEY,<br>
 status_id INT NOT NULL,<br>
 sushi_id INT NOT NULL,<br>
 createdAt TIMESTAMP NOT NULL default CURRENT_TIMESTAMP<br>
);<br>
DROP TABLE IF EXISTS status;<br>
CREATE TABLE status (<br>
 id INT AUTO_INCREMENT PRIMARY KEY,<br>
 name VARCHAR(30) NOT NULL<br>
);<br>
INSERT INTO sushi (name, time_to_make) VALUES<br>
('California Roll', 30),<br>
('Kamikaze Roll', 40),<br>
('Dragon Eye', 50);<br>
INSERT INTO status (name) VALUES<br>
('created'),<br>
('in-progress'),<br>
('paused'),<br>
('finished'),<br>
('cancelled');<br>
3. REST APIs:<br>
a. All the response bodies should include the following fields:<br>
i. code: 0 for success, and any other integers for failures<br>
ii. msg: A meaningful message represents the result <br>
b. Build the following REST APIs that accepts and returns JSON data:<br>
i. Submitting an order: POST /api/orders:<br>
§ Request body: {<br>
 "sushi_name": "California Roll"<br>
} § Response:<br>
- Code: 200<br>
- Body<br>
{<br>
 "order": {<br>
 "id": 10,<br>
 "statusId": 1,<br>
 "sushiId": 1,<br>
 "createdAt": 1582643059540<br>
 },<br>
 "code": 0,<br>
 "msg": "Order submitted"<br>
} § Only three orders can be processed at the same time<br>
§ When an order is submitted, the order record should be saved into <br>
database with status set to “created”<br>
§ When a chef is ready to process an order, the corresponding order <br>
record should be updated in the database with status set to “in￾progress”<br>
§ The field “time_to_make” from sushi table represents how long it <br>
takes to make a specific kind of sushi. For example, a California Roll <br>
takes 30 seconds to make, thus a chef will be occupied for 30 seconds <br>
to finish making the sushi<br>
§ When an order is completed, the corresponding order record should <br>
be updated in the database with status set to “finished”<br>
ii. Cancelling an order: PUT /api/orders/cancel/{order_id}<br>
§ Path parameter order_id <br>
§ Response:<br>
- Code: 200<br>
- Body:<br>
{<br>
"code": 0,<br>
"msg": "Order cancelled"<br>
} § The chef who is working on making the ordered sushi should stop <br>
upon cancellation request<br>
§ The order should be updated in the database with status set to <br>
“cancelled”<br>
iii. Pausing an order: PUT /api/orders/pause/{order_id}<br>
§ Path parameter order_id <br>
§ Response:<br>
- Code: 200<br>
- Body:<br>
{<br>
"code": 0,<br>
"msg": "Order paused"<br>
} § When an order needs to be paused, the chef must pause the progress <br>
of making the sushi until the order is resumed<br>
§ The order should be updated in the database with status set to <br>
“paused”<br>
iv. Resuming an order: PUT /api/orders/resume/{order_id}<br>
§ Path parameter order_id <br>
§ Response:<br>
- Code: 200<br>
- Body:<br>
{<br>
"code": 0,<br>
"msg": "Order resumed"<br>
} § When a resuming order request is received, the chef should continue <br>
to process the order with high priority. A resumed order should only <br>
be processed base on the remaining processing time. For example, an <br>
order of California Roll is paused after 20 seconds since the order <br>
became in-progress, then it should take 10 more seconds to finish<br>
once resumed. <br>
§ The order should be updated in the database with status set to “in￾progress”<br>
v. Displaying all orders: GET /api/orders/status<br>
§ Response:<br>
- Code: 200<br>
- Body:<br>
{<br>
"in-progress": [<br>
{<br>
"orderId": 4,<br>
"timeSpent": 23<br>
},<br>
{<br>
"orderId": 5,<br>
"timeSpent": 21<br>
}<br>
],<br>
"pending": [<br>
{<br>
"orderId": 6,<br>
"timeSpent": 0<br>
}<br>
],<br>
"paused": [<br>
{<br>
"orderId": 2,<br>
"timeSpent": 5<br>
}<br>
],<br>
"cancelled": [<br>
{<br>
"orderId": 3,<br>
"timeSpent": 6<br>
}<br>
],<br>
"completed": [<br>
{<br>
"orderId": 1,<br>
"timeSpent": 30<br>
} ] }<br>
Evaluation:<br>
1. Code completion and correctness <br>
2. Code brevity and clarity<br>
3. Code efficiency and readability<br>
