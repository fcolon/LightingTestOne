package Logic;

public class TestOneLogic {
    private final Float[][] settings;
    private final Integer[][] testComparisons;
    private int currentTestNum;
    private String state;
    
    /**
     * constructor class initializes values
     */
    public TestOneLogic( Float[][] settings, Integer[][] testComparisons) {
        this.settings = settings;
        this.testComparisons = testComparisons;
        this.currentTestNum = 0;
        this.state = "waiting";
    }
    //Settings
    //We're going to return a list of lists. List looks like [[conditionNumber,R1,B1,G1,R2,B2,G2],...]
    //where the RBG1's correspond to long lights, and RGB2's correspond to the sides 
    
    //NOTE: "conditionA/B" is equivalent to "setting" in method above this
    
    //We're going to return an  array of integer arrays [[testNum,conditionA,conditionB],...]
    
    public void moveToNextTest(){
        this.currentTestNum++;
        int currentTestNum = this.currentTestNum;
        Integer[] comparisons = this.testComparisons[currentTestNum];
        int conditionA = comparisons[1];
        int conditionB = comparisons[2];
        
//        Float[]this.settings[];
    }
}
