/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.chess.pieces;

import draughtsoop.BoardGame;
import draughtsoop.GamePiece;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James.Fennell
 */
public class Bishop extends ChessPiece{

    public Bishop(String player) {
        super(player, "resources/Chess_"+player+"_bishop.png", "BB");
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        List<Integer> validMoves = new ArrayList<>();
        int[] currentSelectedSquareCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int squareRow = currentSelectedSquareCoords[0];
        int squareCol = currentSelectedSquareCoords[1];

        // Check moves in all four diagonal directions
        for (int i = 1; i <= 7; i++) {
            int newRow = squareRow + i;
            int newCol = squareCol + i;
            if (newRow <= 7 && newCol <= 7 && board[newRow][newCol] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
            } else if (newRow <= 7 && newCol <= 7 && board[newRow][newCol].getPlayer() != this.player) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
                break;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newRow = squareRow + i;
            int newCol = squareCol - i;
            if (newRow <= 7 && newCol >= 0 && board[newRow][newCol] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
            } else if (newRow <= 7 && newCol >= 0 && board[newRow][newCol].getPlayer() != this.player) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
                break;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newRow = squareRow - i;
            int newCol = squareCol + i;
            if (newRow >= 0 && newCol <= 7 && board[newRow][newCol] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
            } else if (newRow >= 0 && newCol <= 7 && board[newRow][newCol].getPlayer() != this.player) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
                break;
            } else {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newRow = squareRow - i;
            int newCol = squareCol - i;
            if (newRow >= 0 && newCol >= 0 && board[newRow][newCol] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
            } else if (newRow >= 0 && newCol >= 0 && board[newRow][newCol].getPlayer() != this.player) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{newRow, newCol}));
                break;
            } else {
                break;
            }
        }

        return validMoves;
    }

    
    
}
