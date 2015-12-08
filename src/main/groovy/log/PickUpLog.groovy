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
    public static def debugTag = "DEBUG"
    public static def crashAnrDetectorTag = "CrashAnrDetector"
    public static def buildFingerprintTag = "Build fingerprint"
    public static def lineRNTag = "\r\n"
    public static def lineNTag = "\n"
    public static def signalTag = "signal"
    public static def backtraceTag = "backtrace"
    public static int backtraceCountTag = 0
    public static def debugTextTag = "DEBUG   : "
    public static def deviceInfoTag = "Build fingerprint: '"
    public static def codeModeTag = "    "
    public static def mvpCode

    public static int count = 0;
    public static int jniSummaryCount = 1;
    public static boolean upWord = false;
    public static boolean isAppend = false;

    public static ArrayList normalTempArrayList





    public static void main(String[] args) {
//        def file = "./assets/error_log.txt"
        def file = "./assets/normal_error_log1.txt"

        handlerFileErrorLog(file)
    }

    public static String reset() {
        count = 0;
        jniSummaryCount = 1;
        backtraceCountTag = 0;
        normalMaxCount = -1
        errorCount = 1
        upWord = false;
        isAppend = false;
        isAddNormalState = false
        isBlodState = false
        isFatalLog = false
        normalMaxCount = -1
        errorCount = 1
    }


    public static String handlerFileErrorLog(String filePath) {
        reset()

        def file = filePath
        File fileP = new File(file)
        String fileParentPath = fileP.getParent()
        String fileName = fileP.getName()

        if (fileName != null && !fileName.equals("")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."))
        }


        normalTempArrayList = new ArrayList()

        ArrayList detailBugInfoList = new ArrayList();
        ArrayList summaryBugInfoList = new ArrayList();
        ArrayList normalBugInfoList = new ArrayList();

        handlerPerLine(file, summaryBugInfoList, detailBugInfoList, normalBugInfoList)

        def jniDetailInfoName = fileParentPath + "/out/" + fileName + "-jni_info.md"
        def jniSummaryInfoName = fileParentPath + "/out/" + fileName + "-jni_summary_info.md"
        def normalSummaryInfoName = fileParentPath + "/out/" + fileName + "-normal_summary_info.md"


        generaFile(jniDetailInfoName, detailBugInfoList)
        generaFile(jniSummaryInfoName, summaryBugInfoList)
        generaFile(normalSummaryInfoName, normalBugInfoList)

        if (new File(normalSummaryInfoName).exists()) {
            normalSummaryInfoName
        } else if (new File(jniSummaryInfoName).exists()){
            jniSummaryInfoName
        }

    }

    /**
     * 处理每一行的数据
     *
     * @param file
     * @param jniSummaryBugInfoList
     * @param jniDetailBugInfoList
     * @param isAppend
     * @param upWord
     * @param count
     * @return
     */
    private static void handlerPerLine(String file, ArrayList jniSummaryBugInfoList, ArrayList jniDetailBugInfoList,
                                       ArrayList normalBugInfoList) {


        new File(file).eachLine { line ->

            getFatalErrorLog(line, normalBugInfoList)
            getJniErrorLog(line, jniSummaryBugInfoList, jniDetailBugInfoList)
        }
    }

    public static def fatalFlag = "FATAL"
    public static def processFlag = "Process"
    public static def exceptionFlag = "EXCEPTION: main"
    public static def excepFlag = "Exception"
    public static def androidRuntimeFlag = "AndroidRuntime"
    public static def androidRuntimeKeyWord = "AndroidRuntime: "
    public static def firstOfndroidRuntimeKeyWord = ": "
    public static def isAddNormalState = false
    public static def isBlodState = false
    public static def isFatalLog = false
    //最多累计15个字符, 默认为-1
    public static def normalMaxCount = -1
    public static int errorCount = 1

    public static String deviceInfo



    /**
     *
     * @param line
     * @param normalBugInfoList
     */
    private static void getFatalErrorLog(String line, ArrayList normalBugInfoList) {

        if (line.contains("Build fingerprint")) {
            deviceInfo = handlerLineText(false, deviceInfoTag, line)
        }

        if (line.contains(fatalFlag) && line.contains(androidRuntimeFlag)) {
            normalTempArrayList.clear()
            isAddNormalState = true
            normalMaxCount = 15
        }

        if (isAddNormalState && normalMaxCount >= 0 && line.contains(androidRuntimeFlag)) {
            if (!line.equals("")) {
                normalMaxCount--

                if (!isBlodState && line.contains(exceptionFlag)) {
                    isBlodState = true
                }

                if (isBlodState) {
                    isFatalLog = true
                    isBlodState = false

                    normalBugInfoList.add("# fatal 崩溃日志:" + lineRNTag + " " + lineRNTag)
                    normalBugInfoList.add("## 设备信息是: " + deviceInfo)

                }

                if (line.contains(excepFlag) && line.contains(androidRuntimeFlag) && !line.contains(fatalFlag)) {
                    mvpCode = handlerLineText(true, firstOfndroidRuntimeKeyWord, line).trim()
                }

                normalTempArrayList.add(handlerLineText(true, firstOfndroidRuntimeKeyWord, line) + lineNTag)



                if (normalMaxCount == 0) {

                    String mainKeyWord = "### 崩溃 " + (errorCount++) + ": " + mvpCode  + "<br><br>"
                    normalBugInfoList.add(mainKeyWord)
                    mvpCode = ""

                    normalBugInfoList.addAll(normalTempArrayList)
                    normalTempArrayList.clear()
                    isAddNormalState = false
                    normalMaxCount = -1
                }

            }
        }



    }

    /**
     * 获取 JNI日志功能
     * @param line
     * @param jniSummaryBugInfoList
     * @param jniDetailBugInfoList
     */
    private static void getJniErrorLog(String line, ArrayList jniSummaryBugInfoList, ArrayList jniDetailBugInfoList) {
        if ((line.contains(debugTag) && line.contains(buildFingerprintTag)) || (line.contains(crashAnrDetectorTag) && line.contains(buildFingerprintTag))) {
            isAppend = true
            upWord = true


            jniSummaryBugInfoList.add("# 主要崩溃日志：" + lineRNTag)
            jniSummaryBugInfoList.add("### 手机和应用信息：" + lineRNTag)
            jniSummaryBugInfoList.add(handlerLineText(true, deviceInfoTag, line))
            jniDetailBugInfoList.add("# 应用崩溃详细日志 " + (jniSummaryCount++) + "：" + lineRNTag)
            jniDetailBugInfoList.add(handlerLineText(true, deviceInfoTag, line))
            return
        }

        if (line.contains("CrashAnrDetector: backtrace")) {
            println "test"
        }

        if (isAppend && upWord && ((line.contains(crashAnrDetectorTag)|| line.equals("")) || (line.contains(debugTag) || line.equals("")))) {
            if (!line.equals("")) {

                getSummaryBugInfo(jniSummaryBugInfoList, line)

                jniDetailBugInfoList.add(handlerLineText(true, debugTextTag, line))
            }
        } else if (isAppend && upWord && !line.contains(debugTag)) {
            if (count == 3) {
                count = 0

                isAppend = false
                upWord = false
            } else {
                count++
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
     * @param bugInfoList 获取信息后的List
     * @param fileName 生成文件的名字
     */
    private static void generaFile(String fileName, ArrayList bugInfoList) {
        if (bugInfoList != null && bugInfoList.size() > 0) {

            fileName = fileName.replace("assets", "build")

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
                    stringBuffer.append(bugInfoList.get(i) + lineRNTag)
                }

                printWriter.println(stringBuffer.toString())
            }
        }
    }

    /**
     * 根据关键字获取 获取处理结果后的文本
     *
     * @param keyWord 关键字
     * @param line 当前一行是文本
     * @return 处理后的结果
     */
    private static String handlerLineText(boolean isCode, String keyWord, String line) {
        def lineText = line.subSequence(line.indexOf(keyWord) + (keyWord.length() - 1), line.length())

        if (isCode) {
            lineText = codeModeTag + lineText //使用md格式的code方式
        }
        lineText
    }


}
