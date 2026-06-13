# Smart Notification Management System

## Overview

A Spring Boot application for managing notifications asynchronously using Kafka.

Supported Notification Types:

* EMAIL
* SMS
* PUSH

Supported Notification Status:

* PENDING
* SENT
* FAILED
* RETRYING

## Technology Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Apache Kafka
* Maven

## APIs

### Create Notification

POST `/api/notifications`

### Get All Notifications

GET `/api/notifications`

Supports:

* Pagination
* Status Filter
* Type Filter

### Get Notification By Id

GET `/api/notifications/{id}`

### Retry Failed Notification

POST `/api/notifications/{id}/retry`

### Dashboard

GET `/api/dashboard`

Returns:

* Total Notifications
* Sent Notifications
* Failed Notifications
* Pending Notifications
* Retrying Notifications
* Type-wise Statistics

## Business Rules

* Duplicate notifications are not allowed within 5 minutes for the same user, type, and message.
* Retry allowed only for FAILED notifications.
* Maximum retry count is 3.
* Retry cooldown period is 2 minutes.
* Notifications containing a word repeated more than 3 consecutive times are rejected.
* Notification processing randomly fails approximately 30% of the time.

## Database

PostgreSQL is used to store notification data.

Main fields:

* id
* userId
* type
* message
* status
* retryCount
* scheduleTime
* processedAt
* createdAt
* updatedAt

## Running the Application

### Start PostgreSQL and Kafka

```bash
docker-compose up -d
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application URL:

```text
http://localhost:8080
```

