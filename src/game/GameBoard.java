package game;

import java.util.List;

import utils.Validate;

/**
 *
 * GameBoards are the abstract representation of whatever game is being played.
 * At it's core, it maintains the state of the board, performs game-rule logic,
 * and offers the users with a rich API of "stuff that can be done in the game".
 * 
 * Game runners will typically instantiate a single one of these and pass it
 * between the players for the game (immutability/copies where required).
 * 
 * @param <T>
 *            The Game being played
 * @param <U>
 *            The exact type of the Moves of the Game. These moves need to
 *            extend the GameType
 * @param <V>
 *            The exact type of the Spaces of the Game. These spaces need to
 *            extend the GameType.
 */
public abstract class GameBoard<T extends Game, U extends Move<T>, V extends Space<T>>
{
    /*
     * TODO: Will all games have a width & height? Right now, yes, in the
     * future... maybe?
     */
    protected final int width_;
    protected final int height_;

    /**
     * Creates a game board of the specified width & height
     * 
     * @param width
     *            Width
     * @param height
     *            Height
     * 
     * @throws IllegalArgumentException
     *             if either width or height are less than or equal to 0
     */
    public GameBoard(int width, int height)
    {
        Validate.isTrue(width > 0, "Cannot create a GameBoard with width <= 0");
        Validate.isTrue(height > 0, "Cannot create a GameBoard with height <=0");
        width_ = width;
        height_ = height;
    }
    
    public GameBoard(final GameBoard<T, U, V> board)
    {
        Validate.notNull(board, "Cannot create a copy of a null board");
        width_ = board.width_;
        height_ = board.height_;
    }

    /**
     * Applies the provided move to the gameboard. This move is checked for
     * Validity, where validity is defined as 
     * -Move is not null 
     * -Player who's turn it is is the owner of the Move 
     * -The provided Move is among the selection of possible moves
     * 
     * If any of these conditions aren't met, this method should throw an
     * IllegalArgumentException.
     * 
     * @param move
     *            The move that is being made. Move must be valid.
     */
    public abstract void addMove(final U move);

    /**
     * Similar to addMove, except that this method will return true if the move
     * that was made was a winning move (ie, placed the game into a state such
     * that no moves can be made by the other player & the player who made the
     * move achieved "victory", however that is defined.")
     * 
     * This method both applies the move & checks for victory condition.
     * 
     * This method is provided as a convenience. Note that since this method has
     * to perform a check to see if the move wins the game or not, this is a more
     * computationally expensive option to addMove(...), and should only be used
     * in places where the result is checked.
     * 
     * @param move
     *            The move that is being made. Move must be valid
     * 
     * @return True if the move wins the game, false otherwise.
     */
    public abstract boolean checkedAddMove(final U move);

    /**
     * Determines whether or not the provided Move will result in the game being
     * won or not.
     * 
     * Note: The move should be validated and throw an IllegalArgumentException
     * if it is not a valid move
     * 
     * Note: This method should not apply the move to the board, only check to
     * see if the Move would result in a winning game state.
     * 
     * @param move
     *            The move that is being made. Move must be valid.
     * @return True if the move wins the game, false otherwise.
     */
    public abstract boolean checkIfWinningMove(final U move);

    /**
     * Returns the player at the specified space. If there is no player at the
     * space, this method should return null. This can be typically thought of
     * as (Player who owns the move that resulted in a piece occupying space
     * ____)
     * 
     * Note: This method should validate the space. A valid space is one that:
     * -Is not null 
     * -Is within the gameboard
     * 
     * @param space
     *            The space to check for a player at.
     * @return The player at the space, if any, null if no player.
     */
    public abstract Player playerAt(final V space);

    /**
     * Determines all of the available moves given the current state of the
     * board for the provided Player.
     * 
     * If no moves exist, this should return an empty list.
     * 
     * Note: This method should throw an IllegalArgumentException if the player
     * is null
     * 
     * @param player
     *            Player to determine moves for
     * @return List of available moves for the player
     */
    public abstract List<U> availableMovesFor(final Player player);

    /**
     * Returns an ordered list of all Moves that have been made for this
     * GameBoard. Returns an empty list if no moves have been made.
     * 
     * @return Ordered List of all moves made from start to current state.
     */
    public abstract List<U> getMoveHistory();

    /**
     * This method should throw an IllegalArgumentException if the move is not
     * valid
     * 
     * @param move
     *            Move to check
     * @throws IllegalArgumentException
     *             if the move is invalid
     */
    protected abstract void validateMove(final U move);

    /**
     * This method should throw an IllegalArgumentException if the space is not
     * valid
     * 
     * @param space
     *            Space to check
     * @throws IllegalArgumentException
     *             if the space is invalid
     */
    protected abstract void validateSpace(final V space);

    /**
     * @return Board height
     */
    public int getHeight()
    {
        return height_;
    }

    /**
     * @return Board width
     */
    public int getWidth()
    {
        return width_;
    }
}
