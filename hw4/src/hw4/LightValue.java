package hw4;

public enum LightValue {
    GREEN(2, "Green Light"),       //  2 lanes may proceed
    RED(0, "Red Light"),         //  0 lanes may proceed
    LEFT_SIGNAL(1, "Left Signal"); //  1 lane may proceed
    private final int value;
    private final String signal;     //  number of lanes allowed to proceed
    LightValue(int value, String signal){
        this.value = value;
        this.signal = signal;
    }
    public int getValue(){
        return value;
    }
    public String getSignal(){
        return signal;
    }
}