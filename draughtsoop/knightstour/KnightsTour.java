///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package draughtsoop.knightstour;
//
//import draughtsoop.Game;
//import javax.swing.JButton;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author James.Fennell
// */
//public class KnightsTour implements Game{
//
//    int startSquare;
//    String[][] BOARD;
//    
//    @Override
//    public String[][] getState() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void paintBoard(JButton[][] boardButtons) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public String[] getGameStateMessage() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void squareSelected(JButton[][] boardButtons, int squareNum) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void newGame() {
//        this.startSquare = Integer.parseInt(JOptionPane.showInputDialog("What square wouldyou like to start from?"));
//                String[][] board_string_start = {
//            {" ", "R", " ", "R", " ", "R", " ", "R"},
//            {"R", " ", "R", " ", "R", " ", "R", " "},
//            {" ", "R", " ", "R", " ", "R", " ", "R"},
//            {" ", " ", " ", " ", " ", " ", " ", " "},
//            {" ", " ", " ", " ", " ", " ", " ", " "},
//            {"W", " ", "W", " ", "W", " ", "W", " "},
//            {" ", "W", " ", "W", " ", "W", " ", "W"},
//            {"W", " ", "W", " ", "W", " ", "W", " "}
//        };
//
//        this.BOARD = board_string_start;
//    }
//
//    @Override
//    public boolean gameIsOver() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//    
//}
