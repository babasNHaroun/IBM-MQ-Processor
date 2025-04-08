````markdown
# MQ Messages Manager

A full-stack application for managing and monitoring IBM MQ messages with batch processing capabilities.

## Architecture

- Frontend: Angular 19
- Backend: Spring Boot
- Database: MySQL
- Message Queue: IBM MQ

## REST API Endpoints

### Messages

- `GET /api/messages` - Retrieve all messages
- `GET /api/messages/{id}` - Get message by ID
- `DELETE /api/messages/{id}` - Delete a message

### Partners

- `GET /api/partners` - Get all partners
- `POST /api/partners` - Create new partner
- `PUT /api/partners/{id}` - Update partner
- `DELETE /api/partners/{id}` - Delete partner

## Setup with Docker

1. Clone the repository:

```bash
git clone <repository-url>
cd mq-messages-manager
```
````

2. Build and run with Docker Compose:

locally tested on a MACOS with ARM64 architecture; IBM provides a method to build the IBM MQ container image compatible with ARM64 architecture:​
git clone https://github.com/ibm-messaging/mq-container.git
cd mq-container
make build-devserver

```bash
docker-compose up --build
```

This will start:

- Frontend on http://localhost:4200
- Backend on http://localhost:8080
- MySQL on port 3306
- IBM MQ on ports 1414 (MQ) and 9443 (Console)

## Environment Configuration

### IBM MQ Settings

- ibm.mq.host=localhost
- ibm.mq.port=1414
- ibm.mq.queueManager=QM1
- ibm.mq.channel=DEV.ADMIN.SVRCONN
- ibm.mq.queue=DEV.QUEUE.1
- ibm.mq.user=admin
- ibm.mq.password=password

### Database (customize if needed)

- Database Name: mq_messages_management
- Username: root
- Password: root

## Development

To run services individually:

```bash
# Frontend
cd mqManager-frontend
npm install
ng serve --port 4200

# Backend
cd mqManager-backend
mvn clean install
mvn spring-boot:run
```

## Features

- Batch message processing
- Real-time message monitoring
- Partner management
- Message display and filtering
- Retry mechanism for failed operations
