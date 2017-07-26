/**
 * Compares two folders selected from main.FolderProgram
 */

package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Comparison extends FolderProgram implements ActionListener{

    Integer aSize;
    Integer bSize;
    private final File equal = new File("temp.txt");
    HashMap<Integer, File> map = new HashMap<>();

    public void actionPerformed(ActionEvent e) throws NullPointerException {
        try {
            if (super.compareOut != null) {
                try {
                    FileWriter writer = new FileWriter(super.compareOut.getAbsolutePath() + "\\difference.txt");
                    compare(writer);
                    writer.close();
                } catch (IOException ex) {
                    log.append("Nothing Here");
                }
            } else {
                compare(null);
            }
        } catch (NullPointerException n) {
            String message = "No Folders Selected";
            JOptionPane.showMessageDialog(new JFrame(), message, "No Selection Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void makeMap(Integer x) {
        Integer idx = 0;
        switch (x) {
            case 0: // A < B
                for (int i = 0; i < aSize; i++) {
                    map.put(idx += 1, super.compareA.listFiles()[i]);
                }
                break;
            case 1: // A > B or A = B
                for (int i = 0; i < bSize; i++) {
                    map.put(idx += 1, super.compareB.listFiles()[i]);
                }
                break;
        }
    }

    public void compare(FileWriter writer) throws NullPointerException {
        aSize = super.compareA.listFiles().length;
        bSize = super.compareB.listFiles().length;
        Integer common = 0;

        if (aSize < bSize) {
            makeMap(0);
            for (File item : super.compareB.listFiles()) {
                if (item == null) {
                    log.append("Nothing in FolderA");
                } else if (map.containsValue(item) == false) {
                    common+=1;
                    makeOutput(writer, item);
                    log.append("Not Found in FolderA - \'" + item + "\'\n");
                }
            }
            log.append("\n" + common + " items from FolderB that are not in FolderA\n\n");
            log.append("Folder A < Folder B\n\n");

        } else if (aSize > bSize || aSize == bSize) {
            makeMap(1);
            for (File item : super.compareA.listFiles()) {
                if (item == null) {
                    log.append("Nothing in FolderB");
                } else if (map.containsValue(item) == false) {
                    common += 1;
                    makeOutput(writer,item);
                    log.append("Not Found in FolderB - \'" + item + "\'\n");
                }
            } //nothing different
            if (common == 0 && writer != null) {
                log.append("Nothing different between FolderA and FolderB.\n");
                makeOutput(writer, equal);
            } else {
                log.append("\n" + common + " items from FolderA that are not in FolderB\n\n");
                log.append("Folder A > Folder B\n\n");
            }
        }
    }
    public void makeOutput(FileWriter writer, File item) {
        if (writer != null && item != equal) {
            try {
                writer.write(item.getAbsolutePath() + System.getProperty("line.separator"));
            } catch (IOException e) {
                log.append("Error entering in " + item);
            }
        } else if (writer != null && item == equal) {
            try {
                writer.write("Folder A and Folder B have the same files/folders.");
            } catch (IOException e) {
                log.append("Error writing to export");
            }
        }
    }

}
