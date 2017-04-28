package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by WangQing on 15/12/6.
 */
class HandlerFileUi extends JFrame{
    private JTextField loadingText;
    private JPanel panel1;
    private JProgressBar progressBar;

    public Container getHandlerFileUi() throws HeadlessException {
        Container container = getContentPane();

        panel1 = new JPanel();

        loadingText = new JTextField();

        loadingText.setHorizontalAlignment(SwingConstants.CENTER);

        panel1.add(loadingText);

        progressBar = new JProgressBar();

        progressBar.setAutoscrolls(true);

        panel1.add(progressBar);

        container.add(panel1);

        return container;
    }
}
