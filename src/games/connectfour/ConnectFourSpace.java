package games.connectfour;

import utils.Validate;
import utils.Vector2;
import game.Space;

/**
 * @author wallstop
 *
 */
public final class ConnectFourSpace extends Space<ConnectFour>
{
    private final Vector2 position_;

    /**
     * Creates a ConnectFourSpace out of a presumed-valid ConnectFourGameBoard position
     * @param position position on the GameBoard that this space occurs
     */
    public ConnectFourSpace(final Vector2 position)
    {
        Validate.notNull(position, "Cannot create a ConnectFourSpace with a null position");
        position_ = position;
    }

    /**
     * @param other
     */
    public ConnectFourSpace(final ConnectFourSpace other)
    {
        Validate.notNull(other, "Cannot make a copy of a null ConnectFourSpace");
        // Vector2 is immutable, so we can just pointer-copy
        position_ = other.position_;
    }

    public Vector2 getPosition()
    {
        return position_;
    }
}
