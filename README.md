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
- Autenticación de usuarios (registro, login y cambio de contraseña) mediante JWT
- Gestión de recetas favoritas por usuario
- Planificación semanal de comidas, con cálculo de ingredientes necesarios por fecha y por usuario

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

2. Crea un archivo `secrets.properties` en `src/main/resources/` (ya está soportado por
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

**Usuarios** (`/api/users`)

| Método | Endpoint                    | Descripción                     |
|--------|------------------------------|----------------------------------|
| POST   | `/create`                    | Registra un nuevo usuario        |
| POST   | `/login`                     | Inicia sesión                    |
| PUT    | `/change-password/{id}`      | Cambia la contraseña del usuario |
| GET    | `/get-users`                 | Lista todos los usuarios         |
| GET    | `/get-user/{email}`          | Busca un usuario por email       |
| GET    | `/get-user-id/{id}`          | Busca un usuario por id          |
| DELETE | `/delete/{id}`               | Elimina un usuario               |

**Recetas** (`/api/recipes`)

| Método | Endpoint                                    | Descripción                                |
|--------|-----------------------------------------------|-----------------------------------------------|
| POST   | `/create/{userId}`                            | Crea una receta para un usuario                |
| POST   | `/update`                                      | Actualiza una receta                           |
| POST   | `/fav-recipes/{recipe_id}/{user_id}`           | Marca/desmarca una receta como favorita        |
| GET    | `/get-recipes`                                 | Lista todas las recetas                        |
| GET    | `/get-by-name/{name}`                          | Busca una receta por nombre                    |
| GET    | `/get/{id}`                                    | Busca una receta por id                        |
| GET    | `/get-by-date/{date}`                          | Recetas planificadas para una fecha            |
| GET    | `/get-by-date-and-user/{date}/{user_id}`       | Recetas de un usuario en una fecha             |
| GET    | `/weekly-ingredients/{date}`                   | Ingredientes necesarios en la semana           |
| GET    | `/weekly-ingredients-user/{date}/{user_id}`    | Ingredientes semanales de un usuario           |
| GET    | `/fav-recipes-list/{user_id}`                  | Lista de recetas favoritas de un usuario       |
| GET    | `/all-recipes-list/{user_id}`                  | Todas las recetas visibles para un usuario     |
| DELETE | `/delete/{name}`                               | Elimina una receta por nombre                  |

**Ingredientes** (`/api/ingredients`)

| Método | Endpoint                | Descripción                       |
|--------|---------------------------|--------------------------------------|
| POST   | `/create`                  | Crea un ingrediente                 |
| POST   | `/update`                  | Actualiza un ingrediente             |
| GET    | `/get-ingredients`         | Lista todos los ingredientes         |
| GET    | `/get/{name}`              | Busca un ingrediente por nombre      |
| DELETE | `/delete/{name}`           | Elimina un ingrediente por nombre    |

## Notas

- El proyecto usa autenticación mediante JWT.
- Nunca subas `secrets.properties` ni credenciales reales al repositorio.
