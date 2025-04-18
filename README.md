# API Monitor

📡 Простой сервис для мониторинга доступности URL. Подобно UptimeRobot, он позволяет добавлять URL'ы, которые будут регулярно пинговаться, и отправляет уведомления при сбоях или восстановлении.

## 🚀 Возможности

- Отслеживание доступности HTTP URL
- TODO: Индивидуальный интервал проверки для каждого URL
- Уведомления (по Email или Telegram) при падении и восстановлении
- REST API для добавления и получения URL

## 🛠️ Технологии

- Java 17
- Spring Boot 3
- RestTemplate
- Lombok
- ScheduledExecutorService
- JavaMailSender  
- Telegram API

## 🔧 Запуск проекта

1. Клонируй репозиторий:
    ```bash
    git clone https://github.com/yourname/apimonitor.git
    cd apimonitor
    ```
2. Убедись, что установлен Java 17+ и Maven.
3. Собери и запусти проект:
    ```bash
    mvn spring-boot:run
    ```

## 📬 REST API

### Добавить URL для мониторинга

`POST /api/urls`

Пример запроса:
```json
{
  "url": "https://example.com",
  "intervalInSeconds": 60
}
```

Можно сделать, например, с помощью curl:
```bash
curl -X POST http://localhost:8080/api/urls \
-H "Content-Type: application/json" \
-d '{
"url": "https://example.com",
"intervalInSeconds": 60
}'
```

### Получить список всех отслеживаемых URL
`GET /api/urls`

Пример ответа:
```json
[
  {
    "url": "https://example.com",
    "intervalInSeconds": 60,
    "up": true,
    "lastError": null
  }
]
```

```bash
curl http://localhost:8080/api/urls
```

## 📢 Уведомления
- Email: реализуется через JavaMailSender

- Telegram: можно добавить поддержку Telegram Bot API

## 📌 Примечание
Проект предназначен для учебных целей. В реальной системе стоит использовать базы данных, систему логирования, продвинутую обработку ошибок и конфигурации.

## _With love, glebchik_gg_
