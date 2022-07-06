plugins {
  application
  kotlin("jvm") version "1.7.0"
  id("org.flywaydb.flyway") version "8.2.1"
  kotlin("plugin.serialization") version "1.7.0"
}

group = "com.heavyforheavy.profiler"
version = "0.0.1"
application { mainClass.set("com.heavyforheavy.profiler.ApplicationKt") }

repositories {
  mavenCentral()
  @Suppress("DEPRECATION")
  jcenter()
  maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
}

dependencies {

  val kotlinVersion = "1.7.0"
  val ktor = "2.0.3"
  val koin = "3.2.0"

  implementation("io.insert-koin:koin-ktor:$koin")
  implementation("io.insert-koin:koin-logger-slf4j:$koin")
  implementation("io.micrometer:micrometer-registry-prometheus:1.9.0")
  implementation("ch.qos.logback:logback-classic:1.2.11")

  implementation("org.apache.commons:commons-email:1.5")

  //ktorDependencies
  implementation("io.ktor:ktor-server-content-negotiation:$ktor")
  implementation("io.ktor:ktor-serialization-gson:$ktor")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
  implementation("io.ktor:ktor-server-locations:$ktor")
  implementation("io.ktor:ktor-server-auth:$ktor")
  implementation("io.ktor:ktor-server-auth-jwt:$ktor")
  implementation("io.ktor:ktor-server-cors:$ktor")
  implementation("io.ktor:ktor-server-default-headers:$ktor")
  implementation("io.ktor:ktor-server-call-logging:$ktor")
  implementation("io.ktor:ktor-server-metrics:$ktor")
  implementation("io.ktor:ktor-server-metrics-micrometer:$ktor")
  implementation("io.ktor:ktor-server-status-pages:$ktor")

  implementation("io.ktor:ktor-client-core:$ktor")
  implementation("io.ktor:ktor-server-host-common:$ktor")
  implementation("io.ktor:ktor-server-sessions:$ktor")
  implementation("io.ktor:ktor-server-netty:$ktor")
  implementation("io.ktor:ktor-serialization:$ktor")


  //databaseDependencies
  implementation("org.jetbrains.exposed:exposed:0.17.14")
  @Suppress("GradlePackageUpdate")
  implementation("com.zaxxer:HikariCP:3.4.5")
  implementation("org.postgresql:postgresql:42.3.6")
  implementation("org.flywaydb:flyway-core:8.5.12")

  testImplementation("io.insert-koin:koin-test:$koin")
  testImplementation("io.ktor:ktor-server-tests:$ktor")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

flyway {
  user = System.getenv("DB_USER") ?: "swtyfeeumzyeox"
  val port = 5432
  password = System.getenv("DB_PASSWORD")
    ?: "8f3c599bd50baca7ac17f18072246cc38c1a0319de2d42bacc0b7babfe0fecd9"
  val uri = "ec2-34-241-212-219.eu-west-1.compute.amazonaws.com"
  val dbName = "d8nfpg506ntt78"
  url = System.getenv("DB_URL") ?: "postgres://$user:$password@$uri:$port/$dbName"
  baselineOnMigrate = true
  locations = arrayOf("filesystem:resources/db/migration")
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
  dependsOn(tasks.getByName("installDist"))
}