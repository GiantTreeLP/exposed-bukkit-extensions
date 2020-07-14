# exposed-bukkit-extensions

[![GitHub issues](https://img.shields.io/github/issues/GiantTreeLP/exposed-bukkit-extensions.svg)](https://github.com/GiantTreeLP/exposed-bukkit-extensions/issues)
[![GitHub forks](https://img.shields.io/github/forks/GiantTreeLP/exposed-bukkit-extensions.svg)](https://github.com/GiantTreeLP/exposed-bukkit-extensions/network)
[![GitHub stars](https://img.shields.io/github/stars/GiantTreeLP/exposed-bukkit-extensions.svg)](https://github.com/GiantTreeLP/exposed-bukkit-extensions/stargazers)
[![GitHub license](https://img.shields.io/github/license/GiantTreeLP/exposed-bukkit-extensions.svg)](https://github.com/GiantTreeLP/exposed-bukkit-extensions/blob/master/LICENSE)
[![Build Status](https://ci.groundmc.net/buildStatus/icon?job=GiantTree/exposed-bukkit-extensions/master)](https://ci.groundmc.net/job/GiantTree/job/exposed-bukkit-extensions/job/master/)

[![Known Vulnerabilities](https://snyk.io/test/github/GiantTreeLP/exposed-bukkit-extensions/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/GiantTreeLP/exposed-bukkit-extensions?targetFile=pom.xml)
[![codebeat badge](https://codebeat.co/badges/152d55e0-94b2-45f5-a517-14ab65c21d7f)](https://codebeat.co/projects/github-com-gianttreelp-exposed-bukkit-extensions-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3b6f0ae9d89647efbde931458c6a6688)](https://www.codacy.com/app/GiantTreeLP/exposed-bukkit-extensions?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=GiantTreeLP/exposed-bukkit-extensions&amp;utm_campaign=Badge_Grade)

Extensions for Kotlin Exposed for Bukkit/Paper

# Use in your project

## Maven

To use this library in your Maven project, simply use the following dependency 
fragment:

Dependency:
```xml
<dependency>
    <groupId>com.github.gianttreelp</groupId>
    <artifactId>bukkit-exposed-extensions</artifactId>
    <version>1.2</version>
    <scope>compile</scope>
</dependency>
```

If you are feeling adventurous, you can also use the latest snapshots.
This requires you to setup the Sonatype snapshots repository.

Repository:
```xml
<repository>
    <id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
``` 

## Examples

```kotlin
class LocationTable: Table("Locations") {

    /**
    * We are using the uniqueId of the players as the id in the table.
    */
    val id = uuid("id").primaryKey()
    
    /**
    * The last location of the player.
    */ 
    val lastLocation = location("lastLocation")
}
```
