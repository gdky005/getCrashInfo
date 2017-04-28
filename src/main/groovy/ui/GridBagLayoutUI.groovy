package ui

import javax.swing.*
import java.awt.*

/**
 * Created by WangQing on 15/12/7.
 */

JFrame frame =new JFrame();

GridBagLayout grid=new GridBagLayout();
GridBagConstraints c1=new GridBagConstraints();
frame.setLayout(grid);

//为button1进行约束
c1.gridwidth=1;     c1.gridheight=1;
c1.insets=new Insets(5,5,0,0);        //和上面的组件间距为5,右边为间距5
JButton button1=new JButton("doTaskButton");
grid.setConstraints(button1,c1);
frame.add(button1);

//为button2进行约束
c1.fill=GridBagConstraints.HORIZONTAL;
JButton button2=new JButton("左Button");
grid.setConstraints(button2,c1);
frame.add(button2);
c1.insets
//为button3进行约束
c1.gridx=0;          c1.gridy=1;          //动态表格（0，1）位置
c1.gridwidth=4;         //组件长占4个单元格，高占一个单元格
JButton button3=new JButton("右Button");
grid.setConstraints(button3,c1);
frame.add(button3);
frame.setVisible(true);
frame.setSize(200,150);