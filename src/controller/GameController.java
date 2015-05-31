package controller;

import game.Player;
import games.connectfour.ConnectFourGameBoard;
import games.connectfour.ConnectFourMove;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.GameView;

public final class GameController
{

    public static void main(String args[])
    {
        // TODO: Move this to testing, just a small playground while I get re-acquainted with swing
        final ConnectFourGameBoard gameBoard = new ConnectFourGameBoard(6, 7);

        final GameView gameView = new GameView(gameBoard);

        gameBoard.addMove(new ConnectFourMove(0, Player.PLAYER_1));
        gameBoard.addMove(new ConnectFourMove(0, Player.PLAYER_1));
        gameBoard.addMove(new ConnectFourMove(0, Player.PLAYER_2));

        final JPanel panel = gameView.getBoardRepresentation();
        gameView.refresh();

        final JFrame frame = new JFrame();
        frame.add(panel);

        frame.setVisible(true);
        frame.setBounds(0, 0,  500, 500);
        frame.setResizable(false);

    }

}
