package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import javax.swing.JOptionPane;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

//STILL UNDER CONSTRUCTION. Gui should be able to display despite the errors currently here.

/**
 * This is the listener to the messages and sends messages to the light server
 * and stores info in the database
 * 
 * @author marquez
 * 
 */
public class MessageListener implements OSCListener {
	// This is the database to be accessed
	private final InetAddress serverIp;
	private final int serverPort;

	/**
	 * Constructs the object and uses the given database
	 * 
	 * @param database
	 *            This is the database to be accessed by the listener.
	 * @throws UnknownHostException
	 *             This is the ip error for the light osc server
	 */
	public MessageListener() throws UnknownHostException {
		this.serverIp = InetAddress.getByName("lights.media.mit.edu");
		this.serverPort = 10002;
		
		//bytes to whatever he's expecting

		// TODO: SET TO ACTIVE WHEN FINISHED TESTING
		sendMessage("/set_inactive", null);
	}

	/**
	 * This method sends OSC messages to the server at the ip addess and port
	 * specified in this object's constructor
	 * 
	 * @param identifier
	 *            This is the identifier and command of the light which is
	 *            identified as /sr##/COMMAND
	 * @param values
	 *            this is a float array of the values sent to the server in the
	 *            form of a float array (one value for red, green, blue, and
	 *            three values for rgb
	 */
	public void sendMessage(String identifier, Float[] values) {
		// creates the sender
		OSCPortOut sender;

		// tries to send the message and displays an error if something goes
		// wrong
		try {
			// sets a vlaue to the sender
			sender = new OSCPortOut(this.serverIp, this.serverPort);
			// instantiates the message
			OSCMessage msg;

			// assigns the message with the identifer and the values
			msg = new OSCMessage(identifier, values);

			// sends the message
			sender.send(msg);

			sender.close();
		} catch (SocketException e1) {
			// displays an error
			JOptionPane.showMessageDialog(null,
					"THERE WAS AN ERROR WITH THE OUTGOING PORT");
		} catch (IOException e) {
			// displays an error
			JOptionPane
					.showMessageDialog(null,
							"THERE WAS AN ERROR WITH SENDING THE MESSAGE TO THE LIGHT SERVER");
		}
	}

    @Override
    public void acceptMessage(Date arg0, OSCMessage arg1) {
        //shouldn't ever have to run this
        System.out.println("WHY YOU DO THIS?!");
        
    }
}
