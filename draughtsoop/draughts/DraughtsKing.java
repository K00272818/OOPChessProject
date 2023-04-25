/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.draughts;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James.Fennell
 */
public class DraughtsKing extends DraughtsPiece {

    public DraughtsKing( String player) {
        super("", player, "");
        if (player.equalsIgnoreCase("Red")) {
            this.pieceImageURL = "resources/dark_king.png";
            this.pieceCode = "RK";
        } else {
            this.pieceImageURL = "resources/light_king.png";
            this.pieceCode = "WK";
        }
    }

    @Override
    public List<Integer> findValidSlide(int squareRow, int squareCol, DraughtsPiece[][] board) {
        List<Integer> slides = new ArrayList();
        slides.addAll(super.getUpBoardSlides(squareRow, squareCol, board));
        slides.addAll(super.getDownBoardSlides(squareRow, squareCol, board));
        return slides;
    }

    @Override
    public List<Integer> findJumps(int squareRow, int squareCol, DraughtsPiece[][] board) {
        List<Integer> jumps = new ArrayList();
        jumps.addAll(super.getUpBoardJumps(squareRow, squareCol, board));
        jumps.addAll(super.getDownBoardJumps(squareRow, squareCol, board));
        return jumps;
    }

}
