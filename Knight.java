import java.awt.*;

public class Knight extends Piece{
    public Knight(Boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        int dx = Math.abs(getX() - toRow);
        int dy = Math.abs(getY() - toCol);

        if(Board.isOccupied(toRow,toCol) && Board.getPiece(toRow,toCol).getColor() == getColor())
            return false;
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }


    public String toString(){
        return "n";
    }

    public String getType() {
        return "knight";
    }


}
