plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.0.0-beta17"
    id("org.cadixdev.licenser") version "0.6.1"
    id("org.ajoberstar.grgit") version "5.3.2"
}

group = "pro.obydux"
version = "${extra["plugin_version"]}"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.minebench.de/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.william278.net/releases/")
    maven("https://repo.william278.net/snapshots/")
    maven("https://repo.spaceio.xyz/repository/maven-snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("net.william278.huskhomes:huskhomes-bukkit:4.9.9-539a151")

    implementation("de.themoep:inventorygui:1.6.5-SNAPSHOT")
    implementation("org.apache.commons:commons-text:1.13.1")
    implementation("net.william278:minedown:1.8.2")
    implementation("dev.dejvokep:boosted-yaml:1.3.7")
    implementation("net.wesjd:anvilgui:2.0.3-SNAPSHOT")
    implementation("net.william278:annotaml:2.0.7-4f14c61")
    implementation("net.william278:desertwell:2.0.4")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.2")
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.processResources {
    val props = mapOf(
        "version" to version,
    )
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }

    relocate("org.apache.commons.io", "pro.obydux.huskhomes.gui.libraries.commons.io")
    relocate("org.apache.commons.text", "pro.obydux.huskhomes.gui.libraries.commons.text")
    relocate("org.apache.commons.lang3", "pro.obydux.huskhomes.gui.libraries.commons.lang3")
    relocate("org.jetbrains", "pro.obydux.huskhomes.gui.libraries")
    relocate("org.intellij", "pro.obydux.huskhomes.gui.libraries")
    relocate("de.themoep.inventorygui", "pro.obydux.huskhomes.gui.libraries.inventorygui")
    relocate("de.themoep.minedown", "pro.obydux.huskhomes.gui.libraries.minedown")
    relocate("net.wesjd.anvilgui", "pro.obydux.huskhomes.gui.libraries.anvilgui")
    relocate("net.william278.annotaml", "pro.obydux.huskhomes.gui.libraries.annotaml")
    relocate("net.william278.desertwell", "pro.obydux.huskhomes.gui.libraries.desertwell")
    relocate("dev.dejvokep", "pro.obydux.huskhomes.gui.libraries.boostedyaml")

    destinationDirectory.set(file("$rootDir/target"))
    archiveClassifier.set("")
}

tasks.jar {
    dependsOn("shadowJar")
}

tasks.test {
    useJUnitPlatform()
}

license {
    setHeader(project.file("HEADER"))
    include("**/*.java")
    newLine(true)
}

logger.lifecycle("Building HuskHomesGUI ${version} by William278 & Obydux")

