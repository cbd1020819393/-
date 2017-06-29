读http://wiki.jikexueyuan.com/project/java-collection/hashmap.html的笔记
# HashMap的实现原理

## HashMap概述
HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用 null 值和 null 键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
HashMap不是同步的，如果多个线程同时访问一个HashMap，而其中至少一个线程从结构上修改了，则必须保持外部同步，以防止对映射进行意外的非同步访问。

## HashMap的数据结构
HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体。
![](http://wiki.jikexueyuan.com/project/java-collection/images/hashmap1.jpg)

## HashMap存储
当我们往HashMap中put元素的时候，先根据key的hashCode重新计算hash值，根据hash值得到这个元素在数组中的位置。如果该数组位置上已经存放有其他元素了，那么在这个位置上元素将以链表的形式存放，新加入的放在链头，最先加入的放在链尾。
