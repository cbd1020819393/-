# SQLite

## SQLite简介
SQLite是一个软件库，实现了自给自足的、无服务的、零配置的、事务性的SQL数据库引擎。

## SQLite的优点
1. 不需要一个单独的服务器进程或操作的系统（无服务器的）。
2. SQLite 不需要配置，这意味着不需要安装或管理。
3. 一个完整的 SQLite 数据库是存储在一个单一的跨平台的磁盘文件。
4. SQLite 是非常小的，是轻量级的，完全配置时小于 400KiB，省略可选功能配置	时小于250KiB。
5. SQLite 是自给自足的，这意味着不需要任何外部的依赖。
6. SQLite 事务是完全兼容 ACID 的，允许从多个进程或线程安全访问。
7. SQLite 支持 SQL92（SQL2）标准的大多数查询语言的功能。
8. SQLite 使用 ANSI-C 编写的，并提供了简单和易于使用的 API。
9. SQLite 可在 UNIX（Linux, Mac OS-X, Android, iOS）和 Windows	（Win32, WinCE, WinRT）中运行。

## SQL命令
### DDL-数据定义语言
CREATE:创建一个新的表，一个表的视图，或者数据库中的其他对象。
ALTER:修改数据库中的某个已有的数据库对象，比如一个表。
DROP:删除整个表，或者表的视图，或者数据库中的其他对象。

### DML-数据操作语言
INSERT：创建一条记录。
UPDATE：修改记录。
DELETE：删除记录。

### DDL-数据查询语言
SELECT：从一个或多个表中检索某些记录。