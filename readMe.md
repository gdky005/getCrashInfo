
### 使用指南

从手机上抓取 崩溃日志导出到电脑后,会产生一个很大的文件,少则几M,多则几十M, 一般情况下是直接打开文本或者用命令行直接搜索关键字.但是这种方法略微有点复杂,不够清晰明了

所以出现了该工具.

我们可以将崩溃的日志拖拽到界面区域,然后点击获取崩溃日志,然后就会在当前目录产生out 目录,里面有 刚生产的日志文件,目前分为三种(后续可能会添加类型):
- jni详细崩溃日志 (方便排查一些地址信息)
- jni 大体崩溃信息 (更人性化,足够普通用户使用)
- 应用程序突然崩溃 主要日志

以上三种基本满足平时的开发使用

具体使用指南和相关原理,请关注网站:

[http://www.gdky005.com](http://www.gdky005.com)



- 添加了 Weblaf

### 必备条件和运行方式

##### 必须要安装jdk 或是 jre

- 下载该项目后,直接使用 Android 或是 idea 编译器导入

-  运行方式
> gradle uberjar

- 可以直接使用 release 里面的jar, 如果安装了jre 或是 jdk 可以直接点击使用
- 也可以使用命令行运行:
> java -jar logCollect-1.0.jar

- windows 用户,可以直接点击 .bat 直接运行, 务必将两个文件夹放在一起




### 运行的效果图是:

![](https://github.com/gdky005/gradle-groovy-runJar/blob/master/image/error_log.gif?raw=true)


### 参考地址:
https://github.com/bond-/gradle-groovy-jar-example
