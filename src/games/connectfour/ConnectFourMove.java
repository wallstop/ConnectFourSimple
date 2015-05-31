package games.connectfour;

import game.Move;
import game.Player;

import java.util.Objects;

/**
 * Moves for the ConnectFour game.
 * 
 * The structure of this class guarantees that once a ConnectFourMove is
 * created, it is immutable. What this means is that even if some malicious AI
 * obtained the "true" GameBoard and had access to all of the moves, they would
 * be unable to alter a move's column or player.
 * 
 * All games should strive towards having immutable Moves.
 *
 */
public final class ConnectFourMove extends Move<ConnectFour>
{
    // Column of the move
    private final int column_;

    /**
     * @param column Column of the move (0 indexed)
     * @param player Player who made the move
     */
    public ConnectFourMove(final int column, final Player player)
    {
        super(player);
        /*
         * Unfortunately, we don't know enough about the gameboard to tell
         * whether or not the column is valid, so we cannot validate it here.
         * This has to be checked in the gameboard when moves are added.
         */
        column_ = column;
    }

    /**
     * Creates a copy of the ConnectFourMove
     * 
     * @param move
     *            Move to take a copy of
     */
    public ConnectFourMove(final ConnectFourMove move)
    {
        super(move);
        column_ = move.getColumn();
    }

    /**
     * @return The column for this move
     */
    public int getColumn()
    {
        return column_;
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
        if (!(other instanceof ConnectFourMove))
        {
            return false;
        }
        if (other == this)
        {
            return true;
        }

        final ConnectFourMove move = (ConnectFourMove) other;
        return Objects.equals(column_, move.column_)
                && Objects.equals(player_, move.player_);
    }
}
