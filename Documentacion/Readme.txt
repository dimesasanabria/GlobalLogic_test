Instrucciones de construccion:

Para construir el proyecto siga las siguinetes instrucciones: 
1. Descargar los fuentes del repositorio "GitHub" a traves del cliente instalado en  visual code. 
2. Importar el proyecto en visual code, esto con el fin de poder analizar el codigo fuente.
3. Informacion relevante sobre la estructura del proyecto.
	3.1 El proyecto se encuentra distribuido en los siguientes paquetes:
      		3.1.1. entity: Se encuentran las clases de los objetos asociados al modelo de datos  que seran almacenados 			en la base de datos.
		3.1.2 exception: Contiene las clases asociadas a la lista de objetos que permite devolver la respuesta de 				   error en el formato solicitado.
		3.1.3 security: Contiene el componenete de seguridad que interactua con la utilidad JWutil para le manejo 				de sesion de la aplicacion.
		3.1.4 service: 	Contiene las clases que funcionan como puertos de entrada y conectores que permiten la 					integracion con las reglas de negocio, la persistencia y el controlador. 
		3.1.5 util: 	Contiene el componente que permite gestionar la creacion y validacion de la sesion.
    		3.1.6 web: Contiene el controlador que expone la API con los metodos post y get que permite la creacion y 			   consulta de los usuarios.
Instrucciones de Ejecucion: 
4. Para Ejecutar el proyecto siga las siguientes instrucciones:
  4.1 Precondicion:  Debe tener un ambiente de desarrollo bajo el IDE  con los siguientes componentes:
		tener Instalado la extenciones de Spring boot 2.5.
		tener instalado JDK  sobre la version 11.0
		tener instalado Gradle sobre la version 7.4
		tener configurado el entorno de desarrollo con las extensiones requeridas como lombok, h2, jjwt jpa etc.
  4.2 Despues de descargado el proyecto desde git y cumpilado los fuentes abrir la terminal con la ruta donde se 	encuentra el proyecto y ejecutar el siguiente comando: 
			./gradlew bootRun  
  4.3 Verificar que el proyecto se publique correctamenete en el servidor 
  4.3 En la carpeta de documentación encontrara el Proyecto postman "Globalogic.postman_collection" con el metodo de creacion de usuario y consulta de 	usuario. Abrirlo desde postman y ejecutarlo los siguinetes pasoso:
		4.3.1 Crear Usuario: Ejecutar el metodo post con url:
		4.3.2
     		 
Gracias
Diego Mesa.
       
