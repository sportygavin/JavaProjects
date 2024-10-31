public class King extends Piece{
    public King(Boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }


    private boolean willPassThroughCheckQueenside() {
        // Check if the squares the king will pass through are not attacked
        for (int col = 1; col <= 3; col++) {
            if (Board.isAttacked(this.getX(), col, this.getColor())) {
                return true;
            }
        }
        return false;
    }

    private boolean willPassThroughCheckKingside() {
        // Check if the squares the king will pass through are not attacked
        for (int col = 5; col <= 6; col++) {
            if (Board.isAttacked(this.getX(), col, this.getColor())) {
                return true;
            }
        }
        return false;
    }

    private boolean willPassThroughCheck(int row, int col) {
        // Check if any square between the king's current position and the new position is attacked
        int rowDiff = row - this.getX();
        int colDiff = col - this.getY();

        if (colDiff < 0) {
            return willPassThroughCheckQueenside();
        } else {
            return willPassThroughCheckKingside();
        }
    }

    private boolean isCastlingMove(int newRow, int newCol) {
        // Check if the move is a castling move
        if (this.hasItMoved() || newRow != this.getX() || Math.abs(newCol - this.getY()) != 2) {
            return false;
        }

        // Check if the king is in check
        if (Board.isCheck(this.getColor())) {
            return false;
        }

        // Check if the squares between the king and rook are unoccupied
        int rookCol = newCol > this.getY() ? 7 : 0;
        Piece rook = Board.getPiece(this.getX(), rookCol);
        if (rook == null || rook.getMoveCount() > 1 || Board.isOccupied(this.getX(), this.getY() + Integer.signum(newCol - this.getY()))) {
            return false;
        }


        // Check if the squares the king will pass through are not attacked
        if (willPassThroughCheck(this.getX(), newCol)) {
            return false;
        }

        // Update the rook's position after castling
        if (newCol == getY() + 2) { // Kingside castling
            rook.move(getX(), 5); // Move the rook to the correct position
        } else if (newCol == getY() - 2) { // Queenside castling
            rook.move(getX(), 3); // Move the rook to the correct position
        }

        // The move is a valid castling move
        return true;
    }

    public boolean isValidMove(int newRow, int newCol) {
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;
        }

        if (Board.isAttacked(newRow, newCol, getColor())) {
            return false;
        }

        // Check if the new position is occupied by a friendly piece
        if (Board.isOccupied(newRow, newCol) && Board.getPiece(newRow, newCol).getColor() == getColor()) {
            return false;
        }

        // Calculate the distance between the king's current position and the new position
        int rowDiff = Math.abs(newRow - this.getX());
        int colDiff = Math.abs(newCol - this.getY());
        int maxDiff = Math.max(rowDiff, colDiff);

        // Check if the new position is adjacent to the king's current position
        if (maxDiff == 1) {
            return !Board.isAttacked(newRow, newCol, getColor());
        }


        // Check if the new position is a valid castling move
        if (isCastlingMove(newRow, newCol)) {
            return true;
        }

        // Check if the king is trying to capture a piece defended by the other king
        if (maxDiff == 2 && colDiff == 2 && rowDiff == 0) {
            int targetCol = newCol > this.getY() ? newCol - 1 : newCol + 1;
            if (Board.isAttacked(this.getX(), targetCol, !getColor())) {
                return false;
            }
        }

        // The move is not valid
        return false;
    }


    public String getType() {
        return "king";
    }


    public String toString(){
        return "K";
    }
}
