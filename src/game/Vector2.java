package game;

public class Vector2
{
    private final int x_;
    private final int y_;

    public Vector2(final int x, final int y)
    {
        x_ = x;
        y_ = y;
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
