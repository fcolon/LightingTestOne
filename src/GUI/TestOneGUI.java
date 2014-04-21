package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import Logic.TestOneLogic;


public class TestOneGUI extends JFrame {
    public final JButton startButton;
    public final JButton blackoutButton;
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
    private final Group row7;
    private final Group vert1;
    private final Group vert2;
    private final Group vert3;
    private final Group vert4;
    private final Group vert5;
    private final Group vert6;
    private final Group vert7;
    
    private final ClockListener clock = new ClockListener();
    private final Timer timer = new Timer(53, clock);
    private final JTextField timeDisplay = new JTextField(5);
    private final SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
    private long startTime;
    private boolean timerRunning;
    
    public TestOneGUI(){
    	
        startButton = new JButton();
        startButton.setName("startButton");
        startButton.setText("Start");
        blackoutButton = new JButton();
        blackoutButton.setName("blackoutButton");
        blackoutButton.setText("Blackout");
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
        blackoutButton.setMargin(new Insets(10, 10, 10, 10));
        
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
        
        //set initial timer delay
        timer.setInitialDelay(0);
        timerRunning = false;
        timeDisplay.setEditable(false);
        
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
        row4.addComponent(timeDisplay);
        
        row5 = layout.createSequentialGroup();
        row5.addComponent(conditionAButton);
        row5.addComponent(conditionBButton);
        row5.addComponent(blackoutButton);
        
        row6 = layout.createSequentialGroup();
        row6.addComponent(spacingLine);
        
        row7 = layout.createSequentialGroup();
        row7.addComponent(startButton);
        row7.addComponent(nextTestButton);
        
        horizontal = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        horizontal.addGroup(row1);
        horizontal.addGroup(row2);
        horizontal.addGroup(row3);
        horizontal.addGroup(row4);
        horizontal.addGroup(row5);
        horizontal.addGroup(row6);
        horizontal.addGroup(row7);
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
        vert4.addComponent(timeDisplay);
        
        vert5 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert5.addComponent(conditionAButton);
        vert5.addComponent(conditionBButton);
        vert5.addComponent(blackoutButton);
        
        vert6 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert6.addComponent(spacingLine);
        
        vert7 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert7.addComponent(startButton);
        vert7.addComponent(nextTestButton);

        vertical = layout.createSequentialGroup();
        vertical.addGroup(vert1);
        vertical.addGroup(vert2);
        vertical.addGroup(vert3);
        vertical.addGroup(vert4);
        vertical.addGroup(vert5);
        vertical.addGroup(vert6);
        vertical.addGroup(vert7);
        layout.setVerticalGroup(vertical);
        
        this.pack();
        
        //initially disable condition buttons
        conditionAButton.setEnabled(false);
        conditionBButton.setEnabled(false);
        blackoutButton.setEnabled(false);
        
        //start of listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GET TIME
                String timeDelayStr =secondsBox.getText();
                int timeDelayInt = Integer.parseInt(timeDelayStr);
                
                startButton.setEnabled(false);
                nextTestButton.setEnabled(false);
                conditionAButton.setEnabled(false);
                conditionBButton.setEnabled(false);
                blackoutButton.setEnabled(false);
                secondsBox.setEditable(false);
                
                //if timer is running
                if(timerRunning){
                    //tell timer to stop
                    updateClock();
                    startTime = 0;
                    timer.stop();
                    timerRunning = false;
                }
                
                //RUN START OF TEST 
                boolean enableToggles;
                try {
                    enableToggles = TestOneLogic.startTest(timeDelayInt);
                    afterTestEnableButtons(enableToggles);
                } catch (InterruptedException e1) {
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
        
        blackoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //blackout room
                TestOneLogic.displayDarkRoom();
                
                if(!timerRunning){
                    startTime = System.currentTimeMillis();
                    timer.start();
                    timerRunning = true;
                }
                conditionAButton.setEnabled(false);
                conditionBButton.setEnabled(false);
                blackoutButton.setEnabled(false);
              
            }
        });
        
        nextTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conditionAButton.setEnabled(false);
                conditionBButton.setEnabled(false);
                blackoutButton.setEnabled(false);
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
        showingLine.setText("About to show: Condition "+currentConditionNum+" and then Condition "+conditionBNum);
    }
    
    public void afterTestEnableButtons(boolean enableToggles){
        startButton.setEnabled(!enableToggles);
        nextTestButton.setEnabled(true);
        conditionAButton.setEnabled(enableToggles);
        conditionBButton.setEnabled(enableToggles);
        blackoutButton.setEnabled(enableToggles);
        secondsBox.setEditable(true);
        
        if(enableToggles){
            showingLine.setText("You have completed this test.");
        }
    }
    
    public void runningTest(int currentTestNum){
        showingLine.setText("Currently running: Condition "+currentTestNum);
        blackoutButton.setEnabled(false);
        startButton.setEnabled(false);
        nextTestButton.setEnabled(false);
        conditionAButton.setEnabled(false);
        conditionBButton.setEnabled(false);
        secondsBox.setEditable(false);
    }
    
    private void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime);
        timeDisplay.setText(date.format(elapsed));
    }
    
    private class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }
    
//    public static void main(final String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                TestOneGUI main = new TestOneGUI();
//                main.setVisible(true);
//            }
//        });
//    }
    
}
