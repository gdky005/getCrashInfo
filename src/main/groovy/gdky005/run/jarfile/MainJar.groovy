package gdky005.run.jarfile

import com.alee.laf.WebLookAndFeel
import com.alee.laf.rootpane.WebFrame

import javax.swing.*
import java.awt.*

/**
 * Created by WangQing on 15/12/7.
 */
class MainJar {

    public static void main(String[] args) {

        WebFrame webFrame = initFrame()
//        LogMainUI dialog = new LogMainUI();
//        webFrame.add(dialog.getContentPane())
//
//        show(webFrame)

        ControlDirection controlDirection = new ControlDirection()

        webFrame.add(controlDirection.topPanel)
        show(webFrame)
    }


    private static show(WebFrame webFrame) {
        webFrame.setVisible(true)
    }


    private static WebFrame initFrame() {

        WebLookAndFeel.install()

        WebFrame frame = new WebFrame("测试工具")
        frame.setTopBg(Color.blue)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭的事件
        frame.setLocation(450, 100)
        frame.setSize(LogMainUI.width, 600)

        frame
    }
}
