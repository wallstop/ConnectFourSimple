package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Validate;
import game.GameBoard;
import game.Player;

public final class GameView
{
    private final GameBoard gameBoard_;
    private final Map<Player, Color> playerColors_;
    private final JPanel boardRepresentation_;

    public GameView(final GameBoard gameBoard)
    {
        Validate.notNull(gameBoard,
                "Cannot create a view into a null gameboard");
        gameBoard_ = gameBoard;
        playerColors_ = new HashMap<Player, Color>(Player.values().length);
        boardRepresentation_ = new JPanel();

        initializeLayout();
        initializePlayerColors();
        refresh();
    }

    private void initializePlayerColors()
    {
        playerColors_.put(Player.PLAYER_1, Color.BLACK);
        playerColors_.put(Player.PLAYER_2, Color.RED);
        playerColors_.put(null, Color.WHITE);
    }


    public JPanel getBoardRepresentation()
    {
        return boardRepresentation_;
    }

    // Assume valid input
    public void setPlayerColor(final Player player, final Color color)
    {
        playerColors_.put(player, color);
        refresh();
    }

    public void setBoardColor(final Color color)
    {
        setPlayerColor(null, color);
    }
    
    private void removeAllExistingComponents()
    {
        final Collection<Component> components = Arrays
                .asList(boardRepresentation_.getComponents());
        for (Component component : components)
        {
            boardRepresentation_.remove(component);
        }
    }
    
    private void initializeLayout()
    {
        final int width = gameBoard_.getWidth();
        final int height = gameBoard_.getHeight();
        final GridLayout connectFourLayout = new GridLayout();
        connectFourLayout.setColumns(width);
        connectFourLayout.setRows(height);

        boardRepresentation_.setLayout(connectFourLayout);
    }
    
    private void addGameMoves()
    {
        final Player[][] boardRepresentation = gameBoard_
                .getBoardRepresentation();
        int width = gameBoard_.getWidth();
        int height = gameBoard_.getHeight();
        for (int j = 0; j < height; ++j)
        {
            for (int i = 0; i < width; ++i)
            {
                final Player player = boardRepresentation[i][j];
                final JPanel panel = new JPanel();
                final Color color = playerColors_.get(player);
                panel.setBackground(color);
                boardRepresentation_.add(panel);
            }
        }
    }

    public void refresh()
    {
        removeAllExistingComponents();
        addGameMoves();
    }
}
