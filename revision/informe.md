Proyecto organizado en `pe.edu.upc.equipovirtual1`, el paquete de `EquipoVirtual1Application` con `@SpringBootApplication`. Se conservan Maven, Java 17, PostgreSQL, los campos y los mapeos de las entidades. No se agrego Spring Security. No se hizo commit ni push.

1. Revision anterior a los cambios

```text
src/main/java/
└── pe/edu/upc/equipovirtual1/
    ├── EquipoVirtual1Application.java
    ├── dtos/
    │   └── CulturalCategoryDTO.java
    ├── entitites/
    │   ├── CulturalActivity.java
    │   ├── CulturalCategory.java
    │   └── CulturalEvent.java
    ├── repositories/
    │   ├── CulturalCategoryRepository.java
    │   ├── IActivityRepository.java
    │   └── IEventRepository.java
    ├── servicesimplements/
    │   ├── ActivityServiceImplement.java
    │   ├── CulturalCategoryImplement.java
    │   └── EventServiceImplement.java
    └── servicesinterfaces/
        ├── IActivityService.java
        ├── ICategoryService.java
        └── IEventService.java
src/test/
└── java/pe/edu/upc/equipovirtual1/
    └── EquipoVirtual1ApplicationTests.java
```

| Revision | Hallazgo inicial |
| --- | --- |
| Paquetes base | Todas las declaraciones estaban bajo `pe.edu.upc.equipovirtual1`. `org.example.pc` aparecia solo en tres imports inexistentes. No habia `com.example.demo`. |
| Duplicados | No habia archivos de clase duplicados ni entidades que representaran lo mismo. Habia una clase interna `CulturalCategoryService` dentro de `CulturalCategoryImplement`. |
| Imports incorrectos | `org.example.pc.dtos.CulturalCategoryDTO`, `org.example.pc.entities.CulturalCategory`, `org.example.pc.repositories.CulturalCategoryRepository` en `CulturalCategoryImplement`. |
| Repositorios y tipos de ID | Los tres repositorios estaban vacios, sin `JpaRepository` ni tipo de ID. Las tres entidades ya tenian ID `Long`. |
| Servicios e interfaces | Ninguna implementacion implementaba su interfaz. Event y Activity estaban vacias y sin `@Service`; Category tenia la anotacion en una clase interna no estatica. El parametro `CuC` del metodo de registro no contenia `e2`. |
| Imports sin uso | DTO en el servicio de Category; `NotBlank`, `NotNull` y `LocalDate` en CulturalCategory. |
| Controladores y rutas | No habia controladores, endpoints ni rutas duplicadas. |
| Entidades y relaciones | Las tres entidades tenian `@Entity`. CulturalActivity tenia `@ManyToOne` hacia Event y Category y `@JoinColumn` con `eventId` y `categoryId`. |
| Gradle | Solo `/.nb-gradle/` en `.gitignore`. No habia build ni wrapper Gradle. |
| Compilacion | Maven confirmo siete errores por imports inexistentes y sus tipos no resueltos. El test inicial dependia del PostgreSQL local. |
| Funcionalidad faltante | Faltaban HUB01, HUB02, consulta nativa y CulturalEventBudgetDTO. |
| Entorno | POM con Java 17, Java por defecto 22 e IDE con Corretto 24. Se encontro y utilizo el JDK 17 instalado para validar. No hay directorio `.git`. |

La copia anterior a la organizacion esta en `antes-de-organizar.zip`; el resultado de Maven anterior a los cambios, en `pruebas-iniciales.log`.

2. Arbol final

```text
src/main/java/
└── pe/edu/upc/equipovirtual1/
    ├── EquipoVirtual1Application.java
    ├── controllers/
    │   └── CulturalEventController.java
    ├── dtos/
    │   ├── CulturalCategoryDTO.java
    │   └── CulturalEventBudgetDTO.java
    ├── entities/
    │   ├── CulturalActivity.java
    │   ├── CulturalCategory.java
    │   └── CulturalEvent.java
    ├── repositories/
    │   ├── CulturalCategoryRepository.java
    │   ├── IActivityRepository.java
    │   └── IEventRepository.java
    ├── servicesInterfaces/
    │   ├── IActivityService.java
    │   ├── ICategoryService.java
    │   └── IEventService.java
    └── servicesImplements/
        ├── ActivityServiceImplement.java
        ├── CulturalCategoryImplement.java
        └── EventServiceImplement.java
src/test/
├── java/pe/edu/upc/equipovirtual1/
│   └── EquipoVirtual1ApplicationTests.java
└── resources/
    └── application-test.properties
```

