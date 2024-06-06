# Task Management API

### Descripción

Este es un proyecto demo para Spring Boot que proporciona una API para gestionar tareas y usuarios.
Requisitos

* Java 17
* Maven 3.6.3 o superior

### Tecnologías

* Spring Boot 2.7.18
* Spring Data JPA
* H2 Database (base de datos en memoria para pruebas)
* Spring Security
* JWT (JSON Web Token) para autenticación
* Lombok (para reducir el boilerplate code)
* Swagger y OpenApi para documentacion
  
### Instrucciones para Levantar el Proyecto
Clonar el Repositorio

    git clone https://github.com/ljuskas/ProyGestionDeTareas.git
    cd ProyGestionDeTareas

Configurar el Entorno
Asegúrate de tener Java 17 y Maven instalados en tu máquina.

Verificar la versión de Java

    java -version

Deberías ver una salida similar a:
    
    java version "17.0.2" 2021-09-14 LTS
    Java(TM) SE Runtime Environment (build 17.0.2+8-LTS-123)
    Java HotSpot(TM) 64-Bit Server VM (build 17.0.2+8-LTS-123, mixed mode, sharing)

Verificar la versión de Maven

    mvn -version

Deberías ver una salida similar a:
    
    Apache Maven 3.6.3
    Maven home: /path/to/maven
    Java version: 17.0.2, vendor: Oracle Corporation, runtime: /path/to/java
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux", version: "5.4.0-42-generic", arch: "amd64", family: "unix"

### Compilar y Ejecutar el Proyecto

Compilar el proyecto

    mvn clean install

Ejecutar el proyecto

    mvn spring-boot:run

_____________________________________
# Vinculos internos 

El servidor se iniciará en:   http://localhost:8080

### [Archivo postman](https://github.com/ljuskas/ProyGestionDeTareas/blob/main/src/main/resources/TaskManager.postman_collection.json)

### [Documentacion Swagger](http://localhost:8080/swagger-ui.html)

_____________________________________

# Informacion pre cargada

Para facilitar el flujo de trabajo y pruebas, se realiza el pre cargado de tablas una vez inicializa el proyecto, estas tablas son:

* ESTADOS

|  ID  |    NOMBRE   |    DESCRIPCION    |
| :--- |     :---:   |             ---:  |
|   1  | Pendiente   | Tarea pendiente   |
|   2  | En progreso | Tarea en progreso |
|   3  | Completada  | Tarea completada  |


* USUARIOs

|  UUID |      NOMBRE      |         EMAIL          |      CLAVE         |
| :---  |       :---:      |         :---:          |       ---:         |
|   ?   | Sebastian Concha | correo_uno@gmail.com   |    12345678        |
|   ?   | Raul Sanhueza    | correo_dos@gmail.com   |    87654321        |
|   ?   | Manuel Poblete   | correo_tres@gmail.com  |    abc12345        |

> UUID es un dato que se autogenera

> CLAVE se encuentra encriptada dentro de H2, se entregan sin encriptar para su uso.

# CARACTERISTICAS DEL DESARROLLO
* **Capas de servicio y repositorio:** El código sigue el patrón de arquitectura de capas, separando las preocupaciones relacionadas con la lógica del negocio (servicios) y el acceso a datos (repositorios). De esta forma facilita el mantenimiento y la escalabilidad del sistema.
* **Manejo de excepciones personalizado:** Se ha implementado un manejo personalizado de excepciones mediante el lanzamiento de excepciones específicas (CustomExceptions.CustomNotFoundException, CustomExceptions.CustomBadRequestException, etc.). Así mejora la claridad y la gestión de errores en la aplicación.
* **Uso de patrón Strategy:** Se implementa el patrón Strategy en la clase ITareaOperation y sus implementaciones (ActualizarTareaOperation, CrearTareaOperation, EliminarTareaOperation). Esto permite definir una familia de algoritmos, encapsular cada uno de ellos y hacer que sean intercambiables, favoreciendo la modularidad y extensibilidad del código.
* **Principio de responsabilidad única:** Cada clase realiza operaciones específicas.
* **Seguridad:** Se utiliza Spring Security para manejar la autenticación y autorización de usuarios. La clase UserDetailsServiceImpl implementa UserDetailsService para cargar los detalles del usuario durante la autenticación.
* **Test unitarios:** Se realizan test unitarios de las casuisticas de Usuario y Tarea service, cubriendo 72% UsuarioService y 96% TareaService (Las clases más demandadas)

_____________________________________
# Documentación de la API TaskManager 

