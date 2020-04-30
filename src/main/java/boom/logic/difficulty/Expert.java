package boom.logic.difficulty;

public class Expert extends Difficulty{

    private static final int HEIGHT=16;
    private static final int WIDTH=30;
    private static final int MINE_COUNT=99;

    public Expert(){
        super(HEIGHT,WIDTH,MINE_COUNT);
    }
}
