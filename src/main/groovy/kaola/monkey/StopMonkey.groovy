package kaola.monkey
/**
 * Created by WangQing on 15/11/20.
 */
def adbShell = "adb shell"
def killCMD = "kill"
def pattern = ~/([0-9]+)/
def getMonkeyCMD = "ps | grep monkey"


//获取当前进程中的 monkey 信息
def mokenyCmd = "${adbShell} ${getMonkeyCMD}"
Process monkeyCmd = mokenyCmd.execute()
def message = monkeyCmd.text
println("当前获取adb shell info: " + message)



//如果获取的信息不为空,那么就启动停止monkey的命令
if (message != null) {
    def matcher = pattern.matcher(message)
    def count = matcher.getCount()
    println "Matches count: ${count}"

    if (count > 0) {
        matcher[0][0]

        def appProcessId = matcher.group(0)
        println("匹配的第一个数字是:" + appProcessId)

        println "当匹配的是：" + appProcessId

        def mokenyKillCmd ="${adbShell} ${killCMD} ${appProcessId}"

        println("停止monkey 的命令是: " + mokenyKillCmd)
        Process monkeyKillResult = mokenyKillCmd.execute()

        println "Kill 结果是" + monkeyKillResult.text
    }
} else {
    println "停止失败"
}