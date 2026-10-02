# API de Gestión de Préstamos

Prueba técnica para desarrolladores Spring Boot + Angular.

## Stack
- Java 17 / Spring Boot
- Spring Web, Data JPA, Security, Validation, Cache
- H2 para ejecución local
- JWT + BCrypt
- Caffeine
- JUnit 5 + Mockito

## Ejecución
```bash
cd backend
./mvnw spring-boot:run
```
En Windows: `mvnw.cmd spring-boot:run`.

Usuarios de prueba: `usuario@test.com / 123` y `admin@test.com / 123`.

## Endpoints
- POST `/api/auth/register`
- POST `/api/auth/login`
- POST `/api/loans`
- GET `/api/loans/mine`
- GET `/api/admin/loans`
- PATCH `/api/admin/loans/{id}/status`

## Decisiones
La capa de dominio se mantiene separada de controladores y repositorios mediante servicios. JWT permite una API stateless; BCrypt evita almacenar contraseñas en texto plano. La caché reduce lecturas repetitivas y se invalida cuando cambia el estado de un préstamo. Las aprobaciones se ejecutan dentro de una transacción y un préstamo solo puede pasar de PENDING a APPROVED/REJECTED.

Para producción cambiaría H2 por PostgreSQL, movería el secreto JWT a un gestor de secretos, usaría HTTPS, refresh tokens, observabilidad y migraciones con Flyway.
