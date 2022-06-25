package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Socket.Connection;

public class KeyboardInput implements KeyListener {

    Connection connection;

    public KeyboardInput(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int inp = e.getKeyCode();

        if (inp == KeyEvent.VK_W) {
            connection.sendRequest("press W");
        } else if (inp == KeyEvent.VK_S) {
            connection.sendRequest("press S");
        }

        if (inp == KeyEvent.VK_A) {
            connection.sendRequest("press A");
        } else if (inp == KeyEvent.VK_D) {
            connection.sendRequest("press D");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int inp = e.getKeyCode();

        if (inp == KeyEvent.VK_W) {
            connection.sendRequest("release W");
        }
        if (inp == KeyEvent.VK_A) {
            connection.sendRequest("release A");
        }
        if (inp == KeyEvent.VK_S) {
            connection.sendRequest("release S");
        }
        if (inp == KeyEvent.VK_D) {
            connection.sendRequest("release D");
        }

    }

}
