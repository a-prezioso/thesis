FROM openjdk:8-jdk-buster

WORKDIR /app

COPY ./target/thesis-0.0.1-SNAPSHOT.jar /app/thesis-application.jar
COPY data/students1.json /app/students.json

ENV HADOOP_HOME=/opt/hadoop
ENV SPARK_HOME=/opt/spark

RUN apt-get update && apt-get install -y wget

RUN wget https://archive.apache.org/dist/hadoop/common/hadoop-3.3.4/hadoop-3.3.4.tar.gz -O hadoop.tar.gz \
    && mkdir -p "$HADOOP_HOME" \
    && tar -xzf hadoop.tar.gz -C "$HADOOP_HOME" --strip-components=1 \
    && rm hadoop.tar.gz

RUN wget https://archive.apache.org/dist/spark/spark-3.5.0/spark-3.5.0-bin-hadoop3.tgz -O spark.tar.gz \
    && mkdir -p "$SPARK_HOME" \
    && tar -xzf spark.tar.gz -C "$SPARK_HOME" --strip-components=1 \
    && rm spark.tar.gz

EXPOSE 8080

CMD ["java", "-jar", "/app/thesis-application.jar"]