3. Fusion y eliminaciones

La logica y la dependencia de la clase interna `CulturalCategoryService` se integraron en `CulturalCategoryImplement`, que conserva `e2_registrarCategoria`. Despues de integrar el codigo se retiro la declaracion de la clase interna. La clase resultante implementa `ICategoryService`, tiene `@Service` e inyeccion por constructor. Su parametro y dependencia contienen `e2`.

No se eliminaron archivos de clases duplicadas porque no existian. Las clases de las carpetas anteriores se movieron; no se crearon versiones paralelas. Tambien se retiro la referencia residual a Gradle en `.gitignore`.

4. Archivos modificados, movidos y nuevos

Las rutas Java de esta tabla son relativas a `src/main/java/pe/edu/upc/equipovirtual1/`.

| Archivo final | Cambio |
| --- | --- |
| `entities/CulturalActivity.java` | Movido desde `entitites`; package corregido e imports explicitos. Relaciones, campos y columnas conservados. |
| `entities/CulturalCategory.java` | Movido desde `entitites`; package corregido, imports sin uso retirados e imports JPA explicitos. Campos y columnas conservados. |
| `entities/CulturalEvent.java` | Movido desde `entitites`; package corregido e imports explicitos. Campos y columnas conservados. |
| `repositories/CulturalCategoryRepository.java` | Extiende `JpaRepository<CulturalCategory, Long>`. |
| `repositories/IActivityRepository.java` | Extiende `JpaRepository<CulturalActivity, Long>`. |
| `repositories/IEventRepository.java` | Extiende `JpaRepository<CulturalEvent, Long>`; consulta nativa de HUB02. |
| `servicesInterfaces/ICategoryService.java` | Movido desde `servicesinterfaces`; contrato de registro con parametro `e2`. |
| `servicesInterfaces/IEventService.java` | Movido desde `servicesinterfaces`; contrato para presupuestos por evento y categoria. |
| `servicesInterfaces/IActivityService.java` | Movido desde `servicesinterfaces`; contrato interno de persistencia de actividades. |
| `servicesImplements/CulturalCategoryImplement.java` | Movido desde `servicesimplements`; fusion del servicio interno, imports correctos, implementacion de interfaz, `@Service` e inyeccion por constructor. |
| `servicesImplements/EventServiceImplement.java` | Movido desde `servicesimplements`; implementacion de interfaz, `@Service` e inyeccion por constructor. |
| `servicesImplements/ActivityServiceImplement.java` | Movido desde `servicesimplements`; implementacion de interfaz, `@Service` e inyeccion por constructor. Persistencia interna, sin endpoint adicional. |
| `dtos/CulturalCategoryDTO.java` | Conserva validaciones, getters y setters; constructores vacio y completo explicitos. |
| `dtos/CulturalEventBudgetDTO.java` | Nuevo DTO con eventName, categoryName, totalBudget, ambos constructores, getters y setters. |
| `controllers/CulturalEventController.java` | Nuevo controlador con los dos endpoints requeridos; DTO, `@Valid` e inyeccion por constructor. |
| `pom.xml` | H2 con alcance exclusivo `test`. Version Java 17 y dependencias PostgreSQL conservadas. |
| `.gitignore` | Retirada referencia `/.nb-gradle/`. |
| `src/test/java/pe/edu/upc/equipovirtual1/EquipoVirtual1ApplicationTests.java` | Pruebas de integracion de contexto, persistencia, validacion HTTP y agregacion nativa. |
| `src/test/resources/application-test.properties` | Nuevo perfil de pruebas en memoria, H2 con modo PostgreSQL y esquema temporal `create-drop`. |

