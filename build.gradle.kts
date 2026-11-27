import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "no.nav.pensjonopptjening"

val junitJupiterVersion = "6.1.3"
val kotlinTestVersion = "2.4.20"
val msal4jVersion = "1.26.1"

plugins {
    val kotlinVersion = "2.4.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `java-library`
    `maven-publish`
    id("net.researchgate.release") version "3.2.0"
    id("io.github.ben-manes.versions") version "0.67.0"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.microsoft.azure:msal4j:$msal4jVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinTestVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = FULL
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

release {
    newVersionCommitMessage = "[Release Plugin] - next version commit: "
    tagTemplate = "release-\${version}"
    git {
        requireBranch = "master"
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/navikt/${rootProject.name}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<DependencyUpdatesTask>().configureEach {
    rejectVersionIf {
        isNonStableVersion(candidate.version)
    }
}

fun isNonStableVersion(version: String): Boolean {
    return listOf("BETA", "RC", "-M").any { version.uppercase().contains(it) }
}

