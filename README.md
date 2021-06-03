To compile project
-----------------
## Important! Java must be 11!
Stable Packaging: *mvn clean package*

Stable Packaging without tests: *mvn clean package -DskipTests*

Native Packaging: *mvn clean package -Pnative*

Docker Build: *docker build -f src/main/docker/Dockerfile.jvm -t binocla/textgenerator .*

Docker Run: *docker run -i --rm --env PORT=8080 -p 8080:8080 binocla/textgenerator*

### Heroku Deploy:
1) Build Image with "mvn clean package -DskipTests" for example
2) Build Docker Image: docker build -f src/main/docker/Dockerfile.jvm -t textgenerator .
3) Login to Heroku by: heroku login
4) Create Cluster if needed: heroku create textgenerator --region eu
5) Login to container hub: heroku container:login
6) Tag Image for better code reading: docker tag textgenerator:latest registry.heroku.com/binoclatextgenerator/web
7) Push Image to Heroku: docker push registry.heroku.com/binoclatextgenerator/web
8) Release Image: heroku container:release web -a <app_name>

### Upload Instructions:
1) To Upload File send POST request to the server (test via Postman or cURL) \
example: curl -F "file=@/home/binocla/Documents/qwe.txt" https://binoclatextgenerator.herokuapp.com/upload/file \
   where "file=@/home/binocla..." is full path to the file (with its extension). The last one is address of the server \
   The response might be "All files *name of the file* successfully" 
2) To Get the generated text of the Uploaded file (default: len:10, depth:1) use: \
   curl -X POST -H "Content-Type: text/plain" --data "qwe.txt" https://binoclatextgenerator.herokuapp.com/api \
   where --data "*name of the file*" \
   To Get the generated text of the Uploaded file (with properties) use: \
   curl -X POST -H "Content-Type: text/plain" --data "qwe.txt 100 3" https://binoclatextgenerator.herokuapp.com/api/param \
   where --data "*name of the file* *len* *depth*"
### IMPORTANT! Please, DO NOT make mistakes in POST requests!!! All --data heavily recommended text/plain and be as a String. Otherwise, proper work is NOT guaranteed   

