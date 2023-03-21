
# Hi, I'm Bianca! 👋

And here you can find the documentation of the Booking.com Clone project
## 🚀 About Me
💻(Aspiring) back-end software developer | 👨‍💻Helping companies to build great back-ends | Java, Spring Boot | Passionate about solving problems using technology | 💼 Actively looking for a job | 4️⃣ personal projects


## 🛠 Skills
Back-end development, Software development, Web development, Java, Spring framework, Spring boot, Data structures, Algorithms, OOP, MySQL, Relational databases, SQL, Git, HTML, CSS, Web services, Rest APIs, Unit Testing


## 🔗 Links

[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/BiancaPeter)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/bianca-peter/)


# Booking.com Clone

This application can be used by anyone who needs to rent a room or offer a room for rent.


## Features

As a client, I can:
- view available rooms for a certain period and a certain number of places
- make a reservation for a specific room

As an admin, I can:
- add hotel, get all hotels
- add room, delete room, update price room, get all rooms by hotel
- view how many rooms are free/occupied for a certain period
- view statistics for sold rooms


## Built with

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)



## API Reference

#### Add a new hotel

```http
  Post /hotel/add
```

| Parameter | Type     | Description                                       |
| :-------- | :------- |:--------------------------------------------------|
| `body` | `json` | **Required**. The properties of hotel to be added |

Request body example:

```json
{
  "name": "Concordia"
}
```  

#### Add a room to hotel

```http
  Post /room/add/${hotelId}
```

| Parameter | Type     | Description                                      |
| :-------- | :------- |:-------------------------------------------------|
| `body` | `json` | **Required**. The properties of room to be added |

Request body example:

```json
{
  "roomNumber": "4",
  "price": "400",
  "numberOfPerson": "3"
}
```  

#### Delete a room

```http
  DELETE /room/delete/${roomId}
```

| Parameter | Type     | Description                            |
| :-------- | :------- |:---------------------------------------|
| `id`      | `string` | **Required**. Id of room to be deleted |

#### Get all rooms by hotel

```http
  GET /room/delete/getAllRooms/${hotelId}
```

| Parameter | Type     | Description                            |
| :-------- | :------- |:---------------------------------------|
| `id`      | `string` | **Required**. Id of the item to fetch |


#### Update room with new price

```http
  PUT /room/updatePriceOfRoom/${roomId}/${newPrice}
```

| Parameter | Type     | Description                                        |
|:----------|:---------|:---------------------------------------------------|
| `id`      | `string` | **Required**. Id of room to update                 |
| `price`   | `string` | **Required**.  The new price of room to be updated |


#### Add a reservation

```http
  Post /reservation/add
```

| Parameter | Type     | Description                                             |
| :-------- | :------- |:--------------------------------------------------------|
| `body` | `json` | **Required**. The properties of reservation to be added |

Request body example:

```json
{
  "roomIds": [15],
  "checkIn": "2022-03-08T10:08:02",
  "checkOut": "2022-03-10T10:08:02"
}
```  

#### Get all available rooms

```http
  GET /reservation/availability/
```

#### Get number of available rooms by hotel

```http
  GET /reservation/numberOfAvailableRooms
```

| Parameter | Type     | Description                                                                    |
| :-------- | :------- |:-------------------------------------------------------------------------------|
| `body` | `json` | **Required**. The properties of reservations (startDate, endDate) and hotel id |

Request body example:

```json
{
  "startDate": "2022-02-04T10:08:02",
  "endDate": "2022-02-08T10:08:02",
  "idHotel": "5"
}
```  

#### Get the price for all reservations between specific dates by hotel

```http
  GET /reservation/priceForAllReservations
```

| Parameter | Type     | Description                                                    |
| :-------- | :------- |:---------------------------------------------------------------|
| `body` | `json` | **Required**. Specific dates (startDate, endDate) and hotel id |

Request body example:

```json
{
  "startDate": "2022-02-04T10:08:02",
  "endDate": "2022-02-08T10:08:02",
  "idHotel": "2"
}
```  


#### Get available rooms ordered by price

```http
  GET /reservation/availableRoomsOrderedByPrice
```

| Parameter | Type     | Description                                                             |
| :-------- | :------- |:------------------------------------------------------------------------|
| `body` | `json` | **Required**. Specific dates (startDate, endDate) and number of persons |

Request body example:

```json
{
  "startDate": "2019-02-01T10:08:02",
  "endDate": "2019-02-03T10:08:02",
  "numberOfPersons": "2"
}
```  


## API Authentication and Authorization

There are only two requests which don't require authorization headers.

#### Authenticate (login)

```http
  POST /authenticate
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of user to authenticate  |

Request body example:

```json
{
  "username": "string",
  "password": "string"
}
```  

#### Register 

```http
  POST /authenticate
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of user to register  |

Request body example:

```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "roleType": "string"
}
```  
After running the authenticate request, the client will obtain an access token that will be used in all subsequent request in order to authenticate the user and to authorize the user based on its role.

This is an example of what should be included in the request header:

```http
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjcxMTQzMzEyfQ.dxIzsD9Bm8y_kw3MOoZ2JXIKOg--uZaA5XNtBLdGYc4Ps3nlzBFDwBJi0bEeHlCggonZ6nQ2zwCI0D5a7dXjmw
```  
## Prerequisites

For building and running the application you need:
- JDK 1.8 or higher
- Maven 3

For deploying on Heroku you need:
- GIT 
- Heroku CLI


## Dependencies
You don't need any additional dependencies.
All dependecies related to database management, server management, security management and so on, will be automatically injected by Maven using the pom.xml file located in the root folder of the project.

## Installation

Clone the project

```bash
  git clone https://github.com/BiancaPeter/booking-app
```

Go to the project directory

```bash
  cd my-project
```
    
## Run Locally

Use maven to build the app and, to run it, and to start the local embedded Tomcat server

```bash
  mvn spring-boot:run
```


## Running Tests

To run tests, run the following command

```bash
  mvn test
```


## Environment Variables

You can deploy this project using Heroku or other providers as well, by specifying the following environment variables:

`PROFILE`

`MYSQL_URL`

`MYSQL_USER`

`MYSQL_PASS`


## Deployment

To deploy this project run

```bash
  git push heroku master
```


## Usage

You can use the demo version of the app, using SwaggerUI and following this link:

```javascript
https://thawing-beyond-57918.herokuapp.com/swagger-ui/
```

First, obtain an access token by running the /authenticate endpoint with username "user" and password "pass", which will grant you admin rights in the application.

![App Screenshot](https://i.imgur.com/VTQibfA_d.webp?maxwidth=760&fidelity=grand)

After that, authorize yourself by clicking the authorize button and copy-pasteing the token from the response.

![App Screenshot](https://i.imgur.com/arTX2Ke_d.webp?maxwidth=760&fidelity=grand)

From now on, you can use all other available endpoints, as per swagger documentation.
## Roadmap

In the future, application can be extended with following:

- to see what the prices are for the rooms during the holidays


## Badges


![Maintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![GIT](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)

