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
public class Pawn extends ChessPiece{
    
    private boolean enPassant;
    
    public Pawn(String player) {
        super(player, "resources/Chess_"+player+"_pawn.png", "BP");
        enPassant = false;
    }
    
    public boolean isEnPassantCapture() {
    return this.enPassant;
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        List<Integer> validMoves = new ArrayList<>();
    
    int[] currentSquare = BoardGame.convertSquareNumToCoords(squareNum);
    int currentRow = currentSquare[0];
    int currentCol = currentSquare[1];

    int forwardDirection = this.player.equalsIgnoreCase("White") ? -1 : 1;
    int startingRow = this.player.equalsIgnoreCase("White") ? 6 : 1;
    int enPassantRow = this.player.equalsIgnoreCase("White") ? 3 : 4;
    
    // Check if the pawn can move one square forward
    int oneSquareForward = BoardGame.convertCoordsToSqNum(new int[]{currentRow + forwardDirection, currentCol});
    if (oneSquareForward >= 1 && oneSquareForward <= 63 && board[currentRow + forwardDirection][currentCol] == null) {
        validMoves.add(oneSquareForward);

        // Check if the pawn is on its starting row and can move two squares forward
        if (currentRow == startingRow && board[currentRow + 2*forwardDirection][currentCol] == null) {
            int twoSquaresForward = BoardGame.convertCoordsToSqNum(new int[]{currentRow + 2*forwardDirection, currentCol});
            validMoves.add(twoSquaresForward);
        }
    }
    
    // Check if the pawn can capture an opponent's piece on the left diagonal
    int[] leftDiagonal = {currentRow + forwardDirection, currentCol - 1};
    int[] rightDiagonal = {currentRow + forwardDirection, currentCol + 1};
    if (BoardGame.validCoords(leftDiagonal) && board[leftDiagonal[0]][leftDiagonal[1]] != null && !board[leftDiagonal[0]][leftDiagonal[1]].getPlayer().equals(getPlayer())) {
        validMoves.add(BoardGame.convertCoordsToSqNum(leftDiagonal));
    }
    if (BoardGame.validCoords(rightDiagonal) && board[rightDiagonal[0]][rightDiagonal[1]] != null && !board[rightDiagonal[0]][rightDiagonal[1]].getPlayer().equals(getPlayer())) {
        validMoves.add(BoardGame.convertCoordsToSqNum(rightDiagonal));
    }
    
    // Check for en passant
    if (currentRow == enPassantRow) {
        // Check for potential capture on the left
        int leftSquare = BoardGame.convertCoordsToSqNum(new int[]{currentRow, currentCol - 1});
        if (leftSquare >= 1 && leftSquare <= 63 && board[currentRow][currentCol - 1] instanceof Pawn && 
                !board[currentRow][currentCol - 1].getPlayer().equalsIgnoreCase(this.player)) {
            Pawn leftPawn = (Pawn) board[currentRow][currentCol - 1];
            if (leftPawn.isEnPassantCapture()) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{currentRow + forwardDirection, currentCol-1}));
            }
        }

        // Check for potential capture on the right
        int rightSquare = BoardGame.convertCoordsToSqNum(new int[]{currentRow, currentCol + 1});
        if (rightSquare >= 1 && rightSquare <= 63 && board[currentRow][currentCol + 1] instanceof Pawn 
        && board[currentRow + forwardDirection][currentCol + 1] != null 
        && board[currentRow + forwardDirection][currentCol + 1].getPlayer() != getPlayer()) {
            validMoves.add(rightSquare);

    }
    
    } 
    return validMoves;
 }
    
    
}