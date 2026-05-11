# Arquitectura de la Aplicación y Decisiones de Diseño

El diseño de la aplicación de **Gestión de Gastos** se ha fundamentado en una arquitectura de software basada en capas y en el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**. Este enfoque garantiza un alto nivel de cohesión dentro de los módulos y un bajo acoplamiento entre ellos, facilitando el mantenimiento, la escalabilidad y el trabajo en equipo.

## 1. Tecnologías Base
El proyecto se ha construido utilizando lo siguiente :
* **Lenguaje:** Java 21.
* **Interfaz Gráfica:** JavaFX (declaración de vistas mediante archivos `.fxml`).
* **Gestión de Dependencias y Construcción:** Maven.
* **Persistencia de Datos:** Jackson (procesamiento y almacenamiento de datos en formato `JSON`).
* **Librerías Externas:** `CalendarFX` (para visualización avanzada en calendario).

## 2. Capas de la Arquitectura

La aplicación se estructura en tres capas claramente diferenciadas:

### 2.1. Capa de Presentación (Vista)
Ubicada en el paquete `umu.tds.gestor.vista`, es responsable de la interacción con el usuario. 
* **Interfaz Gráfica (GUI):** Las vistas están diseñadas con Scene Builder en archivos `.fxml` separados de la lógica de control. Cada vista tiene asignado un controlador (ej. `AddGastoViewPopUpController`, `CalendarioViewController`) que captura los eventos del usuario y los traslada a la capa de negocio.
* **Interfaz de Línea de Comandos (CLI):** Se ha implementado la clase `TerminalViewController` para poder operar el sistema por consola. Esta vista se comunica exactamente con los mismos controladores de negocio que la GUI, demostrando un correcto desacoplamiento entre vista y dominio.

### 2.2. Capa de Negocio o Dominio (Modelo / Controlador)
Ubicada en `umu.tds.gestor.modelo` y `umu.tds.gestor.controladores`, contiene el núcleo funcional de la aplicación.
* **Entidades del Dominio:** Clases como `Gasto`, `Categoria`, `Alerta` y `CuentaGasto`. Se hace uso intensivo del polimorfismo y de las características avanzadas de Java (como Streams y Lambdas para los filtrados complejos de gastos por meses, fechas o categorías).
* **Gestores (Controladores de Caso de Uso):** El `ControladorGestion` actúa como fachada. Recibe peticiones de la Vista, aplica las reglas de negocio (ej. comprobación de límites de alertas, división de gastos equitativos/porcentuales) y ordena la persistencia.

### 2.3. Capa de Persistencia (Repositorio)
Ubicada en el paquete `umu.tds.gestor.repository`, se encarga de almacenar y recuperar los datos del sistema, aislándola completamente del modelo de negocio.
* Se ha aplicado rigurosamente el **Patrón Repositorio**. 
* Toda la información del programa se centraliza y persiste de forma local en el fichero `gastos.json`.

## 3. Decisiones de Diseño Principales

A lo largo del desarrollo, el equipo ha tomado decisiones clave para resolver distintos retos técnicos:

1. **Serialización con Jackson:** 
   Dado que tenemos jerarquías de clases abstractas e interfaces (como el caso del patrón Estrategia en `Alerta` o las `CuentaGasto`), instanciar correctamente los objetos al leer el JSON era un desafío. Se decidió utilizar las anotaciones `@JsonTypeInfo` y `@JsonSubTypes` de Jackson. Esto permite guardar un identificador de tipo en el JSON y que Jackson sepa automáticamente qué clase hija concreta (ej. `AlertaSemanal` o `CuentaGastoPorcentual`) debe instanciar al deserializar.

2. **Aislamiento a través del Controlador Principal:**
   Las ventanas gráficas jamás instancian ni llaman directamente a los repositorios. Todas las interacciones pasan obligatoriamente por los métodos públicos de `ControladorGestion`. Esta decisión de diseño blinda la persistencia y asegura que las validaciones previas siempre se ejecuten.

3. **Delegación del parseo mediante Adaptadores:**
   La importación de datos (`.csv` y `.txt`) se ha extraído a un paquete propio (`umu.tds.gestor.importador`). Se tomó la decisión de que el sistema central trabaje siempre con objetos `Gasto` y que sean los importadores concretos quienes se responsabilicen de transformar el formato de texto plano a objetos de nuestro modelo del dominio.