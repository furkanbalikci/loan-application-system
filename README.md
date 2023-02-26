# loan-application-system

## Introduction

This project is a loan application web service developed using Spring Security to provide secure registration, login, and loan application services. The loan application form includes validation for all inputs, and a loan status check can be performed using the customer's TC number and date of birth. Additionally, the customer section allows for creating, editing, and deleting customers.


## Tech Stack

**Client:** HTML5, CSS3, JavaScript , Bootstrap

**Server:** Java, Spring, Spring Security, MongoDB


## Features

### Registration, Login, and Logout
All pages, except the registration and login pages, are secured using Spring Security. Users can register by clicking on the "Register Here" link on the login page. After providing the necessary information, the user is saved to the database with a default "user" role. To return to the login page, users can click on the "Login Here" link. Once logged in, users can logout using the button located in the top-right corner of each page.
![Screenshot 2023-02-27 at 01 00 57](https://user-images.githubusercontent.com/46796424/221440458-4c83a470-c49a-4bcb-8531-a0547f2a3bbd.png)
![Screenshot 2023-02-27 at 01 01 15](https://user-images.githubusercontent.com/46796424/221440464-5d61b4a7-f934-406a-9d94-ef731a3dd472.png)


### Loan Application Form
All inputs are required, except for collateral. The TC number must be exactly 11 digits, and the email must be in the correct format. Applicants must be at least 18 years old and born after 1900. If the applicant is a registered customer, their information will be automatically filled in. If the applicant is not registered, they will be added to the customer list with a credit score of 0. After submission, a page with the applicant's information, application status, and limit details will be displayed, and an email will be sent to the provided email address. Only one application is allowed per TC number.
![Screenshot 2023-02-27 at 01 01 28](https://user-images.githubusercontent.com/46796424/221440471-316654f8-5175-4ac5-84b7-9da8a14db487.png)

### Loan Status Check
Users can check their loan application status using their TC number and date of birth. The result will be displayed in a table that combines the customer's information with the loan application status.
![Screenshot 2023-02-27 at 01 02 05](https://user-images.githubusercontent.com/46796424/221440545-e67336d0-acef-40f3-a6cb-b412c3bfb398.png)


### Customer
Users can create new customers using the "Create New Customer" form. All inputs are required, except for collateral. The TC number must be exactly 11 digits, and the customer must be at least 18 years old and born after 1900. A list of all customers is displayed below the form, and each row includes an "edit" and "delete" button. Clicking on "edit" will prompt the user for each field's new value. Clicking on "delete" will display a confirmation message before deleting the customer from the database.
![Screenshot 2023-02-27 at 01 01 35](https://user-images.githubusercontent.com/46796424/221440511-fa3b0524-154a-49fe-88eb-03fe75dca7c9.png)


### About Me
This section includes a brief summary of the developer's skills, experience, and goals.
![Screenshot 2023-02-27 at 01 02 58](https://user-images.githubusercontent.com/46796424/221440558-047f6a6a-2c5f-473a-9a72-bfef8f726256.png)


### Conclusion

This project demonstrates the use of Spring Security for securing web applications and includes a loan application form, loan status check, customer management, and developer information.
