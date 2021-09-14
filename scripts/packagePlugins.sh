
#DATA COLLECTOR
mvn -f '../plugins/dev/data collector/Jaeger/pom.xml' clean package -DskipTests -U

#SYSTEM CONNECTOR
mvn -f '../plugins/dev/system connector/Kubernetes/pom.xml' clean package -DskipTests -U
mvn -f '../plugins/dev/system connector/VirtualMachine/pom.xml' clean package -DskipTests -U

#INJEKTOR
mvn -f '../plugins/dev/injektor/HttpAbort/pom.xml' clean package -DskipTests -U
mvn -f '../plugins/dev/injektor/HttpDelay/pom.xml' clean package -DskipTests -U
mvn -f '../plugins/dev/injektor/MachineShutdown/pom.xml' clean package -DskipTests -U
mvn -f '../plugins/dev/injektor/ProcessTerminator/pom.xml' clean package -DskipTests -U
