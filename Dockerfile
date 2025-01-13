# OpenJDK bazlı bir image kullanıyoruz
FROM openjdk:21-jdk

# Çalışma dizinini ayarla
WORKDIR /app

# Maven build çıktısını (JAR dosyasını) image içine kopyala
COPY target/PersonalFinance-0.0.1-SNAPSHOT.jar app.jar

# Uygulamanın çalışacağı portu aç
EXPOSE 8080

# Spring Boot uygulamasını başlat
ENTRYPOINT ["java", "-jar", "app.jar"]