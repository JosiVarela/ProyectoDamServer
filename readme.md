<h1>
    Proyecto DAM Server 2022
</h1>
<h3>Descripción:</h3>
<h3>Requisitos:</h3>
<ul>
    <li>
        Java 16.0.2 o superior
    </li>
</ul>
<h3>Modo de instalación/ejecución:</h3>
<p>
Para ejecutar el servidor, simplemente tenemos que
meter dentro de una carpeta en la máquina que será 
el servidor el contenido del directorio target. 
</p>
<p>
Lo siguiente que tendremos que hacer será realizar la
configuración del programa, la cual se hará desde el 
archivo "config.properties".
En dicho archivo tendremos que indicar los campos:
</p>
<ul>
<li>
    dbName -> Nombre de la BD
</li>
<li>
    dbHost -> Host de la BD
</li>
<li>
    dbPort -> Puerto de la BD
</li>
<li>
    port -> Puerto por el que arrancará nuestro servidor
</li>
<li>
    dbUser -> Usuario de la BD
</li>
<li>
    dbPassword -> Contraseña de la BD
</li>
</ul>
<p>
Si el servidor se trata de una máquina windows,
podremos ejecutar el programa haciendo clic sobre
"WindowsStartup.bat" o desde la cmd con el comando
java -jar LibraryServer.jar
</p>
<p>
Si el servidor se trata de una máquina linux,
podremos ejecutar el programa haciendo clic sobre
desde el script "LinuxStartup.sh" (Nos debemos asegurar
que el usuario que lo instala tenga permisos de ejecución
sobre él), o desde la terminal con el comando
java -jar LibraryServer.jar

<b>IMPORTANTE</b><br/>
En linux nos puede dar el error: "Probable fatal error:
 No physical fonts found."<br/>
Este error se debe a que la máquina no tiene las fuentes instaladas.
<br/> Una manera sencilla de solucionarlo es con el comando: <br/>
<i><b>- apt install fontconfig</b></i>
</p>