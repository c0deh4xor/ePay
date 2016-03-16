### Project Members

 * Ahmed Abouzeid
 * Reza Farhadi
 * Daniel Magni
 * Frederick Mfinanga
 * Maziar Mohammad-Shahi

### Project Description

EriPay is a project to integrate online payments with NFC/RFID cards. The goal is to provide a payment solution integrated with NFC/RFID cards that is usually used within any workplace. The card could be recharged and/or the balance could be deducted from employee's paycheck later.

The project is divided into three parts:

 * Front-end: Using Angular 2, we built a simple frontend menu of items and checkout page.
 * Back-end: Using Spring Boot and MongoDB, we built a gradle project backend that integrates with PayPal APIs and processes payments of items.
 * RFID/NFC-Backend: This is the last piece of the puzzle, which is a Maven Project that will provide the front-end with the card information through a socket connection.

The connection between the front-end and the back-end is done using RESTful Web Services.

### Procedure

 * Go to the RFID/NFC folder, mvn clean install and run the generated Jar File
 * The back-end folders are inside the eriPay directory, so you can import this gradle project and run the springboot application
 * Go to the front-end folder, npm install then npm start to run the front-end
