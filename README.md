## Wenance Challenge

Ejercicio para demostrativo de la api de Streams de java.util

Docs swagger : http://localhost:8080/swagger-ui.html

## Tecnologias necesarias

`Java 1.8` `Maven` `Spring-Boot` `Reactor` `WebFlux` `H2` `JPA`

## Instalacion del proyecto

1. Clonar el repositorio a tu maquina, mediante consola: > git clone https://github.com/siritfelix/wenancechallenge.git
2. Importar el proyecto en tu ide de preferencia
3. Levantar el proyecto por comando si prefiere con : > mvn spring-boot:run

## Solucion

Empleando la interfaz WebClient se crea un cliente reactivo o no bloqueante para consumir un servicio rest : https://cex.io/api/last_price/BTC/USD , que produce un String/json con el precio del Bitcoint en Dolares Estadounidenses. Para ello se configura el cliente en una clase de configuracion de spring, inyectando la uri a consultar por medio de las properties.

Mediante la libreria de Quartz, se planifica una tarea recurrente cada 10 segundos, que llamara al servicio que posee la funcionabilidad del WebClient para consultar el valor del Btc y almacenarlo en una base de datos embebida sql como H2. La marca de tiempo es el Id , que representa un valor entero y el precio en ese instante es la muestra.

El controlador si se consulta a la documentacion : http://localhost:8080/swagger-ui.html , posee 3 endpoint.

* EL primero sirve para consultar el precio del Btc segun la muestra o marca de tiempo mediente el Id.
* El segundo es para hacer un consulta entre 2 marcas de tiempos, y devuelve una estadistica, basado en la serie temporal que se ha creado automaticamente cada 10 segundo desde el momento de la ejecucion del programa. La solucion al calculo esta en el servicio `BtcUsdPriceServiceImpl`, en el metodo `getMetric` el cual hace una consulta a la base de datos para obtener toda la lista de los precios obtenidos por marcas de tiempo, empleando la api Streams hacemos un recorrido para verificar primero existan registros segun las dos marcas de tiempo suministradas, de caso contrario creara una excepcion `NotFoundException`, de continuar se hace un filtrado de la lista para obtener el precio que esta almacenado como String, este se obtiene empleando el map y pasando la clase del objeto con el metodo por referencia para tener el precio, seguidamente se aplica otro map de transformacion del valor de String a un double y del resultado se calcula el promedio. Seguidamente hacemos uso de la api de Streams para determinar el valor maximo de la lista almacenada, siguiendo el principio previamente realizado con la diferencia que la funcion de conversion es max. A partir de estos valores calculamos la diferencia porcentual entre este promedio y el valor maximo
  * difPorct = (max-avg)*100/max con este valor ya podemos crear la respuesta para el objeto de salida metricAvg, que contendra tres campos de informacion, el promedio de las dos marcas de tiempo, la diferencia porcentual y el valor maximo es la serie temporal.
* El tercer endpoint es informativo, es para mostrar una lista de toda la serie temporal.

## Test

Los test realizados , estan en dos clases, una es el test del controllers, empleando el WebClientTest, y el otro test del servicio empleando Mockito, para hacer mock de los repositorios y WebClient , e inyectar estos mock en los servicios.

Para hacer una comprobacion de la conbertura de codigo, se agrego la dependencia jacoco, la cual crea el reporte de convertura de los test mediante el comando

1. mvn clean install
2. mvn jacoco:report

En la carpeta target->site, se creara un archivo index.html, alli se podra explorar la conbertura de codigo, de igual forma tambien segun el IDE empleado, esta funcionabilidad se podra observar de otra forma.
