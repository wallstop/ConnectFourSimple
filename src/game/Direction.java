package game;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utils.Vector2;

/**
 * Direction represents a movement along a 2D plane. This is used primarily for
 * navigation across 2D gameboards.
 *
 */
public enum Direction
{
    UP, UPPER_RIGHT, RIGHT, LOWER_RIGHT, DOWN, LOWER_LEFT, LEFT, UPPER_LEFT;

    /**
     * @return Whatever Direction is opposite (lies on the same line)
     */
    public Direction opposite()
    {
        final int totalDirections = Direction.values().length;
        final int oppositeDirectionOffset = totalDirections / 2;
        final int currentDirectionIndex = ordinal();
        return Direction.values()[(currentDirectionIndex + oppositeDirectionOffset)
                % totalDirections];
    }

    /**
     * @return The Unit Vector for the direction
     */
    public Vector2 unitVector()
    {
        switch(this)
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
        throw new IllegalArgumentException("Could not determine unit vector for " + this);
    }

    /**
     * 
     * @return Provides a Collection of Directions such that calling opposite()
     *         on each member of the Collection will produce a Direction that is
     *         not in the Collection, and the Union of all Directions produced
     *         in this manner with the original Collection will be every
     *         direction. IE, each Direction within the returned Collection will
     *         be a unique line in the 2D plane.
     * 
     */
    public static Collection<Direction> uniqueLineDirections()
    {
        final int totalDirections = Direction.values().length;
        final int numberOfUniqueDirections = totalDirections / 2;
        final Set<Direction> uniqueDirections = new HashSet<Direction>(numberOfUniqueDirections);
        Arrays.asList(Direction.values()).forEach(direction ->
        {
            if(!uniqueDirections.contains(direction.opposite()))
            {
                uniqueDirections.add(direction);
            }
        });
        return uniqueDirections;
    }
}
