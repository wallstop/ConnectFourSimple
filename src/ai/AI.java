package ai;

import utils.Validate;
import game.GameBoard;
import game.Move;
import game.Player;

public abstract class AI
{
    final Player player_;

    public AI(final Player player)
    {
        Validate.notNull(player, "Cannot create an AI based on a null Player");
        player_ = player;
    }

    public abstract Move determineMove(final GameBoard gameBoard);

    @Override
    public String toString()
    {
        return String.format("%s piloting %s", getClass().getSimpleName(),
                player_);
    }
}
