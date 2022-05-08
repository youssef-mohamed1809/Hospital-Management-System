# Hospital-Management-System

## Overview
This project was assigned as part of the CSE334 Software Engineering course and it is an outpatient hospital management system. A total of 8 students contributed to
this project. The targeted audience for this desktop application are the doctors and secretaries of a hospital where each of them have different roles. The secretary can 
add a patient as well as add an appointment and the data gets saved in a MySQL database. The secretary can also view the patients and doctors that are present today and
can add a doctor excuse for the doctors if the doctor's not present. The doctors can view the ID of the patients that are scheduled today current patient that is
scheduled for the current time and is able to add the patient's medical history, the diagnosis as well as the treatment and it gets saved in the MySQL database. 
Both employees can login and logout of their account as well as view their information.

## Required Environment
- Java SDK version 17.0.2
- JavaFX
- MySQL and MySQL Workbench
- MySQL Connector/J version 8.0.28
- Scene Builder would help view the GUI better but isn't required

## Running the project
1. Using MySQL Workbench import the database files in /Database Files/hospital directory
2. In src/main//src/main/java/com/hospital_management_system/DatabaseManager.java change the parameters to the username ,password and port connection of your MySQL
connection as well as the name of the database you specified in your import.
3. Import MySQL Connector/J to your project.
4. After these steps the project should run successfully
