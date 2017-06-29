# android四种检测性能的工具
## lint
lint在android studio中使用非常方便，只要点击Analyze目录中的inspect code就可以自动检测，检测完会生成一堆分析的东西，点击就会跳转到相应的文件，使用简单，效果超级好。
作用：排查无用资源，提示不当代码行为，可以优化代码。
## findbugs
1. 首先装插件，在setting中的plugins里搜索安装，重启android studio就可以。
2. 可配置过滤
3. 添加自动化配置
```
apply plugin: "findbugs"

//findbugs静态代码分析自动化配置
task findbugs(type: FindBugs,dependsOn:'assembleDebug') {
    ignoreFailures= true
    effort= "default"
    reportLevel= "high"
    println( "$project.buildDir")
    classes = files("$project.buildDir/intermediates/classes")
    source= fileTree("src/main/java/")
    classpath= files()
    reports{
        xml.enabled=false
        html.enabled=true
        xml {
            destination "$project.buildDir/findbugs.xml"
        }
        html{
            destination "$project.buildDir/findbugs.html"
        }
    }
}
```
4. 使用跟lint相似，点击检测运行，就会有一个列表，使用其实相当方便，根据提示就可以根据上下文理解bug所在。

## PMD
 事实上 PMD 是一个非常强大的工具，它的作用类似 Findbugs，但是它的检测扫描是基于源码的，而且 PMD 不仅仅能检测 Java 语言，还能检测其他语言。PMD 的目标和 Findbugsd 非常的相似，都是通过定义的规则静态分析代码中可能出现的错误。 由于 Findbugs 和 PMD 的扫描方式不一样，PMD 能发现的一些 Findbugs 发现不了的问题，反之亦然。所以两个一起用对你的工程会更好。有互补作用。

使用方式也与findbugs相近。在tools目录下里面有个QAPlugs，点击分析即可。
```
task pmd(type: Pmd) {
    ruleSetFiles = files("${project.rootDir}/config/quality/pmd/pmd-ruleset.xml")
    ignoreFailures = false
    ruleSets = []

   source 'src'
   include '**/*.java'
   exclude '**/gen/**'

    reports {
        xml.enabled = false
        html.enabled = true
    xml {
        destination "$project.buildDir/reports/pmd/pmd.xml"
    }
   html {
        destination "$project.buildDir/reports/pmd/pmd.html"
       }
    }
}
```

## CheckStyles
配置方式相同
```
check.dependsOn 'checkstyle'

task checkstyle(type: Checkstyle) {
    source 'src'
    configFile file("config/checkstyle.xml")
    include '**/*.java'
    exclude '**/gen/**'
    ignoreFailures true

    classpath = files()
}
```



