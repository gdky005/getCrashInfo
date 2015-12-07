package ui

import javax.swing.*
import java.awt.*

/**
 * Created by WangQing on 15/12/7.
 */

JFrame frame=new JFrame();
frame.setLayout(new FlowLayout());
for(int i = 1; i <=5; i++)
    frame.add(new JButton("Button " + i));
frame.setVisible(true);
frame.setSize(500,100);