package game;

import java.util.Objects;

import utils.Validate;

/**
 * Simple Move class specifying the base for Moves of a specific game
 * 
 * @param <T>
 *            A specific game
 */
public abstract class Move<T extends Game>
{
    protected final Player player_;

    /**
     * Creates a move for the specified Player
     * @param player
     *            The player that made this move
     * @throws IllegalArgumentException
     *             if the player is null
     */
    public Move(final Player player)
    {
        Validate.notNull(player, "Cannot create a move without a valid player");
        player_ = player;
    }
    
    /**
     * Copies the player for the specified move
     * 
     * @param move
     *            An existing move
     * @throws IllegalArgumentException
     *             if the player is null
     */
    public Move(final Move<T> move)
    {
        Validate.notNull(move, "Cannot copy a null move");
        player_ = move.player_;
    }

    /**
     * Note: Will never return null
     * @return The player that made this move
     */
    public Player getPlayer()
    {
        return player_;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(player_);
    }

    @Override
    public String toString()
    {
        return player_.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof Move))
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }

        @SuppressWarnings("unchecked")
        final Move<T> move = (Move<T>) other;
        return Objects.equals(player_, move.player_);
    }

}
