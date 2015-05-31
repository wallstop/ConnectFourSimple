package utils;

/**
 * Represents a point in 2D space
 * 
 * (x, y)
 *
 */
public final class Vector2
{
    private final int x_;
    private final int y_;

    /**
     * Creates a new Vector2 with the specified properties
     * 
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     */
    public Vector2(final int x, final int y)
    {
        x_ = x;
        y_ = y;
    }

    /**
     * Creates a copy of the specified Vector2.
     * 
     * @param other
     *            Vector to make a copy of.
     */
    public Vector2(final Vector2 other)
    {
        Validate.notNull(other, "Cannot create a copy of a null Vector2");
        x_ = other.x_;
        y_ = other.y_;
    }

    /**
     * @return X Coordinate
     */
    public int getX()
    {
        return x_;
    }

    /**
     * @return Y coordinate
     */
    public int getY()
    {
        return y_;
    }

    /**
     * Adds the two vectors together, returning the result.
     * 
     * Note: Does not modify either Vector2.
     * 
     * @param other
     *            Non-null Vector2 to add
     * @return A new Vector2 representing the result of the addition.
     */
    public Vector2 add(final Vector2 other)
    {
        Validate.notNull(other, "Cannot add a null vector");
        return new Vector2(x_ + other.x_, y_ + other.y_);
    }
}
