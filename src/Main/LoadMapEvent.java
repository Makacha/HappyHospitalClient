package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import Socket.Connection;

public class LoadMapEvent implements ActionListener {

    Connection connection;

    LoadMapEvent(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileUpload = new JFileChooser();
        int res = fileUpload.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File filePath = new File(fileUpload.getSelectedFile().getAbsolutePath());
            StringBuilder fileData = new StringBuilder();
            try (Scanner myReader = new Scanner(filePath)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    fileData.append(data);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            connection.sendRequest("load " + fileData.toString());
        }
    }

}
