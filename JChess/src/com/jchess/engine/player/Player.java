package com.jchess.engine.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jchess.engine.Alliance;
import com.jchess.engine.board.Board;
import com.jchess.engine.board.Move;
import com.jchess.engine.pieces.King;
import com.jchess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isCheck;


    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calcuKingCastle(legalMoves,opponentMoves)));
        this.isCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board");
    }

    public King getPlayerKing(){
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean inCheck(){
        return this.inCheck();
    }

    public boolean isCheckmate(){
        return this.inCheck() && !hasEscapeMoves();
    }

    public boolean isStalemate(){
        return !this.isCheck &&  !hasEscapeMoves();
    }

    public boolean hasEscapeMoves(){
        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move) {

        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttack = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttack.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }


    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calcuKingCastle(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals);

}
