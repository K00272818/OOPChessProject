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
public class Rook extends ChessPiece {
    
    private boolean hasMoved;
    
    public Rook(String player) {
        super(player,"resources/Chess_"+player+"_rook.png", "BR");
        hasMoved = false;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        int[] currentSelectedSquareCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int squareRow = currentSelectedSquareCoords[0];
        int squareCol = currentSelectedSquareCoords[1];

        List<Integer> validMoves = new ArrayList();

        // Check moves to the right
        for (int col = squareCol + 1; col < 8; col++) {
            int[] coords = {squareRow, col};
            if (!addMove(coords, validMoves, board)) {
                break;
            }
        }

        // Check moves to the left
        for (int col = squareCol - 1; col >= 0; col--) {
            int[] coords = {squareRow, col};
            if (!addMove(coords, validMoves, board)) {
                break;
            }
        }

        // Check moves upwards
        for (int row = squareRow - 1; row >= 0; row--) {
            int[] coords = {row, squareCol};
            if (!addMove(coords, validMoves, board)) {
                break;
            }
        }

        // Check moves downwards
        for (int row = squareRow + 1; row < 8; row++) {
            int[] coords = {row, squareCol};
            if (!addMove(coords, validMoves, board)) {
                break;
            }
        }

        return validMoves;
    }

    private boolean addMove(int[] coords, List<Integer> validMoves, GamePiece[][] board) {
        if (BoardGame.validCoords(coords)) {
            int squareNum = BoardGame.convertCoordsToSqNum(coords);
            GamePiece piece = board[coords[0]][coords[1]];
            if (piece == null) {
                validMoves.add(squareNum);
                return true;
            } else if (!piece.getPlayer().equals(this.getPlayer())) {
                validMoves.add(squareNum);
            }
        }
        return false;
    }

}
