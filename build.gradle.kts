plugins {
    id("java")
}

group = "me.squidxtv"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("me.squidxtv:FrameUI:1.0-SNAPSHOT")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}
