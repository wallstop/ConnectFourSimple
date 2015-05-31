package utils;


public final class Vector2
{
    private final int x_;
    private final int y_;

    public Vector2(final int x, final int y)
    {
        x_ = x;
        y_ = y;
    }
    
    public Vector2(final Vector2 other)
    {
        Validate.notNull(other, "Cannot create a copy of a null Vector2");
        x_ = other.x_;
        y_ = other.y_;
    }

    public int getX()
    {
        return x_;
    }

    public int getY()
    {
        return y_;
    }

    public Vector2 add(final Vector2 other)
    {
        return new Vector2(x_ + other.x_, y_ + other.y_);
    }
}
