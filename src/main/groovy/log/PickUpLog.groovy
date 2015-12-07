package log

/**
 *
 * 提取日志成功
 *
 * 分析JNI日志点：
 * 1. Build fingerprint: 'Honor/H30-L01/hwH30:4.4.2/HonorH30-L01/C00B256:user/ota-rel-keys,release-keys' 一般是JNI日志的第一句，关键字是：
 *  『Build fingerprint』 并且，在开始部分一定会包含 『DEBUG』
 *
 * 2. 其中一个坑点是： 可能某一行 是空的没有数据，一定要记得排除这类
 *
 * 3. 我们获取日志的记录是规则是：获取到关键字后，可以看到 基本是连续的（之所以说是基本，因为其中可能还真 会断开一小节），等下一句没有debug 后，说明
 * JNI日志已经结束了，所以我们就应该结束获取。 这里需要注意的是，我们必须要记得上一行是不是debug 的状态，所以要一个变量来标记。
 *
 * 4. 在最后结尾部分 我们需要将收集的信息保存成文件，方便我们查阅。
 *
 *
 *
 * 扩展功能点：
 *  1. 优化点：我们把生成的文件 按照我们需要的格式，替换成对应的文件，例如：md文件，或是html 更标记出来 特别要注意的地方  OK
 *  2. 便捷性：使用java 生成一个 窗体，然后 拖拽文本到里面，获取文件路径，把固定的路径替换成功。
 *  3. 输出目标：我们把JNI文件放到一个新文件里面，有多个，可以写个文件目标，存储，明白是关键路径
 *  4. 可以抓取其他崩溃信息，例如空指针等：
 *
 * Created by WangQing on 15/12/2.
 */
class PickUpLog {

    public static def pidTag = "pid"
    public static def lineRNTag = "\r\n"
    public static def lineNTag = "\n"
    public static def signalTag = "signal"
    public static def backtraceTag = "backtrace"
    public static int backtraceCountTag = 0
    public static def debugTextTag = "DEBUG   : "
    public static def deviceInfoTag = "Build fingerprint: '"
    public static def codeModeTag = "    "


    public static String handlerFileErrorLog(String filePath) {

        def file = filePath
        File fileP = new File(file)
        String fileParentPath = fileP.getParent()

        ArrayList detailBugInfoList = new ArrayList();
        ArrayList summaryBugInfoList = new ArrayList();

        handlerPerLine(file, summaryBugInfoList, detailBugInfoList)


//        def jniDetailInfoName = fileParentPath + "/out/my_info.md"
//        def jniSummaryInfoName = fileParentPath + "/out/jni_summary_info.md"
//
        def jniDetailInfoName = fileParentPath + "/out/my_info.md"
        def jniSummaryInfoName = fileParentPath + "/out/jni_summary_info.md"


        generaFile(jniDetailInfoName, detailBugInfoList)
        generaFile(jniSummaryInfoName, summaryBugInfoList)

        jniSummaryInfoName
    }

//    public static void main(String[] args) {
//        handlerFileErrorLog("./assets/error_log.txt")
//    }


//    public static void main(String[] args) {
//
//        def file = "./assets/error_log.txt"
//
//        ArrayList detailBugInfoList = new ArrayList();
//        ArrayList summaryBugInfoList = new ArrayList();
//
//        handlerPerLine(file, summaryBugInfoList, detailBugInfoList)
//
//
//        def jniDetailInfoName = "./out/my_info.md"
//        def jniSummaryInfoName = "./out/jni_summary_info.md"
//
//
//        generaFile(jniDetailInfoName, detailBugInfoList)
//        generaFile(jniSummaryInfoName, summaryBugInfoList)
//    }

    /**
     * 处理每一行的数据
     *
     * @param file
     * @param summaryBugInfoList
     * @param detailBugInfoList
     * @param isAppend
     * @param upWord
     * @param count
     * @return
     */
    private static void handlerPerLine(String file, ArrayList summaryBugInfoList, detailBugInfoList) {

        int count = 0;
        boolean upWord = false;
        boolean isAppend = false;

        new File(file).eachLine { line ->

            if (line.contains("DEBUG") && line.contains("Build fingerprint")) {
                isAppend = true
                upWord = true


                summaryBugInfoList.add("# 主要崩溃日志：" + lineRNTag)
                summaryBugInfoList.add("### 手机和应用信息：" + lineRNTag)
                summaryBugInfoList.add(codeModeTag +
                        line.subSequence(line.lastIndexOf(deviceInfoTag) + deviceInfoTag.length(), line.length() - 1) +
                        lineNTag)


                detailBugInfoList.add("# 应用崩溃详细日志：" + lineRNTag)
                detailBugInfoList.add(handlerLineText(true, deviceInfoTag, line))
                return
            }

            if (isAppend && upWord && (line.contains("DEBUG") || line.equals(""))) {
                if (!line.equals("")) {

                    getSummaryBugInfo(summaryBugInfoList, line)

                    detailBugInfoList.add(handlerLineText(true, debugTextTag, line))
                }
            } else if (isAppend && upWord && !line.contains("DEBUG")) {
                if (count == 3) {
                    count = 0

                    isAppend = false
                    upWord = false
                } else {
                    count++
                }
            }
        }
    }

    /**
     * 获取概要数据信息
     *
     * @param summaryBugInfoList
     * @param line
     */
    private static void getSummaryBugInfo(ArrayList summaryBugInfoList, String line) {
        if (line.contains(pidTag)) {
            summaryBugInfoList.add("### 应用进程和包名：" + lineRNTag)
            summaryBugInfoList.add(handlerLineText(true, debugTextTag, line) + lineNTag)
        }

        if (line.contains(signalTag)) {
            summaryBugInfoList.add("### 系统崩溃信息：" + lineRNTag)
            summaryBugInfoList.add(handlerLineText(true, debugTextTag, line) + lineNTag)
        }

        if (line.contains(backtraceTag)) {
            summaryBugInfoList.add("### 主要崩溃原因：" + lineRNTag)
            backtraceCountTag++;
        }


        if (backtraceCountTag > 5) {
            backtraceCountTag = 0
        } else if (backtraceCountTag >= 1) {
            backtraceCountTag++

            summaryBugInfoList.add(handlerLineText(true, debugTextTag, line) + lineNTag)
        }
    }

    /**
     * 根据获取的到的list将数据保存到文件中
     *
     * @param bugInfoList     获取信息后的List
     * @param fileName    生成文件的名字
     */
    private static void generaFile(String fileName, ArrayList bugInfoList) {
        if (bugInfoList != null && bugInfoList.size() > 0) {

            File file1 = new File(fileName);

            if (file1.exists()) {
                file1.delete()
            } else {
                String filePath = file1.getParent()
                new File(filePath).mkdirs()

                file1.createNewFile()
            }

            new File(fileName).withPrintWriter { printWriter ->

                StringBuffer stringBuffer = new StringBuffer();

                for (int i = 0; i < bugInfoList.size(); i++) {
                    stringBuffer.append(bugInfoList.get(i) + "\r\n")
                }

                printWriter.println(stringBuffer.toString())
            }
        }
    }

    /**
     * 根据关键字获取 获取处理结果后的文本
     *
     * @param keyWord   关键字
     * @param line  当前一行是文本
     * @return 处理后的结果
     */
    private static CharSequence handlerLineText(boolean isCode, String keyWord, String line) {

        CharSequence lineText = line.subSequence(line.lastIndexOf(keyWord) + (keyWord.length() - 1), line.length())

        if (isCode) {
            lineText = codeModeTag + lineText //使用md格式的code方式
        }
        lineText
    }


}
