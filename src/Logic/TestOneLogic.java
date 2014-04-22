package Logic;

import java.net.UnknownHostException;

import GUI.TestOneGUI;
import Server.MessageListener;

public class TestOneLogic {
    private static Float[][] settings;
    private static Integer[][] testComparisons;
    private static int currentTestNum;
    private String state;
    private static Float[] messageA;
    private static Float[] messageB;
    private static Float[] darkRoomMessage;
    private static int totalNumTests;
    private static TestOneGUI gui;
    private static MessageListener listener;
    private static int nextTestInt;
    private static int conditionAInt;
    private static int conditionBInt;
    
    /**
     * constructor class initializes values
     * @throws UnknownHostException 
     */
    public TestOneLogic( Float[][] settings, Integer[][] testComparisons, TestOneGUI gui) throws UnknownHostException {
        this.listener = new MessageListener();
        this.settings = settings;
        this.testComparisons = testComparisons;
        this.currentTestNum = 0;
        this.state = "waiting";
        this.messageA = new Float[20];
        this.messageB = new Float[20];
        this.totalNumTests = testComparisons.length;
        this.nextTestInt = 0;
        this.conditionAInt = 0;
        this.conditionBInt = 0;
        this.gui = gui;
        
        this.darkRoomMessage = new Float[] {
                0f, 0f, 0f, //1 left wall
                0f, 0f, 0f, //2
                0f, 0f, 0f, //3
                0f, 0f, 0f, //4
                0f, 0f, 0f, //5
                0f, 0f, 0f, //6 
                0f, 0f, 0f, //7 right wall
                0f, 0f, 0f, //8
                0f, 0f, 0f, //9
                0f, 0f, 0f, //10
                0f, 0f, 0f, //11
                0f, 0f, 0f, //12
                0f, 0f, 0f, //13 left-center
                0f, 0f, 0f, //14
                0f, 0f, 0f, //15
                0f, 0f, 0f, //16
                0f, 0f, 0f, //17 right-center
                0f, 0f, 0f, //18
                0f, 0f, 0f, //19
                0f, 0f, 0f }; //20
        
        //initially prepare for first test
        prepareNextTest();
    }
    //Settings
    //We're going to return a list of lists. List looks like [[conditionNumber,R1,B1,G1,R2,B2,G2],...]
    //where the RBG1's correspond to long lights, and RGB2's correspond to the sides 
    //long = down, sides = wall
    
    //NOTE: terminology: "conditionA/B" is equivalent to "setting"
    
    //We're going to return an  array of integer arrays [[testNum,conditionA,conditionB],...]
    
