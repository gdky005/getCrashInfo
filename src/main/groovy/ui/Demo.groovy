package ui

import javax.swing.*
import java.awt.*

/**
 * Created by WangQing on 15/12/7.
 */
JPanel interfacePanel;
JPanel userPanel;
JLabel userLabel;
JComboBox userComboBox;
JLabel messageLabel;
JButton sendButton;
JTextField messageText;
JTabbedPane textTabbedPane;
JScrollPane publicScrollPane;
JTextPane publicTextPane;
JScrollPane ScrollPane;
JTextPane TextPane;


interfacePanel = new JPanel();
interfacePanel.setLayout(new BorderLayout(10, 10));
//两个菜单项
////实例化菜单与菜单项
//JMenu[] menus = { new JMenu("File"); new JMenu("Action") };
//JMenuItem[] items = { new JMenuItem("Save"); new JMenuItem("Exit") };
//menus[0].add(items[0]);
//menus[0].add(items[1]);
////实例化菜单棒，添加菜单项至菜单棒
//JMenuBar mb = new JMenuBar();
//mb.add(menus[0]);
//mb.add(menus[1]);
////设置菜单条的位置在界面的最上方
//interfacePanel.add(mb, BorderLayout.NORTH);

//界面中央的信息面板
//实例化共有和私有的文本域 、 滚动面板、设置不可读
publicTextPane = new JTextPane();
publicScrollPane = new JScrollPane(publicTextPane);
publicTextPane.setEditable(false);

TextPane = new JTextPane();
ScrollPane = new JScrollPane(TextPane);
TextPane.setEditable(false);
//实例化动态选项卡
textTabbedPane = new JTabbedPane();
textTabbedPane.addTab("public", publicScrollPane);
textTabbedPane.addTab("", ScrollPane);
textTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
interfacePanel.add(textTabbedPane, BorderLayout.CENTER);

//界面底部的用户面板
//实例化并初始化化各组件
userPanel = new JPanel();
userLabel = new JLabel("    Send to :");
//userComboBox = new JComboBox();
//String[] users = { "Public"; "ClientB"; "CientA" };
//userComboBox.addItem(users[0]);
//userComboBox.addItem(users[1]);
//userComboBox.addItem(users[2]);
messageLabel = new JLabel("Message:");
messageText = new JTextField(22);
sendButton = new JButton("Send");
//为下面的uesePanel面板进行布局
//userPanel 设置为两行一列的网格布局，两行分别放两个面板，userPanel2.与userPanel
userPanel.setLayout(new GridLayout(2, 1));
JPanel userPanel2 = new JPanel();
JPanel userPanel3 = new JPanel();
userPanel.add(userPanel2);
userPanel.add(userPanel3);

//第一行的面板 userPanel2 采用网格精准定位布局，并添加一个标签与组合框
userPanel2.add(userLabel);
//userPanel2.add(userComboBox);
GridBagLayout gridbag = new GridBagLayout();
userPanel2.setLayout(gridbag);
//对第一行第二个组件组合框进行布局约束,实例化一个对象C
GridBagConstraints c = new GridBagConstraints();
//当组合框被拉伸后所按的的比例
c.weightx = 1;
c.weighty = 1;
//当组件框所占的单位行数还有剩余的时候，组件的填充方式为水平
c.fill = GridBagConstraints.HORIZONTAL;
//组件与组件之前的距离，参数依次为上 左 下 右
c.insets = new Insets(0, 10, 0, 5);
//将布局约束添加在组合框上
//gridbag.setConstraints(userComboBox, c);

//第二行的面板 userPanel3采用流布局，添加一个标签，一个输入文本的框，一个发送按钮
userPanel3.setLayout(new FlowLayout());
userPanel3.add(messageLabel);
userPanel3.add(messageText);
userPanel3.add(sendButton);
//放置在页面下方，并添加面板到用户面板中去
interfacePanel.add(BorderLayout.SOUTH, userPanel);
JFrame frame = new JFrame();
frame.add(interfacePanel);
frame.setVisible(true);
frame.setSize(410, 400);
