
## 程序打包命令
```shell script
mvn clean compile package -Dmaven.test.skip=true
```
可以参考start.bat
```shell script
java -jar collectfiles.jar
```


> **文件内路径分隔符使用 /, 不适用Windows系统文件分隔符 \, Windows 系统认 / **

## 描述
需要拷贝的文件集(Required)
> source-file=./sourcefiles

源文件路径(Required), 由于从SVN上拷贝, 此处删除 /src 目录 
> source-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome

项目工程编译源文件的class地址(Required)
> compiled-class-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/tdshome/app/stp/classes

临时停放目录(Required)
> temp-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/QPaymentRefund_jar/QPaymentRefund

最终生成文件在 `临时停放目录(Required)`
手动放入jar中
---
### 以下暂无用, 等后期开发
最终将打包的文件拷贝目录
> target-jar-folder=D:/svn/2019-icbc/mpay_1.0.1.8_P2019100901_icbc/tdshome/lib/plugins

打包文件名
> target-jar-filename=QPaymentRefund.jar



