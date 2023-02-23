package model;

import java.util.ArrayList;
import java.util.List;

public class BoardReplays {

    private List<Board> replay;
    private List<Board> completions;


    public BoardReplays() {
        replay = new ArrayList<>();
        completions = new ArrayList<>();
    }


    public void addReplay(Board board) {
        replay.add(board);
    }

    public Board viewReplay(Integer index) {
        return replay.get(index);
    }

    public void addCompletion(Board board) {
        completions.add(board);
    }

    public List<Board> getReplay() {
        return replay;
    }


    public List<Board> getCompletion() {
        return completions;
    }
}

