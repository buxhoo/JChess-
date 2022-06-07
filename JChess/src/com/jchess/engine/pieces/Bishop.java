package com.jchess.engine.pieces;


import com.google.common.collect.ImmutableList;
import com.jchess.engine.board.Board;
import com.jchess.engine.board.BoardUtils;
import com.jchess.engine.board.Move;
import com.jchess.engine.board.Tile;
import com.jchess.engine.Alliance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jchess.engine.board.Move.*;

public class Bishop extends Piece{

    private static final int[] CANDIDATE_VECTOR_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculatedLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset: CANDIDATE_VECTOR_MOVE_COORDINATES){

            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }else{
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if(this.pieceAlliance != pieceAlliance){
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&  (candidateOffset == -9 || candidateOffset ==7);
    }
    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
