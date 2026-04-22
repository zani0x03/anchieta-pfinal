# Usa uma imagem do Maven com Java para buildar
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom e baixa as dependências (otimiza o cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e builda
COPY src ./src
RUN mvn clean package -DskipTests

# Imagem final, muito mais leve
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia apenas o jar gerado no build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que sua API usa
EXPOSE 8085

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
