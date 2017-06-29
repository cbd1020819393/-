# SQLite语法

## 大小写敏感性
SQLite是不区分大小写的，但也有一些命令是大小写敏感的，比如GLOB和glob在SQLite的语句中有不同的含义。

## 注释
SQLite注释是附加的注释，可以在SQLite代码中添加注释以增加其可读性，他们可以在任何空白处，包括在表达内和其他SQL语句的中间，但它们不能嵌套。
1. 可以用连续两个“-”开始
2. /* */

## SQLite语句
所有的SQLite语句可以以任何关键字开始，如SELECT、INSERT、UPLETE、ALTER、DROP等，所有的语句以分号结束。

