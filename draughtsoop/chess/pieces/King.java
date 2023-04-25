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
public class King extends ChessPiece{
    
    private boolean hasMoved;

    public King(String player) {
        super(player, "resources/Chess_"+player+"_king.png", "BK");
        hasMoved = false;
    }
    
    public int[] findKingCoords(GamePiece[][] board) {
    int[] kingCoords = new int[2];
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            GamePiece piece = board[row][col];
            if (piece instanceof King && piece.getPlayer().equals(this.player)) {
                kingCoords[0] = row;
                kingCoords[1] = col;
                return kingCoords;
            }
        }
    }
    // King not found
    return null;
}
    
    public boolean isInCheck(GamePiece[][] board) {
    int[] kingCoords = findKingCoords(board);
    int kingRow = kingCoords[0];
    int kingCol = kingCoords[1];

    // Check if any opponent pawns can attack the king
    int opponentForwardDirection = this.player.equalsIgnoreCase("White") ? 1 : -1;
    int leftDiagonal = BoardGame.convertCoordsToSqNum(new int[]{kingRow + opponentForwardDirection, kingCol-1});
    int rightDiagonal = BoardGame.convertCoordsToSqNum(new int[]{kingRow + opponentForwardDirection, kingCol+1});
    if (leftDiagonal >= 0 && leftDiagonal <= 63 && board[kingRow + opponentForwardDirection][kingCol - 1] instanceof Pawn && 
            !board[kingRow + opponentForwardDirection][kingCol - 1].getPlayer().equalsIgnoreCase(this.player)) {
        return true;
    }
    if (rightDiagonal >= 0 && rightDiagonal <= 63 && board[kingRow + opponentForwardDirection][kingCol + 1] instanceof Pawn && 
            !board[kingRow + opponentForwardDirection][kingCol + 1].getPlayer().equalsIgnoreCase(this.player)) {
        return true;
    }
    
    // Check if any opponent knights can attack the king
    int[][] knightMoves = new int[][]{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
    for (int[] move : knightMoves) {
        int newRow = kingRow + move[0];
        int newCol = kingCol + move[1];
        if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && 
                board[newRow][newCol] instanceof Knight && 
                !board[newRow][newCol].getPlayer().equalsIgnoreCase(this.player)) {
            return true;
        }
    }
    
    // Check if any opponent rooks or queens can attack the king in a straight line
    int[][] straightLineMoves = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    for (int[] move : straightLineMoves) {
        for (int i = 1; i <= 7; i++) {
            int newRow = kingRow + i*move[0];
            int newCol = kingCol + i*move[1];
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
                if (board[newRow][newCol] instanceof Rook || board[newRow][newCol] instanceof Queen) {
                    if (!board[newRow][newCol].getPlayer().equalsIgnoreCase(this.player)) {
                        return true;
                    } else {
                        break; // stop searching in this direction since there's a same-colored piece blocking the way
                    }
                } else if (board[newRow][newCol] != null) {
                    break; // stop searching in this direction since there's a piece blocking the way
                }
            }
        }
    }
    
    // Check if any opponent bishops or queens can attack the king diagonally
    int[][] diagonalMoves = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    for (int[] move : diagonalMoves) {
        for (int i = 1; i <= 7; i++) {
            int newRow = kingRow + move[0];
                int newCol = kingCol + move[1];
                if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
                    GamePiece piece = board[newRow][newCol];
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        if (!piece.getPlayer().equalsIgnoreCase(this.player)) {
                            return true;
                        } else {
                            break;
                        }
                    } else if (piece != null) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    return false;
        }


    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        List<Integer> validMoves = new ArrayList<>();

    int[] currentSquare = BoardGame.convertSquareNumToCoords(squareNum);
    int currentRow = currentSquare[0];
    int currentCol = currentSquare[1];

    int[] possibleMoves = {-1, 0, 1};

    // Check all possible moves for the king
    for (int rowOffset : possibleMoves) {
        for (int colOffset : possibleMoves) {
            // Don't check the king's current position
            if (rowOffset == 0 && colOffset == 0) {
                continue;
            }
            
            int rowToCheck = currentRow + rowOffset;
            int colToCheck = currentCol + colOffset;

            // Make sure the position is on the board
            if (rowToCheck < 0 || rowToCheck > 7 || colToCheck < 0 || colToCheck > 7) {
                continue;
            }

            GamePiece piece = board[rowToCheck][colToCheck];
            if (piece == null || !piece.getPlayer().equals(this.player)) {
                // This position is empty or has an opponent's piece, so the king can move here
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{rowToCheck, colToCheck}));
            }
        }
    }
    
    // Check for castling moves
    if (!hasMoved && !isInCheck(board)) {
        int row = this.player.equalsIgnoreCase("White") ? 7 : 0;

        // Check for kingside castling
        if (board[row][7] instanceof Rook && !((Rook) board[row][7]).hasMoved()) {
            // Make sure the spaces between the king and rook are empty
            if (board[row][5] == null && board[row][6] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{row, 6}));
            }
        }

        // Check for queenside castling
        if (board[row][0] instanceof Rook && !((Rook) board[row][0]).hasMoved()) {
            // Make sure the spaces between the king and rook are empty
            if (board[row][1] == null && board[row][2] == null && board[row][3] == null) {
                validMoves.add(BoardGame.convertCoordsToSqNum(new int[]{row, 2}));
            }
        }
    }
    
    return validMoves;
    
    }
}