    public static void prepareNextTest(){
        if(currentTestNum >= totalNumTests){
            return;
        }
        currentTestNum++;
        
        Integer[] comparisons = testComparisons[currentTestNum-1];
        int conditionANum = comparisons[1];
        int conditionBNum = comparisons[2];
        
//        System.out.println("Test: "+currentTestNum+" A: "+conditionANum+" B: "+conditionBNum);
        
        Float[] settingsA = settings[conditionANum-1];
        Float[] settingsB = settings[conditionBNum-1];
        
        float downRA = settingsA[1]; 
        float downGA = settingsA[2]; 
        float downBA = settingsA[3]; 
        float wallRA = settingsA[4]; 
        float wallGA = settingsA[5]; 
        float wallBA = settingsA[6]; 
        
        float downRB = settingsB[1]; 
        float downGB = settingsB[2]; 
        float downBB = settingsB[3]; 
        float wallRB = settingsB[4]; 
        float wallGB = settingsB[5]; 
        float wallBB = settingsB[6]; 
        
        // creates the message to send for all 20 lights for condition A
        Float[] messageValuesA = {
                wallRA, wallGA, wallBA, //1 left wall
                wallRA, wallGA, wallBA, //2
                wallRA, wallGA, wallBA, //3
                wallRA, wallGA, wallBA, //4
                wallRA, wallGA, wallBA, //5
                wallRA, wallGA, wallBA, //6 
                wallRA, wallGA, wallBA, //7 right wall
                wallRA, wallGA, wallBA, //8
                wallRA, wallGA, wallBA, //9
                wallRA, wallGA, wallBA, //10
                wallRA, wallGA, wallBA, //11
                wallRA, wallGA, wallBA, //12
                downRA, downGA, downBA, //13 left-center
                downRA, downGA, downBA, //14
                downRA, downGA, downBA, //15
                downRA, downGA, downBA, //16
                downRA, downGA, downBA, //17 right-center
                downRA, downGA, downBA, //18
                downRA, downGA, downBA, //19
                downRA, downGA, downBA }; //20
        
        // creates the message to send for all 20 lights for condition B
        Float[] messageValuesB = {
                wallRB, wallGB, wallBB, //1 left wall
                wallRB, wallGB, wallBB, //2
                wallRB, wallGB, wallBB, //3
                wallRB, wallGB, wallBB, //4
                wallRB, wallGB, wallBB, //5
                wallRB, wallGB, wallBB, //6 
                wallRB, wallGB, wallBB, //7 right wall
                wallRB, wallGB, wallBB, //8
                wallRB, wallGB, wallBB, //9
                wallRB, wallGB, wallBB, //10
                wallRB, wallGB, wallBB, //11
                wallRB, wallGB, wallBB, //12
                downRB, downGB, downBB, //13 left-center
                downRB, downGB, downBB, //14
                downRB, downGB, downBB, //15
                downRB, downGB, downBB, //16
                downRB, downGB, downBB, //17 right-center
                downRB, downGB, downBB, //18
                downRB, downGB, downBB, //19
                downRB, downGB, downBB }; //20
        
//        System.out.println("Condition A Wall- R: "+wallRA+" G: "+wallGA+" B: "+wallBA);
//        System.out.println("Condition B Down- R: "+downRB+" G: "+downGB+" B: "+downBB);
        
        messageA = messageValuesA;
        messageB = messageValuesB;

        conditionAInt = conditionANum;
        conditionBInt = conditionBNum;
        nextTestInt = conditionANum;
        
        //change text in gui
        gui.updateGUI(currentTestNum, nextTestInt, conditionANum, conditionBNum, totalNumTests);
    }
    
    public static void displayConditionA(){
        listener.sendMessage("/all", messageA);
    }
    
    public static void displayConditionB(){
        listener.sendMessage("/all", messageB);
    }
    
    public static void displayDarkRoom(){
        listener.sendMessage("/all", darkRoomMessage);
    }
    
    public static boolean startTest(int timeDelay) throws InterruptedException{
        boolean enableToggles = false;
        
        int timeDelayms = timeDelay*1000;//CHECK THIS
        
       Thread thisThread = Thread.currentThread();
//       int totalSec = timeDelay;
//       while (totalSec >= 0){
//           thisThread.sleep(1000);
//           totalSec--;
//           //update GUI?
//       }
       
       gui.runningTest(currentTestNum);
       
       //display Condition A for some given time
       displayConditionA();
       thisThread.sleep(timeDelayms);
       
       //display Condition B for same given time
       displayConditionB();
       thisThread.sleep(timeDelayms);
       
       //enable Toggles
       enableToggles = true;
        
       //If Matt decides he wants to be able to control each part of each test, uncomment this section.
//        //if nextTestInt is conditionAInt, that means we're on test condition A
//        if(nextTestInt == conditionAInt){
//            displayConditionA();
//            nextTestInt = conditionBInt;
//        }
//        //else if it's conditionBInt, we're on test condition B
//        else if(nextTestInt == conditionBInt){
//            displayConditionB();
//            nextTestInt = -1;
//            enableToggles = true;
//        }
//        else{
//            //should not get here
//            System.out.println("Error in startTest()!!!!");
//        }
        
        //room currently goes to black after each condition test
        displayDarkRoom();
        
        nextTestInt = -1; // we are done testing each condition
        
        gui.updateGUI(currentTestNum, nextTestInt, conditionAInt, conditionBInt, totalNumTests);

        return enableToggles;
    }
}
