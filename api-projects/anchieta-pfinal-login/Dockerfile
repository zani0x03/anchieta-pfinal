# Estágio 1: Build
# Usamos imagens Debian (Bullseye) que são extremamente estáveis no ARM64/Raspberry Pi
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Compilação
COPY src ./src
RUN mvn clean package -DskipTests -B

# Estágio 2: Runtime
# Imagem JRE otimizada para execução
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8085

# Configurações de memória para containers em dispositivos com RAM limitada
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
