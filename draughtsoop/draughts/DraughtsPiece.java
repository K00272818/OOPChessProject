/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.draughts;

import draughtsoop.BoardGame;
import draughtsoop.GamePiece;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author James.Fennell
 */
public abstract class DraughtsPiece implements GamePiece {

    String pieceImageURL = "";
    protected final String player;
    protected String pieceCode;

    public DraughtsPiece(String imageURL, String player,String pieceCode) {
        this.pieceImageURL = imageURL;
        this.player = player;
        this.pieceCode = pieceCode;
    }

    @Override
    public List<Integer> findValidMoves(int squareNum, GamePiece[][] board) {
        DraughtsPiece[][] draughtsBoard = (DraughtsPiece[][]) board;
        int[] coords = BoardGame.convertSquareNumToCoords(squareNum);

        List<Integer> validSlides = findValidSlide(coords[0], coords[1], draughtsBoard);
        List<Integer> validJumps = findJumps(coords[0], coords[1], draughtsBoard);
        validSlides.addAll(validJumps);
        return validSlides;
    }

    @Override
    public Image getImage() {
        URL imageURL = DraughtsPiece.class.getResource(this.pieceImageURL);
        Image pieceImage = null;
//        System.out.println("getImage this.pieceImageURL "+pieceImageURL);
//        System.out.println("\ngetImage imageURL "+imageURL.toString());

        try {
            pieceImage = ImageIO.read(imageURL);

        } catch (IOException ex) {
            System.out.println("Image not Found.\n" + ex);
        }
        return pieceImage;
    }

    protected List<Integer> getDownBoardSlides(int squareRow, int squareCol, DraughtsPiece[][] board) {

        List<Integer> validSlides = new ArrayList();
        if (squareRow < 7) {
            if (squareCol > 0 && board[squareRow + 1][squareCol - 1] == null) {
                int[] potentialMove = {squareRow + 1, squareCol - 1};
                validSlides.add(BoardGame.convertCoordsToSqNum(potentialMove));
            }
            if (squareCol < 7 && board[squareRow + 1][squareCol + 1] == null) {
                int[] potentialMove = {squareRow + 1, squareCol + 1};
                validSlides.add(BoardGame.convertCoordsToSqNum(potentialMove));
            }
        }
        return validSlides;
    }

    protected List<Integer> getDownBoardJumps(int squareRow, int squareCol, DraughtsPiece[][] board) {

        System.out.println("getDownBoardJumps int squareRow," + squareRow + " int squareCol," + squareCol);

        List<Integer> validJumps = new ArrayList();

        if (squareRow + 2 <= 7) {
            System.out.println("getDownBoardJumps Cond1");
            if (squareCol + 2 <= 7) {
                System.out.println("getDownBoardJumps Cond2");
                if (isJumpAvailable(squareRow, squareCol, squareRow + 2, squareCol + 2, board)) {
                    System.out.println("getDownBoardJumps Cond3");
                    int[] potentialMove = {squareRow + 2, squareCol + 2};
                    validJumps.add(BoardGame.convertCoordsToSqNum(potentialMove));
                }
            }
            if (squareCol - 2 >= 0) {
                System.out.println("getDownBoardJumps Cond4");
                if (isJumpAvailable(squareRow, squareCol, squareRow + 2, squareCol - 2, board)) {
                    System.out.println("getDownBoardJumps Cond5");
                    int[] potentialMove = {squareRow + 2, squareCol - 2};
                    validJumps.add(BoardGame.convertCoordsToSqNum(potentialMove));
                }
            }
        }
        return validJumps;
    }

    protected List<Integer> getUpBoardSlides(int squareRow, int squareCol, DraughtsPiece[][] board) {
        List<Integer> validSlides = new ArrayList();
        if (squareRow > 0) {
            if (squareCol > 0 && board[squareRow - 1][squareCol - 1] == null) {
                int[] potentialMove = {squareRow - 1, squareCol - 1};
                validSlides.add(BoardGame.convertCoordsToSqNum(potentialMove));
            }
            if (squareCol < 7 && board[squareRow - 1][squareCol + 1] == null) {
                int[] potentialMove = {squareRow - 1, squareCol + 1};
                validSlides.add(BoardGame.convertCoordsToSqNum(potentialMove));
            }
        }
        return validSlides;
    }

    protected List<Integer> getUpBoardJumps(int squareRow, int squareCol, DraughtsPiece[][] board) {

        System.out.println("getUpBoardJumps int squareRow," + squareRow + " int squareCol," + squareCol);

        List<Integer> validJumps = new ArrayList();

        if (squareRow - 2 >= 0) {
            System.out.println("getUpBoardJumps Cond1");
            if (squareCol + 2 <= 7) {
                System.out.println("getUpBoardJumps Cond2");
                if (isJumpAvailable(squareRow, squareCol, squareRow - 2, squareCol + 2, board)) {
                    System.out.println("getUpBoardJumps Cond3");
                    int[] potentialMove = {squareRow - 2, squareCol + 2};
                    validJumps.add(BoardGame.convertCoordsToSqNum(potentialMove));
                }
            }
            if (squareCol - 2 >= 0) {
                System.out.println("getUpBoardJumps Cond4");
                if (isJumpAvailable(squareRow, squareCol, squareRow - 2, squareCol - 2, board)) {
                    System.out.println("getUpBoardJumps Cond5");
                    int[] potentialMove = {squareRow - 2, squareCol - 2};
                    validJumps.add(BoardGame.convertCoordsToSqNum(potentialMove));
                }
            }
        }
        return validJumps;
    }

    protected boolean isJumpAvailable(int origionSquareRow, int origionSquareCol, int destSquareRow, int destSquareCol, DraughtsPiece[][] board) {

        System.out.println("isJumpAvailable int origionSquareRow,"+origionSquareRow+" int origionSquareCol,,"+origionSquareCol+"  int destSquareRow,,"+destSquareRow+"  int destSquareCol,,"+destSquareCol);
        
        DraughtsPiece jumpingPiece = board[origionSquareRow][origionSquareCol];

        boolean landingAvailable = board[destSquareRow][destSquareCol] == null;

        int jumpSquareRow = (origionSquareRow + destSquareRow) / 2;
        int jumpSquareCol = (origionSquareCol + destSquareCol) / 2;

        DraughtsPiece jumpedPiece = board[jumpSquareRow][jumpSquareCol];

        boolean jumpAvailable = false;
        
        if(jumpedPiece != null){
            System.out.println("isJumpAvailable Cond1");
            jumpAvailable = landingAvailable && jumpedPiece.isOpponent(jumpingPiece);
             System.out.println("landingAvailable : "+landingAvailable+", jumpedPiece != null"+", jumpedPiece.isOpponent(jumpingPiece)"+jumpedPiece.isOpponent(jumpingPiece));
        }
        

        
       
        
        return jumpAvailable;
    }

        @Override
     public boolean isOpponent(GamePiece piece) {
        return !piece.getPlayer().equalsIgnoreCase(this.player);
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return this.pieceCode;
    }
    
     
     
    public abstract List<Integer> findValidSlide(int squareRow, int squareCol, DraughtsPiece[][] board);

    public abstract List<Integer> findJumps(int squareRow, int squareCol, DraughtsPiece[][] board);

}
