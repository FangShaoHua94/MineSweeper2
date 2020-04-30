package boom.logic.difficulty;

public abstract class Difficulty {

    private int height;
    private int width;
    private int mineCount;

    public Difficulty(int height, int width, int mineCount){
        this.height=height;
        this.width=width;
        this.mineCount=mineCount;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getMineCount(){
        return mineCount;
    }

}
