plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mavenlibs.com/maven/dependency/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.11.0")
    // https://mavenlibs.com/maven/dependency/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.7")
    // https://mavenlibs.com/maven/pom/org.slf4j/slf4j-log4j12
    implementation("org.slf4j:slf4j-log4j12:2.0.7")
}


tasks.test {
    useJUnitPlatform()
}