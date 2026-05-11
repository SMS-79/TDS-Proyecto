#  Historias de Usuario

A continuación se detallan las historias de usuario que han guiado el desarrollo del sistema de Gestión de Gastos.

## Resumen de Funcionalidades

| Gestión Gastos | Categorías | Filtrar Gastos | Mostrar Datos | Gestión Alertas | Gestión Cuentas |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Añadir gasto | Crear categoría | Por lista de meses | Consulta por tabla/lista | Configurar por categoría | Crear con nombres |
| Editar gasto | Asignar categoría | Por intervalos de fechas | Representar en diagrama | Configurar intervalo | Definir porcentaje |
| Borrar gasto | | Por categoría | Mostrar en calendario | Límite de gasto | Distribuir gastos |
| Importar ficheros | | Combinar filtros | Mostrar en terminal | Listar notificaciones | |
| | | | | Mostrar al superar límite | |

---

###  Historia de Usuario 1: Añadir gasto
**Como** propietario,  
**quiero** poder añadir un gasto en la aplicación  
**para** poder hacer uso de la gestión de gastos.

**Criterios de verificación:**
* La aplicación debe permitir al propietario añadir un gasto proporcionando su categoría, nombre y el dinero utilizado.
* El nuevo gasto debe aparecer en la aplicación en los diferentes sistemas disponibles de muestra de datos (tabla, lista, calendario...).
* El gasto debe poder ser modificado en cualquier momento.
* El gasto debe poder ser borrado en cualquier momento.
* El sistema debe permitir registrar, modificar y borrar un gasto también a través de una interfaz de línea de comandos.

---

### Historia de Usuario 2: Añadir/Crear categoría
**Como** propietario,  
**quiero** poder añadir una categoría,  
**para** facilitar la gestión de gastos y poder asociar un gasto a dicha categoría.

**Criterios de verificación:**
* La aplicación debe permitir añadir una categoría proporcionando un nombre para la misma.
* La aplicación debe permitir añadir gastos previamente existentes a la categoría.
* El sistema debe mostrar la nueva categoría en el sistema al registrar gastos.

---

###  Historia de Usuario 3: Filtrar gastos
**Como** propietario,  
**quiero** poder filtrar gastos  
**para** encontrar fácilmente registros según los criterios utilizados.

**Criterios de verificación:**
* La aplicación debe permitir filtrar gastos por meses, por intervalo de fechas o por categoría.
* La aplicación debe permitir combinar varios sistemas de filtrado de forma simultánea (ejemplo: buscar por meses concretos y una categoría específica).

---

### Historia de Usuario 4: Consulta de gastos
**Como** propietario,  
**quiero** consultar mis gastos registrados en la aplicación  
**para** estar informado de mis movimientos económicos.

**Criterios de verificación:**
* La aplicación debe permitir la consulta de datos en cualquier momento.
* La aplicación debe mostrar toda la información relevante de cada gasto.
* La consulta se debe poder visualizar en distintos formatos: tabla/lista detallada, vista en calendario y representación gráfica (diagrama de barras/circular).

---

###  Historia de Usuario 5: Configuración de alertas
**Como** propietario,  
**quiero** configurar una alerta  
**para** que el sistema me avise cuando sobrepase o me acerque a un límite de gasto concreto.

**Criterios de verificación:**
* La aplicación debe permitir crear una alerta de gasto con distintos parámetros: asociada a una categoría, con un intervalo temporal (semanal/mensual) y un límite económico.
* La aplicación debe generar una notificación visible una vez superado el límite definido por la alerta.
* La aplicación debe contener un historial para almacenar las notificaciones generadas.
* La aplicación debe permitir consultar las notificaciones pasadas en cualquier momento a través del historial.

---

###  Historia de Usuario 6: Crear cuentas compartidas
**Como** propietario,  
**quiero** crear una cuenta de gastos compartidos  
**para** poder gestionar gastos divididos entre varias personas.

**Criterios de verificación:**
* La aplicación debe permitir crear una cuenta compartida indicando los nombres de las personas con quienes se va a dividir el gasto.
* El sistema no debe permitir modificar la lista de personas una vez creada la cuenta.
* El sistema debe soportar dos tipos de distribución para la cuenta: equitativa (por defecto) o porcentual (personalizada).
* El sistema debe calcular y mantener un registro del saldo de dinero pendiente (positivo o negativo) para cada persona dentro de la cuenta compartida.

---

###  Historia de Usuario 7: Importar fichero de gastos
**Como** propietario,  
**quiero** importar gastos desde un fichero externo  
**para** añadir transacciones en bloque a la aplicación (ej. desde una plataforma bancaria).

**Criterios de verificación:**
* La aplicación debe permitir importar archivos de texto plano que contengan datos de gastos.
* La aplicación debe soportar la importación de archivos en diferentes formatos, concretamente en formato CSV y TXT, procesando correctamente la información hacia la aplicación.