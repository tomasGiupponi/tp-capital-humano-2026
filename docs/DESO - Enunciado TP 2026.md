## Desarrollo de Software 2026

## Trabajo práctico

## Fecha límite de entrega: 13/12

## Objetivos

El presente trabajo práctico tiene como finalidad evaluar la aplicación de:

- Principios de encapsulamiento, herencia y polimorfismo.

- Manejo de excepciones.

- Uso de expresiones lambda, streams y colecciones.

- Implementación de patrones de diseño.

- Conocimientos sobre bases de datos (persistencia relacional).

- Implementación de testing unitario, utilizando JUnit y Mockito.

- Spring Boot y conceptos de API REST.

- Desarrollo frontend moderno con Next.js.

- Control de versiones con Git y GitHub.


## Enunciado

Considerando los casos de uso del trabajo práctico de la materia Diseño de sistemas y los diagramas generados para el mismo se deberán implementar los siguientes:

- CU 01: autenticar usuario

- CU 06: Importar candidatos

- CU 09: Dar de alta puesto

- CU 13: Dar de alta competencia

- CU 17: Dar de alta factor

- CU 20: Gestionar preguntas

- CU 21: Dar de alta pregunta

- CU 22: Modificar pregunta

- CU 23: Dar de baja pregunta

- CU 24: Gestionar opciones de respuestas

- CU 29: Dar de alta opción de respuesta

- CU 33: Generar pregunta con IA (opcional)

- CU 25: Evaluar candidatos

- CU 26: Completar cuestionario

- CU 28: Emitir orden de mérito

Y además, todos los casos de uso que el grupo considere necesarios para poder ejecutar los anteriores.

## Requisitos mínimos

- 1. Implementar los casos de uso indicados en el punto anterior, quitando aquellos marcados como opcionales.

- 2. Cumplir con el diseño realizado por el grupo (diagramas de clase, de secuencia, de entidad-relación, etc).

- 3. La interacción con el usuario debe realizarse vía web. Esto es, levantar una aplicación frontend de Next.js en un puerto local y consumirlo desde algún navegador.

- 4. La lógica de negocio (servicios, gestores) debe resultar independiente de la lógica de presentación.

- 5. El acceso a datos debe implementarse mediante el patrón DAO (Data Access Object).

- 6. El almacenamiento de los datos debe realizarse utilizando una base de datos relacional (PostgreSQL, MySQL, SQLite, etc). Se deberá proveer el script de creación de tablas y de creación de datos en la entrega.

- 7. El grupo además deberá seleccionar e implementar cuatro patrones de diseño que considere más adecuados para su sistema.

- 8. El código del proyecto deberá estar subido a un repositorio público en GitHub. Deberá contener también un archivo README.md con las instrucciones para ejecutar localmente (para backend, BD y frontend), y con endpoints de ejemplo para probar los CUs.

- 9. El frontend en Next.js deberá ser capaz de ejecutar como mínimo los casos de uso: 01, 06, 09, 20, 21, 22, 23, 24, 29, 25, 26, 28. Deberán contener buenas prácticas de usabilidad: validaciones, manejo de errores, feedback al usuario, etc.

- 10. El código deberá tener como mínimo un 80% de cobertura en 3 archivos diferentes con tests unitarios en la capa de servicio.


## Entregables

El grupo deberá subir el link a su repositorio en GitHub (compartir con el grupo docente) que contenga:

- Código fuente completo del proyecto, con todos sus paquetes, clases, interfaces y demás componentes necesarios, organizado en carpetas separadas (/api, /frontend).

- Archivos para popular la BD, con las entidades suficientes como para probar todos los CUs.

- Documentación técnica (README.md dentro del repositorio) con instrucciones para ejecutar el proyecto, comando para ejecutar el archivo de seed, cómo correr las pruebas unitarias y cuáles son los endpoints para cada CU.

- Diagramas de clases y de secuencia elaborados para el trabajo práctico de Diseño de Sistemas.

## Consideraciones

Durante la corrección del trabajo práctico se valorará (no son obligatorios):

- Implementación de principios SOLID [URL 🔗](https://www.digitalocean.com/community/conceptual-articles/s-o-l-i-d-the-first-five-principles-of-object-oriented-design)

- Manejo de Optionals, Generics, Interfaces funcionales (Function, Predicate, Supplier, etc) y Clases de java.time. [URL 🔗](https://docs.oracle.com/javase/8/docs/api/java/time/package-frame.html)

- Uso de javadoc para documentar los métodos y clases implementadas.

- Uso de Swagger para documentar el proyecto de Spring Boot.

- Implementación de patrones de diseño estructurales y de comportamiento.

- Implementación de Mockito, para implementar mocks a la hora de testear los métodos. [URL 🔗](https://site.mockito.org/)

- Utilización de Gitflow o Feature Branch para trabajar de manera ordenada, con Conventional Commits y pull requests coherentes. [URL 🔗](https://www.conventionalcommits.org/en/v1.0.0/)
