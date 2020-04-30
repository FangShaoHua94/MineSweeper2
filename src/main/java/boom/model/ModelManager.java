package boom.model;

import boom.logic.difficulty.Difficulty;
import boom.logic.difficulty.Intermediate;

public class ModelManager implements Model{

    private static final Difficulty DEFAULT= new Intermediate();
    private Board board;

    public ModelManager(){
        createBoard(DEFAULT);
    }

    public void createBoard(Difficulty difficulty){
        board = new Board(difficulty);
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
