import java.util.Scanner;

public class Pawn extends Piece {
    private boolean hasMoved; // to keep track of whether the pawn has moved yet or not
    private boolean didItPromote = false;

    public Pawn(Boolean isWhite, int y, int x) {
        super(isWhite, y, x);
        this.hasMoved = false; // initialize to false, since the pawn hasn't moved yet
    }


    @Override
    public boolean isValidMove(int toX, int toY) {
        int yDiff = toY - getY();
        int xDiff = toX - getX();

        if (Board.isOccupied(toX, toY)) {
            Piece targetPiece = Board.getPiece(toX, toY);
            if (targetPiece.getColor() != getColor()) {
                // Valid capture move
                if (Math.abs(yDiff) == 1 && xDiff == getColorDirection()) {
                    return true;
                }
            } else {
                // Invalid move, can't capture own piece
                return false;
            }
        } else {
            // Move without capturing
            if (yDiff == 0) {
                // Move forward
                if (getColor()) {
                    // White pawn
                    if (xDiff == -1) {
                        return true;
                    } else if (getX() == 6 && xDiff == -2) {
                        // First move, can move two spaces
                        return true;
                    }
                } else {
                    // Black pawn
                    if (xDiff == 1) {
                        return true;
                    } else if (getX() == 1 && xDiff == 2) {
                        // First move, can move two spaces
                        return true;
                    }
                }
            }
        }

        return false; // Invalid move
    }

    private int getColorDirection() {
        if (getColor()) {
            return -1; // White pawn moves in the negative x-direction
        } else {
            return 1; // Black pawn moves in the positive x-direction
        }
    }


//    public boolean isValidMove(int toX, int toY) {
//        int yDiff = toY - getY();
//        int xDiff = toX - getX();
//
//
//        if (Board.isOccupied(toX, toY) && (Board.getPiece(toX,toY).getColor() != getColor())) {
//            if ((getColor() && xDiff == -1 && yDiff != 0) || (!getColor() && xDiff == 1 && yDiff != 0)) {
//                if (toX == 1 && toY == 5)
//                return true; // valid attack
//            } else {
//                return false; // invalid attack (moving in the wrong direction)
//            }
//        }
//
//        if(Board.isOccupied(toX,toY))
//            return false;
//        if(xDiff > 2 || xDiff < -2)
//            return false;
//        if(hasMoved && (xDiff > 1 || xDiff < -1))
//            return false;
//        if(getColor() && xDiff >-1)
//            return false;
//        if(!getColor() && xDiff <1)
//            return false;
//
//
//
//
//        //White
//        if (getColor()) {
//            if (yDiff < 0 || yDiff > 2) {
//                return false;
//            } else if (xDiff < -2){
//                return false;
//            } else if(xDiff < -1 && hasMoved){
//                return false;
//            } else if (yDiff == 2 && hasMoved) {
//                return false;
//            } else if (yDiff == 2 && xDiff != 0) {
//                return false;
//            } else if (yDiff == 2 && Board.getPiece(toX, toY-1) != null) {
//                return false;
//            } else if (yDiff == 1 && xDiff != 0) {
//                return false;
//            } else if (yDiff == 1 && Board.getPiece(toX, toY) != null) {
//                return false;
//            }
//            //Black
//        } else {
//            if (yDiff > 0 || yDiff < -2) {
//                return false;
//            } else if (xDiff > 2){
//                return false;
//            } else if(xDiff > 1 && hasMoved){
//                    return false;
//            } else if (yDiff == -2 && hasMoved) {
//                return false;
//            } else if (yDiff == -2 && xDiff != 0) {
//                return false;
//            } else if (yDiff == -2 && Board.getPiece(toX, toY+1) != null) {
//                return false;
//            } else if (yDiff == -1 && xDiff != 0) {
//                return false;
//            } else if (yDiff == -1 && Board.getPiece(toX, toY) != null) {
//                return false;
//            }
//        }
//        hasMoved = true;
//        return true;
//    }





    public boolean isValidMove(String toPosition) {
        int toX = convertPositionToX(toPosition);
        int toY = convertPositionToY(toPosition);
        // If the pawn has not moved yet, it can move forward 1 or 2 spaces
        if (!hasMoved && toX == getX() && Math.abs(toY - getY()) <= 2 && toY != getY()) {
            return true;
        }

        // If the pawn has moved already, it can only move forward 1 space
        if (hasMoved && toX == getX() && Math.abs(toY - getY()) == 1 && toY != getY()) {
            return true;
        }

        // If the pawn is capturing an opposing piece, it can move forward 1 space diagonally
        Piece targetPiece = Board.getPiece(toX, toY);
        if (targetPiece != null && targetPiece.getColor() != getColor() && Math.abs(toX - getX()) == 1 && Math.abs(toY - getY()) == 1) {
            return true;
        }

        // If none of the above conditions are met, the move is not valid
        return false;
    }

    private int convertPositionToX(String position) {
        char xChar = position.charAt(0);
        switch(xChar) {
            case 'a': return 1;
            case 'b': return 2;
            case 'c': return 3;
            case 'd': return 4;
            case 'e': return 5;
            case 'f': return 6;
            case 'g': return 7;
            case 'h': return 8;
            default: return -1; // invalid position
        }
    }

    private int convertPositionToY(String position) {
        String yStr = position.substring(1);
        int y = Integer.parseInt(yStr);
        return y;
    }

    public Piece promote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose a piece to promote to (Queen, Rook, Bishop, Knight):");
        String pieceType = scanner.nextLine();
        Piece promotedPiece;
        switch (pieceType.toLowerCase()) {
            case "queen":
                promotedPiece = new Queen(this.getColor(), this.getX(), this.getY());
                break;
            case "rook":
                promotedPiece = new Rook(this.getColor(), this.getX(), this.getY());
                break;
            case "bishop":
                promotedPiece = new Bishop(this.getColor(), this.getX(), this.getY());
                break;
            case "knight":
                promotedPiece = new Knight(this.getColor(),this.getX(),this.getY());
                break;
            default:
                System.out.println("Invalid input. Defaulting to Queen.");
                promotedPiece = new Queen(this.getColor(), this.getX(), this.getY());
                break;
        }
        promotedPiece.setDidItPromote(true);
        return promotedPiece;
    }


    @Override
    public void move(int toX, int toY) {
        // move the piece to the new position
        this.setX(toX);
        this.setY(toY);

        if (( toX == 0) || ( toX == 7)) {
            this.promote();
        } else
            Board.setPiece(toX,toY,this);

        // set hasMoved to true, since the pawn has now moved at least once
        this.hasMoved = true;
    }

    public String toString(){
        return "p";
    }

    public boolean didItPromote() {
        return didItPromote;
    }

    public String getType() {
        return "pawn";
    }

    public void setDidItPromote(boolean didItPromote) {
        this.didItPromote = didItPromote;
    }
}

