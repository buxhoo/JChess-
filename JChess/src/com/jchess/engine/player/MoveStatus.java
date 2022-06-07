package com.jchess.engine.player;

import com.jchess.engine.board.Move;

public enum MoveStatus {
    DONE{
        @Override
        boolean isDone(){
            return true;
        }
    },

    ILLEGAL_MOVE{
        @Override
        boolean isDone(){
            return true;
        }
    },

    PLAYER_IN_CHECK {
        @Override
        boolean isDone() {
            return false;
        }
    };


    abstract boolean isDone();
}
