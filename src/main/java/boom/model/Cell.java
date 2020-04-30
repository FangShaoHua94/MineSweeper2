package boom.model;

public class Cell {

    private int value;
    private boolean isReveal=false;
    private boolean isFlag=false;
    private boolean isMine=false;

    public void setValue(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }

    public void reveal(){
        isReveal=true;
    }

    public boolean isRevealed(){
        return isReveal;
    }

    public void setFlag(){
        if(isFlag){
            isFlag=false;
        }else{
            isFlag=true;
        }
    }

    public boolean isFlagged(){
        return isFlag;
    }

    public void setMine(){
        isMine=true;
    }

    public boolean isMineTile(){
        return isMine;
    }

}
