package Server;


import java.io.IOException;

import com.illposed.osc.OSCPortIn;

import javax.swing.SwingUtilities;

import GUI.TestOneGUI;

/**
 * This is the main execution class. This begins the listening threads and runs
 * the front-end of the GUI associated with it.
 * 
 * @author marquez
 * 
 */
public class Main {
    
    public static void main(String[] args) throws IOException {        
//        // sets the port in which data is collected
//        int PORT = 5555;
//
//        // Instantiates the receiver on the specific port
//        OSCPortIn receiver = new OSCPortIn(PORT);
//        // Instantiates the listener which calls the database object
//        MessageListener listener = new MessageListener();
//
//        // Adds addresses from the mobile client to the listener
//        receiver.addListener("/1/xy1", listener);
//        receiver.addListener("/2/multifader1/1", listener);
//        receiver.addListener("/2/multifader1/2", listener);
//        receiver.addListener("/2/multifader1/3", listener);
//        receiver.addListener("/2/multifader1/4", listener);
//        receiver.addListener("/1/toggle1", listener);
//        receiver.addListener("/2/toggle1", listener);
//        
//        // begins listening
//        receiver.startListening();
//        // creates a thread pointing to the receiver's runnable file
//        Thread listenThread = new Thread(receiver);
//        // starts the listening thread
//        listenThread.start();
        
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TestOneGUI gui = new TestOneGUI();

                gui.setVisible(true);
            }
        });
    }
}
