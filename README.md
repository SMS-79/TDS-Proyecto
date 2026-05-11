# Gestión de Gastos - Proyecto TDS (Curso 2025/2026)

Este repositorio contiene el proyecto de la asignatura **Tecnologías de Desarrollo de Software (TDS)** de la Universidad de Murcia. Consiste en una aplicación de escritorio para la gestión y control de gastos personales, desarrollada siguiendo una arquitectura basada en capas y aplicando patrones de diseño.

##  Integrantes del Grupo
* **Salvador Martínez Sánchez** (`salvador.m.s@um.es`) - Subgrupo 1.2
* **Juan Navarro Fernández** (`juan.navarrof@um.es`) - Subgrupo 1.1
* **Pedro Chico Caballero** (`pedro.chicoc@umu.es`) - Subgrupo 1.2

##  Descripción del Proyecto
La aplicación permite a los usuarios registrar, modificar, borrar y analizar sus gastos personales de forma sencilla. Los datos persisten localmente en formato JSON utilizando la librería Jackson. 

Entre sus funcionalidades principales destacan:
* **Gestión de Gastos y Categorías:** Control total sobre los gastos mediante una interfaz gráfica (JavaFX) y una línea de comandos.
* **Cuentas Compartidas:** Soporte para cuentas con distribución equitativa o porcentual, calculando automáticamente el saldo pendiente de cada persona.
* **Sistema de Alertas:** Configuración de notificaciones semanales y mensuales.
* **Filtros y Visualización Avanzada:** Búsqueda por mes, año o categoría, representados en diagramas de barras y en un calendario completo usando la librería externa `CalendarFX`.
* **Importación Externa:** Carga de ficheros `.csv` y `.txt`.

##  Cómo Ejecutar el Proyecto
El proyecto utiliza **Maven** para la gestión de dependencias y la compilación. Para ejecutar la aplicación, sigue estos pasos:

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/SMS-79/TDS-Proyecto.git
   ```
2. Navega hasta el directorio del proyecto J2EE:
   ```bash
   cd TDS-Proyecto/proyectoJ2eeTDS
   ```
3. Compila y descarga las dependencias usando Maven:
   ```bash
   mvn clean install
   ```
4. Ejecuta la aplicación:
   ```bash
   mvn javafx:run
   ```

##  Documentación Adjunta
Toda la documentación requerida para la evaluación se encuentra en la carpeta `/docs` de este repositorio:

* [1. Diagrama de clases del dominio](docs/diagrama_clases.md)
* [2. Especificación de las historias de usuario](docs/historias_usuario.md)
* [3. Diagrama de interacción](docs/diagrama_interaccion.md)
* [4. Arquitectura de la aplicación](docs/arquitectura.md)
* [5. Manual de usuario](docs/manual_usuario.md)

---
