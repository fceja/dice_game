FROM arm64v8/ubuntu:20.04

# install jdk 17
RUN apt-get update \
    && apt-get install -y openjdk-17-jdk wget gzip tar \
    && rm -rf /var/lib/apt/lists/*

# ENV DISPLAY=:1

# install Maven 3.9.5
RUN wget -qO- https://downloads.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz | tar xvz -C /opt
ENV PATH=/opt/apache-maven-3.9.5/bin:$PATH


# copy local javafx lib to docker dir
COPY /linux_aarch64/javafx-sdk-21 /home/workspace/app_lib/javafx-sdk-21

# set docker work dir
WORKDIR /home/workspace/app

# copy build / app files
COPY pom.xml .
COPY src ./src


# build
RUN mvn clean install -e -P docker
# CMD java --module-path /home/workspace/app_lib/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml -jar target/jar-output/dice_game-1.0.0.jar
# RUN ["java", "--module-path", "/home/workspace/app_lib/javafx-sdk-21/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/jar-output/dice_game-1.0.0.jar"]

CMD tail -f /dev/null








# FROM arm64v8/amazonlinux:2023

# # install jdk 17
# RUN yum update -y && \
#     yum install -y java-17-amazon-corretto wget gzip tar

# # install Maven 3.9.5
# RUN wget -qO- https://downloads.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz | tar xvz -C /opt
# ENV PATH=/opt/apache-maven-3.9.5/bin:$PATH

# # copy local javafx lib to docker dir
# COPY /linux_aarch64/javafx-sdk-21 /home/workspace/app_lib/javafx-sdk-21

# # set docker work dir
# WORKDIR /home/workspace/app

# # copy build / app files
# COPY pom.xml .
# COPY src ./src

# # install libGL
# RUN yum install -y mesa-libGL



# # build
# RUN mvn clean install -e -P docker
# # RUN ["java", "--module-path", "/home/workspace/app_lib/javafx-sdk-21/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/jar-output/dice_game-1.0.0.jar"]

# # exec app
# CMD ["sh", "-c", "while true; do sleep 1; done"]
