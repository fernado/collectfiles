```shell script
java -cp target\collect-files.jar pr.iceworld.fernando.AppMain
```

## 描述
需要拷贝的文件集(Required)
> source-file=./sourcefiles

源文件路径(Required), 由于从SVN上拷贝, 此处删除 /src 目录 
> source-folder=C:\\Users\\fernando\\Documents\\SVN\\mpay\\tdshome

项目工程编译源文件的class地址(Required)
> compiled-class-folder=C:\\Users\\fernando\\Documents\\SVN\\mpay\\tdshome\\app\\stp\\classes

临时停放目录(Required)
> temp-folder=C:\\Users\\fernando\\Documents\\SVN\\mpay\\tdshome\\QPaymentRefund_jar\\QPaymentRefund

最终将打包的文件拷贝目录
> target-jar-folder=C:\\Users\\fernando\\Documents\\SVN\\mpay\\tdshome\\lib\\plugins

打包文件名
> target-jar-filename=QPaymentRefund.jar