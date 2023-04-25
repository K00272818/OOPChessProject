/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package draughtsoop;

import java.awt.Image;
import java.util.List;

/**
 *
 * @author James.Fennell
 */
public interface GamePiece {
    
    public List<Integer> findValidMoves(int squareNum, GamePiece[][]board);
    
    public Image getImage();
    
    public String getPlayer();
    
    @Override
    public String toString();
    
    public boolean isOpponent(GamePiece piece);
    
}
