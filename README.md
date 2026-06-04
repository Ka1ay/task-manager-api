# `Task Manager API`

REST API для управления задачами.

Стек технологий:

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Spring Security
- Lombok
Возможности:

Создание задачи
Получение списка задач
Получение задачи по ID
Обновление задачи
Удаление задачи

Запуск:
Создать базу данных PostgreSQL:
CREATE DATABASE task_manager;
Настроить application.properties Запустить: mvn spring-boot:run

API

Создать задачу
- POST /api/tasks

Получить все задачи
- GET /api/tasks

Получить задачу по ID
- GET /api/tasks/{id}

Обновить задачу
- PUT /api/tasks/{id}

Удалить задачу
- DELETE /api/tasks/{id}
