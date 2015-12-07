package ui

import javax.swing.*
import java.awt.*

JFrame frame=new JFrame();
frame.add(BorderLayout.NORTH,new JButton("North"));
frame.add(BorderLayout.SOUTH,new JButton("South"));
frame.add(BorderLayout.WEST,new JButton("West"));
frame.add(BorderLayout.EAST,new JButton("East"));
frame.add(BorderLayout.CENTER,new JButton("Center"));
frame.setVisible(true);
frame.setSize(400,200);