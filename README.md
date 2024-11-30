Redis Cache Optimization with Spring Boot
This project demonstrates how to handle cache breakdown, cache penetration, and cache avalanche using Spring Boot, Redis, and MySQL. It includes the following features:

Cache Breakdown Solution:

Implements a distributed lock to prevent multiple threads from simultaneously querying the database for the same key.
Cache Penetration Solution:

Uses a Bloom filter to filter out keys that are guaranteed not to exist in the cache or database.
Cache Avalanche Solution:

Applies random expiration times to cached keys to avoid large-scale simultaneous cache expiration.
Features
CRUD operations with Redis and MySQL integration.
Spring Boot RESTful APIs for cache handling.
Solutions for common caching problems:
Cache Breakdown (using distributed locks)
Cache Penetration (using Bloom filters)
Cache Avalanche (using randomized expiration times)
Technologies Used
Spring Boot: REST API development and dependency injection.
Redis: Caching layer for fast access.
MySQL: Persistent storage layer.
JUnit & Mockito: Unit testing for controllers and services.
Database Schema
Create the following MySQL table for storing key-value pairs:

sql
Copy code
CREATE TABLE data (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
data_key VARCHAR(255) NOT NULL UNIQUE,
data_value TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
Endpoints
Cache Operations
Method	Endpoint	Description
GET	/api/cache/{key}	Fetch data by key (Read)
POST	/api/add	Add a new key-value pair
DELETE	/api/delete/{key}	Delete a key-value pair
Installation Instructions
Clone the repository:

bash
Copy code
git clone https://github.com/accpliuchen/RedisExample.git
cd redis-cache-optimization
Set up MySQL database:

Update application.yml with your database credentials.
Create the data table using the schema provided.
Run the application:

bash
Copy code
mvn spring-boot:run
Test the endpoints: Use tools like Postman or curl to test the REST API endpoints.

Unit Testing
Run the tests using Maven:

bash
Copy code
mvn test
Tests cover:

Cache Breakdown (CacheControllerTest)
Cache Penetration (CachePenetrationControllerTest)
Cache Avalanche (CacheAvalancheControllerTest)
