package com.jchess;

import com.jchess.engine.board.Board;
import com.jchess.graphics.Table;

public class JChess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();


    }

}
