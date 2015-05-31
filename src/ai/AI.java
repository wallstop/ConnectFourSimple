package ai;

import utils.Validate;
import game.Player;
import games.connectfour.ConnectFourGameBoard;
import games.connectfour.ConnectFourMove;

public abstract class AI
{
    final Player player_;

    public AI(final Player player)
    {
        Validate.notNull(player, "Cannot create an AI based on a null Player");
        player_ = player;
    }

    public abstract ConnectFourMove determineMove(final ConnectFourGameBoard gameBoard);

    @Override
    public String toString()
    {
        return String.format("%s piloting %s", getClass().getSimpleName(),
                player_);
    }
}
