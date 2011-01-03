
Prototype Format Registry

Ross King
AIT Austrian Institute of Technology GmbH
03.01.2011


INSTALLATION

1. Requires Java 1.6 or higher

2. Requires Tomcat 6 or higher


3. Create a working directory for the application, for example

C:/registry

Then create two sub-directories under this directory

C:/registry/work
C:/registry/download


4. Populate the application directory with the source format XML files

4a. You can download these from

https://github.com/rcking/formatxmlrepository

Unpack the container to the application directory (e.g. "C:/registry") 

Rename the new subdirectory from (for example)

C:/registry/rcking-formatxmlrepository-0f9fc13

to

C:/registry/formatxmlrepository

4b. Alternatively, if you have GIT installed, go to the application directory (e.g. "/registry") and execute

git clone https://github.com/rcking/formatxmlrepository.git


5. Copy registry.war to the WEBAPPS directory of your Tomcat installation


6. Edit the WEB-INF/classes/registry.properties file in order to point to the application directories

You can either edit this directly in the war archive, or you can unpack the war archive into the Tomcat webapps directory and then edit the properties file.

formatxmlpath=C:/registry/formatxmlrepository
outputxmlpath=C:/registry/work
downloadpath=C:/registry/download

Note that it is important that these three directories be different!


7. Start Tomcat; the application should be available under

http://localhost:8080/registry/

