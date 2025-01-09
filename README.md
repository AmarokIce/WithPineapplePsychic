<div align="center">

![](src/main/resources/assets/Logox128.png)  
# Pineapple Psychic
![](https://img.shields.io/badge/Minecraft-1.7.10-yellow)
![](https://img.shields.io/badge/Version-1.9-blue)
![](https://img.shields.io/badge/Java-8-red)
![](https://img.shields.io/badge/License-PineappleLicense-green)

---

**📖 About 📖**  
Pineapple Psychic is a set of specialized utilities and shared code that I use for my Minecraft 1.7.10 mods.
It includes my coding library like AmarokJson4J and PineappleCookie. And other something will be used in MC mods coding.
Like MatchUtil, Capability, Setting Config etc.

**🔧 Function overview 🔧**  
Json & Json5 support by AJ4J ([AmarokJson4J](https://github.com/AmarokIce/AmarokJsonForJava))  
Auto config & Auto hotfix config  
NBT datafile input and output  
Capability with auto sync  
Auto registry TileEntity by anno & Auto load and save data in TileEntity by anno  
Abstract class, pitch, and other good for coding and more  

**🍍 Usage 🍍**  
Use with GithubPackage or...

</div>

```groovy
repositories {
    maven {
        name "Amarok Maven"
        url "http://maven.snowlyicewolf.club/"
        allowInsecureProtocol = true
    }
}


dependencies {
    includeCompile 'club.someoneice.pineapplepsychic:pineapple-psychic:1.9'
}
```



