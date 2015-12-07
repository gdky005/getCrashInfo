package gdky005.run.jarfile
import com.alee.laf.WebLookAndFeel
import com.alee.laf.rootpane.WebFrame

import javax.swing.*
import java.awt.*
/**
 * Created by WangQing on 15/12/5.
 */

WebFrame webFrame = initFrame()

//webFrame.add(new WebButton("hello"))

LogMainUI dialog = new LogMainUI();


webFrame.add(dialog.getContentPane())

show(webFrame)

private show(WebFrame webFrame) {
    webFrame.setVisible(true)
}


private WebFrame initFrame() {

    WebLookAndFeel.install()

    WebFrame frame = new WebFrame("获取JNI崩溃日志")
    frame.setTopBg(Color.blue)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭的事件
    frame.setLocation(500, 150)
    frame.setSize(LogMainUI.width, 500)

    frame
}