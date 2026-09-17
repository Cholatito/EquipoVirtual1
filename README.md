# EquipoVirtual1

API REST con Java 17, Spring Boot 4.0.8, Maven y PostgreSQL. Paquete base: `pe.edu.upc.equipovirtual1`.

## Ejecutar (PowerShell)

Necesitas JDK 17 y una base de datos PostgreSQL existente. Hibernate actualiza las tablas; no crea la base de datos.

```powershell
$env:JAVA_HOME = 'C:\Users\Acer\.jdks\ms-17.0.20.1'
$env:DB_URL = 'jdbc:postgresql://localhost:5432/equipovirtual1'
$env:DB_USERNAME = 'postgres'
$env:DB_PASSWORD = 'tu_clave'
.\mvnw.cmd spring-boot:run
```

Adapta la ruta del JDK y las credenciales a tu equipo. Selecciona un JDK 17 mediante JAVA_HOME; el compilador Maven está configurado con release 17. No necesitas instalar Maven: el Wrapper descarga la versión configurada.
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI: http://localhost:8080/v3/api-docs

## HUB01: registrar categoría

POST http://localhost:8080/api/events/news
En Postman: Body → raw → JSON, con Content-Type: application/json.

```json
{
  "name": "Teatro",
  "description": "Actividades de artes escénicas",
  "type": "Arte",
  "targetAudience": "Público general",
  "active": true
}
```

Respuesta HTTP 201: los mismos campos y el `id` generado.
Todos los campos son obligatorios. `active: false` es válido.

Ejemplo con PowerShell:

```powershell
$body = @{
    name = 'Teatro'
    description = 'Actividades de artes escénicas'
    type = 'Arte'
    targetAudience = 'Público general'
    active = $true
} | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/events/news' -ContentType 'application/json; charset=utf-8' -Body ([System.Text.Encoding]::UTF8.GetBytes($body))
```

Para probar validación, envía `{}`: devuelve HTTP 400 con errores por campo.
Las cadenas vacías o en blanco y los valores que superen el máximo también devuelven HTTP 400.

```json
{
  "status": 400,
  "message": "Datos inválidos",
  "errors": {
    "name": "El campo name es obligatorio",
    "description": "El campo description es obligatorio",
    "type": "El campo type es obligatorio",
    "targetAudience": "El campo targetAudience es obligatorio",
    "active": "El campo active es obligatorio"
  }
}
```

## HUB02: consultar presupuestos

GET http://localhost:8080/api/events/cultural
En Postman: GET, sin cuerpo.

```powershell
Invoke-RestMethod -Method Get -Uri 'http://localhost:8080/api/events/cultural'
```

Ejemplo de respuesta HTTP 200:

```json
[
  {
    "eventName": "Festival cultural",
    "categoryName": "Teatro",
    "totalBudget": 36.00
  }
]
```

La consulta SQL nativa calcula `SUM(quantity * cost)` y agrupa por identificadores y nombres del evento y categoría. El presupuesto de CulturalEvent no interviene en este cálculo. Sin actividades devuelve `[]`.

Para probar el cálculo, crea una categoría con HUB01 y ejecuta este SQL de ejemplo en tu PostgreSQL, reemplazando `123` por su ID. Es una carga manual opcional; no se ejecuta al iniciar la aplicación.

```sql
WITH new_event AS (
    INSERT INTO cultural_event
        (name, description, location, modality, event_date, capacity, status, budget)
    VALUES
        ('Festival cultural', 'Encuentro de artes', 'Lima', 'Presencial',
         '2026-10-01', 100, 'Activo', 1000.00)
    RETURNING id
)
INSERT INTO cultural_activity
    (name, description, quantity, activity_date, start_time, duration,
     cost, responsible, event_id, category_id)
SELECT 'Obra A', 'Presentación teatral', 2, DATE '2026-10-01',
       TIME '10:00:00', 60, 10.50, 'Ana', id, 123
FROM new_event
UNION ALL
SELECT 'Obra B', 'Presentación teatral', 3, DATE '2026-10-01',
       TIME '11:00:00', 60, 5.00, 'Ana', id, 123
FROM new_event;
```

El total de ese evento/categoría será `2 * 10.50 + 3 * 5.00 = 36.00`.

## Build y pruebas

```powershell
$env:JAVA_HOME = 'C:\Users\Acer\.jdks\ms-17.0.20.1'
.\mvnw.cmd clean test
```

Las pruebas usan H2 en modo PostgreSQL y una configuración separada; no necesitan credenciales ni modifican PostgreSQL. Cubren el contexto, registro, validación, JSON mal formado, consulta nativa y documentación Swagger. H2 no reemplaza una verificación contra PostgreSQL real.

Para generar el JAR ejecutable:

```powershell
.\mvnw.cmd clean package
java -jar target\equipovirtual1-0.0.1-SNAPSHOT.jar
```

Para comprobar la versión de Java que utiliza Maven: `.\mvnw.cmd -v`. Debe mostrar Java 17. En IntelliJ selecciona también JDK 17 como Project SDK y como JDK del runner de Maven, e importa el proyecto desde `pom.xml`.

## Archivos

- `pom.xml`: Java 17, Spring Boot 4.0.8, dependencias existentes y plugins de compilación y empaquetado; H2 únicamente para pruebas.
- `mvnw`, `mvnw.cmd` y `.mvn/wrapper/`: Maven Wrapper 3.3.4 con Maven 3.9.16.
- `src/main/resources/application.properties`: variables DB_URL, DB_USERNAME y DB_PASSWORD, Hibernate update y Swagger.
- `entity/`: CulturalEvent, CulturalCategory y CulturalActivity, con JPA, Jakarta Validation y Lombok.
- `repositories/`: repositorios JPA y consulta SQL nativa de HUB02.
- `services/CulturalEventService.java`: registro y consulta; las variables de HUB01 contienen e2.
- `controllers/`: endpoints y manejo global de excepciones.
- `dto/`: solicitud, respuestas y errores.
- `src/test/`: pruebas de integración y configuración aislada.
