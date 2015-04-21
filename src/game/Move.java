package game;

import java.util.Objects;

import utils.Validate;

public class Move
{
    private final int column_;
    private final Player player_;

    public Move(final int column, final Player player)
    {
        Validate.notNull(player, "Player cannot be null");
        column_ = column;
        player_ = player;
    }

    public Move(final Move move)
    {
        Validate.notNull(move, "Cannot create a move from a null move");
        column_ = move.getColumn();
        player_ = move.getPlayer();
    }

    public int getColumn()
    {
        return column_;
    }

    public Player getPlayer()
    {
        return player_;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(column_, player_);
    }

    @Override
    public String toString()
    {
        return player_.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Move))
        {
            return false;
        }
        if (other == this)
        {
            return true;
        }

        final Move move = (Move) other;
        return Objects.equals(column_, move.column_)
                && Objects.equals(player_, move.player_);
    }
}
