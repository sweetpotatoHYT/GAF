FROM harbor.gaf.net.cn/library/ugo-linux-zh:simple-20210419
MAINTAINER user "user@supermap.com"
# 需要配置数据库
ENV GAF_ENV_DATASOURCE_URL 'jdbc:postgresql://localhost:5432/postgres'
ENV GAF_ENV_DATASOURCE_DRIVER 'org.postgresql.Driver'
ENV GAF_ENV_DATASOURCE_PASSWORD ''
ENV GAF_ENV_DATASOURCE_USERNAME 'postgres'
# 需要配置redis服务器
ENV GAF_ENV_REDIS_HOST 'localhost'
ENV GAF_ENV_REDIS_PORT 6379
ENV GAF_ENV_REDIS_PASSWORD ''
# 需要配置zipkin服务器
ENV GAF_ENV_ZIPKIN_URL 'http//localhost:9411'
# 需要配置fluentd服务器
ENV GAF_ENV_FLUENTD_PORT 24224
ENV GAF_ENV_FLUENTD_HOST 'localhost'
VOLUME /tmp
# 需要超图许可文件
VOLUME /usr/local/supermap/Bin/supermap_any_2020.lic9d
# 需要minio挂载
VOLUME /data-s3fs
ADD target/*.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=1m --timeout=5s CMD curl -fs http://localhost:8080/actuator/health || exit 1
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=prod"]
CMD /opt/init.sh && java -jar /app.jar --spring.profiles.active=prod
