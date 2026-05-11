# Patrones de Diseño Aplicados

En el desarrollo de la aplicación de Gestión de Gastos, se han aplicado diversos patrones de diseño (basados en los patrones GoF y principios GRASP) para resolver problemas comunes de arquitectura, reducir el acoplamiento y mejorar la escalabilidad y mantenibilidad del código. 

A continuación, se detallan los patrones implementados y su justificación dentro del dominio del proyecto:

## 1. Patrón Singleton
**Propósito:** Garantizar que una clase tenga una única instancia en toda la aplicación y proporcionar un punto de acceso global a la misma.

**Implementación en el proyecto:**
Este patrón se ha aplicado de forma estricta en la capa de persistencia, concretamente en los repositorios:
* `RepositorioGastosImpl`
* `RepositorioAlertasImpl`
* `RepositorioCategoriasImpl`
* `RepositorioCuentasImpl`
* `RepositorioNotificacionesImpl`

**Justificación:**
Dado que los repositorios manejan el almacenamiento y la lectura/escritura de los datos en formato JSON, es crítico que no existan múltiples instancias que puedan sobrescribir el archivo simultáneamente o generar inconsistencias en los datos cargados en memoria. El uso de Singleton asegura que toda la aplicación interactúa con el mismo estado de datos.

## 2. Patrón Método Factoría (Factory Method)
**Propósito:** Definir una interfaz para crear un objeto, pero dejar que sean las subclases (o la lógica interna de la factoría) quienes decidan qué clase instanciar.

**Implementación en el proyecto:**
Se ha utilizado en el paquete `umu.tds.gestor.importador` mediante la clase `ImportadorGastosFactory`.

**Justificación:**
El sistema necesita importar ficheros de gastos desde distintas fuentes y en distintos formatos (`.csv`, `.txt`). La clase `ImportadorGastosFactory` expone el método estático `getImportador(String rutaFichero)`. Evaluando la extensión del fichero suministrado, la factoría decide dinámicamente si instanciar un `ImportadorGastosCSVImpl` o un `ImportadorGastosTXTImpl`. Esto cumple con el principio *Open/Closed*, ya que si en el futuro se añade soporte para XML, solo se debe crear la nueva clase y añadirla a la factoría sin modificar la lógica del controlador principal.

## 3. Patrón Adaptador (Adapter)
**Propósito:** Convertir la interfaz de una clase en otra interfaz que esperan los clientes, permitiendo que clases con interfaces incompatibles trabajen juntas.

**Implementación en el proyecto:**
Aplicado en conjunto con la Factoría en el sistema de importación (`ImportadorGastos`, `ImportadorGastosCSVImpl`, `ImportadorGastosTXTImpl`).

**Justificación:**
Los datos provenientes de plataformas bancarias externas tienen formatos planos ajenos a nuestro sistema orientado a objetos. Las clases implementadoras actúan como adaptadores que leen la información externa fila a fila y la traducen o "adaptan" instanciando objetos nativos de nuestro dominio (`Gasto`, `Categoria`, `CuentaGasto`). De este modo, la aplicación principal ignora los detalles de parseo externo y trabaja directamente con los objetos `Gasto` ya construidos.

## 4. Patrón Estrategia (Strategy)
**Propósito:** Definir una familia de algoritmos, encapsular cada uno de ellos y hacerlos intercambiables. Permite que el algoritmo varíe independientemente de los clientes que lo utilicen.

**Implementación en el proyecto:**
Implementado en el módulo de alertas de gastos en el paquete `umu.tds.gestor.modelo`. Se tiene una clase abstracta base `Alerta` y dos implementaciones concretas: `AlertaSemanal` y `AlertaMensual`.

**Justificación:**
La evaluación de si un gasto supera el límite establecido por el usuario depende del intervalo de tiempo configurado. En lugar de tener una única clase `Alerta` con múltiples condicionales (`if(tipo == SEMANAL) {...} else if(tipo == MENSUAL) {...}`), se delega la responsabilidad del cálculo temporal a las subclases. El gestor `AlerNotifGestorImpl` interactúa de forma polimórfica con la abstracción `Alerta`, delegando el cálculo exacto a la estrategia concreta instanciada, lo que simplifica la lógica y facilita añadir nuevos tipos de alertas en el futuro (ej. Alerta Anual).

## 5. Patrón Controlador / Fachada (GRASP Controller)
**Propósito:** Asignar la responsabilidad de manejar los eventos del sistema a una clase que no represente la interfaz de usuario.

**Implementación en el proyecto:**
Realizado a través de la clase `ControladorGestion`.

**Justificación:**
Para evitar que los controladores visuales de JavaFX (ej. `AddGastoViewPopUpController`) tengan acceso directo a los repositorios o contengan lógica de negocio, se centralizan las operaciones en el `ControladorGestion`. Este actúa como un punto de entrada único (Fachada) que coordina los subsistemas (Gastos, Cuentas, Importador) y devuelve la respuesta a la vista.