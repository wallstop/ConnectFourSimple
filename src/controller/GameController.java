package controller;

import game.GameBoard;
import game.Move;
import game.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.GameView;

public final class GameController
{

    public static void main(String args[])
    {
        // TODO: Move this to testing, just a small playground while I get re-acquainted with swing
        final GameBoard gameBoard = new GameBoard(6, 7);

        final GameView gameView = new GameView(gameBoard);

        gameBoard.addMove(new Move(0, Player.PLAYER_1));
        gameBoard.addMove(new Move(0, Player.PLAYER_1));
        gameBoard.addMove(new Move(0, Player.PLAYER_2));

        final JPanel panel = gameView.getBoardRepresentation();
        gameView.refresh();

        final JFrame frame = new JFrame();
        frame.add(panel);

        frame.setVisible(true);
        frame.setBounds(0, 0,  500, 500);
        frame.setResizable(false);

    }

}
