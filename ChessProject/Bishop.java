import java.awt.*;

public class Bishop extends Piece{
    public Bishop(Boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int toX, int toY) {

        if(toX > 7 || toY > 7 || toX < 0 || toY < 0){
            return false;
        }
        // Check if the move is diagonal
        int deltaX = Math.abs(toX - this.getX());
        int deltaY = Math.abs(toY - this.getY());
        if (deltaX != deltaY) {
            return false;
        }

        // Check if there are any pieces blocking the bishop's path
        int xDir = toX > this.getX() ? 1 : -1;
        int yDir = toY > this.getY() ? 1 : -1;
        int x = this.getX() + xDir;
        int y = this.getY() + yDir;
        while (x != toX && y != toY) {
            if((x < 8 && x >=0)&& (y < 8 && y >= 0))
                if (Board.isOccupied(x, y)) {
                    return false;
                }
            x += xDir;
            y += yDir;
        }

        // The move is valid if there are no pieces blocking the bishop's path
        // and the destination square is either unoccupied or occupied by an opponent's piece
        Piece destPiece = Board.getPiece(toX, toY);
        return destPiece == null || destPiece.getColor() != this.getColor();
    }

    public String getType() {
        return "bishop";
    }

    public String toString(){
        return "b";
    }

}
