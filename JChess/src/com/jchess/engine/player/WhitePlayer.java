package com.jchess.engine.player;

import com.google.common.collect.ImmutableList;
import com.jchess.engine.Alliance;
import com.jchess.engine.board.Board;
import com.jchess.engine.board.Move;
import com.jchess.engine.board.Tile;
import com.jchess.engine.pieces.Piece;
import com.jchess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jchess.engine.board.Move.*;

public class WhitePlayer extends Player{

    public WhitePlayer(final Board board, final Collection<Move> whiteNormalMoves, final Collection<Move> blackNormalMoves) {
        super(board, whiteNormalMoves, blackNormalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calcuKingCastle(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.inCheck()){
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){ // White's Castle
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() && Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new KingCastleMove(this.board, this.playerKing, 62, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() && Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()){
                    kingCastles.add(new QueenCastleMove(this.board, this.playerKing, 58, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59 ));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
