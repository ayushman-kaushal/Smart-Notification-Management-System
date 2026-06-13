{
"info": {
"name": "Smart Notification Management System",
"_postman_id": "9b8b52e1-1234-4d91-a111-123456789abc",
"description": "Postman collection for Smart Notification Management System",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
},
"item": [
{
"name": "Create Notification",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\n  "userId": 101,\n  "type": "EMAIL",\n  "message": "Welcome User",\n  "scheduleTime": "2026-05-28T10:00:00"\n}"
},
"url": {
"raw": "{{baseUrl}}/api/notifications",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications"]
}
}
},
{
"name": "Get Notification By Id",
"request": {
"method": "GET",
"url": {
"raw": "{{baseUrl}}/api/notifications/1",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications", "1"]
}
}
},
{
"name": "Get Notifications",
"request": {
"method": "GET",
"url": {
"raw": "{{baseUrl}}/api/notifications?page=0&size=10",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications"],
"query": [
{
"key": "page",
"value": "0"
},
{
"key": "size",
"value": "10"
}
]
}
}
},
{
"name": "Filter By Status",
"request": {
"method": "GET",
"url": {
"raw": "{{baseUrl}}/api/notifications?status=FAILED",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications"],
"query": [
{
"key": "status",
"value": "FAILED"
}
]
}
}
},
{
"name": "Filter By Type",
"request": {
"method": "GET",
"url": {
"raw": "{{baseUrl}}/api/notifications?type=EMAIL",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications"],
"query": [
{
"key": "type",
"value": "EMAIL"
}
]
}
}
},
{
"name": "Retry Failed Notification",
"request": {
"method": "POST",
"url": {
"raw": "{{baseUrl}}/api/notifications/1/retry",
"host": ["{{baseUrl}}"],
"path": ["api", "notifications", "1", "retry"]
}
}
},
{
"name": "Dashboard",
"request": {
"method": "GET",
"url": {
"raw": "{{baseUrl}}/api/dashboard",
"host": ["{{baseUrl}}"],
"path": ["api", "dashboard"]
}
}
}
],
"variable": [
{
"key": "baseUrl",
"value": "http://localhost:8080"
}
]
}
