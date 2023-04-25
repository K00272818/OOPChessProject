/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.chess;

import draughtsoop.BoardGame;
import draughtsoop.chess.pieces.ChessPiece;
import draughtsoop.Game;
import draughtsoop.GamePiece;
import draughtsoop.chess.pieces.Bishop;
import draughtsoop.chess.pieces.King;
import draughtsoop.chess.pieces.Knight;
import draughtsoop.chess.pieces.Pawn;
import draughtsoop.chess.pieces.Queen;
import draughtsoop.chess.pieces.Rook;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author James.Fennell
 */
public class Chess implements Game {

    private String[] gameStatMessages = new String[3];

    private ChessPiece[][] BOARD;
    private Color[][] board_highlights;

    static int ROWCOUNT = 8;
    static int COLCOUNT = 8;

    private String playerToMove = "White";//or "B"

    List<Integer> validMoves = new ArrayList();
    List<Integer> validJumps = new ArrayList();

    int currentSelectedSquare;
    int currentSelectedPieceLocation;

    @Override
    public void newGame() {
        //        String[][] board_string_start = {
//            {" ", "R", " ", "R", " ", "R", " ", "R"},
//            {"R", " ", "R", " ", "R", " ", "R", " "},
//            {" ", "R", " ", "R", " ", "R", " ", "R"},
//            {" ", " ", " ", " ", " ", " ", " ", " "},
//            {" ", " ", " ", " ", " ", " ", " ", " "},
//            {"W", " ", "W", " ", "W", " ", "W", " "},
//            {" ", "W", " ", "W", " ", "W", " ", "W"},
//            {"W", " ", "W", " ", "W", " ", "W", " "}
//        };
        ChessPiece[][] board_start = {
            //            {new Rook(), new Knight(), new Bishop(), new King(), new Queen(), new Bishop(), new Knight(), new Rook()},
            //            {new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn(), new Pawn()},
            {new Rook("Black"), new Knight("Black"), new Bishop("Black"), new Queen("Black"), new King("Black"), new Bishop("Black"), new Knight("Black"), new Rook("Black")},
            {new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black")},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White")},
            {new Rook("White"), new Knight("White"), new Bishop("White"), new Queen("White"), new King("White"), new Bishop("White"), new Knight("White"), new Rook("White")}

        };

