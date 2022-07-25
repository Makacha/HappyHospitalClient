package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;

import Socket.Connection;

public class SaveMapEvent implements ActionListener {

    Connection connection;

    SaveMapEvent(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        connection.sendRequest("save");
        String data = connection.getData();
        JFileChooser fileSave = new JFileChooser();
        int res = fileSave.showOpenDialog(null);
        fileSave.setCurrentDirectory(new File("/home/me/Documents/save"));
        if (res == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fileSave.getSelectedFile() + ".json")) {
                fw.write(data.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
