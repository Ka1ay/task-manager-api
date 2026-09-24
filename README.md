# Task Manager API

REST API для управления задачами на Java 17 и Spring Boot 3.

## Возможности

- CRUD для задач;
- валидация входных данных и единый JSON-формат ошибок;
- пагинация, сортировка и фильтрация списка задач;
- PostgreSQL и версионируемые миграции Flyway;
- HTTP Basic-аутентификация для API;
- интерактивная документация OpenAPI: `/swagger-ui/index.html`.

## Технологии

Java 17, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Bean Validation, PostgreSQL, Flyway, Lombok и springdoc-openapi.

## Запуск

1. Создайте БД: `CREATE DATABASE task_manager;`.
2. Задайте настройки (пример для локальной разработки):

   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/task_manager
   export DB_USERNAME=postgres
   export DB_PASSWORD=postgres
   export APP_USERNAME=taskmanager
   export APP_PASSWORD='replace-with-a-strong-password'
   ```

3. Запустите приложение: `bash ./mvnw spring-boot:run`.

По умолчанию приложение доступно на `http://localhost:8080`. Не используйте значения по умолчанию для `APP_PASSWORD` вне локальной разработки.

## API

Все `/api/**` endpoint’ы требуют HTTP Basic-аутентификацию. Swagger UI и OpenAPI-спецификация доступны без аутентификации.

| Метод | Endpoint | Описание |
| --- | --- | --- |
| `POST` | `/api/tasks` | Создать задачу; возвращает `201 Created`. |
| `GET` | `/api/tasks` | Получить страницу задач. |
| `GET` | `/api/tasks/{id}` | Получить задачу по идентификатору. |
| `PUT` | `/api/tasks/{id}` | Обновить задачу, включая её статус. |
| `DELETE` | `/api/tasks/{id}` | Удалить задачу; возвращает `204 No Content`. |

### Создание задачи

```bash
curl -u "$APP_USERNAME:$APP_PASSWORD" \
  -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Подготовить релиз","description":"Проверить changelog","priority":"HIGH"}'
```

Допустимые значения `priority`: `LOW`, `MEDIUM`, `HIGH`. Новая задача создаётся в статусе `TODO`; возможные статусы: `TODO`, `IN_PROGRESS`, `DONE`.

### Список задач

`GET /api/tasks?page=0&size=20&sort=createdAt,desc&status=TODO&priority=HIGH`

Параметры `status` и `priority` необязательны. Ответ имеет стандартный формат Spring `Page` и содержит записи в поле `content` и метаданные пагинации.

### Ошибки

Ошибки API возвращаются в едином формате с HTTP-статусом, сообщением, путём запроса и, для ошибок валидации, `fieldErrors`.