        this.BOARD = board_start;
        resetBorderColors();
        this.gameStatMessages[0] = writePlayerToMove();//writePlayerToMoveMessage();
        this.gameStatMessages[1] = (" ");
        this.gameStatMessages[2] = (" ");
    }

        private void resetBorderColors() {
        Color[][] board_highlights_start = {
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
        };

        board_highlights = board_highlights_start;
    }
    
    
    @Override
    public GamePiece[][] getState() {
        return this.BOARD;
    }

    @Override
    public void paintBoard(JButton[][] boardButtons) {
//        GamePiece pieceCode = "";
        javax.swing.JButton button = null;
        for (int i = 0; i < boardButtons.length; i++) {
            for (int j = 0; j < boardButtons[0].length; j++) {

                GamePiece piece = BOARD[i][j];
                Color borderColour = board_highlights[i][j];
                button = boardButtons[i][j];

//                System.out.println("paintBoard i : " + i + " j : " + j + " pieceCode : " + pieceCode + " button : " + button);
                int borderThickness = borderColour == null ? 0 : 4;
                Border border = new LineBorder(borderColour, borderThickness, true);
                button.setBorder(border);

                if (piece == null) {
                    button.setIcon(null);
                } else {
//                    System.out.println("paintBoard Piece : " + piece.toString());
                    addImageToButton(piece.getImage(), button);
                }
            }
        }

    }

    private void addImageToButton(Image img, javax.swing.JButton square) {
        Image scaled = img.getScaledInstance(square.getWidth(), square.getHeight(), java.awt.Image.SCALE_SMOOTH);
        square.setIcon(new ImageIcon(scaled));
    }

    @Override
    public String[] getGameStateMessage() {
        return this.gameStatMessages;
    }

    @Override
    public void squareSelected(JButton[][] boardButtons, int squareNum) {
       System.out.println("mouseReleased squareNum : " + squareNum+" validMoves : "+this.validMoves+" validJumps : "+this.validJumps);
        
       
       currentSelectedSquare = squareNum;
        int[] currentSelectedSquareCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int squareRow = currentSelectedSquareCoords[0];
        int squareCol = currentSelectedSquareCoords[1];

        
        ChessPiece piece = BOARD[squareRow][squareCol];
        String pieceCode = "";
        if (piece != null) {
            if(piece.getPlayer().equalsIgnoreCase(playerToMove)){
                currentSelectedPieceLocation = squareNum;
                pieceCode = piece.toString();
                resetBorderColors();
                paintBoard(boardButtons);
                this.validMoves = piece.findValidMoves(squareNum, BOARD);
            }
        }

        if (validMoves.contains(squareNum)) {
            System.out.println("mouseReleased validSlides.contains : " + squareNum);
            performMove(this.currentSelectedPieceLocation, this.currentSelectedSquare);            
            clearValidMoves();
            paintBoard(boardButtons);
            endTurn();
        } else {
            System.out.println("squareSelected Cond5");
            System.out.println("Something Gone Wrong mouseReleased\n playerToMove : " + playerToMove + "  pieceCode: " + pieceCode + "  squareNum : " + squareNum + " validSlides: " + validMoves.toString() + "\n Board : " + Arrays.deepToString(BOARD) + " \n sqRow : " + squareRow + " sqCol : " + squareCol);
        }

        if (!winner().isEmpty()) {
            System.out.println("squareSelected Cond6");
            gameStatMessages[0] = "Winner is : " + winner();
        }

    
    }
    
    private String writePlayerToMove(){
        String playerToMoveMessage = "";
        if (playerToMove.equalsIgnoreCase("Black")) {
            playerToMoveMessage = "Black Player to Move";
        } else {
            playerToMoveMessage = "White Player to Move";
        }

        return playerToMoveMessage;
    }

    private String winner() {
        boolean whiteWin = true;
        boolean blackWin = true;
        for (ChessPiece[] col : BOARD) {
            for (ChessPiece piece : col) {
                if (piece instanceof King) {
                    if (piece.getPlayer().equalsIgnoreCase("Black")) {
                        whiteWin = false;
                    } else if (piece.getPlayer().equalsIgnoreCase("White")) {
                        blackWin = false;
                    }
                    if (!whiteWin && !blackWin) {
                        return "";
                    }
                }
            }
        }
        return whiteWin ? "White" : "Black";
    }

    @Override
    public boolean gameIsOver() {
        String winner = winner();
        boolean gameOver = winner.isEmpty();
        if (gameOver) {
            this.gameStatMessages[0] = "Winner is : " + winner;
        }
        return gameOver;
    }

    private void performMove(int origionSquareNum, int destSquareNum) {
                System.out.println("performMove origionSquareNum : "+origionSquareNum+", destSquareNum : "+destSquareNum);
        
        int[] origCoords = BoardGame.convertSquareNumToCoords(origionSquareNum);
        int origionSquareRow = origCoords[0];
        int origionSquareCol = origCoords[1];

        int[] destCoords = BoardGame.convertSquareNumToCoords(destSquareNum);
        int destSquareRow = destCoords[0];
        int destSquareCol = destCoords[1];

        ChessPiece piece = BOARD[origionSquareRow][origionSquareCol];

        BOARD[destSquareRow][destSquareCol] = piece;
        BOARD[origionSquareRow][origionSquareCol] = null;
    }

        public void setBorderColourForSelectedPieceSquare(Integer square) {
        setHighlightForSquare(square, Color.GREEN);
    }

    private void setHighlightForSquare(Integer square, Color colour) {

        int[] currentSquare = BoardGame.convertSquareNumToCoords(square);
        int squareRow = currentSquare[0];
        int squareCol = currentSquare[1];

        this.board_highlights[squareRow][squareCol] = colour;

        board_highlights[squareRow][squareCol] = colour;
    }

    private void setBorderColourForValidMoves() {
        for (Integer potentialMove : this.validMoves) {
            setHighlightForSquare(potentialMove, Color.YELLOW);
        }
    }

//    private void clearValidMoves() {
//        System.out.println("clearValidMoves called");
//        for (Integer potentialMove : this.validMoves) {
//            setHighlightForSquare(potentialMove, Color.lightGray);
//        }
//        validMoves.clear();
//        validJumps.clear();
//        resetBorderColors();
//    }
    
    
    private void clearValidMoves() {
        this.validMoves.clear();
    }

    private void endTurn() {
        resetBorderColors();
        this.playerToMove = playerToMove.equalsIgnoreCase("White")? "Black" :"White";
    }

}
