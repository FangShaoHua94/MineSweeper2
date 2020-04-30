package boom.logic;

import boom.model.Board;
import boom.model.Model;

public class LogicManager implements Logic{

    private Model model;


    public LogicManager(Model model){
        this.model = model;
    }

    public Board getBoard(){
        return model.getBoard();
    }

}
