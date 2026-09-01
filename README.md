# Weatly - Meal Planner (Backend)

Backend de **Weatly**, una aplicación para la gestión de planes nutricionales: recetas,
ingredientes y usuarios, con una API REST consumida por el frontend en React Native.

> Repo del frontend: [MP_React](https://github.com/SihaoCheng123/MP_React)

Proyecto desarrollado en equipo. Mi aportación: diseño de la arquitectura full-stack,
implementación de la API REST y la contenerización del proyecto con Docker.

## Tecnologías

- **Backend:** Java 21, Spring Boot
- **Base de datos:** MySQL
- **Contenedores:** Docker
- **Gestión de dependencias:** Maven

## Funcionalidades principales

- API REST con operaciones CRUD para recetas, ingredientes y usuarios
- [COMPLETAR: cualquier otra funcionalidad relevante, ej. autenticación, planes semanales, etc.]

## Cómo ejecutarlo

### Requisitos previos

- Java 21
- Maven (o usa el wrapper incluido `./mvnw`)
- MySQL instalado localmente o vía Docker

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/SihaoCheng123/MP_SpringBoot.git
   cd MP_SpringBoot
   ```

2. Crea un archivo `secrets.properties` en la raíz del proyecto (ya está soportado por
   `spring.config.import=optional:secrets.properties`, y debe estar en `.gitignore`) con:
   ```properties
   spring.datasource.username=[COMPLETAR]
   spring.datasource.password=[COMPLETAR]
   jwt.secret=[GENERA_UNO_NUEVO_CON_openssl_rand_-hex_32]
   ```
   > ⚠️ Nunca subas este archivo al repositorio.

3. Instala las dependencias y compila el proyecto:
   ```bash
   ./mvnw clean install
   ```

4. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

5. La API estará disponible en `http://localhost:8080/api`

### Con Docker

```bash
docker build -t weatly-backend .
docker run -p 8080:8080 weatly-backend
```

## Endpoints principales

| Método | Endpoint              | Descripción                  |
|--------|------------------------|-------------------------------|
| GET    | `/api/recetas`         | Lista todas las recetas       |
| POST   | `/api/recetas`         | Crea una nueva receta         |
| [COMPLETAR con el resto de endpoints relevantes]           |

## Notas

- El proyecto usa autenticación mediante JWT.
- Nunca subas `secrets.properties` ni credenciales reales al repositorio.
- [COMPLETAR: cualquier otra limitación conocida, ej. "el proyecto requiere actualizar dependencias, en revisión"]