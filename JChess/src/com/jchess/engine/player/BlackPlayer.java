package com.jchess.engine.player;

import com.google.common.collect.ImmutableList;
import com.jchess.engine.Alliance;
import com.jchess.engine.board.Board;
import com.jchess.engine.board.Move;
import com.jchess.engine.board.Move.KingCastleMove;
import com.jchess.engine.board.Tile;
import com.jchess.engine.pieces.Piece;
import com.jchess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jchess.engine.board.Move.*;

public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> whiteNormalMoves, final Collection<Move> blackNormalMoves) {
        super(board, blackNormalMoves, whiteNormalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calcuKingCastle(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.inCheck()){
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){ // Blacks's Castle
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() && Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new KingCastleMove(this.board, this.playerKing, 6, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty() && Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                    kingCastles.add(new QueenCastleMove(this.board, this.playerKing, 2, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
