package kaola.monkey
/**
 * Created by WangQing on 15/11/20.
 */

def adbShell = "adb shell"

def monkey = """monkey -p com.itings.myradio -c android.intent.category.LAUNCHER -s 500
                    --hprof --ignore-crashes
                    --ignore-timeouts
                    --ignore-security-exceptions
                    --monitor-native-crashes
                    --throttle 50 -v -v -v 15000000>>/mnt/sdcard/monkey_kaola.txt &
                    logcat  -v time >>/mnt/sdcard/logcat_kaola.txt &
                 """

def startMokenyCmd = "${adbShell} ${monkey}"

println("启动的考拉monkey 的命令是: ${startMokenyCmd}")

startMokenyCmd.execute()

println("开启考拉Monkey的命令成功")