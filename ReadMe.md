目的 - 修改的`java`及对应编译的`class`太多,容易遗漏, 因此造一个偷懒工具
> 1. 文件从 `sourcefiles` 列表中拷贝到 `temp-folder` 以后会做两个文件的MD5校验, 已避免文件不一致
> 2. 生成的 jar 是使用原有jar`解压并删除`相关(修改的`java`及对应编译的`class`)需要映射的文件, 
> 3. 然后拷贝最新的 (修改的`java`及对应编译的`class`) 到已解压的目录中。重新打包jar, 其他不相干文件不会被修改。
> 4. 文件内路径分隔符使用 `/`, 不适用Windows系统文件分隔符 `\`, Windows 系统认 `/`

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
需要拷贝的文件集(Required)
> source-file=./sourcefiles

sourcefiles 文件svn 提交的源码手机及对应的格式如下
```shell script
src/com/mp/QPaymentRefund/HttpPostService.java
src/com/mp/QPaymentRefund/LIBRefundTran.java
src/com/mp/QPaymentRefund/Factory/MPRefundTran.java
```
源文件路径(Required), 由于从SVN上拷贝, 此处删除 /src 目录 
> source-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome

项目工程编译源文件的class地址(Required)
> compiled-class-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/tdshome/app/stp/classes

临时停放目录(Required)
> temp-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/QPaymentRefund_jar/QPaymentRefund

最终生成文件在 `临时停放目录(Required)`
手动放入jar中
---
如果不想手动放入, 让程序自动打成jar, 需配置如下目录
最终将打包的文件拷贝目录
> target-jar-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/lib/plugins

打包文件名
> target-jar-filename=QPaymentRefund.jar

是否拷贝临时生成的jar即${original-jar-filename}到${target-jar-folder}/${target-jar-filename}, true 最终会拷贝
> copy-temp-jar-2-target-folder=true


