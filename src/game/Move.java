package game;

import java.util.Objects;

import utils.Validate;

public abstract class Move<T extends Game>
{
    protected final Player player_;

    public Move(final Player player)
    {
        Validate.notNull(player, "Cannot create a move without a valid player");
        player_ = player;
    }
    
    public Move(final Move<T> move)
    {
        Validate.notNull(move, "Cannot copy a null move");
        player_ = move.player_;
    }

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
