package com.jchess.graphics;


import com.jchess.engine.board.Board;
import com.jchess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Table {
    private final JFrame gameFrame;
    private Board chessBoard;
    private final BoardPanel boardPanel;

    private String iconPath;

    private final static Dimension OUTER_FRAME = new Dimension(600,600);
    private final static Dimension BOARD_DIMENSIONS = new Dimension(400,350);
    private final static Dimension TILE_DIMENSIONS = new Dimension(10,10);

    private final Color lightTileColor = Color.decode("#C493C1");
    private final Color darkTileColor = Color.decode("#9B5596");
    public Table (){

        this.gameFrame = new JFrame("JChess");
        final JMenuBar menuBar = createMenuBar();
        this.gameFrame.setJMenuBar(menuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.iconPath = "JChess/art/holywarriors/";
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setSize(OUTER_FRAME);
        this.gameFrame.setVisible(true);



    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem loadPGN = new JMenuItem("Open PGN File");
        loadPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load File.");
            }
        });
        fileMenu.add(loadPGN);

        final JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenu);

        return fileMenu;
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_DIMENSIONS);
            validate();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;
        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_DIMENSIONS);
            assignTileColors();
            assignTileIcons(chessBoard);
            validate();
        }

        private void assignTileIcons(final Board board){
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()){
                try{
                    String filename = iconPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) + board.getTile(this.tileId).getPiece().toString() + ".gif";
                    System.out.println(filename);

                    final BufferedImage image = ImageIO.read(new File(iconPath +
                            board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) + "" +
                            board.getTile(this.tileId).getPiece().toString() +
                            ".gif"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        private void assignTileColors() {
            if (BoardUtils.FIRST_ROW[this.tileId]||
                    BoardUtils.THIRD_ROW[this.tileId] ||
                    BoardUtils.FIFTH_ROW[this.tileId] ||
                    BoardUtils.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SECOND_ROW[this.tileId] ||
                    BoardUtils.FOURTH_ROW[this.tileId] ||
                    BoardUtils.SIXTH_ROW[this.tileId] ||
                    BoardUtils.EIGHT_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

    }
}
