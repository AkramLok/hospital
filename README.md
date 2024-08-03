# Medical Cabinet Backend Application

For Frontend Code (React): https://github.com/AkramLok/hospital-frontend-react

## Overview
The Medical Cabinet Application is designed to streamline the management of patient information, bed assignments, doctor and nurse shifts, and pharmacy inventory within a hospital setting. Built using Java Spring Boot, this application ensures a robust, scalable, and secure system accessible exclusively to hospital doctors. The Code is in French, it can be updated later to English!

## Features

1. **Patient Management**
   - Add, update, and delete patient information.
   - View patient details and medical history.
   - Track assignments of patients to beds.

2. **Bed Assignment History**
   - Maintain a history of patient bed assignments.

3. **Doctor and Patient Assignment**
   - Manage assignments within two specific blocs:
     - Rhythmology Block
     - Catheterization Room

4. **Shift Scheduling**
   - Plan and manage shifts for doctors and nurses.
   - Add, update, and consult shift schedules.

5. **Pharmacy Management**
   - Add and manage medications in the pharmacy inventory.

6. **User Authentication and Authorization**
   - Role-based access control for doctors.

## Technologies Used

- **Backend**
  - Java Spring Boot
  - Spring Data JPA (Hibernate)
  - Spring Security
  - RESTful APIs

- **Database**
  - MySQL (using WAMP or XAMPP)

- **Frontend**
  - HTML, CSS, JavaScript
  - React.js
  - Tailwind CSS

- **Tools**
  - Maven
  - Lombok
  - Postman

## Installation

### Run WAMP or XAMPP application
Configure mysql port to 3306 and keep it running.

### Configure the Database
Update the `application.properties` or `application.yml` file with your database configuration.
No need to create the database in PhpMyAdmin interface (using WAMP or XAMPP), in MySql CLI (using MySql Command Line Client) or MySql Workbench, it will be created automatically.

#### `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medical_cabinet #medical_cabinet is the name of database
spring.datasource.username=yourusername #username we put in PhpMyAdmin (Default Usernamee: 'root')
spring.datasource.password=yourpassword #password we put in PhpMyAdmin (Default Password: '')
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true
```

### Build and run the backend application
```properties
mvn clean install
mvn spring-boot:run
```

### Build and run the frontend application
Navigate to the React frontend directory and install dependencies.
```bash
cd path-to-react-frontend
npm install
```
Start the React application.
```bash
npm start
```

## Usage


1. Ensure both the Spring Boot backend and the React frontend applications are running.

2. Access the application at http://localhost:3000.

3. Sign in and log in with existing doctor credentials.

4. Navigate through the application to manage patients, bed assignments, doctor and nurse shifts, and pharmacy inventory.

## Contributing

1. Fork the repository.

2. Create a new feature branch.
```bash
git checkout -b feature/your-feature-name
```

3. Commit your changes.
```bash
git commit -m "Add your message"
```
4. Push to the branch.
```bash
git push origin feature/your-feature-name
```
5. Push to the branch.

## License
This project is licensed under the MIT License.

## Contact
For any questions or support, please reach out to achibaneakram@gmail.com
