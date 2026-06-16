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
ALTER SEQUENCE notifications_id_seq RESTART WITH 1;
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

``` 
    sudo apt install postgresql postgresql-contrib -y 
    sudo systemctl start postgresql
    sudo systemctl enable postgresql
    sudo systemctl status postgresql
    sudo -i -u postgres
    ALTER USER postgres WITH PASSWORD 'postgres';
    \q
    exit
    psql -h localhost -U postgres -d notification_db
    CREATE DATABASE notification_db;
    
    KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
    bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
    bin/kafka-server-start.sh config/server.properties
    
    bin/kafka-topics.sh \
    --create \
    --topic notification-topic \
    --bootstrap-server localhost:9092 \
    --partitions 3 \
    --replication-factor 1
    
    bin/kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --list
    
    bin/kafka-console-producer.sh \
    --topic notification-topic \
    --bootstrap-server localhost:9092
    
    bin/kafka-console-consumer.sh \
    --topic notification-topic \
    --from-beginning \
    --bootstrap-server localhost:9092
```

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

