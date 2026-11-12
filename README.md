# Laboratorio 13: Service Discovery y API Gateway

Gateway de Spring Cloud para publicar los servicios registrados en Eureka. El gateway escucha en `8080` y enruta categorías, productos, clientes y pedidos usando balanceo por nombre de servicio (`lb://`).

## Requisitos

- JDK 17 o superior
- Maven 3.9 o superior
- Eureka Server activo en `http://localhost:8761/eureka`
- Microservicios `categoria-service`, `producto-service`, `cliente-service` y `pedido-service` registrados en Eureka

## Ejecución

1. Inicie Eureka Server y confirme que su panel está disponible en `http://localhost:8761`.
2. Inicie los cuatro microservicios. Confirme en Eureka que sus nombres de aplicación aparecen como `UP`.
3. Desde la carpeta del proyecto ejecute `mvn spring-boot:run`.
4. Abra `http://localhost:8080/actuator/health`; la respuesta debe indicar `UP`.
5. Pruebe las rutas desde el navegador o Postman:

   | Servicio | URL por el gateway |
   | --- | --- |
   | Categorías | `http://localhost:8080/api/categorias` |
   | Productos | `http://localhost:8080/api/productos` |
   | Clientes | `http://localhost:8080/api/clientes` |
   | Pedidos | `http://localhost:8080/api/pedidos` |

Las rutas conservan el path de entrada y lo envían al servicio registrado con ese nombre. Si el endpoint real de un servicio usa otro prefijo, ajuste el predicado en `src/main/resources/application.yaml`.

## Presentación de evidencias

Prepare capturas legibles, sin datos personales ni ventanas que oculten el resultado. Numere los archivos en este orden y pegue cada captura en el informe del curso:

1. `01-proyecto.png`: estructura del proyecto en el IDE, mostrando `pom.xml`, clase principal y `application.yaml`.
2. `02-dependencias.png`: sección de dependencias del `pom.xml` con Gateway, Eureka Client y LoadBalancer.
3. `03-eureka.png`: panel de Eureka con `GATEWAY-SERVICE`, `CATEGORIA-SERVICE`, `PRODUCTO-SERVICE`, `CLIENTE-SERVICE` y `PEDIDO-SERVICE` en estado `UP`.
4. `04-gateway.png`: consola del gateway iniciado correctamente en el puerto `8080`.
5. `05-categorias.png` y `06-productos.png`: respuestas exitosas obtenidas usando las URLs del gateway, no los puertos internos.
6. `07-clientes.png` y `08-pedidos.png`: respuestas exitosas para la actividad de cliente y pedido a través del gateway.
7. `09-github.png`: repositorio remoto mostrando el commit final publicado.

Para cada captura de endpoint, muestre la URL completa, el método HTTP, el código de respuesta y el cuerpo devuelto. Si un endpoint devuelve `503`, confirme primero que el servicio está registrado en Eureka y que el path coincide con el controller del microservicio; una ruta del gateway no crea ese servicio.

## Conclusiones sugeridas

1. Eureka permite que los servicios se localicen por nombre lógico y evita fijar sus direcciones en la configuración del gateway.
2. Spring Cloud Gateway centraliza los puntos de entrada HTTP y mantiene desacoplados a los clientes de los puertos internos.
3. Las rutas con `lb://` integran el descubrimiento con el balanceador de Spring Cloud.
4. El registro correcto en Eureka y la coincidencia del path son condiciones necesarias para que cada petición llegue al microservicio esperado.
5. Un API Gateway simplifica la publicación de nuevos servicios: se agrega una ruta y se verifica su registro y endpoint, sin cambiar el punto de entrada del cliente.

## Configuración

La URL de Eureka se puede cambiar sin modificar el código definiendo `EUREKA_URL`. El valor por defecto es `http://localhost:8761/eureka`.
