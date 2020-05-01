package boom.model;

import boom.logic.difficulty.Difficulty;

import java.util.Random;

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
                board[i][j]=new Cell(i,j);
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

    public void setMine(Cell cell){
        for (int i = 0; i < mineCount; i++) {
            Cell mineCell;
            Random random = new Random(System.currentTimeMillis() * i + System.currentTimeMillis());
            do {
                mineCell = board[random.nextInt(height)][random.nextInt(width)];
            } while ((mineCell.equals(cell) || mineCell.isMineTile()));
            mineCell.setMine();
        }
    }

    public void setCellValue(Cell cell){
        int count=0;
        if(cell.isMineTile()){
            cell.setValue(count);
            return;
        }
        for(int i=cell.getRowIndex()-1;i<=cell.getRowIndex()+1;i++){
            for(int j=cell.getColIndex()-1;j<=cell.getColIndex()+1;j++){
                if(i<0 || i>=height || j<0 || j>=width){
                    continue;
                }
                if(board[i][j].isMineTile()){
                    count++;
                }
            }
        }
        cell.setValue(count);
    }


    public void print(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(board[i][j].isMineTile()){
                    System.out.print("b ");
                }else{
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }



}
