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

}
