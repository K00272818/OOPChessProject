/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.draughts;

import draughtsoop.BoardGame;
import draughtsoop.Game;
import draughtsoop.GamePiece;
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
public class Draughts implements Game {

//    Image redManPiece;
//    Image redKingPiece;
//    Image whiteManPiece;
//    Image whiteKingPiece;
    private String[] gameStatMessages = new String[3];

    private DraughtsPiece[][] BOARD;
    private Color[][] board_highlights;

    private String playerToMove = "R";//or "W"

    private List<Integer> validMoves = new ArrayList();
    private List<Integer> validJumps = new ArrayList();

    private int jumpCount;
    private int currentSelectedSquare;
    private int currentSelectedPieceLocation;

    @Override
    public void newGame() {

        this.BOARD = new DraughtsPiece[][] {
            {null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red")},
            {new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null},
            {null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red"), null, new DraughtsMan("Red")},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White"), null},
            {null, new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White")},
            {new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White"), null, new DraughtsMan("White"), null}
        };

        this.board_highlights = new Color[][]{
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null}
        };

        this.gameStatMessages[0] = writePlayerToMoveMessage();
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
    public void squareSelected(JButton[][] boardButtons, int squareNum) {
        System.out.println("mouseReleased squareNum : " + squareNum + " validMoves : " + this.validMoves + " validJumps : " + this.validJumps);
        currentSelectedSquare = squareNum;
        int[] currentSelectedSquareCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int squareRow = currentSelectedSquareCoords[0];
        int squareCol = currentSelectedSquareCoords[1];

        DraughtsPiece piece = BOARD[squareRow][squareCol];
        String pieceCode = "";
        if (piece != null) {
            pieceCode = piece.toString();
            this.validMoves = piece.findValidSlide(squareRow, squareCol, BOARD);
            this.validJumps = piece.findJumps(squareRow, squareCol, BOARD);
        }

        if (this.jumpCount > 0 && pieceCode.contains(playerToMove)) {
            
            System.out.println("squareSelected Cond1");
            System.out.println("squareSelected Cond1 jumpCount"+jumpCount);
            clearValidMoves();
            gameStatMessages[1] = " ";
            gameStatMessages[2] = " ";
            endTurn();
        } else if (pieceCode.contains(playerToMove)) {
            System.out.println("squareSelected Cond2");
            System.out.println("mouseReleased pieceCode.contains : " + playerToMove);
            clearValidMoves();
            paintBoard(boardButtons);

            this.validMoves = piece.findValidMoves(squareNum, BOARD);
            this.validJumps = piece.findJumps(squareRow, squareCol, BOARD);
            setBorderColourForValidMoves();
            currentSelectedPieceLocation = currentSelectedSquare;
            setBorderColourForSelectedPieceSquare(squareNum);
        } else if (validJumps.contains(squareNum)) {
            System.out.println("squareSelected Cond4");
            System.out.println("mouseReleased validJumps.contains : " + squareNum);
            performJump(this.currentSelectedPieceLocation, this.currentSelectedSquare);
        } else if (validMoves.contains(squareNum)) {
            System.out.println("squareSelected Cond3");
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

    private void setBorderColourForSelectedPieceSquare(Integer square) {
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

    private void clearValidMoves() {
        System.out.println("clearValidMoves called");
        for (Integer potentialMove : this.validMoves) {
            setHighlightForSquare(potentialMove, Color.lightGray);
        }
        validMoves.clear();
        validJumps.clear();
        resetBorderColors();
    }

    private void performMove(int origionSquareNum, int destSquareNum) {

        System.out.println("performMove origionSquareNum : " + origionSquareNum + ", destSquareNum : " + destSquareNum);

        int[] origCoords = BoardGame.convertSquareNumToCoords(origionSquareNum);
        int origionSquareRow = origCoords[0];
        int origionSquareCol = origCoords[1];

        int[] destCoords = BoardGame.convertSquareNumToCoords(destSquareNum);
        int destSquareRow = destCoords[0];
        int destSquareCol = destCoords[1];

        DraughtsPiece piece = BOARD[origionSquareRow][origionSquareCol];

        if (destSquareRow == 7 && piece.getPlayer().equalsIgnoreCase("Red")) {
            piece = new DraughtsKing("Red");
        } else if (destSquareRow == 0 && piece.getPlayer().equalsIgnoreCase("White")) {
            piece = new DraughtsKing("White");
        }

        BOARD[destSquareRow][destSquareCol] = piece;
        BOARD[origionSquareRow][origionSquareCol] = null;
    }

    private void performJump(int origionSquareNum, int destSquareNum) {

        System.out.println("performJump " + playerToMove + " int origionSquareNum," + origionSquareNum + " int destSquareNum, " + destSquareNum);
        int[] origCoords = BoardGame.convertSquareNumToCoords(origionSquareNum);
        int origionSquareRow = origCoords[0];
        int origionSquareCol = origCoords[1];

        int[] destCoords = BoardGame.convertSquareNumToCoords(destSquareNum);
        int destSquareRow = destCoords[0];
        int destSquareCol = destCoords[1];

        DraughtsPiece jumpingPiece = BOARD[origionSquareRow][origionSquareCol];

        int takenPieceRow = (origionSquareRow + destSquareRow) / 2;
        int takenPieceCol = (origionSquareCol + destSquareCol) / 2;

        BOARD[takenPieceRow][takenPieceCol] = null;
        performMove(origionSquareNum, destSquareNum);
        this.jumpCount++;
        clearValidMoves();
        jumpingPiece = BOARD[destSquareRow][destSquareCol];
        this.currentSelectedPieceLocation = destSquareNum;
        this.validJumps = jumpingPiece.findJumps(destSquareRow, destSquareCol, BOARD);
        this.validMoves = validJumps;
        if (this.validJumps.isEmpty()) {
            endTurn();
        } else {
            setBorderColourForValidMoves();
            this.gameStatMessages[1] = "Multi Jump Available!";
            this.gameStatMessages[2] = "Click your piece to rescind.";
        }

    }

    private void endTurn() {
        jumpCount = 0;
        playerToMove = playerToMove.equals("W") ? "R" : "W";
        this.gameStatMessages[0] = writePlayerToMoveMessage();
        this.gameStatMessages[1] = (" ");
        this.gameStatMessages[2] = (" ");

    }

    private String writePlayerToMoveMessage() {
        String playerToMoveMessage = "";
        if (playerToMove.equalsIgnoreCase("R")) {
            playerToMoveMessage = "Red Player to Move";
        } else {
            playerToMoveMessage = "White Player to Move";
        }

        return playerToMoveMessage;
    }

    private String winner() {
        boolean whitePresent = false;
        boolean redPresent = false;
        for (GamePiece[] col : BOARD) {
            for (GamePiece piece : col) {
                if (piece != null) {
                    if (piece.getPlayer().equalsIgnoreCase("White")) {
                        whitePresent = true;
                    } else if (piece.getPlayer().equalsIgnoreCase("Red")) {
                        redPresent = true;
                    }
                }
                if (whitePresent && redPresent) {
                    return "";
                }
            }
        }
        return whitePresent ? "White" : "Red";
    }

    @Override
    public GamePiece[][] getState() {
        return this.BOARD;
    }

    @Override
    public String[] getGameStateMessage() {
        return this.gameStatMessages;
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

}
