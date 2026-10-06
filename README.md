# Proyecto Spring Batch - Banco XYZ
El objetivo de este proyecto es definir dos microservicios, comunicados de manera asíncrona a través de eventos. Esta semana se agregó autenticación y autorización con OAuth2. El proyecto está conformado por las siguientes aplicaciones:
- El microservicio **msbanco** procesa transacciones bancarias y genera estados de cuenta.
- El microservicio **ms_correo** recibe notificaciones de transacciones completadas e imprime la información de cada transacción en consola.
- Se utiliza **Config Server** para configurar los microservicios de manera centralizada a través de archivos de configuración.
- Se utiliza **Eureka Server** para registrar el microservicio de banco, para facilitar su comunicación con otras aplicaciones más adelante.
- Se crea un **Auth Server** para gestionar la conexión por OAuth2, a través de GitHub, generando JWTs que deberán ser usados en solicitudes HTTP dirigidas al microservicio banco. 

## Requisitos previos
- **Java 21**: Asegúrate de tener instalado JDK 21.
- **Maven 3.9.x** o superior: Para compilar y ejecutar el proyecto.

## Tecnologías utilizadas
- **Java 21**
- **Spring Boot**
- **Maven**

## Estructura del proyecto
El proyecto incluye cinco sub-proyectos, cada uno corresponde a una aplicación diferente.
Cada aplicación tiene su propio `Dockerfile`, los cuales proveen instrucciones para la construcción de las imágenes de su respectiva aplicación.
El directorio raíz contiene un archivo `docker-compose.yml`.

# Como utilizar
El proyecto utiliza un archivo `docker-compose.yml` para desplegar las aplicaciones en contenedores Docker (también despliega **ActiveMQ** usando su imagen).
Antes de ejecutar `docker-compose.yml` se debe crear un archivo `.env` en la carpeta `./auth_server` que contenga las variables de entorno **GITHUB_CLIENT_ID** y **GITHUB_CLIENT_SECRET**. Estas variables serán usadas por el **Auth Server** para conectarse a la aplicación OAuth en GitHub y solicitar acceso al usuario. Las variables anteriores deben obtenerse desde la aplicación OAuth creada en GitHub.
Con el archivo `.env` creado para el **Auth Server**, se debe ejecutar el archivo `docker-compose.yml` usando el comando: `docker-compose up -d --build`