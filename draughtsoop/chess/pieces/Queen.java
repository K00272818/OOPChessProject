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
public class Queen extends ChessPiece{

    public Queen(String player) {
        super(player, "resources/Chess_"+player+"_queen.png", "BQ");
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        List<Integer> validMoves = new ArrayList<Integer>();
        int[] currentCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int currentRow = currentCoords[0];
        int currentCol = currentCoords[1];

        // Check vertical and horizontal moves
        Rook rook = new Rook(player);
        List<Integer> rookMoves = rook.findValidMoves(squareNum, board);
        validMoves.addAll(rookMoves);

        // Check diagonal moves
        Bishop bishop = new Bishop(player);
        List<Integer> bishopMoves = bishop.findValidMoves(squareNum, board);
        validMoves.addAll(bishopMoves);

        return validMoves;
    }

}
