import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.*;
import java.io.IOException;

/**
 * Create the frame of the dictionary with all functions.
 */
public class MainFrame extends javax.swing.JFrame{
    private JTextArea meaning;
    private JTextField textField;
    private JList word;
    private JButton translateButton;
    private JButton deleteButton;
    private JButton googleButton;
    private JButton editButton;
    private JButton addButton;
    private JButton speechButton;
    private JButton refreshButton;
    private JPanel main;
    private JScrollPane scrollPaneMeaning;
    private JScrollPane scrollPaneWord;
    private JPanel panel;
    private JFrame addFrame;
    private JFrame editFrame;
    private JFrame deleteFrame;

    DefaultListModel<String> model = new DefaultListModel<>();
    FileManagement fileManagement = new FileManagement();
    private DefaultListModel lst;

    public MainFrame() {
        setSize(600, 450);
        setContentPane(main);
        setTitle("Từ điển Anh_Việt 1.0");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        fileManagement.readFileXml();
        //Create functions and button.
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                textFieldRelease(e);
            }
        });

        word.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jListChange(e);
            }
        });

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                translatePerformed();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                meaning.setText("");
            }
        });

        speechButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speakPerformed(e);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFrame = new Add();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (word.isSelectionEmpty() || Dictonary.map.get(word.getSelectedValue()) == null) {
                    JOptionPane.showMessageDialog(rootPane, "\tBạn chưa chọn từ!");
                }
                else {
                    editFrame = new Replace(textField.getText());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (word.isSelectionEmpty() || Dictonary.map.get(word.getSelectedValue()) == null) {
                    JOptionPane.showMessageDialog(rootPane, "\t\t\tBạn chưa chọn từ!");
                }
                else {
                    deleteFrame = new Delete(textField.getText());
                }
            }
        });

        googleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                googlePerformed(e);
            }
        });
    }
    private void textFieldRelease(KeyEvent e) {
        // Find all the words with the following string in textField.
        String search = textField.getText();
        DefaultListModel<String> model1 = new DefaultListModel<>();
        Dictonary.map.keySet().forEach((String key) -> {
            int check = key.indexOf(search);
            if (check == 0) {
                model1.addElement(key);
            }
        });

        if (search.equalsIgnoreCase("")) {
            meaning.setText("");
            model.addElement(search);
            word.setModel(model);
        } else {
            word.setModel(model1);
        }

    }

    private void jListChange(MouseEvent e) {
        if (word.getSelectedValue() != null) {
            String name = (String) word.getSelectedValue();
            textField.setText(name);
            meaning.setText(Dictonary.map.get(name));
        }
    }

    private void translatePerformed() {
        String tab = textField.getText();
        if (Dictonary.map.get(tab) != null) {
            meaning.setText(Dictonary.map.get(tab));
        } else meaning.setText("Từ cần tìm không có trong danh sách\n" +
                "Bạn có thể sử dụng chức năng dịch online");
    }

    private void speakPerformed(ActionEvent e) {
        final String VOICENAME = "kevin16";
        Voice voice;
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice(VOICENAME);
        voice.allocate();
        voice.speak(textField.getText());
    }

    private void googlePerformed(ActionEvent e) {
        try {
            GoogleTranslator translator = new GoogleTranslator();
            translator.setSrcLang(GoogleTranslator.LANGUAGE.ENGLISH);
            translator.setDestLang(GoogleTranslator.LANGUAGE.VIETNAMESE);
            String data = translator.translate(textField.getText());
            meaning.setText(data);
        } catch (IOException | ParseException ex) {
        }
    }
}
