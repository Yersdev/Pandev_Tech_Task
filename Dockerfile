# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Переменная для JAR-файла
ARG JAR_FILE=build/libs/Pandev_Tech_Task-0.0.1-SNAPSHOT.jar

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /opt/app

# Копируем JAR-файл в контейнер
COPY ${JAR_FILE} app.jar

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
