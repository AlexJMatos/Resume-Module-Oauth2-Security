# Resume module service with Oauth 2.0 Security

This is an implementation using Oauth 2.0 as a client server for security. The service has the 
same funcionality of view, create and edit of employee's resumes with scopes defined as roles
ROLE_USER, ROLE_MANAGER, ROLE_ADMIN

## How to run it

### 1. Install Docker and Docker Desktop
It is neccesary to have docker installed in your computer in order to run the container 

#### 2. Run setup commands
It is neccesary to run the following commands in order before running the application 
##### netsh int ip add addr 1 10.5.0.4/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.5/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.6/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.7/32 st=ac sk=tr

### 3. Open a command prompt in the main root
In the main root, run the command "docker-compose up" in order to create the container, wait until finished

### 4. Connect to database server
Once the container is up, the next step is to go to the Pgadmin container in http://localhost:5050
The password for pgadmin is "admin". Once loaded, connect to server with the following parameters

#### HOST: app-db
#### PORT: 5432
#### User: itk
#### Password: itk2022

### 5. Load the database 
Once connected, create two databases called "authorization-db" and "resume-module-db" and run the query tool in each one using the "authorization-db-sql" and "resume-module-sql" SQL files.

### 6. Log in 
Now, login in the redirect login form found in http://localhost:8080. The following users and passwords are present:
#### Username: carlos.reyes@theksquaregroup.com, Password: admin, ROLE_ADMIN
#### Username: guillermo.ceme@theksquaregroup.com, Password: manager, ROLE_MANAGER
#### username: ulises.ancona@theksquaregroup.com, Password: user, ROLE_USER
#### username: julio.vargas@theksquaregroup.com, Password: user, ROLE_USER
#### username: alejandro.matos@theksquaregroup.com, Password: user, ROLE_USER
#### username: erick.ortiz@theksquaregroup.com, Password: user, ROLE_USER
#### username: shaid.bojorquez@theksquaregroup.com, Password: user, ROLE_USER
#### username: gregorio.escobedo@theksquaregroup.com, Password: user, ROLE_USER
#### username: giovanna.borges@theksquaregroup.com, Password: user, ROLE_USER
#### username: gabriel.osorno@theksquaregroup.com, Password: user, ROLE_USER 

### 7. Test endpoints
The following are available, each one has uniques roles for accesibility.

## ENDPOINTS 

#### GET(ROLE_ADMIN) --> /employees/resumes 
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume
#### POST(ROLE_USER) --> /employees/{employeeId}/resume
#### PUT(ROLE_USER)--> /employees/{employeeId}/resume
#### PATCH(ROLE_USER) --> /employees/{employeeId}/resume
#### 
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/technologies
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/technologies/{technologyId}
#### POST(ROLE_USER) --> /employees/{employeeId}/resume/technologies
#### PUT(ROLE_USER) --> /employees/{employeeId}/resume/technologies/{technologyId}
#### 
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/skills
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/skills/{skillId}
#### POST(ROLE_USER) --> /employees/{employeeId}/resume/skills
#### PUT(ROLE_USER) --> /employees/{employeeId}/resume/skills/{skillId}
#### 
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/education
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/education/{educationId}
#### POST(ROLE_USER) --> /employees/{employeeId}/resume/education
#### PUT(ROLE_USER) --> /employees/{employeeId}/resume/education/{educationId}
#### 
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/work-experience
#### GET(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER) --> /employees/{employeeId}/resume/work-experience/{workId}
#### POST(ROLE_USER) --> /employees/{employeeId}/resume/work-experience
#### PUT(ROLE_USER) --> /employees/{employeeId}/resume/work-experience/{workId}