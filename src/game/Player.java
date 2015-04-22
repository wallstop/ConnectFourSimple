package game;

public enum Player
{
    PLAYER_1, PLAYER_2;

    public Player opponent()
    {
        switch (this)
        {
        case PLAYER_1:
            return PLAYER_2;
        case PLAYER_2:
            return PLAYER_1;
        }

        throw new IllegalArgumentException("Unexpected Player: " + this);
    }

    @Override
    public String toString()
    {
        // Players should be 1-indexed, as humans tend to count from 1 instead of 0
        return String.format("%d", ordinal() + 1);
    }
}