`src/main/resources/application.properties` y la clase principal conservan su contenido. Los archivos de evidencia e informe estan en `revision/`.

5. Endpoints de la aplicacion

Puerto configurado: `8085`.

| Metodo | Ruta | Resultado |
| --- | --- | --- |
| POST | `/api/events/news` | Registra CulturalCategory mediante CulturalCategoryDTO. Name, description, type, targetAudience y active son obligatorios. Exito HTTP 201; campos ausentes, nulos o textos en blanco HTTP 400. `active=false` es valido. |
| GET | `/api/events/cultural` | HTTP 200 y lista de CulturalEventBudgetDTO con eventName, categoryName y totalBudget. Sin actividades devuelve `[]`. |

Las dependencias Swagger existentes tambien anuncian `/v3/api-docs` y `/swagger-ui.html` durante la carga del contexto.

6. SQL nativa obligatoria de HUB02

En `IEventRepository`, `@Query(..., nativeQuery = true)`:

```sql
SELECT e.name AS "eventName",
       c.name AS "categoryName",
       SUM(a.quantity * a.cost) AS "totalBudget"
FROM cultural_activity a
JOIN cultural_event e ON a.event_id = e.id
JOIN cultural_category c ON a.category_id = c.id
GROUP BY e.id, e.name, c.id, c.name
ORDER BY e.name, c.name, e.id, c.id
```

No se renombraron campos ni anotaciones de tablas o columnas. La estrategia fisica predeterminada de Hibernate transforma `CulturalActivity`, `CulturalEvent`, `CulturalCategory`, `eventId` y `categoryId` en `cultural_activity`, `cultural_event`, `cultural_category`, `event_id` y `category_id`. La consulta utiliza esos nombres fisicos. La agrupacion incluye los IDs para conservar separados eventos y categorias distintos con nombres iguales. El presupuesto procede de quantity por cost de las actividades, no del campo budget del evento.

7. Validacion y resultado de las pruebas

Ejecutado con el JDK 17 instalado, sin cambiar Java 17 del POM:

```powershell
$env:JAVA_HOME = Join-Path $env:USERPROFILE '.jdks\ms-17.0.20.1'
.\mvnw.cmd clean test
```

```text
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Total time: 19.281 s
```

Las 18 pruebas incluyen carga del contexto e inyeccion real de los tres servicios; POST con DTO, persistencia y HTTP 201; 14 casos de campos ausentes, nulos o vacios; GET sin actividades; y GET con varias actividades y registros con nombres iguales. La agregacion devuelve 32.80, 10.00, 7.00 y 6.00 en grupos separados. Se ejecutan repositorios y servicios reales contra H2, sin simular la consulta. Cada prueba revierte sus datos.

La compilacion de 16 archivos principales y el test se realizo con `release 17`. La inspeccion final no encontro imports sin uso, referencias a paquetes ajenos ni clases de servicio anidadas. El contexto inicia sin errores de inyeccion ni mappings ambiguos.

El log completo esta en `pruebas-finales.log`, y el resumen Surefire en `resultado-pruebas.txt`. PostgreSQL sigue siendo la base de la aplicacion. La comprobacion de autenticacion contra el servidor local rechazo la contraseña configurada; no se alteraron esas credenciales ni se validaron los endpoints contra esa base. H2 en modo PostgreSQL no sustituye una validacion contra PostgreSQL real.

8. Git

Se ejecutaron `git diff --stat` y `git status`. Esta carpeta no tiene `.git` y los comandos no pueden obtener el estado de un repositorio. `git status` devolvio:

```text
fatal: not a git repository (or any of the parent directories): .git
```

La salida de los comandos esta en `git-diff-stat.log` y `git-status.log`. Como alternativa se genero `comparacion-stat.log` con `git diff --no-index --stat --find-renames` entre la copia inicial y el estado final de src, pom.xml y .gitignore:

```text
25 files changed, 407 insertions(+), 68 deletions(-)
```

Este recuento incluye seis movimientos de carpetas de servicios que Git muestra como alta y baja; no significa que se hayan eliminado seis clases del proyecto. La comparacion alternativa no es un estado Git del proyecto. No se inicializo un repositorio, no se hizo commit ni push.
