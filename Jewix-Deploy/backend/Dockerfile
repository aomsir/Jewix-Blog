FROM openjdk:8-jre-alpine
ENV BASE_DIR /app
WORKDIR $BASE_DIR

# 将jar包与配置文件目录放入镜像
ADD ./ $BASE_DIR

CMD java -Djasypt.encryptor.password=123456 -jar Jewix-API-1.0.0.jar ----spring.config.location=./config/application-dev.yml
EXPOSE 7891

# TODO:将密钥和配置文件等进行操作