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
        return String.format("%d", ordinal());
    }
}
