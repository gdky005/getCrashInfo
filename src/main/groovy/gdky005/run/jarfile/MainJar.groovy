package gdky005.run.jarfile

import com.alee.laf.WebLookAndFeel
import com.alee.laf.panel.WebPanel
import com.alee.laf.rootpane.WebFrame

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * Created by WangQing on 15/12/7.
 */
class MainJar {

    public static void main(String[] args) {

        WebFrame webFrame = initFrame()


        WebPanel webPanel = new WebPanel();
        WebPanel webPaneBig = new WebPanel();
        Button controlBtn = new Button("启动控制器");

        LogMainUI dialog = new LogMainUI();

        controlBtn.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                webPanel.removeAll();
                webPanel.add(BorderLayout.CENTER, new ControlDirection().topJPanel);
                webPanel.validate();
            }
        })


        webPanel.add(BorderLayout.NORTH, controlBtn);
        webPanel.add(BorderLayout.CENTER, dialog.getContentPane());

        webPaneBig.add(webPanel)
        webFrame.add(webPaneBig);


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
        frame.setLocation(500, 150)
        frame.setSize(LogMainUI.width, 600)

        frame
    }
}
