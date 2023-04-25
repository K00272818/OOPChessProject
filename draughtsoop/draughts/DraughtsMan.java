/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.draughts;

import java.util.List;

/**
 *
 * @author James.Fennell
 */
public class DraughtsMan extends DraughtsPiece {

    public DraughtsMan( String player) {
        super("", player, "");

        if (player.equalsIgnoreCase("Red")) {
            this.pieceImageURL = "resources/dark_man.png";
            this.pieceCode = "RM";
        } else {
            this.pieceImageURL = "resources/light_man.png";
            this.pieceCode = "WM";
        }
    }

    @Override
    public List<Integer> findValidSlide(int squareRow, int squareCol, DraughtsPiece[][] board) {

        if (player.equalsIgnoreCase("Red")) {
            return super.getDownBoardSlides(squareRow, squareCol, board);
        } else {
            return super.getUpBoardSlides(squareRow, squareCol, board);
        }
    }

    @Override
    public List<Integer> findJumps(int squareRow, int squareCol, DraughtsPiece[][] board) {
        if (player.equalsIgnoreCase("Red")) {
            return super.getDownBoardJumps(squareRow, squareCol, board);
        } else {
            return super.getUpBoardJumps(squareRow, squareCol, board);
        }
    }

}
