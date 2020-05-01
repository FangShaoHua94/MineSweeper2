package boom.model;

public class Cell {

    private final int rowIndex;
    private final int colIndex;
    private int value;
    private boolean isReveal=false;
    private boolean isFlag=false;
    private boolean isMine=false;

    public Cell(int rowIndex,int colIndex){
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
    }

    public int getRowIndex(){
        return rowIndex;
    }

    public int getColIndex(){
        return colIndex;
    }

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
