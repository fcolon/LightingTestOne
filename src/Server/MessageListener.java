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
	private final Data database;
	private Float lastMessage = null;

	/**
	 * Constructs the object and uses the given database
	 * 
	 * @param database
	 *            This is the database to be accessed by the listener.
	 * @throws UnknownHostException
	 *             This is the ip error for the light osc server
	 */
	public MessageListener() throws UnknownHostException {
		this.database = new Data();
		this.serverIp = InetAddress.getByName("lights.media.mit.edu");
		this.serverPort = 10002;
		
		//bytes to whatever he's expecting

		// TODO: SET TO ACTIVE WHEN FINISHED TESTING
		sendMessage("/set_inactive", null);
	}

//	/**
//	 * This is ran every time the OSCport receives an OSCMessage and it stores
//	 * data into the database given an address of "/1/xy1", "/2/multifader1/#",
//	 * "/1/toggle1", and "/2/toggle1"
//	 */
//	public void acceptMessage(Date executionTime, OSCMessage message) {
//		// Address of the message (aka component name)
//		String address = message.getAddress();
//		// Data in an object array
//		Object[] data = message.getArguments();
//
//		// returns if the data is empty or null
//		if (!(data.length != 0 && data[0] != null)) {
//			return;
//		}
//
//		// adds a point to the database
//		if (address.equals("/1/xy1")) {
//			
//			// catch bugs and flashes and malformed packets
//			if (!(data.length >= 2) &&
//					!(data[1] == null) &&
//					!((Float) data[1]).equals((Float) 1.611492E-10f) &&
//					!(((Float) data[1]) > 1f)) {
//				return;
//			}
//
//			// filters the x and y to the correct axes
//			Float filteredX = this.database.filterVal((Float) data[0], 100000,
//					1);
//			Float filteredY = this.database.filterVal((Float) data[1], 100000,
//					0);
//
//			// checks if a nessage should or not be sent
//			if (filteredX == null || filteredY == null) {
//				return;
//			}
//
//			// gets the light intensity
//			// System.out.println("input: " + (Float) data[1] + " filtered: " + filteredY);
//			Float intensity = this.database
//					.lightIntensity(filteredX, filteredY);
//
//			// checks if it is the same value as before or not.
//			if (!intensity.equals(this.lastMessage)) {
//				// sets the last value
//				this.lastMessage = intensity;
//
//				// System.out.println(intensity);
//
//				// sends the message to all 20 lights
//				Float[] messageValues = {
//				        intensity, intensity, intensity, //1 left wall
//						intensity, intensity, intensity, //2 
//						intensity, intensity, intensity, //3 
//						intensity, intensity, intensity, //4 
//						intensity, intensity, intensity, //5 
//						intensity, intensity, intensity, //6 
//						intensity, intensity, intensity, //7 right wall
//						intensity, intensity, intensity, //8
//						intensity, intensity, intensity, //9
//						intensity, intensity, intensity, //10
//						intensity, intensity, intensity, //11
//						intensity, intensity, intensity, //12
//						intensity, intensity, intensity, //13 left-center
//						intensity, intensity, intensity, //14
//						intensity, intensity, intensity, //15
//						intensity, intensity, intensity, //16
//						intensity, intensity, intensity, //17 right-center
//						intensity, intensity, intensity, //18
//						intensity, intensity, intensity, //19
//						intensity, intensity, intensity }; //20
//				sendMessage("/all", messageValues);
//			}
//		}
//
//		else if (address.contains("/2/multifader1")) {
//			// instantiates the fader number
//			float faderNum = 0;
//
//			try {
//				// finds the bar number
//				String barNumber = address.split("/")[3];
//				faderNum = Float.parseFloat(barNumber);
//
//			} catch (Exception e) {
//				System.out.println("There was an error parsing the multifader");
//				e.printStackTrace();
//			}
//
//			Float filteredVal = this.database.filterVal((Float) data[0], 1000,
//					-1);
//
//			Float[] messageValues = { filteredVal, filteredVal, filteredVal };
//
//			if (faderNum == 1) {
//				for (float i = 0; i < 7; i++) {
//					String istr = i + "";
//					istr = istr.split("\\.")[0];
//					sendMessage("/sr" + istr + "/rgb", messageValues);
//				}
//			}
//
//			else if (faderNum == 2) {
//				for (float i = 7; i < 13; i++) {
//					String istr = i + "";
//					istr = istr.split("\\.")[0];
//					sendMessage("/sr" + istr + "/rgb", messageValues);
//				}
//			}
//
//			else if (faderNum == 3) {
//				for (float i = 13; i < 17; i++) {
//					String istr = i + "";
//					istr = istr.split("\\.")[0];
//					sendMessage("/sr" + istr + "/rgb", messageValues);
//				}
//			}
//
//			else if (faderNum == 4) {
//				for (float i = 17; i < 21; i++) {
//					String istr = i + "";
//					istr = istr.split("\\.")[0];
//					sendMessage("/sr" + istr + "/rgb", messageValues);
//				}
//			}
//		}
//	}

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
