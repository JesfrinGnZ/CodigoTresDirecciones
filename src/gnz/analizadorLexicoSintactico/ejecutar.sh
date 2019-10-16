#! /bin/bash 
echo "STARTING JFLEX COMPILING"
java -jar /home/jesfrin/Documentos/libreriasJava/jflex-1.7.0/lib/jflex-full-1.7.0.jar -d /home/jesfrin/NetBeansProjects/Proyecto1Compi2/src/gnz/backend/analizadores analizadorLexico.flex

echo "STARTING CUP COMPILING"
java -jar /home/jesfrin/Documentos/libreriasJava/java-cup-11b.jar analizadorSintactico.cup 
mv parser.java /home/jesfrin/NetBeansProjects/Proyecto1Compi2/src/gnz/backend/analizadores/parser.java
mv sym.java /home/jesfrin/NetBeansProjects/Proyecto1Compi2/src/gnz/backend/analizadores/sym.java


