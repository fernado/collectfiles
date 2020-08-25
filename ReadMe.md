目的 - 修改的`java`及对应编译的`class`太多,容易遗漏, 因此造一个偷懒工具
> 1. 文件从 `sourcefiles` 列表中拷贝到 `temp-folder` 以后会做两个文件的MD5校验, 已避免文件不一致
> 2. 生成的 jar 是使用原有jar`解压并删除`相关(修改的`java`及对应编译的`class`)需要映射的文件, 
> 3. 然后拷贝最新的 (修改的`java`及对应编译的`class`) 到已解压的目录中。重新打包jar, 其他不相干文件不会被修改。
> 4. 文件内路径分隔符使用 `/`, 不适用Windows系统文件分隔符 `\`, Windows 系统认 `/`

> 由于需使用jdk6来编译。所以start.bat, start.sh 需额外指定jdk版本
```shell script
set JAVA_HOME=C:\PROGRA~1\Java\jdk1.6.0_45
```


---
## 程序打包命令
如果想对以上做`偷懒`自己不手动拷贝文件生成的class及对应的java到jar中
```shell script
%JAVA_HOME%\bin\java -jar collectfiles.jar type=advance
```
如果想自己手动拷贝文件生成的class及对应的java到jar中
```shell script
%JAVA_HOME%\bin\java -jar collectfiles.jar
```

## 描述

sourcefiles 文件svn 提交的源码手机及对应的格式如下
```shell script
src/com/mp/QPaymentRefund/HttpPostService.java
src/com/mp/QPaymentRefund/LIBRefundTran.java
src/com/mp/QPaymentRefund/Factory/MPRefundTran.java
```

config.properties文件配置
```
# **文件内路径分隔符使用 /, 不适用Windows系统文件分隔符 /, Windows 系统认 /
# 需要拷贝的文件集(Required)
source-file=./sourcefiles
# 由于引入了spring place holder, 此处只需修改 source-project
source-project=D:/svn/2020-Aug/2020-Aug-WLB
# 源文件路径(Required), 由于从SVN上拷贝, 此处删除 /src 目录
source-folder=${source-project}/tdshome
# 项目工程编译源文件的class地址(Required)
compiled-class-folder=${source-folder}/app/stp/classes
#临时停放目录(Required)
temp-folder=${source-folder}/QPaymentRefund_jar/QPaymentRefund

# 最终将打包的文件拷贝目录
target-jar-folder=${source-folder}/lib/plugins
# 打包文件名
target-jar-filename=QPaymentRefund.jar
# 原已打包好的文件目录
original-jar-folder=${source-folder}/lib/plugins
# 原已打包好的文件
original-jar-filename=QPaymentRefund.jar
# 是否拷贝临时生成的jar即${original-jar-filename}到${target-jar-folder}/${target-jar-filename}
copy-temp-jar-2-target-folder=true
```


