 # DDSI Seminario 2: acceso a bases de datos

1. Escoger un lenguaje de programación y SGDB relacional que permita la gestión de transacciones

   - **C++**, C, Python, Ruby, Java, otros previa consulta
   - **Oracle**, otros previa consulta

2. Implementar un SI sencillo que trabaje con las siguientes tablas

   - `Stock (Cproducto,Cantidad)`
   - `Pedido (Cpedido,Ccliente,Fecha-pedido)`
   - `Detalle-Pedido (Cpedido,Cproducto,Cantidad)`

   Todos los campos son numéricos menos `Fecha-pedido`

3. En la implementación se puede tomar código externo siempre que esté correctamente referenciado y **no se haya desarrollado en años anteriores expresamente para esta asignatura**. 

   Es importante poder explicar el código y su funcionamiento



### Funcionalidades de la aplicación

- Conexión a la BD mediante ODBC o JDBC

- Mostrar una interfaz sencilla (texto en terminal) con las siguientes opciones:

  - Borrado y nueva creación de las tablas e inserción de 10 tuplas predefinidas en el código en la tabla Stock. 
  - Dar de alta nuevo pedido 
  - Borrar un pedido (borrando sus detalles en cascada) 
  - Salir del programa y **cerrar conexión a BD** (explicitada)

  

  El programa deberá obligatoriamente hacer uso de control de transacciones para el alta de nuevos pedidos, comenzando las mismas, estableciendo Savepoints donde convenga y haciendo uso adecuado de los comandos Rollback y Commit.

##### Dentro de "dar de alta nuevo pedido"...

- El interfaz capturará los datos básicos del pedido, que se insertarán en la tabla Pedido con un INSERT. 
- A continuación ofrecerá como opciones:
  - “1. Añadir detalle de producto”
  - “2. Eliminar todos los detalles de producto”
  - “3. Cancelar pedido” 
  - “4. Finalizar pedido”
- **La opción 1** debe capturar los datos de un artículo y cantidad, y realizar la inserción correspondiente en la tabla Detalle- Pedido, si hay stock, así como actualizar el stock, quedando en este mismo menú. 
- **La opción 2** debe eliminar todos los detalles de pedido que se han insertado en Detalle-Pedido para el pedido actual (pero no el pedido en la tabla Pedidos) y quedar en este mismo menú. 
- **La opción 3** debe eliminar el pedido y todos sus detalles y volver al menú principal de la aplicación. 
- **La opción 4** debe hacer los cambios permanentes y volver al menú principal de la aplicación.



### Entrega

En un fichero .zip se encontrará:

- Un documento .pdf que contenga:
  - El lenguaje utilizado
  - Las tareas de instalación realizadas en relación tanto al lenguaje como a los paquetes necesarios para la conexión. 
  - En caso de usar partes de código implementado por otros, las fuentes de donde se ha obtenido. 
  - El reparto de trabajo en estos aspectos y en la implementación entre las personas del grupo (que en todo caso tendrán que conocer y estudiar el trabajo realizado por el resto de personas del grupo, pudiéndoseles preguntar por ello en la defensa).
- El código fuente

