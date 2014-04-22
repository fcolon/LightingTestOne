package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class Data {
	private final String filepath;

	private final Float[][] settingsInfo;
	private final Integer[][] testComparisonsInfo;

	/**
	 * constructor class initializes values
	 */
	public Data() {
		this.settingsInfo = readSettings("settings");

		JOptionPane.showMessageDialog(null,
				"Select the testID.csv that you would\nlike to load.",
				"Select CSV", JOptionPane.INFORMATION_MESSAGE);
		// Gets Filepath
		this.filepath = fileChooser();
		// exits the program if null or if closed.
		if (this.filepath == null) {
			System.exit(0);
		}
		this.testComparisonsInfo = readTestIDs(this.filepath);
	}

	public Float[][] getSettings() {
		return settingsInfo;
	}

	public Integer[][] getTestComparisonsInfo() {
		return testComparisonsInfo;
	}

	private Float[][] readSettings(String dataset) {
		try {
			// count the number of lines
			int numberOfLines = 0;
			int numberOfSettings = 0;
			FileReader fr2 = new FileReader("TestFiles/" + dataset + ".csv");
			BufferedReader br2 = new BufferedReader(fr2);
			String counterRead = br2.readLine();

			// loop that counts the lines
			while (counterRead != null) {
				counterRead = br2.readLine();
				numberOfLines++;
			}

			numberOfSettings = numberOfLines / 2; // numberOfLines will always
													// be even

			// closes the reader
			br2.close();
			fr2.close();

			// We're going to return a list of lists. List looks like
			// [[conditionNumber,R1,B1,G1,R2,B2,G2],...]
			// where the RBG1's correspond to long lights, and RGB2's correspond
			// to the sides

			// initializations for parsing the lines
			FileReader fr = new FileReader("TestFiles/" + dataset + ".csv");
			BufferedReader br = new BufferedReader(fr);
			String stringRead = br.readLine();

			// initializes the output array
			Float[][] settings = new Float[numberOfSettings][7];

			// counter for the lines
			int lineCounter = 1;

			// splits and parses every line and adds it to the float array
			while (stringRead != null) {
				String[] rowData = stringRead.split(",");

				for (int i = 0; i < rowData.length; i++) {
					String cellVal = rowData[i];
					Float number = 0f;

					int settingNum = (lineCounter + 1) / 2;

					if (Integer.parseInt(rowData[0]) != settingNum) {
						System.out.println("derp testNum: " + settingNum);
					} else {
						settings[settingNum - 1][0] = (float) settingNum;
					}

					// We're going to return a list of lists. List looks like
					// [[conditionNumber,R1,B1,G1,R2,B2,G2],...]
					// where the RBG1's correspond to long lights, and RGB2's
					// correspond to the sides

					// if it is a number, parse it
					if (!cellVal.equals("NaN") && !cellVal.equals("DOWN90")
							&& !cellVal.equals("WALL10")
							&& !cellVal.equals("DOWN10")
							&& !cellVal.equals("WALL90")) {
						number = Float.parseFloat(cellVal);
						number = convertToScale(number);
					}

					// if we are looking at column C (in excel sheet, which is R
					// value)
					if (i == 2) {
						// if i is odd, then long lights
						if (lineCounter % 2 == 1) {
							settings[settingNum - 1][1] = number;
						}
						// if i is even, then short lights
						else {
							settings[settingNum - 1][4] = number;
						}
					}
					// column D, which is G
					else if (i == 3) {
						// if i is odd, then long lights
						if (lineCounter % 2 == 1) {
							settings[settingNum - 1][2] = number;
						}
						// if i is even, then short lights
						else {
							settings[settingNum - 1][5] = number;
						}

					}
					// column E, which is B
					else if (i == 4) {
						// if i is odd, then long lights
						if (lineCounter % 2 == 1) {
							settings[settingNum - 1][3] = number;
						}
						// if i is even, then short lights
						else {
							settings[settingNum - 1][6] = number;
						}

					}

				}

				// read the next line
				stringRead = br.readLine();
				lineCounter++;
			}

			// closes the reader
			br.close();
			fr.close();

			return settings;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ////////////////////////////////////////////////////////////////////////////

	private Integer[][] readTestIDs(String givenfilepath) {
		try {
			// count the number of tests
			int numberOfTests = 0;
			FileReader fr2 = new FileReader(givenfilepath);
			BufferedReader br2 = new BufferedReader(fr2);
			String counterRead = br2.readLine();

			// loop that counts the lines
			while (counterRead != null) {
				counterRead = br2.readLine();
				numberOfTests++;
			}

			// closes the reader
			br2.close();
			fr2.close();

			// NOTE: "conditionA/B" is equivalent to "setting" in method above
			// this

			// We're going to return an array of integer arrays
			// [[testNum,conditionA,conditionB],...]

			// initializations for parsing the lines
			FileReader fr = new FileReader(givenfilepath);
			BufferedReader br = new BufferedReader(fr);
			String stringRead = br.readLine();

			// initializes the output array
			Integer[][] testComparisons = new Integer[numberOfTests][3];

			// counter for the lines
			int lineCounter = 1;

			// splits and parses every line and adds it to the float array
			while (stringRead != null) {
				String[] rowData = stringRead.split(",");

				for (int i = 0; i < rowData.length; i++) {
					String cellVal = rowData[i];
					int number = 0;

					int testNum = lineCounter;

					if (Integer.parseInt(rowData[0]) != testNum) {
						System.out.println("derp testNum: " + testNum);
					} else {
						testComparisons[testNum - 1][0] = testNum;
					}

					// We're going to return an array of integer arrays
					// [[testNum,conditionA,conditionB],...]

					number = Integer.parseInt(cellVal);

					// if we are looking at column B in excel sheet, which
					// corresponds to condition A
					if (i == 1) {
						testComparisons[testNum - 1][1] = number;
					}
					// column C, which is condition B
					else if (i == 2) {
						testComparisons[testNum - 1][2] = number;
					}

				}

				// read the next line
				stringRead = br.readLine();
				lineCounter++;
			}

			// closes the reader
			br.close();
			fr.close();

			return testComparisons;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Scales values 0-255 to 0-1.
	 * 
	 * @return
	 */
	public float convertToScale(float oldVal) {
		float newVal = oldVal / 255f;
		return newVal;
	}

	private static String fileChooser() {
		// Creates the File Chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new ExtensionFilter());
		// Displays the chooser
		int returnedInt = chooser.showOpenDialog(null);

		// If a file is selected, return the string of the filepath
		if (returnedInt == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	// Creates a filter for fileChooser that only shows .abc files
	private static class ExtensionFilter extends FileFilter {

		// This shows only .abc files
		@Override
		public boolean accept(File f) {
			return f.getName().toLowerCase().endsWith(".csv")
					|| f.isDirectory();
		}

		// This gives that filter a label
		@Override
		public String getDescription() {
			return "Comma Separated Value file (*.csv)";
		}

	}

}