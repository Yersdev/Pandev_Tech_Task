# Используем официальный образ OpenJDK для сборки
FROM openjdk:17-jdk-slim AS build

# Устанавливаем необходимые утилиты
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon

# Этап запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /opt/app

# Копируем JAR из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Открываем порт приложения (если нужен)
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
