package game;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public enum Direction
{
    UP, UPPER_RIGHT, RIGHT, LOWER_RIGHT, DOWN, LOWER_LEFT, LEFT, UPPER_LEFT;

    public Direction opposite()
    {
        final int totalDirections = Direction.values().length;
        final int oppositeDirectionOffset = totalDirections / 2;
        final int currentDirectionIndex = ordinal();
        return Direction.values()[(currentDirectionIndex + oppositeDirectionOffset)
                % totalDirections];
    }

    public Vector2 unitVector()
    {
        switch (this)
        {
        case UP:
            return new Vector2(0, 1);
        case UPPER_RIGHT:
            return new Vector2(1, 1);
        case RIGHT:
            return new Vector2(1, 0);
        case LOWER_RIGHT:
            return new Vector2(1, -1);
        case DOWN:
            return new Vector2(0, -1);
        case LOWER_LEFT:
            return new Vector2(-1, -1);
        case LEFT:
            return new Vector2(-1, 0);
        case UPPER_LEFT:
            return new Vector2(-1, 1);
        }
        throw new IllegalArgumentException(
                "Could not determine unit vector for " + this);
    }

    public static Collection<Direction> uniqueDirections()
    {
        final int totalDirections = Direction.values().length;
        final int numberOfUniqueDirections = totalDirections / 2;
        final Set<Direction> uniqueDirections = new HashSet<Direction>(
                numberOfUniqueDirections);
        Arrays.asList(Direction.values()).forEach(direction ->
        {
            if (!uniqueDirections.contains(direction.opposite()))
            {
                uniqueDirections.add(direction);
            }
        });
        return uniqueDirections;
    }
}