**Información General**
* **Nombre del proyecto**: TaskManager
* **Esquema**: Postman Collection v2.0.0
_____________________________________

# Colecciones y Endpoints

## Usuarios (Endpoints Públicos)

1. **Crear Usuario**
    * Ruta: POST /api/public/usuario/crear
    * Descripción: Crea un nuevo usuario.
    * Autenticación: No requerida.
    * Cuerpo de la solicitud:
      
            {
                "nombre": "Andres",
                "email": "correo_2@gmail.com"
            }

2. **Obtener Todos los Usuarios**
    * Ruta: GET /api/public/usuario/all
    * Descripción: Obtiene la lista de todos los usuarios.
    * Autenticación: No requerida (aunque incluye un token en el ejemplo, está deshabilitado).
    * Cuerpo de la solicitud: No aplica.

## Usuarios (Endpoints Privados)

1. Obtener Usuario por ID
    * Ruta: GET /api/private/usuario/{id}
    * Descripción: Obtiene la información de un usuario por su ID.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud: No aplica.

2. Eliminar Usuario
    * Ruta: DELETE /api/private/usuario/delete/{id}
    * Descripción: Elimina un usuario por su ID.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud: No aplica.

## Tareas (Endpoints Privados)

1. Crear Tarea
    * Ruta: POST /api/private/tarea
    * Descripción: Crea una nueva tarea.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud:

            {
                "nombre": "Tarea de prueba",
                "descripcion": "Tarea a realizar",
                "estado": {
                    "id": 1
                }
            }
2. Obtener Todas las Tareas por Usuario
    * Ruta: GET /api/private/tarea/all/byuser
    * Descripción: Obtiene todas las tareas asociadas a un usuario.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud: No aplica.

3. Obtener Tarea por ID
    * Ruta: GET /api/private/tarea/{id}
    * Descripción: Obtiene la información de una tarea por su ID.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud: No aplica.

4. Actualizar Tarea
    * Ruta: PUT /api/private/tarea/update
    * Descripción: Actualiza la información de una tarea.
    * Autenticación: Requiere token Bearer.
    * Cuerpo de la solicitud:
      
            {
                "id": "882e3867-ff68-4359-9028-fe402bc34465",
                "comentarioModificacion": "Se realiza modificación del estado por otro usuario",
                "estado": {
                    "id": 3
                }
            }

5. Obtener Todas las Tareas
    * Ruta: GET /api/private/tarea/all
    * Descripción: Obtiene la lista de todas las tareas.
    * Autenticación: No requerida.
    * Cuerpo de la solicitud: No aplica.

6. Eliminar Tarea
    * Ruta: DELETE /api/private/tarea/delete/{id}
    * Descripción: Elimina una tarea por su ID.
    * Autenticación: No requerida.
    * Cuerpo de la solicitud: No aplica.

7. Obtener Todas las Tareas por Estado
    * Ruta: GET /api/private/tarea/estado/{estadoId}
    * Descripción: Obtiene todas las tareas filtradas por estado.
    * Autenticación: No requerida.
    * Cuerpo de la solicitud: No aplica.

## Auth

1. Iniciar Sesión
    * Ruta: POST /api/public/auth/login
    * Descripción: Autentica a un usuario y devuelve un token.
    * Autenticación: No requerida.
    * Cuerpo de la solicitud:
      
            {
                "email": "correo_uno@gmail.com",
                "clave": "12345678"
            }
## Estados

1. Obtener Todos los Estados de Tareas
   * Ruta: GET /api/public/tarea/estado
   * Descripción: Obtiene la lista de todos los estados posibles para las tareas.
   * Autenticación: No requerida.
   * Cuerpo de la solicitud: No aplica.

## Autenticación

Algunos endpoints requieren autenticación mediante un token Bearer. El token debe incluirse en el encabezado de la solicitud de la siguiente manera:

    Authorization: Bearer <token>

### Ejemplos de Uso
Crear un Usuario

    curl -X POST http://localhost:8080/api/public/usuario/crear \
    -H "Content-Type: application/json" \
    -d '{
        "nombre": "Andres",
        "email": "correo_2@gmail.com"
    }'

Obtener Todos los Usuarios

    curl -X GET http://localhost:8080/api/public/usuario/all

Crear una Tarea


    curl -X POST http://localhost:8080/api/private/tarea \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer <token>" \
    -d '{
        "nombre": "tarea 3",
        "descripcion": "Tarea 3 a realizar",
        "estado": {
            "id": 1
        }
    }'
