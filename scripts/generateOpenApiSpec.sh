mvn -f ../pom.xml clean install -DskipTests -U
mvn -f ../common/pom.xml package -DskipTests
