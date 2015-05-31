package games.connectfour;

import game.Direction;
import game.GameBoard;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import utils.Validate;
import utils.Vector2;

/**
 * ConnectFour for everyone!
 *
 */
public final class ConnectFourGameBoard extends
        GameBoard<ConnectFour, ConnectFourMove, ConnectFourSpace>
{
    private static final short MOVES_TO_WIN = 4;

    private final List<ConnectFourMove>[] board_;

    private final List<ConnectFourMove> moveHistory_;

    /**
     * Creates a new ConnectFourGameBoard with the provided width & height
     * 
     * @param width
     *            Width of board
     * @param height
     *            Height of board
     */
    @SuppressWarnings("unchecked")
    public ConnectFourGameBoard(final int width, final int height)
    {
        super(width, height);
        Validate.inOpenInterval(MOVES_TO_WIN, 0, width);
        Validate.inOpenInterval(MOVES_TO_WIN, 0, height);

        board_ = new List[width];
        moveHistory_ = new ArrayList<ConnectFourMove>(width * height);
        initializeBoard();
    }

    /**
     * Creates a copy of the provided board. Modifying the state of either board
     * will have no impact on the other.
     * 
     * @param copy
     *            Board to copy
     */
    @SuppressWarnings("unchecked")
    public ConnectFourGameBoard(final ConnectFourGameBoard copy)
    {
        super(copy);
        board_ = new List[width_];
        moveHistory_ = new ArrayList<ConnectFourMove>(copy.moveHistory_);
        for(int i = 0; i < width_; ++i)
        {
            board_[i] = new ArrayList<ConnectFourMove>(copy.board_[i]);
        }
    }

    @Override
    public void addMove(final ConnectFourMove move)
    {
        validateMove(move);
        internalAddMove(move);
    }

    // Adds the move, move is assumed valid
    private void internalAddMove(final ConnectFourMove move)
    {
        final int column = move.getColumn();
        board_[column].add(move);
        moveHistory_.add(move);
    }

    @Override
    public boolean checkedAddMove(final ConnectFourMove move)
    {
        final boolean won = checkIfWinningMove(move);
        addMove(move);
        return won;
    }

    @Override
    public boolean checkIfWinningMove(final ConnectFourMove move)
    {
        final int x = move.getColumn();
        final int y = lengthOfColumn(x);
        final Vector2 movePosition = new Vector2(x, y);
        final Player player = move.getPlayer();
        boolean isWinningMove = false;
        for(final Direction direction : Direction.uniqueLineDirections())
        {
            // Winner if any line from the move's point have length
            isWinningMove |= internalCheckWinnerOnLine(movePosition, direction, player);
        }

        return isWinningMove;
    }

    /*
     * Walk in both both directions away from the starting position to determine
     * the the length of a line
     */
    private boolean internalCheckWinnerOnLine(final Vector2 startingPosition,
            final Direction direction, final Player player)
    {
        final int consecutiveMovesTowards = consecutiveMovesByPlayerInDirection(startingPosition,
                direction, player, 0);
        final int consecutiveMovesAway = consecutiveMovesByPlayerInDirection(startingPosition,
                direction.opposite(), player, 0);
        return (consecutiveMovesAway + consecutiveMovesTowards + 1) >= MOVES_TO_WIN;
    }

    /**
     * Returns the number
     */
    private int consecutiveMovesByPlayerInDirection(final Vector2 currentPosition,
            final Direction direction, final Player player, final int currentMoveCount)
    {
        if(isWithinBounds(currentPosition) && internalPlayerAt(currentPosition) == player)
        {
            return consecutiveMovesByPlayerInDirection(currentPosition.add(direction.unitVector()),
                    direction, player, currentMoveCount + 1);
        }
        return currentMoveCount;
    }

    // Returns true if the Vector2 is in the bounds of the board
    private boolean isWithinBounds(final Vector2 position)
    {
        final int x = position.getX();
        final int y = position.getY();
        return x >= 0 && x < board_.length && y >= 0 && lengthOfColumn(x) > y;
    }

    /*
     * Returns the player at the specified boardIndex, or null if no player is
     * there.
     */
    private Player internalPlayerAt(final Vector2 boardIndex)
    {
        final int x = boardIndex.getX();
        final int y = boardIndex.getY();
        final List<ConnectFourMove> column = board_[x];
        if(column.size() >= y)
        {
            return null;
        }
        return column.get(y).getPlayer();
    }

    // How many moves are stacked in the given column
    private int lengthOfColumn(final int column)
    {
        return board_[column].size();
    }

    @Override
    protected void validateMove(final ConnectFourMove move)
    {
        Validate.notNull(move, "Provided move cannot be null");
        Validate.inOpenInterval(move.getColumn(), 0, board_.length);
    }

    private void initializeBoard()
    {
        for(int i = 0; i < board_.length; ++i)
        {
            board_[i] = new ArrayList<ConnectFourMove>(height_);
        }
    }

    @Override
    public Player playerAt(final ConnectFourSpace position)
    {
        validateSpace(position);
        final Vector2 coordinates = position.getPosition();
        if(position == null || !isWithinBounds(coordinates))
        {
            return null;
        }

        return internalPlayerAt(coordinates);
    }

    /**
     * Returns a representation of the board as a 2D array of Players.
     * 
     * Board[column][Height]
     * 
     * Note: Null represents no player (playing there)
     * 
     * Note: Board is returned "visually", ie, the first move in a column will
     * be at the maximum height for that column (instead of 0)
     * 
     * @return 2D Player array representing the board state.
     */
    public Player[][] getBoardRepresentation()
    {
        final Player[][] representation = new Player[board_.length][height_];

        applyFunctionToBoard((columnIndex, rowIndex) ->
        {
            final List<ConnectFourMove> column = board_[columnIndex];
            Player player = null;
            if(column.size() > rowIndex)
            {
                // Grab the player only if it exists
                final ConnectFourMove move = column.get(rowIndex);
                player = move.getPlayer();
            }
            representation[columnIndex][height_ - 1 - rowIndex] = player;
        }, column ->
        {
            // No row-end function
            });

        return representation;
    }

    @Override
    public List<ConnectFourMove> availableMovesFor(final Player player)
    {
        final List<ConnectFourMove> availableMoves = new ArrayList<ConnectFourMove>(board_.length);
        for(int i = 0; i < board_.length; ++i)
        {
            if(board_[i].size() < height_)
            {
                final ConnectFourMove availableMove = new ConnectFourMove(i, player);
                availableMoves.add(availableMove);
            }
        }
        return availableMoves;
    }

    /**
     * @return True if the board is full and no more moves can be made.
     */
    public boolean boardFull()
    {
        return availableMovesFor(null).isEmpty();
    }

    @Override
    /**
     * @return A copy of the move history (so we don't mutate state)
     */
    public List<ConnectFourMove> getMoveHistory()
    {
        return new ArrayList<ConnectFourMove>(moveHistory_);
    }

    @Override
    public int hashCode()
    {
        return Arrays.deepHashCode(board_);
    }

    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof ConnectFourGameBoard))
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }

        final ConnectFourGameBoard gameBoard = (ConnectFourGameBoard) other;
        return Arrays.deepEquals(board_, gameBoard.board_);
    }

    /**
     * This is kind of confusing, but this allows us to apply any BiConsumer to
     * the board (to be executed for every move in each row) and a Consumer to
     * be executed once the end of each row has been hit. This allows us to do
     * share code between converting the board into a String & converting the
     * board into a 2D array
     * 
     * @param middleOfRow
     *            Function to apply for (row, column) each element in a game
     *            board row
     * @param endOfRow
     *            Function to apply for (row) at the end of each row
     */
    private void applyFunctionToBoard(final BiConsumer<Integer, Integer> middleOfRow,
            final Consumer<Integer> endOfRow)
    {
        for(int i = (height_ - 1); i >= 0; --i)
        {
            for(int j = 0; j < board_.length; ++j)
            {
                middleOfRow.accept(j, i);
            }
            endOfRow.accept(i);
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder boardBuilder = new StringBuilder();
        applyFunctionToBoard((columnIndex, rowIndex) ->
        {
            final List<ConnectFourMove> column = board_[columnIndex];
            if(column.size() <= rowIndex)
            {
                boardBuilder.append("  ");
            }
            else
            {
                boardBuilder.append(" ");
                final ConnectFourMove move = column.get(rowIndex);
                boardBuilder.append(move.toString());
            }
        }, column ->
        {
            boardBuilder.append("\n");
        });

        final String boardRepresentation = boardBuilder.toString();
        return boardRepresentation;
    }

    @Override
    protected void validateSpace(final ConnectFourSpace space)
    {
        Validate.notNull(space, "Space cannot be null");
        /*
         * We don't have to validate space.getPosition() here due to the
         * invariant that Spaces cannot be initialized with null positions
         */
    }
}
