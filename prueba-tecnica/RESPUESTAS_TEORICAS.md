# Parte teórica — Prueba técnica Spring Boot / Angular

## 1. Optimización en sistemas financieros

Para una aplicación bancaria con transacciones en tiempo real separaría primero las operaciones de lectura y escritura y mediría antes de optimizar. En backend usaría conexiones de base de datos correctamente dimensionadas, índices sobre las columnas consultadas con frecuencia, paginación y consultas que traigan solo los datos necesarios.

Para concurrencia usaría transacciones cortas, control de concurrencia optimista cuando sea posible y restricciones de base de datos para proteger la consistencia. En operaciones críticas evitaría mantener recursos bloqueados más tiempo del necesario.

Para lecturas repetitivas aplicaría caché, con expiración e invalidación cuando cambien los datos. Para escalar horizontalmente mantendría la API stateless y trasladaría sesiones, caché compartida y otros estados a componentes externos cuando el volumen lo requiera.

Como patrones, utilizaría separación por capas/hexagonal, Repository para persistencia y Strategy cuando existan reglas de negocio que cambien según el tipo de operación. Para procesos asíncronos que no necesiten respuesta inmediata usaría mensajería.

## 2. Seguridad en APIs financieras

- Inyección SQL: usaría JPA/queries parametrizadas y nunca concatenaría entrada del usuario directamente en SQL.
- Autenticación: JWT de corta duración o un proveedor OAuth2/OIDC, con contraseñas almacenadas mediante BCrypt/Argon2.
- Autorización: controlaría permisos por rol y, cuando corresponda, por recurso. Un usuario no debe poder consultar préstamos de otro usuario cambiando un ID en la URL.
- CSRF: en una API stateless con JWT enviado en Authorization y sin autenticación basada en cookies, el riesgo es diferente; mantendría CSRF deshabilitado solo bajo ese diseño y revisaría CORS cuidadosamente.
- XSS: validación de entrada, codificación de salida en el frontend y evitar insertar HTML no confiable.
- CORS: permitiría únicamente los orígenes necesarios.
- HTTPS: obligatorio para producción.
- Secretos: nunca en el repositorio; usaría variables de entorno o un gestor de secretos.
- Rate limiting, logs de auditoría, headers de seguridad y monitoreo para detectar abuso.

## 3. Transacciones en sistemas distribuidos

Si una transferencia involucra varios servicios no intentaría resolver todo con una transacción local. Usaría un identificador único de operación/idempotency key y un flujo transaccional distribuido, por ejemplo Saga, con eventos y acciones compensatorias.

Cada servicio confirmaría su parte dentro de su propia transacción. Si una etapa falla, el orquestador o los consumidores ejecutarían la compensación correspondiente. Los eventos deberían ser idempotentes para que un reintento no genere un doble débito o crédito.

También usaría outbox transaccional para evitar que una transacción de base de datos se confirme pero el evento no llegue al broker. Los errores se clasificarían entre temporales, que pueden reintentarse, y permanentes, que deben enviarse a una cola de errores o generar una alerta.

## 4. Pruebas unitarias y de integración

La suite tendría varios niveles:

1. Unitarias: reglas de negocio del servicio, validaciones, transiciones de estado y casos de error. Usaría JUnit 5 y Mockito.
2. Integración: controladores + Spring Security + JPA + H2/Testcontainers. Verificaría códigos HTTP, persistencia y autorización.
3. Seguridad: usuario normal intentando entrar a endpoints de administrador, token inválido y ausencia de token.
4. Casos de negocio: préstamo pendiente, aprobación, rechazo e intento de modificar un préstamo que ya fue decidido.
5. En CI ejecutaría todos los tests antes de permitir merge.

En esta entrega incluí pruebas unitarias con JUnit 5 y Mockito para creación de préstamos, reglas de cambio de estado y registro de usuarios.

## 5. Front-end

Mantendría el token de autenticación bajo una estrategia coherente con el modelo de seguridad. Para una aplicación real evaluaría preferentemente cookies HttpOnly/Secure/SameSite cuando el backend lo permita, para reducir exposición a XSS. En esta prueba, por simplicidad y porque se solicitó JWT, el ejemplo usa Authorization Bearer.

Angular tendría un AuthService, un interceptor que agrega el token, guards para proteger rutas y un guard específico para administración. Los formularios usan Reactive Forms y validadores. El frontend no se considera la última barrera de autorización: el backend también valida el rol ADMIN.

Para consistencia de datos, después de crear o modificar un préstamo vuelvo a consultar el recurso. En una aplicación más grande usaría un store como NgRx si el estado compartido justificara esa complejidad.

## 6.a. @SpringBootApplication

`@SpringBootApplication` agrupa principalmente `@Configuration`, `@EnableAutoConfiguration` y `@ComponentScan`.

Durante el arranque, Spring crea el ApplicationContext, descubre componentes mediante component scanning y aplica la auto-configuración según las dependencias presentes y las propiedades disponibles. Por ejemplo, al incluir Spring Data JPA y un driver de base de datos, Boot puede configurar automáticamente gran parte de la infraestructura de persistencia.

Esto reduce configuración manual, pero no significa que todo sea mágico: las auto-configuraciones se aplican bajo condiciones y pueden personalizarse cuando el proyecto lo necesita.

## 6.b. Ciclo de vida de un bean

De forma simplificada:

1. Spring encuentra la definición del bean.
2. Instancia el objeto.
3. Inyecta dependencias.
4. Ejecuta callbacks de inicialización y métodos `@PostConstruct`.
5. El bean queda disponible para la aplicación.
6. Al cerrar el contexto se ejecutan callbacks de destrucción, como `@PreDestroy`.

Se puede intervenir con `@PostConstruct`, `@PreDestroy`, interfaces como `InitializingBean`/`DisposableBean` o, cuando se necesita una configuración más global, `BeanPostProcessor`.

## 6.c. Personalizar auto-configuración

Primero intentaría configurar mediante `application.properties` o `application.yml`. Si eso no alcanza, definiría explícitamente el bean que quiero personalizar. Spring Boot normalmente permite que una configuración propia reemplace una auto-configuración cuando se cumple la condición correspondiente.

No excluiría auto-configuraciones de forma indiscriminada. Si realmente necesito hacerlo, usaría `@EnableAutoConfiguration(exclude = ...)` y documentaría la razón. La idea es conservar la convención de Spring Boot y sobrescribir solamente lo necesario.
