# Expense Reimbursement System 

## Project Description
The Expense Reimbursement System manages the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

### Language / Technologies: 

* Java 8  
* Spring Boot 
* Spring Data
* Spring MVC
* Maven
* Lombok
* RestTemplate

### Persistence: 

* MySQL DB 
* GCP Cloud SQL

### Testing: 

* JUnit 
* Mockito 
* Insomnia

### Deployment: 

* Docker 
* Docker Compose

### Version Control: 

* Git/Github

## Features

List of Features
* Reimbursement API that managers users, the creation of reimbursements, and status updates 
* The Reimbursement API also communicates with the Email API notifying it of the status change and requesting for an email to be sent out, using RestTemplate
* Email API that, upon being requested, sends emails to user of their reimbursement creation / status change

To-do List:
* User login and session management

## Contributors

* Zufishan Ali

## License

This project uses the following license: [BSD](LICENSE).
