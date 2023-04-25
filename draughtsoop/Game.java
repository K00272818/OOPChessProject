/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package draughtsoop;

import javax.swing.JButton;

/**
 *
 * @author James.Fennell
 */
public interface Game {
    
    public GamePiece[][] getState();
    public void paintBoard(JButton[][] boardButtons);
    public String[] getGameStateMessage();
    public void squareSelected(JButton[][] boardButtons,int squareNum);
    public void newGame();
    public boolean gameIsOver();

}
