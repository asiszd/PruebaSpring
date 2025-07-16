# 1. Utilizar una imagen base de Java 17 (puedes cambiar la versión de acuerdo a tu proyecto)
FROM eclipse-temurin:21-jdk-alpine

# 2. Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiar el archivo JAR de la aplicación desde el host al contenedor
COPY target/cita.jar /app/cita.jar

# 4. Exponer el puerto en el que corre la aplicación Spring Boot
EXPOSE 8010

# 5. Comando para ejecutar la aplicación cuando el contenedor arranca
CMD ["java", "-jar", "/app/cita.jar"]