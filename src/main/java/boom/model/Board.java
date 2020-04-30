package boom.model;

import boom.logic.difficulty.Difficulty;

public class Board {

    private int height;
    private int width;
    private int mineCount;

    private Cell[][] board;

    public Board(Difficulty difficulty){
        height=difficulty.getHeight();
        width=difficulty.getWidth();
        mineCount=difficulty.getMineCount();
        setUpBoard();
    }

    private void setUpBoard(){
        board= new Cell[height][width];
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                board[i][j]=new Cell();
            }
        }
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Cell getCell(int i,int j){
        return board[i][j];
    }

}
