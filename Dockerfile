FROM java:8
MAINTAINER tanghs<782599753@qq.com>
ENV MAPATH /user/local
WORKDIR $MAPATH
ADD jdk-8u11-linux-x64.tar.gz /usr/local
COPY *.jar /user/local/app.jar

ENV JAVA_HOME /usr/local/javaWeb/jdk1.8.0_11
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:JAVA_HOME/lib/tools.jar
ENV PATH $PATH:$JAVA_HOME/bin

CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
CMD echo $MYPATH
CMD "-------End--------"


