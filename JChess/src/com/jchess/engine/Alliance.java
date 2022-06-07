package com.jchess.engine;

import com.jchess.engine.player.BlackPlayer;
import com.jchess.engine.player.Player;
import com.jchess.engine.player.WhitePlayer;

public enum Alliance {
    WHITE{
        @Override
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK{
        @Override
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
}
