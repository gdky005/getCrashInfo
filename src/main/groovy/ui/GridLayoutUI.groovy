package ui

import javax.swing.*
import java.awt.*

/**
 * Created by WangQing on 15/12/7.
 */

JFrame frame=new JFrame();
frame.setLayout(new GridLayout(3,2));   //3行2列的表格布局
for(int i = 0; i < 7; i++)
    frame.add(new JButton("Button " + i));
frame.setVisible(true);
frame.setSize(500,300);