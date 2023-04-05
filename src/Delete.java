import org.w3c.dom.DOMException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Create the frame for delete function.
 */
public class Delete extends javax.swing.JFrame {
    private JPanel panel1;
    private JButton deleteButton;
    private JButton exitButton;
    private JTextField tfWord;
    private JTextField tfMeaning;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    public Delete(String newWord) {
        setSize(360, 200);
        setTitle("Xóa từ");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        FileManagement fileManagement = new FileManagement();

        tfWord.setText(newWord);
        tfMeaning.setText(Dictonary.map.get(newWord));

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                btAddMouseClicked(e);
            }

            void btAddMouseClicked(MouseEvent evt) {
                String word = tfWord.getText();
                String read = tfMeaning.getText();
                try {
                    fileManagement.deleteWord(word);
                    //Ghi file writeFile();
                } catch (DOMException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(rootPane, "Đã xóa từ thành công!");
                Dictonary.map.remove(word);
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
