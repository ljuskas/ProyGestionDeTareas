Documentación de la API TaskManager
Información General

    Nombre del proyecto: TaskManager
    Esquema: Postman Collection v2.0.0

Colecciones y Endpoints
Usuarios
Endpoints Públicos

    Crear Usuario
        Ruta: POST /api/public/usuario/crear
        Descripción: Crea un nuevo usuario.
        Autenticación: No requerida.
        Cuerpo de la solicitud:

        json

        {
            "nombre": "Andres",
            "email": "correo_2@gmail.com"
        }

    Obtener Todos los Usuarios
        Ruta: GET /api/public/usuario/all
        Descripción: Obtiene la lista de todos los usuarios.
        Autenticación: No requerida (aunque incluye un token en el ejemplo, está deshabilitado).
        Cuerpo de la solicitud: No aplica.

Endpoints Privados

    Obtener Usuario por ID
        Ruta: GET /api/private/usuario/{id}
        Descripción: Obtiene la información de un usuario por su ID.
        Autenticación: Requiere token Bearer.
        Cuerpo de la solicitud: No aplica.

    Eliminar Usuario
        Ruta: DELETE /api/private/usuario/delete/{id}
        Descripción: Elimina un usuario por su ID.
        Autenticación: Requiere token Bearer.
        Cuerpo de la solicitud: No aplica.

Tareas
Endpoints Privados

    Crear Tarea
        Ruta: POST /api/private/tarea
        Descripción: Crea una nueva tarea.
        Autenticación: Requiere token Bearer.
        Cuerpo de la solicitud:

        json

    {
        "nombre": "tarea 3",
        "descripcion": "Tarea 3 a realizar",
        "estado": {
            "id": 1
        }
    }

Obtener Todas las Tareas por Usuario

    Ruta: GET /api/private/tarea/all/byuser
    Descripción: Obtiene todas las tareas asociadas a un usuario.
    Autenticación: Requiere token Bearer.
    Cuerpo de la solicitud: No aplica.

Obtener Tarea por ID

    Ruta: GET /api/private/tarea/{id}
    Descripción: Obtiene la información de una tarea por su ID.
    Autenticación: Requiere token Bearer.
    Cuerpo de la solicitud: No aplica.

Actualizar Tarea

    Ruta: PUT /api/private/tarea/update
    Descripción: Actualiza la información de una tarea.
    Autenticación: Requiere token Bearer.
    Cuerpo de la solicitud:

    json

        {
            "id": "882e3867-ff68-4359-9028-fe402bc34465",
            "comentarioModificacion": "Se realiza modificación del estado por otro usuario",
            "estado": {
                "id": 3
            }
        }

    Obtener Todas las Tareas
        Ruta: GET /api/private/tarea/all
        Descripción: Obtiene la lista de todas las tareas.
        Autenticación: No requerida.
        Cuerpo de la solicitud: No aplica.

    Eliminar Tarea
        Ruta: DELETE /api/private/tarea/delete/{id}
        Descripción: Elimina una tarea por su ID.
        Autenticación: No requerida.
        Cuerpo de la solicitud: No aplica.

    Obtener Todas las Tareas por Estado
        Ruta: GET /api/private/tarea/estado/{estadoId}
        Descripción: Obtiene todas las tareas filtradas por estado.
        Autenticación: No requerida.
        Cuerpo de la solicitud: No aplica.

Auth

    Iniciar Sesión
        Ruta: POST /api/public/auth/login
        Descripción: Autentica a un usuario y devuelve un token.
        Autenticación: No requerida.
        Cuerpo de la solicitud:

        json

        {
            "email": "correo_uno@gmail.com",
            "clave": "12345678"
        }

Estados

    Obtener Todos los Estados de Tareas
        Ruta: GET /api/public/tarea/estado
        Descripción: Obtiene la lista de todos los estados posibles para las tareas.
        Autenticación: No requerida.
        Cuerpo de la solicitud: No aplica.

Autenticación

Algunos endpoints requieren autenticación mediante un token Bearer. El token debe incluirse en el encabezado de la solicitud de la siguiente manera:

makefile

Authorization: Bearer <token>

Ejemplos de Uso
Crear un Usuario

bash

curl -X POST http://localhost:8080/api/public/usuario/crear \
-H "Content-Type: application/json" \
-d '{
    "nombre": "Andres",
    "email": "correo_2@gmail.com"
}'

Obtener Todos los Usuarios

bash

curl -X GET http://localhost:8080/api/public/usuario/all

Crear una Tarea

bash

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