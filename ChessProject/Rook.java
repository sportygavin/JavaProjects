public class Rook extends Piece{
    private boolean hasMoved = false;
    private int moveCount = 0;

    public Rook(Boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        int fromRow = this.getX();
        int fromCol = this.getY();

        // Rook can only move vertically or horizontally
        if (toRow != fromRow && toCol != fromCol) {
            return false;
        }

        int rowStep = (toRow - fromRow == 0) ? 0 : (toRow - fromRow > 0) ? 1 : -1;
        int colStep = (toCol - fromCol == 0) ? 0 : (toCol - fromCol > 0) ? 1 : -1;

        // Check if there is any piece blocking the path of the rook
        int r = fromRow + rowStep;
        int c = fromCol + colStep;
        while (r != toRow || c != toCol) {
            if (Board.isOccupied(r, c)) {
                return false;
            }
            r += rowStep;
            c += colStep;
        }

        // The move is valid if there is no piece blocking the path
        // or if the destination square is occupied by an opponent's piece
        return !Board.isOccupied(toRow, toCol) || Board.getPiece(toRow, toCol).getColor() != this.getColor();
    }

    @Override
    public void move(int toX, int toY) {
        int fromX = getX();
        int fromY = getY();
        moveCount++;

        // Check if there is a piece at the destination
        Piece capturedPiece = Board.getPiece(toX, toY);

        // Update the piece's position
        setX(toX);
        setY(toY);

        // Update the board array
        Board.setPiece(toX, toY, this);
        Board.setPiece(fromX, fromY, null);

        hasMoved = true;
        setHasItMoved(true);

        // Capture the piece if necessary
        if (capturedPiece != null) {
            capturedPiece.setCaptured(true);
            Board.setPiece(capturedPiece.getX(), capturedPiece.getY(), null);
        }
    }


    public String getType() {
        return "rook";
    }

    public String toString(){
        return "r";
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
