import org.w3c.dom.DOMException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Create the frame for replace(editWord) function.
 */
public class Replace extends javax.swing.JFrame{
    private JPanel panel1;
    private JButton replaceButton;
    private JButton exitButton;
    private JTextField tfWord;
    private JTextField tfMeaning;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    public Replace(String newWord) {
        setSize(360, 200);
        setTitle("Sửa từ");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        FileManagement fileManagement = new FileManagement();

        tfWord.setText(newWord);
        tfMeaning.setText(Dictonary.map.get(newWord));
        setVisible(true);
        replaceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                super.mouseClicked(evt);
                btReplaceMouseClicked(evt);
            }

            void btReplaceMouseClicked (MouseEvent evt) throws DOMException {
                String word = tfWord.getText();
                String read = tfMeaning.getText();
                if (word.length() == 0 || read.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Không được để trống thông tin!");
                } else if (Dictonary.map.get(word) != null) {
                    record rs =new record(word, read);
                    fileManagement.editWord(rs, newWord);
                    //---------------------------------------------
                    //Ghi file
                    //writeFile();
                    JOptionPane.showMessageDialog(rootPane, "Đã sửa từ thành công!");
                    dispose();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    };
}