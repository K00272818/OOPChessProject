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
public class Knight extends ChessPiece {

    public Knight(String player) {
        super(player, "resources/Chess_" + player + "_knight.png", "BK");
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        int[] currentSelectedSquareCoords = BoardGame.convertSquareNumToCoords(squareNum);
        int squareRow = currentSelectedSquareCoords[0];
        int squareCol = currentSelectedSquareCoords[1];

        List<Integer> validMoves = new ArrayList();

        int[][] potentialMoves = {{squareRow + 2, squareCol + 1}, {squareRow + 1, squareCol + 2}, {squareRow - 2, squareCol + 1}, {squareRow - 1, squareCol + 2}, {squareRow + 2, squareCol - 1}, {squareRow + 1, squareCol - 2}, {squareRow - 2, squareCol - 1}, {squareRow - 1, squareCol - 2}};

        for (int[] coords : potentialMoves) {
            if (BoardGame.validCoords(coords)) {
                validMoves.add(BoardGame.convertCoordsToSqNum(coords));
            }
        }
        System.out.println("validMoves : "+validMoves);
        return validMoves;
    }



}
