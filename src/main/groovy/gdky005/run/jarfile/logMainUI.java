package gdky005.run.jarfile;

import com.alee.laf.WebFonts;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextArea;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import log.PickUpLog;
import log.TakeFileContent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

class LogMainUI extends WebDialog implements ActionListener {

    public static final int width = 450;

    private String filePath;


    private JPanel contentPane;
    private JScrollPane scrollPane;


    private JButton doTaskButton;
    private JButton dragAreaButton;
    private JTextArea textAreaText;
    private JProgressBar progressBar;
    private WebPanel editPanel;
    private WebButton retry;

    public LogMainUI() {


        WebPanel webPanel = new WebPanel();
        WebPanel taskPanel = new WebPanel();
        WebPanel contentPanel = new WebPanel();
        WebPanel buttonPanel = new WebPanel();
        textAreaText = new WebTextArea();
        progressBar = new WebProgressBar();


        doTaskButton = new WebButton("开始收集崩溃日志");
        doTaskButton.setFont(getSystemTextFont());

        taskPanel.add(BorderLayout.NORTH, doTaskButton);

        taskPanel.add(BorderLayout.SOUTH, progressBar);


        WebLabel webLabel = new WebLabel("请将崩溃日志的文件拖动到下面区域");
        webLabel.setFont(getSystemTextFont());
        contentPanel.add(BorderLayout.NORTH, webLabel);

        dragAreaButton = new WebButton("拖拽区域");
        dragAreaButton.setFont(getSystemTextFont());
        editPanel = new WebPanel();


        editPanel.add(BorderLayout.CENTER, dragAreaButton);

        buttonPanel.add(BorderLayout.CENTER, editPanel);
        contentPanel.add(BorderLayout.CENTER, buttonPanel);
        taskPanel.add(BorderLayout.CENTER, contentPanel);
        webPanel.add(taskPanel);


        setContentPane(webPanel);
        setModal(true);

        textAreaText.setLineWrap(true);

        progressBar.setOrientation(JProgressBar.HORIZONTAL);
        progressBar.setValue(100);
        progressBar.setAutoscrolls(true);
        progressBar.setVisible(false);

        doTaskButton.addActionListener(this);


        dropTarget();

    }

    private void dropTarget() {
        DropTarget dropTarget = new DropTarget(dragAreaButton, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
                        List<File> list = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String tempPath = "";

                        if (list != null) {
                            for (File file : list) {
                                tempPath = file.getAbsolutePath();
                                break;
                            }
                        }

                        filePath = tempPath;

                        dragAreaButton.setText("你选择的文件路径是:" + filePath);

//                        showDialog(tempPath);

//                JOptionPane.showMessageDialog(null, temp);
                        dtde.dropComplete(true);//指示拖拽操作已完成
                    } else {
                        dtde.rejectDrop();//否则拒绝拖拽来的数据
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dragAreaButton.setDropTarget(dropTarget);
    }

    private void showDialog(String tempPath) {

        JLabel jLabel = new JLabel(tempPath);

        jLabel.setText(tempPath);
        jLabel.setFont(WebFonts.getSystemTitleFont());

        WebOptionPane.showMessageDialog(jLabel, tempPath);
    }


    private boolean isSuccess = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doTaskButton) {

            if (!isSuccess) {
                if (filePath != null && filePath != "") {

                    editPanel.remove(dragAreaButton);

                    scrollPane = new JScrollPane(textAreaText);
                    retry = new WebButton("重新收集日志");
                    retry.setFont(getSystemTextFont());
                    retry.addActionListener(this);


                    editPanel.add(BorderLayout.CENTER, scrollPane);
                    editPanel.add(BorderLayout.SOUTH, retry);

                    dragAreaButton.setVisible(false);
                    progressBar.setVisible(true);

                    textAreaText.setText("正在处理中...");
                    textAreaText.setFont(WebFonts.getSystemTitleFont());
                    textAreaText.setRows(25);


                    String filePath = PickUpLog.handlerFileErrorLog(this.filePath);

                    String content;
                    if (filePath != null && !filePath.equals("")) {
                        TakeFileContent takeFileContent = new TakeFileContent();
                        content = takeFileContent.getContent(filePath);
                    } else {
                        content = "暂时没有获取到崩溃日志";
                    }
                    textAreaText.setText(content);


                    progressBar.setVisible(false);
                    isSuccess = true;


                } else {
                    showDialog("您没有选择文件,请选择后重试!");
                }
            } else {
                showDialog("请点击 重新收集日志");
            }


        } else if (e.getSource() == retry) {
            isSuccess = false;
            editPanel.add(BorderLayout.CENTER, dragAreaButton);
            dragAreaButton.setVisible(true);
            dragAreaButton.setText("拖拽区域");


            editPanel.remove(scrollPane);
            editPanel.remove(retry);

            filePath = null;
            getContentPane().notifyAll();
        }
    }

    private Font getSystemTextFont() {
//        return WebFonts.getSystemTextFont();
        return new Font("dialog", Font.PLAIN, 12);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        doTaskButton = new JButton();
        doTaskButton.setText("Button");
        panel1.add(doTaskButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("请将崩溃日志的文件拖动到下面区域");
        panel2.add(label1, BorderLayout.NORTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, BorderLayout.CENTER);
        dragAreaButton = new JButton();
        dragAreaButton.setText("拖拽区域");
        panel3.add(dragAreaButton, BorderLayout.CENTER);
        scrollPane = new JScrollPane();
        panel3.add(scrollPane, BorderLayout.SOUTH);
        textAreaText = new JTextArea();
        textAreaText.setColumns(10);
        textAreaText.setRows(0);
        scrollPane.setViewportView(textAreaText);
        progressBar = new JProgressBar();
        contentPane.add(progressBar, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
