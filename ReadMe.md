# aller de trabajo individual en patrones arquitecturales

En este repositorio se encuentra la aplicacion con la aquitectura propuesta y su despliegue en AWS usando EC2 y docker, la cual consta de conectarse a 3 servicios los cuales van a almanecar datos en ina base de datos mongodb y retornara los mensajes.

## Para empezar

Descargue y copie el repositorio en su maquina local.

### Prerrequisitos

- Java
- Maven

### Ejecución

Ubiquese en la carpeta del proyecto y ejecute el siguiente comando el cual creara las imagenes y los volumenes segun la configuracion que se le establecio en la carpeta docker-compose.yml, la cual es la optima para su ejecución

```
docker-compose up -d
```


<br>

![](images/com.png)

<br>

El resultado de ejecutar estos comandos usando docker desktop es el siguiente:

<br>

![](images/docker.png)

<br>


### Despliegue del programa

Para desplegar manualmente el programa o en AWS se usan los siguientes comandos:

```
docker network create services_network
```

```
docker run -d -p 35000:4567 --name robin --network services_network jsebastianrod/logroundrobin
```
```
docker run -d -p 35002:4568 --name service2 --network services_network jsebastianrod/test
```
```
docker run -d -p 35003:4568 --name service3 --network services_network jsebastianrod/test
```
```
docker run -d -p 27017:27017 -v mongodb:/data/db -v mongodb_config:/data/configdb --name db --network services_network mongo:3.6.1 mongod
```

### Arquitectura

![](images/arq.png)


Servicio MongoDB:
Una instancia de MongoDB se ejecuta en un contenedor Docker en una máquina virtual de EC2.

LogService:
Es un servicio REST que recibe una cadena, la almacena en la base de datos y responde con un objeto JSON que contiene las 10 últimas cadenas almacenadas en la base de datos y la fecha en que fueron almacenadas.

Aplicación web APP-LB-RoundRobin:
Esta aplicación web consta de un cliente web y al menos un servicio REST. El cliente web posee un campo de entrada y un botón. Cada vez que el usuario envía un mensaje, este se envía al servicio REST, que a su vez actualiza la pantalla con la información devuelta en formato JSON. El servicio REST recibe la cadena y emplea un algoritmo de balanceo de cargas de Round Robin, delegando el procesamiento del mensaje y la respuesta a cada una de las tres instancias del servicio LogService.

## Funcionamiento y Código



### Funcionamiento
<br>

![](images/despliegue1.png)
<br>
Al escribir algo en el cuadro de texto y hacer click en el boton de sumbit se envia la peticion al servicio LogService el cual se encarga de almacenar el mensaje en la base de datos y retornar los ultimos 10 mensajes almacenados en la base de datos.

### Código

Usamos la clase LogroundRobin la cual es la encargada de recibir y retornar todas las peticiones que se realicen hacia el servidor
<br>

![](images/ele.png)
<br>

Esta hace uso de HttpRemoteCaller para realizar las peticiones a los servicios LogService1, LogService2 y LogService3, el cual calcula realizando un calculo
<br>

![](images/htp.png)
<br>
<br>

![](images/htc.png)
<br>
Por lo cual viene el servicio logService el cual es el encargado de almacenar los mensajes en la base de datos y retornar los ultimos 10 mensajes almacenados en la base de datos.
<br>

![](images/logs.png)
<br>

## Docker Hub
Aparte de la forma de realizar el despliegue presentada anteriormente, se crearon los respectivos repositorios en Docker Hub por si desea consultarlos
<br>

![](images/do.png)
<br>

Y sus direcciones son:

- [LogService](https://hub.docker.com/repository/docker/jsebastianrod/test/general)
- [logRoundRobin](https://hub.docker.com/repository/docker/jsebastianrod/logroundrobin/general/)

## Video Funcionamiento AWS
    
- [Video](https://youtu.be/7QP3Edgnqvo)

# Built With

- [Maven](https://maven.apache.org/) - Gestión de dependencias
- [JAVA](https://rometools.github.io/rome/) - Lenguaje de programacion utlizado

## Authors

- **Juan Sebastian Rodriguez Peña** - [JSebastianRod](https://github.com/JSebastianRod)
