Telegram-бот для управления деревом категорий
Описание
Этот проект представляет собой Telegram-бота, который позволяет пользователям создавать, просматривать и удалять дерево категорий. Бот реализован с использованием Spring Boot и PostgreSQL, а для взаимодействия с Telegram API применяется библиотека TelegramBots.

Функционал
Просмотр дерева категорий: /viewTree
Добавление элемента:
Корневого: /addElement <название элемента>
Дочернего: /addElement <родительский элемент> <дочерний элемент>
Удаление элемента (вместе с дочерними): /removeElement <название элемента>
Просмотр списка команд: /help
Дополнительные (необязательные) команды:
Скачивание дерева в Excel: /download
Загрузка дерева из Excel: /upload
Технологии
Backend: Spring Boot
База данных: PostgreSQL (Spring Data JPA)
Telegram API: TelegramBots
Контейнеризация: Docker
Архитектура: Command pattern, SOLID
Тестирование: Unit Tests (JUnit)
Запуск проекта
Локальный запуск
Клонировать репозиторий:

bash
Копировать
Редактировать
git clone https://github.com/Yersdev/Pandev_Tech_Task.git
cd Pandev_Tech_Task
Создать .env файл с настройками (при необходимости).

Запустить PostgreSQL (если локально):

bash
Копировать
Редактировать
docker-compose up -d
Запустить приложение:

bash
Копировать
Редактировать
./mvnw spring-boot:run
Запуск в Docker
Собрать Docker-образ:

bash
Копировать
Редактировать
docker build -t pandev_telegram_bot .
Запустить контейнер:

bash
Копировать
Редактировать
docker run -d --name pandev_bot -p 8080:8080 pandev_telegram_bot
Тестирование
bash
Копировать
Редактировать
./mvnw test
Контакты
Если у вас есть вопросы, вы можете связаться с нами:

Email: yersultanamangedlindev@gmail.com