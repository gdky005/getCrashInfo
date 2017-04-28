package gdky005.run.jarfile;

import kaola.monkey.StartCommond;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by WangQing on 2017/4/28.
 */
public class ControlDirection implements ActionListener {

    private static final int KEYCODE_ENTER = 66;
    private static final int KEYCODE_DPAD_CENTER = 23;
    private static final int KEYCODE_DPAD_UP = 19;
    private static final int KEYCODE_DPAD_DOWN = 20;
    private static final int KEYCODE_DPAD_LEFT = 21;
    private static final int KEYCODE_DPAD_RIGHT = 22;



    private JButton 上Button;
    private JButton 下Button;
    private JButton OKButton;
    private JButton 左Button;
    private JButton 右Button;
    private JButton 确定Button1;
    private JButton 取消Button;
    public JPanel topJPanel;

    public ControlDirection() {
        上Button.addActionListener(this);
        下Button.addActionListener(this);
        OKButton.addActionListener(this);
        左Button.addActionListener(this);
        右Button.addActionListener(this);
        确定Button1.addActionListener(this);
        取消Button.addActionListener(this);

    }

    /**
     界面支持方向键，事件是android的标准事件；
     KEYCODE_ENTER     回车键     66
     KEYCODE_DPAD_CENTER     导航键 确定键     23
     KEYCODE_DPAD_UP     导航键 向上     19
     KEYCODE_DPAD_DOWN     导航键 向下     20
     KEYCODE_DPAD_LEFT     导航键 向左     21
     KEYCODE_DPAD_RIGHT     导航键 向右     22

     adb可模拟方向键，方法：adb shell  input  keyevent  21/22/19...

     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {


        String text = "";
        int directionCode = 0;

        if (e.getSource() == 上Button) {
            text = "上";
            directionCode = KEYCODE_DPAD_UP;
        } else if (e.getSource() == 下Button) {
            text = "下";
            directionCode = KEYCODE_DPAD_DOWN;
        }else if (e.getSource() == 左Button) {
            text = "左";
            directionCode = KEYCODE_DPAD_LEFT;
        } else if (e.getSource() == 右Button) {
            text = "右";
            directionCode = KEYCODE_DPAD_RIGHT;
        } else if (e.getSource() == OKButton) {
            text = "Ok";
            directionCode = KEYCODE_ENTER;
        } else if (e.getSource() == 确定Button1) {
            text = "确定";
            directionCode = KEYCODE_ENTER;
        } else if (e.getSource() == 取消Button) {
            text = "取消";
            directionCode = KEYCODE_DPAD_CENTER;
        }

        String adbShell = "adb shell input keyevent " + directionCode;
        StartCommond cmd = new StartCommond();
        cmd.commond(adbShell, text);
    }

}
