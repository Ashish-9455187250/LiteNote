import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import java.io.*;

class TextEditor implements ActionListener {

    JFrame jFrame;
    JMenuBar menuBar;
    JMenu file, edit, themes;
    JTextArea jTextArea;
    JScrollPane jScrollPane;
    JMenuItem darkTheme, moonLightTheme, defaultTheme, save, open, close, cut, copy, paste, New, selectAll, fontSize;
    
    JPanel saveFileWindow;
    JLabel fileLabel, dirlabel;
    JTextField filename, dirName;

    TextEditor(){
        jFrame = new JFrame("Untitled_Document-1");


        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        themes = new JMenu("Themes");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(themes);
        jFrame.setJMenuBar(menuBar);


        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        New = new JMenuItem("New");
        close = new JMenuItem("Exit");

        file.add(open);
        file.add(New);
        file.add(save);
        file.add(close);


        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        fontSize = new JMenuItem("Font Size");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(fontSize);


        darkTheme = new JMenuItem("Dark theme");
        moonLightTheme = new JMenuItem("Moonlight Theme");
        defaultTheme = new JMenuItem("Default theme");
        themes.add(darkTheme);
        themes.add(moonLightTheme);
        themes.add(defaultTheme);


        jTextArea = new JTextArea(32,88);
        jFrame.add(jTextArea);


        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jFrame.add(jScrollPane);


        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        fontSize.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        New.addActionListener(this);
        darkTheme.addActionListener(this);
        moonLightTheme.addActionListener(this);
        defaultTheme.addActionListener(this);
        close.addActionListener(this);


        jFrame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(jFrame,"Do you want to exit?","Confirm Before Saving...", JOptionPane.YES_NO_OPTION);

                if(confirmExit == JOptionPane.YES_OPTION){
                    jFrame.dispose();
                }
                else if(confirmExit == JOptionPane.NO_OPTION){
                    jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event){ }
        
            @Override
            public void keyPressed(KeyEvent event){
                int keyCode = event.getKeyCode();
                if(keyCode == KeyEvent.VK_S && event.isControlDown()){
                    saveTheFile();
                }
            }
            @Override
            public void keyReleased(KeyEvent event){}
        };
        
        jTextArea.addKeyListener(k);
        
        jFrame.setSize(1000, 600);
        jFrame.setResizable(true);
        jFrame.setLocation(250, 100);
        jFrame.setLayout(new FlowLayout());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        @Override
        public void actionPerformed(ActionEvent event){
        
        if (event.getSource()==cut){
            jTextArea.cut();
        }
        if (event.getSource()==copy){
            jTextArea.copy();
        }
        if (event.getSource()==paste){
            jTextArea.paste();
        }
        if(event.getSource()==selectAll){
            jTextArea.selectAll();
        }
        
        if (event.getSource()==fontSize){
            String sizeofFont = JOptionPane.showInputDialog(jFrame, "Enter Font size", JOptionPane.OK_CANCEL_OPTION);
                if(sizeofFont != null){
                    int convertSizeofFont = Integer.parseInt(sizeofFont);
                    Font font = new Font(Font.SANS_SERIF,Font.PLAIN,convertSizeofFont);
                    jTextArea.setFont(font);
                }
        
        }
        
        if(event.getSource()==open){
            JFileChooser chooser = new JFileChooser();
            int i = chooser.showOpenDialog(jFrame);
            if(i==JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                String filePAth = file.getPath();
                String fileNameToShow = file.getName();
                jFrame.setTitle(fileNameToShow);

                try{
                    BufferedReader reader = new BufferedReader(new FileReader(filePAth));
                    String temString1 = "";
                    String temString2 = "";

                    while ((temString1 = reader.readLine()) != null)
                        temString2 += temString1 + "\n";

                    
                        jTextArea.setText(temString2);
                        reader.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        
        }

        if(event.getSource()==save){
            saveTheFile();
        }

        if(event.getSource()==New){
            jTextArea.setText("");
        }

        if(event.getSource()==close){
            System.exit(1);
        }

        if (event.getSource()==darkTheme){
            jTextArea.setBackground(Color.DARK_GRAY);
            jTextArea.setForeground(Color.WHITE);
        }

        if (event.getSource()==moonLightTheme){
            jTextArea.setBackground(new Color(107, 169, 0255));
            jTextArea.setForeground(Color.black);
        }

        if (event.getSource()==defaultTheme){
            jTextArea.setBackground(new Color(255,255,255));
            jTextArea.setForeground(Color.black);
        }
    }

    public void saveTheFile(){
        saveFileWindow = new JPanel(new GridLayout(2,1));
        fileLabel = new JLabel("Filename     :- ");
        dirlabel = new JLabel("Save File to  :- ");
        filename = new JTextField();
        dirName = new JTextField();


        saveFileWindow.add(fileLabel);
        saveFileWindow.add(dirlabel);
        saveFileWindow.add(filename);
        saveFileWindow.add(dirName);

        JOptionPane.showMessageDialog(jFrame, saveFileWindow);
        String fileContent = jTextArea.getText();
        String filePath = jTextArea.getText();

        try{
            BufferedWriter content = new BufferedWriter(new FileWriter(filePath));
            content.write(fileContent);
            content.close();
            JOptionPane.showMessageDialog(jFrame, "File Successfully Saved! ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
    
}


