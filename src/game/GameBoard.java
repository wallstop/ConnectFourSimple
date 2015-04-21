package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Validate;

public class GameBoard
{
    private static final short MOVES_TO_WIN = 4;
    
    private final List<Move>[] board_;
    private final int maxHeight_;
    
    @SuppressWarnings("unchecked")
    public GameBoard(final int width, final int height)
    {
        Validate.inRange(width, 0, MOVES_TO_WIN);
        Validate.inRange(height, 0, MOVES_TO_WIN);

        board_ = new List[width];
        maxHeight_ = height;
        initializeBoard();
    }
    
    @SuppressWarnings("unchecked")
    public GameBoard(final GameBoard copy)
    {
        Validate.notNull(copy, "Provided GameBoard cannot be null");

        final int width = copy.board_.length;
        board_ = new List[width];
        maxHeight_ = copy.maxHeight_;

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
    
    @Override
    public String toString()
    {
        final StringBuilder boardBuilder = new StringBuilder();
        for (int i = maxHeight_; i >= 0; --i)
        {
            for (final List<Move> column : board_)
            {
                if (column.size() <= i)
                {
                    boardBuilder.append("  ");
                    continue;
                }

                boardBuilder.append(" ");
                final Move move = column.get(i);
                boardBuilder.append(move.toString());
            }
            boardBuilder.append("\n");
        }
        final String boardRepresentation = boardBuilder.toString();
        return boardRepresentation;
    }
}
