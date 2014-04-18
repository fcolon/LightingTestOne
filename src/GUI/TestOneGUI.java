package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


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
    
    public TestOneGUI(){
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
        compareLine.setText("Compare Condition A and Condition B for 0:30 seconds.");
        showingLine = new JLabel();
        showingLine.setText("About to show: Condition A");
        
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
        
        row3 = layout.createSequentialGroup();
        row3.addComponent(showingLine);
        
        row4 = layout.createSequentialGroup();
        row4.addComponent(conditionAButton);
        row4.addComponent(conditionBButton);
        
        row5 = layout.createSequentialGroup();
        row5.addComponent(spacingLine);
        
        row6 = layout.createSequentialGroup();
        row6.addComponent(cancelButton);
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
        
        vert3 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert3.addComponent(showingLine);
        
        vert4 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert4.addComponent(conditionAButton);
        vert4.addComponent(conditionBButton);
        
        vert5 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert5.addComponent(spacingLine);
        
        vert6 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        vert6.addComponent(cancelButton);
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
                cancelButton.setEnabled(true);
                startButton.setEnabled(false);
                nextTestButton.setEnabled(false);
                
                //GET TIME
                //RUN START OF TEST 
            }
        });
        
        conditionAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display condition A
            }
        });
        
        conditionBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display condition B
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
                
                //cancel current test
            }
        });
        
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
