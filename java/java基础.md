读https://github.com/GeniusVJR/LearningNotes/blob/master/Part2/JavaSE/Java基础知识.md
笔记
# java基础

## 八种基本类型
基本类型：int,double,float,character,long,boolean,short,byte
封装类型：Int,Double,Float,Character,Long,Boolean,Short,Byte

## Switch能否用string
java7前不可以，java7后可以。

## equal与==区别
### ==的使用
1. 数值比较
2. 对象比较，比的是对象的内存地址。

### equals
1. object的equals是对象内存地址的比较。
2. String的equals的比较是字符的比较。
3. 有可能某些继承了Object重写了equals方法。

## Object的公有方法
1. clone
2. getClass
3. toString
4. finalize
5. equals
6. hashCode
7. wait
8. notify
9. notifyAll

## java的四种引用，强弱软虚，用到的场景。
### 强引用（Strong Reference）
只要强引用存在则GC时必定不被回收。如Object obj=new Object();

### 软引用（Soft Reference）
用于描述还有用但非必须的对象。当堆将发生OOM是则会回收软引用指向的内存空间,若回收后依然空间不足才会抛出OOM。一般用于实现内存铭感的高速缓存。

### 弱引用（Weak Reference）
发生GC时必定回收弱引用指向的内存空间。

### 虚引用（Phantom Reference）
又称为幽灵引用或幻影引用，虚引用既不会影响对象的生命周期，也无法通过虚引用来获取对象实例，仅用于在发生GC时接收一个系统通知。

## Hashcode的作用
提高获取对象的效率，可以通过hash获取对象的数组位置。

## String、StringBuffer,StringBuilder的区别
String引用的字符串不能改变。
StringBuffer和StringBuilder的字符串可以改变。
StringBuffer是线程安全的，StringBulider是线程不安全的。

## try catch finally ，try里有return，finally还执行吗？
执行。

## Excption与Error区别
Error是错误，是内存溢出，这种程序不能处理的状况。
Excption是需要捕捉或者程序处理的异常，是一种设计和实现的问题。

## Interface与abstact类的区别
抽象类和接口都不能够实例化，但可以定义抽象类和接口类型的引用。
### 区别
1. 接口比抽象类更抽象，因为抽象类可以定义构造器，可以有抽象方法和具体方法，而接口中不能定义构造器而且其中的方法全部都是抽象方法。
2. 抽象类中的成员可以是private，protected，public，默认。而接口只能是public。
3. 抽象类中可以定义成员变量，而接口中定义的成员变量实际都是常量。

有抽象方法的类必须被声明为抽象类，而抽象类未必要有抽象方法。

## foreach、for循环、迭代器的效率
for>迭代器>foreach

## 泛型的优缺点
优点：泛型可以最大限度的重用代码，保护类型的安全以及提高性能。
缺点：在性能上不如数组快。

