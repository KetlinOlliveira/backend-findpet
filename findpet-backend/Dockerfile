# Etapa 1: Construção (Build)
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY . .
# Dá permissão ao Maven e compila o projeto
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Execução (Run)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copia apenas o ficheiro .jar gerado na etapa anterior
COPY --from=build /app/target/findpet-backend-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Inicia a aplicação com limites de memória para o plano gratuito (512MB)
ENTRYPOINT ["java", "-Xmx300m", "-Xms300m", "-jar", "app.jar"]