package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import Logic.TestOneLogic;


public class TestOneGUI extends JFrame {
    public final JButton startButton;
    public final JButton cancelButton;
    public final JButton nextTestButton;
    
    public final JLabel spacingLine;
    
    public final JButton conditionAButton;
    public final JButton conditionBButton;
    
    public final JLabel testNum;
    public final JLabel compareLine;
    public final JLabel showingLine;
    public final JTextField secondsBox;
    public final JLabel secondsWord;
    
    private final Group horizontal;
    private final Group vertical;

    private final Group row1;
    private final Group row2;
    private final Group row3;
    private final Group row4;
    private final Group row5;
    private final Group row6;
    private final Group vert1;
    private final Group vert2;
    private final Group vert3;
    private final Group vert4;
    private final Group vert5;
    private final Group vert6;
    
    private final String filepath;
    
    public TestOneGUI(){
    	// TODO: implement filepath
    	// Gets Filepath
    	this.filepath = fileChooser();
    	
    	// exits the program if null or if closed.
    	if (this.filepath == null) {
    		System.exit(0);
    	}
    	
        startButton = new JButton();
        startButton.setName("startButton");
        startButton.setText("Start");
        cancelButton = new JButton();
        cancelButton.setName("cancelButton");
        cancelButton.setText("Cancel");
        nextTestButton = new JButton();
        nextTestButton.setName("nextTestButton");
        nextTestButton.setText("Next Test");
        
        spacingLine = new JLabel();
        spacingLine.setText("");
        
        conditionAButton = new JButton();
        conditionAButton.setName("conditionAButton");
        conditionAButton.setText("Condition A");
        conditionBButton = new JButton();
        conditionBButton.setName("conditionBButton");
        conditionBButton.setText("Condition B");
        
        //Make condition buttons larger (simply to differentiate from start, cancel, and next test buttons)
        conditionAButton.setMargin(new Insets(10, 10, 10, 10));
        conditionBButton.setMargin(new Insets(10, 10, 10, 10));
        
        testNum = new JLabel();
        testNum.setText("Test 1");
        compareLine = new JLabel();
        compareLine.setText("Compare Condition A and Condition B for ");
        showingLine = new JLabel();
        showingLine.setText("About to show: Condition A");
        secondsBox = new JTextField();
        secondsBox.setText("30");
        secondsWord = new JLabel();
        secondsWord.setText(" seconds.");
        
        //add spacing in GUI just so buttons aren't so clumped together
        showingLine.setBorder(new EmptyBorder(0, 0, 20, 0));
        spacingLine.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        //set up layout
        Container panel = this.getContentPane();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        
        // Arrange horizontal
        row1 = layout.createSequentialGroup();
        row1.addComponent(testNum);
        
        row2 = layout.createSequentialGroup();
        row2.addComponent(compareLine);
        row2.addComponent(secondsBox);
        row2.addComponent(secondsWord);
        
        row3 = layout.createSequentialGroup();
        row3.addComponent(showingLine);
        
        row4 = layout.createSequentialGroup();
        row4.addComponent(conditionAButton);
        row4.addComponent(conditionBButton);
        
        row5 = layout.createSequentialGroup();
        row5.addComponent(spacingLine);
        
        row6 = layout.createSequentialGroup();
//        row6.addComponent(cancelButton);
        row6.addComponent(startButton);
        row6.addComponent(nextTestButton);
        
        horizontal = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        horizontal.addGroup(row1);
        horizontal.addGroup(row2);
        horizontal.addGroup(row3);
        horizontal.addGroup(row4);
        horizontal.addGroup(row5);
        horizontal.addGroup(row6);
        layout.setHorizontalGroup(horizontal);
        
        // Arrange vertical
        vert1 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert1.addComponent(testNum);


        vert2 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert2.addComponent(compareLine);
        vert2.addComponent(secondsBox);
        vert2.addComponent(secondsWord);
        
        vert3 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert3.addComponent(showingLine);
        
        vert4 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert4.addComponent(conditionAButton);
        vert4.addComponent(conditionBButton);
        
        vert5 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert5.addComponent(spacingLine);
        
        vert6 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
//        vert6.addComponent(cancelButton);
        vert6.addComponent(startButton);
        vert6.addComponent(nextTestButton);

        vertical = layout.createSequentialGroup();
        vertical.addGroup(vert1);
        vertical.addGroup(vert2);
        vertical.addGroup(vert3);
        vertical.addGroup(vert4);
        vertical.addGroup(vert5);
        vertical.addGroup(vert6);
        layout.setVerticalGroup(vertical);
        
        this.pack();
        
        //initially disable condition buttons
        conditionAButton.setEnabled(false);
        conditionBButton.setEnabled(false);
        cancelButton.setEnabled(false);
        
        //start of listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GET TIME
                String timeDelayStr =secondsBox.getText();
                int timeDelayInt = Integer.parseInt(timeDelayStr);
                
                cancelButton.setEnabled(true);
                startButton.setEnabled(false);
                nextTestButton.setEnabled(false);
                conditionAButton.setEnabled(false);
                conditionBButton.setEnabled(false);
                secondsBox.setEditable(false);
                
                //RUN START OF TEST 
                boolean enableToggles;
                try {
                    enableToggles = TestOneLogic.startTest(timeDelayInt);
                    afterTestEnableButtons(enableToggles);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        
        conditionAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display condition A
                TestOneLogic.displayConditionA();
            }
        });
        
        conditionBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display condition B
                TestOneLogic.displayConditionB();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conditionAButton.setEnabled(true);
                conditionBButton.setEnabled(true);
                cancelButton.setEnabled(false);
                startButton.setEnabled(true);
                nextTestButton.setEnabled(true);
                secondsBox.setEditable(true);
                
                //cancel current test
                TestOneLogic.displayDarkRoom();
            }
        });
        
        nextTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conditionAButton.setEnabled(false);
                conditionBButton.setEnabled(false);
                cancelButton.setEnabled(false);
                startButton.setEnabled(true);
                nextTestButton.setEnabled(true);
                secondsBox.setEditable(true);
                
                //prepare for the next test
                TestOneLogic.prepareNextTest();
            }
        });
    }
    
    public void updateGUI(int testInt, int currentConditionNum, int conditionANum, int conditionBNum, int totalNumTests){
        //update the text in the gui
        testNum.setText("Test "+testInt+" out of "+totalNumTests);
        conditionAButton.setText("Condition "+conditionANum);
        conditionBButton.setText("Condition "+conditionBNum);
        compareLine.setText("Compare Condition "+conditionANum+" and Condition "+conditionBNum);
        showingLine.setText("About to show: Condition "+currentConditionNum);
    }
    
    public void afterTestEnableButtons(boolean enableToggles){
        cancelButton.setEnabled(false);
        startButton.setEnabled(!enableToggles);
        nextTestButton.setEnabled(true);
        conditionAButton.setEnabled(enableToggles);
        conditionBButton.setEnabled(enableToggles);
        secondsBox.setEditable(true);
        
        if(enableToggles){
            showingLine.setText("You have completed this test.");
        }
    }
    
    public void runningTest(int currentTestNum){
        showingLine.setText("Currently running: Condition "+currentTestNum);
        cancelButton.setEnabled(false);
        startButton.setEnabled(false);
        nextTestButton.setEnabled(false);
        conditionAButton.setEnabled(false);
        conditionBButton.setEnabled(false);
        secondsBox.setEditable(false);
    }
    
//    public static void main(final String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                TestOneGUI main = new TestOneGUI();
//                main.setVisible(true);
//            }
//        });
//    }
    
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
            return f.getName().toLowerCase().endsWith(".csv") || f.isDirectory();
        }

        // This gives that filter a label
        @Override
        public String getDescription() {
            return "Comma Separated Value file (*.csv)";
        }
        
    }
}
