package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import utils.Validate;

public final class GameBoard
{
    private static final short MOVES_TO_WIN = 4;
    
    private final List<Move>[] board_;
    private final int maxHeight_;
    
    private final List<Move> moveHistory_;
    
    @SuppressWarnings("unchecked")
    public GameBoard(final int width, final int height)
    {
        Validate.inRange(MOVES_TO_WIN, 0, width);
        Validate.inRange(MOVES_TO_WIN, 0, height);

        board_ = new List[width];
        maxHeight_ = height;
        moveHistory_ = new ArrayList<Move>(width * height);
        initializeBoard();
    }
    
    @SuppressWarnings("unchecked")
    public GameBoard(final GameBoard copy)
    {
        Validate.notNull(copy, "Provided GameBoard cannot be null");

        final int width = copy.board_.length;
        board_ = new List[width];
        maxHeight_ = copy.maxHeight_;
        moveHistory_ = new ArrayList<Move>(copy.moveHistory_);

        for (int i = 0; i < width; ++i)
        {
            board_[i] = new ArrayList<Move>(copy.board_[i]);
        }
    }
    
    public void addMove(final Move move)
    {
        validateMove(move);
        internalAddMove(move);
    }
    
    private void internalAddMove(final Move move)
    {
        final int column = move.getColumn();
        board_[column].add(move);
        moveHistory_.add(move);
    }
    
    public boolean checkedAddMove(final Move move)
    {
        final boolean won = checkIfWinningMove(move);
        addMove(move);
        return won;
    }
    
    public boolean checkIfWinningMove(final Move move)
    {
        final int x = move.getColumn();
        final int y = lengthOfColumn(x);
        final Vector2 movePosition = new Vector2(x, y);
        final Player player = move.getPlayer();
        boolean isWinningMove = false;
        for (final Direction direction : Direction.uniqueDirections())
        {
            isWinningMove = isWinningMove
                    || internalCheckWinnerOnLine(movePosition, direction, player);
        }

        return isWinningMove;
    }
    
    private boolean internalCheckWinnerOnLine(final Vector2 startingPosition,
            final Direction direction, final Player player)
    {
        final int consecutiveMovesTowards = consecutiveMovesByPlayerInDirection(
                startingPosition, direction, player, 0);
        final int consecutiveMovesAway = consecutiveMovesByPlayerInDirection(
                startingPosition, direction.opposite(), player, 0);
        return (consecutiveMovesAway + consecutiveMovesTowards + 1) >= MOVES_TO_WIN;
    }
    
    private int consecutiveMovesByPlayerInDirection(
            final Vector2 currentPosition, final Direction direction,
            final Player player, final int currentMoveCount)
    {
        if (isWithinBounds(currentPosition)
                && internalPlayerAt(currentPosition) == player)
        {
            return consecutiveMovesByPlayerInDirection(
                    currentPosition.add(direction.unitVector()), direction,
                    player, currentMoveCount + 1);
        }
        return currentMoveCount;
    }
    
    private boolean isWithinBounds(final Vector2 position)
    {
        final int x = position.getX();
        final int y = position.getY();
        return x >= 0 && x < board_.length && y >= 0 && lengthOfColumn(x) > y;
    }
    
    private Player internalPlayerAt(final Vector2 boardIndex)
    {
        final int x = boardIndex.getX();
        final int y = boardIndex.getY();
        final List<Move> column = board_[x];
        if (column.size() >= y)
        {
            return null;
        }
        return column.get(y).getPlayer();
    }

    private int lengthOfColumn(final int column)
    {
        return board_[column].size();
    }
    
    private void validateMove(final Move move)
    {
        Validate.notNull(move, "Provided move cannot be null");
        Validate.inRange(move.getColumn(), 0, board_.length);
    }

    private void initializeBoard()
    {
        for (int i = 0; i < board_.length; ++i)
        {
            board_[i] = new ArrayList<Move>(maxHeight_);
        }
    }
    
    public Player playerAt(final Vector2 position)
    {
        if (position == null || !isWithinBounds(position))
        {
            return null;
        }

        return internalPlayerAt(position);
    }
    
    public int getHeight()
    {
        return maxHeight_;
    }
    
    public int getWidth()
    {
        return board_.length;
    }
    
    public Player[][] getBoardRepresentation()
    {
        final Player[][] representation = new Player[board_.length][maxHeight_];

        applyFunctionToBoard((columnIndex, rowIndex) ->
        {
            final List<Move> column = board_[columnIndex];
            Player player = null;
            if (column.size() > rowIndex)
            {
                final Move move = column.get(rowIndex);
                player = move.getPlayer();
            }
            representation[columnIndex][maxHeight_ - 1 - rowIndex] = player;
        }, column ->
        {
        });

        return representation;
    }

    public List<Move> availableMovesFor(final Player player)
    {
        final List<Move> availableMoves = new ArrayList<Move>(board_.length);
        for (int i = 0; i < board_.length; ++i)
        {
            if (board_[i].size() < maxHeight_)
            {
                final Move availableMove = new Move(i, player);
                availableMoves.add(availableMove);
            }
        }
        return availableMoves;
    }
    
    public boolean boardFull()
    {
        return availableMovesFor(null).isEmpty();
    }
    
    // Return by copy so we don't mutate state
    public List<Move> getMoveHistory()
    {
        return new ArrayList<Move>(moveHistory_);
    }

    @Override
    public int hashCode()
    {
        return Arrays.deepHashCode(board_);
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof GameBoard))
        {
            return false;
        }
        if (other == this)
        {
            return true;
        }

        final GameBoard gameBoard = (GameBoard) other;
        return Arrays.deepEquals(board_, gameBoard.board_);
    }
    
    private void applyFunctionToBoard(
            final BiConsumer<Integer, Integer> middleOfRow,
            final Consumer<Integer> endOfRow)
    {
        for (int i = (maxHeight_ - 1); i >= 0; --i)
        {
            for (int j = 0; j < board_.length; ++j)
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
            final List<Move> column = board_[columnIndex];
            if (column.size() <= rowIndex)
            {
                boardBuilder.append("  ");
            } else
            {
                boardBuilder.append(" ");
                final Move move = column.get(rowIndex);
                boardBuilder.append(move.toString());
            }
        }, column ->
        {
            boardBuilder.append("\n");
        });

        final String boardRepresentation = boardBuilder.toString();
        return boardRepresentation;
    }
}
