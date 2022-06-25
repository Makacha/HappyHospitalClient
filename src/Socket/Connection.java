package Socket;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import Graphic.Entity;

public class Connection {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Connection() {
        try {
            socket = new Socket("localhost", 8888);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public Entity[] needDraw() {
        String receiveData = null;
        ArrayList<String> receiveEntitiesInfo = new ArrayList<String>();
        sendRequest("draw");
        while (true) {
            try {
                receiveData = dataInputStream.readUTF();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.getStackTrace();
                receiveData = "end";
            }
            if (receiveData.equals("end"))
                break;
            receiveEntitiesInfo.add(receiveData);
        }
        Entity[] entities = new Entity[receiveEntitiesInfo.size()];
        for (int i = 0; i < entities.length; i++) {
            entities[i] = new Entity(receiveEntitiesInfo.get(i));
        }
        return entities;
    }

    public void sendRequest(String data) {
        try {
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void disconect() {
        sendRequest("finish");
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
}
