FROM flink:1.14.3-scala_2.12-java11

COPY target/auto-scale-flink-1.0-SNAPSHOT.jar /opt/flink/usrlib/auto-scale-flink-1.0-SNAPSHOT.jar
