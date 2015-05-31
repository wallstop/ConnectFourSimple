package game;

/**
 * Player objects for games. These represent the actual "owners" of whatever
 * entities are playing the game, allowing for "opponent detection" as well as
 * "self-actualization" or something.
 *
 */
public enum Player
{
    /*
     * Currently we assume 2-player games only. This may or may not be able to
     * be safely expanded. Should we parameterize this and have each game define
     * their own players? Not right not, but something to think about in the
     * future.
     */
    PLAYER_1, PLAYER_2;

    /**
     * Note: This style of API only really makes sense for 2-Player games.
     * 
     * @return The opponent for each Player.
     */
    public Player opponent()
    {
        switch(this)
        {
        case PLAYER_1:
            return PLAYER_2;
        case PLAYER_2:
            return PLAYER_1;
        }

        throw new IllegalArgumentException("Unexpected Player: " + name());
    }

    @Override
    public String toString()
    {
        /*
         * Players should be 1-indexed, as humans tend to count from 1 instead
         * of 0
         */
        return String.format("%d", ordinal() + 1);
    }
}
