# Telegram-бот для управления деревом категорий

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

---

## Описание

Этот проект представляет собой **Telegram-бота**, который позволяет пользователям создавать, просматривать и удалять дерево категорий. Бот реализован с использованием **Spring Boot** и **PostgreSQL**, а для взаимодействия с Telegram API применяется библиотека **TelegramBots**.

---

## Функционал

- **Просмотр дерева категорий:**  
  `/viewTree`

- **Добавление элемента:**
    - **Корневого элемента:**  
      `/addElement <название элемента>`
    - **Дочернего элемента:**  
      `/addElement <родительский элемент> <дочерний элемент>`

- **Удаление элемента (вместе с дочерними):**  
  `/removeElement <название элемента>`

- **Просмотр списка команд:**  
  `/help`

> **Дополнительные (необязательные) команды:**
>
> - **Скачивание дерева в Excel:** `/download`
> - **Загрузка дерева из Excel:** `/upload`

---

## Технологии

- **Backend:** Spring Boot
- **База данных:** PostgreSQL (Spring Data JPA)
- **Telegram API:** TelegramBots
- **Контейнеризация:** Docker
- **Архитектура:** Command pattern, SOLID
- **Тестирование:** Unit Tests (JUnit)

---

## Запуск проекта

### Локальный запуск

1. **Клонирование репозитория:**

   ```bash
   git clone https://github.com/Yersdev/Pandev_Tech_Task.git
   cd Pandev_Tech_Task
   ```

2. **Настроить базу данных в `application.yml`:**
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/dbname
       username: user
       password: password
   ```

3. **Запустить проект:**
   ```bash
   mvn spring-boot:run
   ```

---

## Тестирование

Запустить тесты можно командой:
```bash
mvn test
```

---

## Докеризация

1. **Собрать Docker-образ:**
   ```bash
   docker build -t pandev-bot .
   ```

2. **Запустить контейнер:**
   ```bash
   docker run -d -p 8080:8080 pandev-bot
   ```

---

## Развертывание

### Развертывание на удаленном сервере

1. Настроить сервер и установить необходимые зависимости (Docker, PostgreSQL).
2. Скопировать `.env` файл с настройками базы данных и API-токена.
3. Выполнить команду деплоя:
   ```bash
   docker-compose up -d
   ```

### CI/CD (если реализовано)

Если проект поддерживает CI/CD, можно настроить автоматический деплой через GitHub Actions или GitLab CI/CD, добавив соответствующие `.yml` файлы в корень репозитория.

---

## Контакты

Если у вас есть вопросы, обращайтесь по почте: [yersultanamangedlindev@gmail.com](yersultanamangedlindev@gmail.com)
