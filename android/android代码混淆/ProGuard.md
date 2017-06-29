# ProGuard
## 介绍
![](https://www.guardsquare.com/files/media/guardsquare2016/Website/ProGuard/ProGuard_build_transparant.png)
通常说的proguard包括四个功能，shrinker（压缩），optimizer(优化)，obfuscator(混淆)，preverifier(预校验)。
* shrink:检测并移除没有用到的类，变量，方法和属性。
* optimize:优化代码，非入口节点类会加上private/static/final,没有用到的参数会被删除，一些方法可能会变成内联代码。
* obfuscate:使用短又没有语义的名字重命名非入口类的类名，变量名，方法名。入口类的名字保持不变。
* preverify:预校验代码是否符合java1.6或者更高的规范（唯一一个与入口类不相关的步骤）。

## keep配置
### -keep [,modifier, ...] class_specification
指定类和类的成员变量时入口节点，保护他们不被移除混淆。
```
-keep public class mypackage.MyMain {
    public static void main(java.lang.String[]);
}
```
### -keepclassmembers[,modifier]class_specification
保护的指定的成员变量不被移除、优化、混淆。
```
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
```
### -keepclasseswithmembers[,modifier,...]class_specification
拥有指定成员的类将被保护，根据类成员确定一些将要被保护的类。
```
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}
```
### -keepnames class_specification
是-keep，allowshrinking class_pecification的简写。指定一些类名受到保护，前提是他们在shrink这一阶段没有被去掉。也就是说没有杯入口节点之间或间接引用的类还是会被删除。仅在obfuscate阶段有效。
### -keepclassmembernames class_specification 
是keepclasseswithmembers,allowshrinking class_specification的简写。与-keepclassmember相似。保护指定的类成员，前提是这些成员在shrink阶段没有被删除。仅在obfuscate阶段有效。
### -keepclasseswithmembernames class_specification 
是-keepclasseswithmembers,allowshrinking class_specification的简写。与-keepclasseswithmembers类似。保护指定的类，如果它们没有在shrink阶段被删除。仅在obfuscate阶段有效。
### -printseeds[filename]
指定通过-keep配置匹配的类或者类成员的详细列表。列表可以打印到标准输出流或者文件里面。这个列表可以看到我们想要保护的类或者成员有没有被真正的保护到，尤其是那些使用通配符匹配的类。

## 代码压缩配置
### -dontshrink
声明不压缩输入文件。 
### -printusage[filename]
声明打印出那些被删除的元素。仅在shrink阶段有效。
### whyareyoukeeping class_specification
声明打印为什么一个类或类的成员变量被保护。

## 代码优化配置
### -dontoptimize
声明不优化输入文件。默认优化是开启的，并且所以优化都是在字节码层进行。
### -optimizations optimization_filter
更加细粒度地声明优化开启或者关闭。只在optimize这一阶段有效。这个选项的使用难度较高。
### -optimizationpasser n
指定执行几次优化，默认情况下，只执行一次优化。执行多次优化可以提高优化的效果，但是，如果执行过一次优化之后没有效果，就会停止优化，剩下的设置次数不再执行。这个选项只在optimizate阶段有效。
### -assumenosideeffects class_specification
指定一些方法杯删除也没有影响（尽管这些方法可能有返回值）。在optimize阶段，如果确定这些方法的返回值没有使用，那么就会删除这些方法的调用。这条配置选项只在optimizate阶段有用。
```
# 删除代码中Log相关的代码
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
```
### -allwaccessmodification
设置是否允许改变作用域的。使用这项配置之后可以提高优化的效果。但是，如果你的代码是一个库的话，最好不要配置这个选项，因为它可能会导致一些private变量被改成public。
### -mergeinterfacesaggressively
指定一些接口可能被合并，即使一些子类没有同时实现两个接口的方法。

## 混淆配置
### -dotobfuscate 
声明不混淆。默认情况下，混淆是开启的。除了keep配置声明的类。
### -printmapping[filename]
指定输出新旧元素名的对照表文件。映射表会被输出到标准输出流或者是一个指定的文件。
### -applymapping filename
指定重用一个已经写好了的map文件作为新旧元素名的映射。仅在obfuscate阶段有效。
### -obfuscationdictionary filename 
指定一个文本文件用来生成混淆后的名字。
1. 可以使用一些非英文字符作为类名，成员变量名，方法名。
2. 字典文件中的空格，标点符号，重复的词还有以'#'开头的行都会被忽略。
3. 添加了字典并不会显著提高混淆的效果，只不过是更不利于阅读。

字典文件的例子：
```
do
if
for
int
new
try
```
### -classobfuscationdictionary filename
指定一个混淆类名的字典，混淆class的类名。字典的格式与-obfuscationdictionary相同。
### -packageobfuscationdictionary filename 
指定一个混淆包名的字典，混淆package的包名。字典格式与-obfuscationdictionary相同。
### -overloadaggressively
混淆的时候大量使用重载，多个方法名使用同一个混淆名，但是他们的方法签名不同。这可以使包的体积减小一部分，也可以加大理解的难度。仅在混淆阶段有效。
### -useuniqueclassmembernames 
指定相同的混淆名对应相同的方法名。不同的混淆名对应不同的方法名。如果不设置这个选项，同一个类中将会有很多方法映射到相同的方法名。
### dotusemixedcaseclassnames 
指定在混淆的时候不适用大小写混用的类名。默认情况下，混淆后的类名可能同时包含大写字母和小写字母。只有在大小写不敏感的系统（例如windows）上解压时，才会涉及到这个问题。因为大小写不区分，可能会导致部分文件在解压的时候相互覆盖。如果有在windows系统上解压输出包的需求的话，可以加上这个配置。
### -keeppackagenames[package+filter]
声明不混淆指定的包名。配置的过滤器是都好隔开的一组包名。包名可以包含？，*通配符，并且可以在前面加！否定符。
### -flatternpackagehierarchy[packagename]
所有重新命名的包都重新打包，并把所有的类移动到packagename包下面。
### -repackageclasses[package_name]
所有重写命名过的类都重新打包，并把他们移动到指定的package那么目录下。它可以代码体积更小，并且更加难以理解。
### -keepattributes[attribute_filter]
指定受保护的属性，可以有一个或者多个keepattributes配置项，每个配置项后面跟随的是Java虚拟机和proguard支持的attribute(具体支持的属性先看这里)，两个属性之间用逗号分隔。
```
-keepattributes SourceFile, LineNumberTable
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# 可以直接写在一行
-keepattributes Exceptions, InnerClasses, Signature, Deprecated,
                SourceFile, LineNumberTable, *Annotation*, EnclosingMethod
```
### -keepparameternames 
指定被保护的方法的参数类型和参数名不被混淆。这项配置在混淆一些类库的时候特别有用，因为根据IDE提示的参数名和参数类型，开发者可以根据他们的语义获得一些信息，这样的类库更友好。

### -renamesourcefileattribute[string]
指定一个字符串常量设置到源文件的类的属性当中。这样就可以在-keepattributes配置中使用。

### -adaptclassstrings[classfilter]
指定字符串常量如果与类名相同，也需要被混淆。

### -adaptresourcefilenames[file_filter]
如果资源文件与某类名相同，那么混淆后资源文件被命名为与之对应的类的混淆名。

### -adaptsourcefilecontents[file_filter]
指定资源文件中的类名随混淆后的名字更新。

## 预校验配置
### -dotpreverify
声明不预校验即将执行的类。默认情况下，在类文件的编译版本为micro版本或者大于1.6版本时，预校验是开启的。目标文件针对java6的情况下，预校验是可选的；针对java7的情况下，预校验是必须的，除非目标运行平台是Android平台，设置它可以节省一点点时间。目标为Java Micro版本的情况下，预校验是必须的。如果你声明了这项配置，你还需要加上下面一条配置。

### -microedition 
声明目标平台是java micro版本。预校验会根据这项配置加载合适的StackMap，而不是用标准的StackMap。

##普通配置
### -verbose
声明在处理过程中输出更多信息。添加这项配置之后，如果处理过程中出现异常，会输出整个StackTrace而不是一条简单的异常说明。

### -dontnote[class_filter]
声明不输出那些潜在的错误和缺失，比如说错别字或者重要的配置缺失。


### -dontwarn[class_filter]
声明不输出那些未找到的应用和一些错误。

### -ignorewarnings
输出所有找不到引用和一些其他错误的警告，但是继续执行处理过程。

### -printconfiguration[filename]
输出整个处理过程中的所有配置参数，包括文件中的参数和命令行中的参数。

### -dump[filename]
声明输出整个处理之后的jar文件的类结构。

