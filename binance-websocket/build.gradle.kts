plugins {
    java
    kotlin("jvm") version "1.7.20"
}

group = "com.something.binancewebsocket"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.binance:binance-connector-java:1.10.0")
    implementation("org.apache.kafka:kafka-clients:3.3.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

//test {
//    useJUnitPlatform()
//}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.something.binancewebsocket.MainKt"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}