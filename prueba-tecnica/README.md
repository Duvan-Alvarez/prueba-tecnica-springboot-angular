# Prueba técnica — Spring Boot + Angular

## Contenido
- `RESPUESTAS_TEORICAS.md`: respuestas de la parte teórica.
- `backend/`: API REST Spring Boot.
- `frontend/`: aplicación Angular.

## Flujo de demostración
1. Ejecutar backend en `http://localhost:8080`.
2. Ejecutar frontend en `http://localhost:4200`.
3. Entrar como `usuario@test.com / 123`.
4. Crear un préstamo y revisar su estado.
5. Salir y entrar como `admin@test.com / 123`.
6. Abrir el panel administrativo y aprobar/rechazar la solicitud.
7. Volver al usuario y comprobar el estado actualizado.

## Nota de entrega
La prueba solicita repositorio público y justificación de decisiones técnicas. Antes de entregar el repositorio conviene cambiar el secreto JWT de ejemplo y revisar el README con los datos de autor y comandos de ejecución del equipo evaluador.
