# Використовуємо офіційний образ Spring Boot з Java 11
FROM eclipse-temurin:17.0.8_7-jre

#RUN groupadd -g 1000 myuser && useradd -r -u 1000 -g myuser myuser

# Копіюємо JAR-файл додатку в контейнер
COPY build/libs/uapp_test_task-0.0.1-SNAPSHOT.jar /app/uapp_test_task-0.0.1-SNAPSHOT.jar

# Запуск додатку при старті контейнера
ENTRYPOINT ["java", "-jar", "/app/uapp_test_task-0.0.1-SNAPSHOT.jar"]