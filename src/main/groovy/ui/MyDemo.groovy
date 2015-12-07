package ui
import javax.swing.*
import java.awt.*
/**
 * Created by WangQing on 15/12/7.
 */

JFrame jFrame = new JFrame()
jFrame.add(BorderLayout.NORTH, new JButton("处理日志"))


JPanel jPanel = new JPanel()


jPanel.add(BorderLayout.NORTH, new JTextArea("kjkjk"))

jFrame.add(BorderLayout.CENTER, jPanel)

//jFrame.add(BorderLayout.CENTER, new JTextArea(""))

jFrame.setVisible(true)
jFrame.setSize(400,400)