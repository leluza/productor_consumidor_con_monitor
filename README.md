#productor_consumidor_con_monitor

Trabajo Práctico de Programacion Concurrente

Implementación de un sistema productor/consumidor, haciendo uso de
un monitor de concurrencia con una red de Petri

CONSIGNA
Se debe implementar un sistema productor/consumidor, haciendo uso de un monitor de concurrencia con
una red de Petri. El sistema debe poseer 2 buffers acotados con diferentes capacidades: 10 y 15 lugares
respectivamente. Existen 5 productores y 8 consumidores.
Considerar:
● Cada buffer como un recurso que solo puede ser usado por un hilo al mismo tiempo.
● Cada productor inserta un producto en cualquiera de los buffers que esté disponible (no esté
ocupado y tenga lugares libres). Si no hay buffer disponible, esperar hasta que uno esté disponible.
● Cada consumidor extrae un producto de cualquiera de los buffers que esté disponible (no esté
ocupado y tenga productos).Si no hay ningún buffer disponible, esperar a que uno esté disponible.
● Una vez desarrollado el proyecto (funcionando) implemente los siguientes casos y extraiga
conclusiones:
A. El tiempo de inserción y extracción de productos es insignificante
B. El tiempo de inserción y extracción de productos es de 50ms
C. Opcional: analice e implemente el proyecto de modo que para el caso b., el tiempo de
ejecución completa no supere los 3 minutos.

IMPLEMENTACION

Se implemento la logica de la primitiva de concurrencia monitor en codigo java y en Red de Petri. La clase monitor contiene el semaforo de acceso unico, ademas de las colas de cada semaforo de condicion.

Las propiedades descritas de la Red de Petri, reflejan la similitud del modelo con el sistema concurrente.

Se adjunta un DIAGRAMA de CLASES, descriptivo del programa. En conjunto con otros dos (2) DIAGRAMAS de SECUENCIA. 

El primero DIAGRAMA_SECENCIA_1, que representan la logica basica del monitor. Ya sea el caso de que pudo cambiar el estado de la red mas su accion predefinida, o no. En tal caso, queda dormido dentro del monitor.

Y un segundo DIAGRAMA_SECUENCIA_2, esquematizando la interaccion entre un consumidor y un productor. Donde el consumodor queda dormido dentro del monitor, por falta de producto. Para luego ser desperado a partir del cambio producido por la colocacion realizada por un productor.
