/**
 * This takes two folders as inputs and returns differences
 */

package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FolderProgram {

    JFrame frame = new JFrame("Folder Comparison Program");
    JPanel folderPanel = new JPanel();

    // Make the log
    static JTextArea log = new JTextArea(15, 20);

    // Folder A, B, Output
    private JFileChooser folderA = new JFileChooser();
    private JFileChooser folderB = new JFileChooser();
    private JFileChooser folderOutput = new JFileChooser();
    JButton openButtonA = new JButton("Folder A");
    JButton openButtonB = new JButton("Folder B");
    JButton openButtonOutput = new JButton("Output Destination");
    JTextArea aLocation = new JTextArea();
    JTextArea bLocation = new JTextArea();
    JTextArea outLocation = new JTextArea();
    static File compareA;
    static File compareB;
    static File compareOut;

    public static void main(String[] args) {
        initLookAndFeel();
        FolderProgram gui = new FolderProgram();
        gui.createGui();
    }

    private static void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Using the default look and feel.");
        } catch (ClassNotFoundException e) {
            System.err.println("Using the default look and feel.");
        } catch (InstantiationException e) {
            System.err.println("Using the default look and feel.");
        } catch (IllegalAccessException e) {
            System.err.println("Using the default look and feel.");
        }
    }

    private void createGui() {

        /* Log Section */
        // Make the log
        log.setEditable(false);
        log.setLineWrap(true);
        log.setCaretPosition(log.getDocument().getLength());
        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        /* Folder Section */
        // Three Folder Selection Buttons
        folderA.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderB.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderOutput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openButtonA.addActionListener(new FolderA());
        openButtonB.addActionListener(new FolderB());
        openButtonOutput.addActionListener(new FolderOutput());

        // Arrange the Folder components
        folderPanel.setLayout(new GridLayout(0,2,10,20));
        folderPanel.add(openButtonA);
        folderPanel.add(aLocation);
        folderPanel.add(openButtonB);
        folderPanel.add(bLocation);
        folderPanel.add(openButtonOutput);
        folderPanel.add(outLocation);

        // Run it Button
        JButton run = new JButton("Compare");
        run.addActionListener(new Comparison());
        folderPanel.add(run);

        /* File Menu Location */
        JMenuBar menubar = new JMenuBar();
        menubar.setBorderPainted(true);
        JMenu file = new JMenu("File");
        menubar.add(file);

        JMenuItem clear = new JMenuItem("Clear set directory");
        JMenuItem about = new JMenuItem("About");
        JMenuItem quit = new JMenuItem("Quit");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aLocation.setText("");
                bLocation.setText("");
                outLocation.setText("");
                compareA = null;
                compareB = null;
                compareOut = null;
            }
        });
        about.addActionListener(new AboutThisProgram());
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {frame.dispose();}
        });

        file.add(clear);
        file.add(about);
        file.add(quit);

        frame.setJMenuBar(menubar);
        frame.getContentPane().add(BorderLayout.NORTH, folderPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, logScrollPane);
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class FolderA implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openButtonA) {
                int returnVal = folderA.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File currentA = folderA.getSelectedFile();
                    getFolderA(currentA);
                    folderA.setCurrentDirectory(currentA);
                    aLocation.setText(currentA.getAbsolutePath());
                } else {
                    log.append("Cancelled Selection for " + "FolderA" + "\n");
                }
            }

        }

        public File getFolderA(File f) {
            return compareA = f;
        }

    }

    class FolderB implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openButtonB) {
                int returnVal = folderB.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File currentB = folderB.getSelectedFile();
                    getFolderB(currentB);
                    folderB.setCurrentDirectory(currentB);
                    bLocation.setText(currentB.getAbsolutePath());
                } else {
                    log.append("Cancelled Selection for " + "FolderB" + "\n");
                }
            }
        }

        public File getFolderB(File f) {
            return compareB = f;
        }
    }

    class FolderOutput implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openButtonOutput) {
                int returnVal = folderOutput.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File currentOut = folderOutput.getSelectedFile();
                    getFolderOut(currentOut);
                    folderOutput.setCurrentDirectory(currentOut);
                    outLocation.setText(currentOut.getAbsolutePath());
                } else {
                    log.append("Cancelled Selection for " + "Output Destination" + "\n");
                }
            }
        }

        public File getFolderOut(File f) {
            return compareOut = f;
        }
    }

    class AboutThisProgram implements ActionListener {
        private String message = "This program compares the contents in Folder A with those in "
                + "Folder B. \nIt'll output what's missing from the smaller folder.\n"
                + "This program has an export to .txt feature for future reference.";

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(new JFrame(), message, "About", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
