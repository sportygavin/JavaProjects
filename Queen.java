public class Queen extends Piece{
    public Queen(Boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    public boolean isValidMove(int toRow, int toCol) {
        int fromRow = this.getX();
        int fromCol = this.getY();

        // Check if the move is valid for a bishop
        Bishop bishop = new Bishop(this.getColor(), fromRow, fromCol);
        if (bishop.isValidMove(toRow, toCol)) {
            return true;
        }

        // Check if the move is valid for a rook

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
        if(!Board.isOccupied(toRow,toCol)|| Board.getPiece(toRow, toCol).getColor() != this.getColor())
            return true;

        // If the move is not valid for either bishop or rook, it's not a valid move for the queen
        return false;
    }

    public String getType() {
        return "queen";
    }

    public String toString(){
        return "Q";
    }

}
