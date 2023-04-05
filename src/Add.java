import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Create the frame box of the add function.
 */
public class Add extends javax.swing.JFrame{
    private JPanel panel1;
    private JButton addButton;
    private JButton exitButton;
    private JTextField tfWord;
    private JTextField tfMeaning;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    public Add() {
        setSize(360, 200);
        setTitle("Thêm từ");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        FileManagement fileManagement = new FileManagement();

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAddMouseClicked(evt);
            }

            void btAddMouseClicked(MouseEvent evt) {
                String word = tfWord.getText();
                String read = tfMeaning.getText();
                if (word.length() == 0 || read.length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Không được để trống thông tin");
                } else if (Dictonary.map.get(word) != null) {
                    JOptionPane.showMessageDialog(rootPane, "Từ khóa của bạn đã tồn tại \n"
                            + "Gợi ý: Bạn nên chọn một từ khóa khác hoặc dùng chức năng thay thế!");
                } else {
                    try {
                        record rs = new record(word, read);
                        Document doc = fileManagement.getDoc();
                        Element dictonary = doc.getDocumentElement();
                        fileManagement.addWord(dictonary, rs);
                    } catch (DOMException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(rootPane, "Đã thêm từ thành công!!");
                    tfWord.setText("");
                    tfMeaning.setText("");
                }
            }
        });
        //
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });

        setVisible(true);
    }
}
