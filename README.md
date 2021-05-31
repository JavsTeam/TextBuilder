To compile project
-----------------
## Important! Java must be 11!
Stable Packaging: *mvn clean package*

Stable Packaging without tests: *mvn clean package -DskipTests*

Native Packaging: *mvn clean package -Pnative*

Docker Build: *docker build -f src/main/docker/Dockerfile.jvm -t binocla/textgenerator .*

Docker Run: *docker run -i --rm -p 8080:8080 binocla/textgenerator*


### TODO:
Look forward to introducing Apache Kafka with Zookeeper.
Check up new features and dig up into Reactive development

