plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("org.flywaydb.flyway") version "8.2.1"
    kotlin("plugin.serialization") version "1.6.10"
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
    val kotlinVersion = "1.6.10"
    val ktor = "1.6.7"
    val koin = "3.1.5"
    implementation("io.insert-koin:koin-ktor:$koin")
    implementation("io.insert-koin:koin-logger-slf4j:$koin")
    implementation("io.micrometer:micrometer-registry-prometheus:1.8.1")
    implementation("ch.qos.logback:logback-classic:1.2.10")

    //ktorDependencies
    implementation("io.ktor:ktor-client-core:$ktor")
    implementation("io.ktor:ktor-gson:$ktor")
    implementation("io.ktor:ktor-metrics-micrometer:$ktor")
    implementation("io.ktor:ktor-metrics:$ktor")
    implementation("io.ktor:ktor-server-host-common:$ktor")
    implementation("io.ktor:ktor-locations:$ktor")
    implementation("io.ktor:ktor-auth:$ktor")
    implementation("io.ktor:ktor-auth-jwt:$ktor")
    implementation("io.ktor:ktor-server-sessions:$ktor")
    implementation("io.ktor:ktor-server-netty:$ktor")
    implementation("io.ktor:ktor-serialization:$ktor")


    //databaseDependencies
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    @Suppress("GradlePackageUpdate")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.flywaydb:flyway-core:8.4.1")

    testImplementation("io.insert-koin:koin-test:$koin")
    testImplementation("io.ktor:ktor-server-tests:$ktor")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

flyway {
    user = System.getenv("DB_USER") ?: "atdvagtkqrbzpg"
    val port = 5432
    password = System.getenv("DB_PASSWORD") ?: "17a6cedde6f4b21c0dc13fec84bafa1cba107c2f6ee964b06a9e8a9a26382efd"
    val uri = "ec2-54-247-122-209.eu-west-1.compute.amazonaws.com"
    val dbName = "d18uskgncuue97"
    url = System.getenv("DB_URL") ?: "postgres://$user:$password@$uri:$port/$dbName"
    baselineOnMigrate = true
    locations = arrayOf("filesystem:resources/db/migration")
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}