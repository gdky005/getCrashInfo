package gdky005.run.jarfile;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebDialog;
import log.PickUpLog;
import log.TakeFileContent;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class LogMainUI extends WebDialog implements ActionListener {

    public static final int width = 400;

    private String filePath;


    private JPanel contentPane;
    private JButton doTaskButton;
    private JButton dragAreaButton;
    private JTextArea textAreaText;
    private JProgressBar progressBar;
    private JScrollPane scrollPane;

    public LogMainUI() {
        setContentPane(contentPane);
        setModal(true);

        textAreaText.setLineWrap(true);

        progressBar.setOrientation(JProgressBar.HORIZONTAL);
        progressBar.setValue(100);
        progressBar.setAutoscrolls(true);
        progressBar.setVisible(false);

//        scrollPane.setPreferredSize(new Dimension(width,300));
        scrollPane.setVisible(false);

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
        WebOptionPane.showMessageDialog(null, tempPath);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doTaskButton) {

            if (filePath != null && filePath != "") {

                dragAreaButton.setVisible(false);
                progressBar.setVisible(true);

                scrollPane.setVisible(true);
                textAreaText.setText("正在处理中...");
                textAreaText.setRows(25);


                String filePath = PickUpLog.handlerFileErrorLog(this.filePath);

                TakeFileContent takeFileContent = new TakeFileContent();

                textAreaText.setText(takeFileContent.getContent(filePath));

                progressBar.setVisible(false);


            } else {
                showDialog("您没有选择文件,请选择后重试!");
            }

        }
    }
}
