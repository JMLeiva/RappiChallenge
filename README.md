# RappiChallenge

Proyecto de evaluacion de android pedido por Rappi.
Se utilizo Kotlin como lenguaje, y una arquitectura MVVM utilzando lso componentes nativos de android (LiveData + ViewModel)

La estructura del proyecto se puede resumir facilmente como:

* DI (Puntos de entrada de Inyeccion de dependencia)
* Models
  * DTOs : POJOs que solo poseen informacion luego utilizada por las Entities
  * Entities : Objectos del negocio. Exponen convientemente la informacion de sus respectivos DTOs
  * Enums : Enums del sistema
  * Tools: herramientas y helpers para manipular las entidades de forma ajena al negocio
* Repository 
  * API : Implementacion e interfaces de la API consumida por la App
  * Memory : Modelado de Cache en memoria
* Tools : herramientas y modulos extra
* View : capa de "View"
* ViewModel : capa de "ViewModel"

La arquitectura a grandes rasgos es la siguiente

![alt text](https://github.com/JMLeiva/RappiChallenge/blob/master/RepoPictures/Architecture.png)

Cada capa se relaciona entre sin a través de Inyección de Depencia
Una capa abstracta de Repository existe por entidad, conteniendo diferentes implementaciones para obtener la información (Memrory, Disk y API)

Un view model (tambien por entidad) utiliza el repository correspondiente para obtener la información necesaria, reaccionando en los cambios de un
"backed" LiveData (por ejemplo, el ID de pelicula), es decir, los cambios en el "backed" LiveData activan cambios en el "result" LiveData.
De esta forma, cuando el cliente (ej. un fragment) modifica el id del ViewModel, se produce una reaccion en cadena que termina modificando el result LiveData (y los derivados, RequestStateObserbale y ErrorObservable) los cuales notifican al cliente.

Toda la informacion intercambiada de esta manera es encapsulada por las entidades del modelo. Estas son clases simples, que contiene DTOs (POJOs) con la información real de la entidad. 
Estos DTOs son los que se obtienen desde los diferentes Repositorios, con los cuales se construyen las entidades del Model.
Ej: (Movie <- MovieDTO)

Importante: 

Puesto que uno de los puntos pedidos era poder hacer búsquedas Offline por categoría, lo que hace la APP actualmente, es hacer una busqeuda por categoria normal, y filtrar localmente los resultados. Esto produce que la busqueda sea lenta (puede requerir varias paginas hasta tener un resultado). Ademas, para no superar la quota maxima de requests de la API (alrededor de 4 requests por segundo), cada pagina que es totalmente filtrada por la busqueda (es decir, no aporta resultados), dispara un pequeño delay de 350ms para justamente evitar superar este límite.


Las siguientes librerias externas fueron utilizadas para el proyecto:

* Retrofit + OkHttp : cliente de WebServices
* PagedRecyclerView : herramienta de mi autoria para manejar listas paginadas facilmente
* Glide : manejo y cacheo de imagenes
* Dagger2 : inyeccion de dependencia
* FlexBox : ViewGroup estilo Flex (para los generos dentro del detalle)
* YouTubeAPI : videos de youtube

----------------------------------------------------------------------------------------------------------------------------------

Preguntas: 

1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?

Es un principio que propone que cada "unidad funcional" (clase, modulo, funcion, componente, etc) debe tener ser responsable de una sola funcionalidad.
Su proposito es principal es modularizar estas funcionalidades dentro dichas unidades. Esto reduce las dependencias entre funcionalidades, diminuye la probabilidad de efectos secundarios de una funcionalidad, y mejora mucho el entendimiento del codigo.

2. Qué características tiene, según su opinión, un “buen” código o código limpio?

Si tuviera que resumirlo de alguna forma, un buen codigo es aquel que:
* Es fácil de entender: 
  * sus componentes de pequeños
  * usa nombres claros y descriptivos
  * no es necesario revisar el codigo interno (de una funcion o clase) para entender que es lo que hace
* Es fácil de modificar
  * sus componentes estan desacomplados a través de interfaces e inyección de dependencia
  * sus componentes son testeables y existen tests de unidad para los mismos ( disminuye la posibilidad de regresiones)
  
  ----------------------------------------------------------------------------------------------------------------------------------
  
  Notas para quien evalúe: 
  
  Dada la magnitud del exámen y el tiempo que le pude dedicar, prioricé algunas cosas por sobre otras, y por lo tanto soy conciente que habria algunas cosas que se podrñian mejorar:
  
* Contenido
  * Agregado de busqueda de series
  * Mostrar mas información de las películas en el detalle
  * Busqueda ONLINE (usando la API correspondiente)
* UI
  * Mutiples columnas en resultado de listing en landscape (o con tablets)
  * Transiciones con "Shared Views" del listing al detalle
* Otros
  * Unit Testing

 
