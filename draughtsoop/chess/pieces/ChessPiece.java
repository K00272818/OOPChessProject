/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draughtsoop.chess.pieces;

import draughtsoop.GamePiece;
import draughtsoop.draughts.DraughtsPiece;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author James.Fennell
 */
public abstract class ChessPiece implements GamePiece {

    Image image;
    String pieceCode;
    final String player;

    public ChessPiece(String player, String imagePath, String pieceCode) {
        this.player = player;
        buildImage(imagePath);
        this.pieceCode = pieceCode;
    }


    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public boolean isOpponent(GamePiece piece) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getPieceCode() {
        return pieceCode;
    }

    public String getPlayer() {
        return player;
    }

    
    
    @Override
    public String toString() {
        return this.pieceCode;
    }

    protected void buildImage(String pathToImage) {
        System.out.println("buildImage." + pathToImage);
        URL imageURL = ChessPiece.class.getResource(pathToImage);
        Image pieceImage = null;

        try {
            pieceImage = ImageIO.read(imageURL);
System.out.println("Image Found.\n" + imageURL);
        } catch (IOException ex) {
            System.out.println("Image not Found.\n" + ex);
        }
        image = pieceImage;
    }

}
