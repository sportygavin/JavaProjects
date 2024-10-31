
// Make each piece have a x and y coordinate
// have there only be 64 possibly x,y coordinates and have
// each one correspond to a certain notation (e4,e2)

public class Piece {
    private boolean isWhite;
    private int x;
    private int y;
    private boolean hasItMoved;
    private int pinnedRow;
    private int pinnedCol;
    private boolean isCaptured;


    public Piece(Boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }



    public boolean isValidMove(String notation) {
        int toX = notation.charAt(0) - 'a'; // convert the file (letter) to a column index (0-7)
        int toY = Integer.parseInt(notation.substring(1)) - 1; // convert the rank (number) to a row index (0-7)

        // check if the new position is a valid move for this piece
        // return true if it is, false otherwise
        return false;
    }

    public void move(int toX, int toY) {
        int fromX = getX();
        int fromY = getY();

        // Check if there is a piece at the destination
        Piece capturedPiece = Board.getPiece(toX, toY);

        // Update the piece's position
        setX(toX);
        setY(toY);


        // Update the board array
        Board.setPiece(toX, toY, this);
        Board.removePiece(fromX, fromY);

        // Capture the piece if necessary
        if (capturedPiece != null) {
            capturedPiece.setCaptured(true);
            Board.setPiece(capturedPiece.getX(), capturedPiece.getY(), null);
        }
    }




    public boolean isValidMove(int newX, int newY) {
        // check if the new position is a valid move for this piece
        // return true if it is, false otherwise

        // You would need to implement this method for each specific piece class
        return false;
    }





    public Boolean getColor() {
        return isWhite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean didItPromote(){
        return false;
    }

    public void setDidItPromote(boolean b) {
    }

    public String getType() {
        return "default";
    }

    public boolean hasItMoved(){
        return hasItMoved;
    }

    public void setHasItMoved(boolean s){
        hasItMoved = s;
    }


    public String getFileName() {
            String color = (isWhite ? "w" : "b");
            String type = "";

            if (this instanceof Pawn) {
                type = "p";
            } else if (this instanceof Knight) {
                type = "n";
            } else if (this instanceof Bishop) {
                type = "b";
            } else if (this instanceof Rook) {
                type = "r";
            } else if (this instanceof Queen) {
                type = "q";
            } else if (this instanceof King) {
                type = "k";
            }
            return color + "_" + type + ".png";
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public int getMoveCount() {
        return 0;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }
}
