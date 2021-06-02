To compile project
-----------------
## Important! Java must be 11!
Stable Packaging: *mvn clean package*

Stable Packaging without tests: *mvn clean package -DskipTests*

Native Packaging: *mvn clean package -Pnative*

Docker Build: *docker build -f src/main/docker/Dockerfile.jvm -t binocla/textgenerator .*

Docker Run: *docker run -i --rm -p 8080:8080 binocla/textgenerator*

### Heroku Deploy:
1) Build Image with "mvn clean package -DskipTests" for example
2) Build Docker Image: docker build -f src/main/docker/Dockerfile.jvm -t textgenerator .
3) Login to Heroku by: heroku login
4) Create Cluster if needed: heroku create textgenerator --region eu
5) Login to container hub: heroku container:login
6) Tag Image for better code reading: docker tag textgenerator:latest registry.heroku.com/binoclatextgenerator/web
7) Push Image to Heroku: docker push registry.heroku.com/binoclatextgenerator/web
8) Release Image: heroku container:release web -a <app_name>
