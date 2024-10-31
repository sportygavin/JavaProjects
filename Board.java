import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;


public class Board {
    private static Piece[][] board;
    private static boolean isWhiteTurn;
    private static JFrame frame;
    private static JPanel panel;


    public Board() {
        // Initialize the board with all pieces in their starting positions
        board = new Piece[8][8];
        isWhiteTurn = true;

        // Add black pieces
        board[0][0] = new Rook(false, 0, 0);
        board[0][1] = new Knight(false, 0, 1);
        board[0][2] = new Bishop(false, 0, 2);
        board[0][3] = new Queen(false, 0, 3);
        board[0][4] = new King(false, 0, 4);
        board[0][5] = new Bishop(false, 0, 5);
        board[0][6] = new Knight(false, 0, 6);
        board[0][7] = new Rook(false, 0, 7);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false, 1, i);
        }

        // Add white pieces
        board[7][0] = new Rook(true, 7, 0);
        board[7][1] = new Knight(true, 7, 1);
        board[7][2] = new Bishop(true, 7, 2);
        board[7][3] = new Queen(true, 7, 3);
        board[7][4] = new King(true, 7, 4);
        board[7][5] = new Bishop(true, 7, 5);
        board[7][6] = new Knight(true, 7, 6);
        board[7][7] = new Rook(true, 7, 7);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true, 6, i);
        }

        // Fill the rest of the board with null, since no pieces are there initially
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                board[i][j] = null;
//            }
//        }
    }

    public static boolean isStalemate(boolean isWhite) {
        // Check if the king is in check
        if (isCheck(isWhite)) {
            return false;
        }

        // Check for insufficient material scenarios
        if (hasInsufficientMaterial()) {
            return true;
        }

        // Check if any piece can move legally
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == isWhite) {
                    for (int targetRow = 0; targetRow < 8; targetRow++) {
                        for (int targetCol = 0; targetCol < 8; targetCol++) {
                            if (piece.isValidMove(targetRow, targetCol)) {
                                // Simulate the move to check for a legal move
                                Piece capturedPiece = board[targetRow][targetCol];
                                movePiece(row, col, targetRow, targetCol);

                                // Check if the move results in a check
                                boolean isCheck = isCheck(isWhite);

                                // Undo the move
                                movePiece(targetRow, targetCol, row, col);
                                board[targetRow][targetCol] = capturedPiece;

                                if (!isCheck) {
                                    // A legal move is found, not a stalemate
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        // No legal moves found, it's a stalemate
        return true;
    }

    private static boolean hasInsufficientMaterial() {
        // Count the number of remaining pieces on the board
        int pieceCount = countPieces();

        // Check for insufficient material scenarios
        if (pieceCount <= 3) {
            // If there are only 3 or fewer pieces, it could be an insufficient material scenario
            // Check for specific scenarios: two bishops, two knights, or king vs. lone king
            if (pieceCount == 3) {
                // Check for two bishops scenario
                if (hasOnlyTwoBishops()) {
                    return true;
                }

                // Check for two knights scenario
                if (hasOnlyTwoKnights()) {
                    return true;
                }

                // Check for king and bishop vs. lone king scenario
                if (hasKingAndBishopVsKing()) {
                    return true;
                }
            }

            // Check for king vs. lone king scenario
            if (pieceCount == 2) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasKingAndBishopVsKing() {
        // Count the number of remaining bishops on the board
        int bishopCount = 0;
        boolean hasKing = false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof Bishop) {
                    bishopCount++;
                } else if (piece instanceof King) {
                    hasKing = true;
                }
            }
        }

        // If there is a king, one bishop, and no other pieces remaining, it is a king and bishop vs. lone king scenario
        return hasKing && bishopCount == 1;
    }

    private static int countPieces() {
        int count = 0;

        // Count the number of remaining pieces on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean hasOnlyTwoBishops() {
        // Count the number of remaining bishops on the board
        int bishopCount = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof Bishop) {
                    bishopCount++;
                }
            }
        }

        // If there are only two bishops remaining, it is the insufficient material scenario
        return bishopCount == 2;
    }

    private static boolean hasOnlyTwoKnights() {
        // Count the number of remaining knights on the board
        int knightCount = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof Knight) {
                    knightCount++;
                }
            }
        }

        // If there are only two knights remaining, it is the insufficient material scenario
        return knightCount == 2;
    }


    public static boolean isAttacked(int row, int col, boolean isWhite) {
        // Check for attacks from pawns
        int direction = isWhite ? -1 : 1;
        int pawnRow = row + direction;
        if (pawnRow >= 0 && pawnRow < 8) {
            if (col > 0 && board[pawnRow][col - 1] instanceof Pawn && board[pawnRow][col - 1].getColor() != isWhite) {
                return true;
            }
            if (col < 7 && board[pawnRow][col + 1] instanceof Pawn && board[pawnRow][col + 1].getColor() != isWhite) {
                return true;
            }
        }

        // Check for attacks from knights
        int[][] knightMoves = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
        for (int[] move : knightMoves) {
            int rowOffset = move[0];
            int colOffset = move[1];
            int newRow = row + rowOffset;
            int newCol = col + colOffset;
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && board[newRow][newCol] instanceof Knight && board[newRow][newCol].getColor() != isWhite) {
                return true;
            }
        }

        // Check for attacks from rooks and queens (horizontal and vertical)
        int[][] rookMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : rookMoves) {
            int rowOffset = move[0];
            int colOffset = move[1];
            int newRow = row + rowOffset;
            int newCol = col + colOffset;
            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece piece = board[newRow][newCol];
                if (piece != null) {
                    if (piece.getColor() == isWhite) {
                        break; // friendly piece blocking attack
                    }
                    if (piece instanceof Rook || piece instanceof Queen) {
                        return true; // rook or queen attack (horizontal or vertical)
                    }
                    break; // enemy piece blocking attack
                }
                newRow += rowOffset;
                newCol += colOffset;
            }
        }

        // Check for attacks from bishops and queens (diagonal)
        int[][] bishopMoves = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] move : bishopMoves) {
            int rowOffset = move[0];
            int colOffset = move[1];
            int newRow = row + rowOffset;
            int newCol = col + colOffset;
            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece piece = board[newRow][newCol];
                if (piece != null) {
                    if (piece.getColor() == isWhite) {
                        break; // friendly piece blocking attack
                    }
                    if (piece instanceof Bishop || piece instanceof Queen) {
                        return true; // bishop or queen attack (diagonal)
                    }
                    break; // enemy piece blocking attack
                }
                newRow += rowOffset;
                newCol += colOffset;
            }
        }


        // Check for attacks from the other king
        int[][] kingMoves = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] move : kingMoves) {
            int rowOffset = move[0];
            int colOffset = move[1];
            int newRow = row + rowOffset;
            int newCol = col + colOffset;
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && board[newRow][newCol] instanceof King && board[newRow][newCol].getColor() != isWhite) {
                return true;
            }
        }

        // No attacks found
        return false;
    }

    public static boolean isCheck(boolean isWhite) {
        // Find the location of the king
        int kingRow = findKingRow(isWhite);
        int kingCol = findKingCol(isWhite);

        // Check if the king is attacked
        return isAttacked(kingRow, kingCol, isWhite);
    }

    public static boolean isCheckmate(boolean isWhite) {
        // Check if the king is in check
        if (!isCheck(isWhite)) {
            return false;
        }

        // Find the king's position
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == isWhite) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        // Check if the king can move to a safe position
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // skip the current square (king's position)
                }
                int newRow = kingRow + rowOffset;
                int newCol = kingCol + colOffset;
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && !isAttacked(newRow, newCol, isWhite) && board[newRow][newCol] == null) {
                    // Simulate the move
                    Piece kingPiece = board[kingRow][kingCol];
                    board[newRow][newCol] = kingPiece;
                    board[kingRow][kingCol] = null;

                    // Check if the king is still in check after the move
                    boolean isInCheck = isCheck(isWhite);

                    // Undo the move
                    board[kingRow][kingCol] = kingPiece;
                    board[newRow][newCol] = null;

                    if (!isInCheck) {
                        return false; // the king can move to a safe square
                    }
                }
            }
        }

        // Check if any piece can block the check or capture the attacking piece
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == isWhite) {
                    for (int targetRow = 0; targetRow < 8; targetRow++) {
                        for (int targetCol = 0; targetCol < 8; targetCol++) {
                            if (piece.isValidMove(targetRow, targetCol)) {
                                // Simulate the move
                                Piece capturedPiece = board[targetRow][targetCol];
                                board[targetRow][targetCol] = piece;
                                board[row][col] = null;

                                // Check if the king is still in check after the move
                                boolean isInCheck = isCheck(isWhite);

                                // Undo the move
                                board[row][col] = piece;
                                board[targetRow][targetCol] = capturedPiece;

                                if (!isInCheck) {
                                    return false; // the piece can block the check or capture the attacking piece
                                }
                            }
                        }
                    }
                }
            }
        }

        // The king is in checkmate
        return true;
    }


    public static int[] findKing(boolean isWhite) {
        int[] kingPos = new int[2];
        kingPos[0] = -1; // Default value if king is not found
        kingPos[1] = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board[row][col];
                if (p instanceof King && p.getColor() == isWhite) {
                    kingPos[0] = row;
                    kingPos[1] = col;
                    return kingPos;
                }
            }
        }
        return null; // Return null if king is not found
    }


    public static int findKingRow(boolean isWhite) {
        int[] kingPos = findKing(isWhite);
        if (kingPos != null) {
            return kingPos[0];
        }
        return -1;
    }

    public static int findKingCol(boolean isWhite) {
        int[] kingPos = findKing(isWhite);
        if (kingPos != null) {
            return kingPos[1];
        }
        return -1;
    }


    public static void movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = getPiece(startX, startY);
        setPiece(startX, startY, null);

        // Check if there is a piece at the ending position
        if (isOccupied(endX, endY)) {
            removePiece(endX, endY);
        }

        setPiece(endX, endY, piece);
        if((piece instanceof Rook || piece instanceof Pawn) && (startX != endX || startY != endY))
            piece.setHasItMoved(true);
    }

    public static Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public static void setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    public static void removePiece(int row, int col) {
        board[row][col] = null;
    }

    public static boolean isOccupied(int x, int y) {
        return board[x][y] != null;
    }





    public static void printBoard() {
        if (frame == null) {
            // Initialize the GUI components
            frame = new JFrame("Chess Board");
            panel = new JPanel(new GridLayout(8, 8));

            // Configure the frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.getContentPane().add(panel);
            frame.setVisible(true);
        } else {
            // Clear the panel before updating
            panel.removeAll();
        }

        // Define the colors for the board squares
        Color lightColor = Color.WHITE;
        Color darkColor = Color.GRAY;

        // Iterate over the board array and create JLabels with images for each square
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);

                // Set the background color based on the square's position
                if ((i + j) % 2 == 0) {
                    label.setBackground(lightColor);
                } else {
                    label.setBackground(darkColor);
                }

                // Set the image icon for the label based on the piece or null value
                if (board[i][j] == null) {
                    label.setIcon(null);
                } else {
                    String imageName = getImageFileName(board[i][j]);  // Get the image file name based on the piece
                    ImageIcon icon = new ImageIcon(imageName);  // Create an ImageIcon from the image file
                    label.setIcon(icon);  // Set the icon for the label
                }

                panel.add(label);
            }
        }

        // Refresh the panel to reflect the updated chessboard state
        panel.revalidate();
        panel.repaint();
    }

    private static String getImageFileName(Piece piece) {
        String color = piece.getColor() ? "white" : "black";
        String type = piece.getType().toLowerCase();

        String fileName = color + "_" + type + ".png";
        return fileName;
    }




//    public static void printBoard() {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (board[i][j] == null) {
//                    System.out.print("- ");
//                } else {
//                    System.out.print(board[i][j].toString() + " ");
//                }
//            }
//            System.out.println();
//        }
//    }

    public static boolean makeMove(String move) {
        // Parse the move string to get the starting and ending coordinates
        if (!move.contains("-"))
            return false;

        // Validate the length of the move string
        if (move.length() != 5)
            return false;

        String[] moveParts = move.split("-");
        if (moveParts.length != 2)
            return false;
        String startPos = moveParts[0];
        String endPos = moveParts[1];
        if (!startPos.matches("[a-h][1-8]") || !endPos.matches("[a-h][1-8]"))
            return false;


        // Convert the chess notation to x,y coordinates
        int startCol = startPos.charAt(0) - 'a';
        int startRow = 8 - Character.getNumericValue(startPos.charAt(1));
        int endCol = endPos.charAt(0) - 'a';
        int endRow = 8 - Character.getNumericValue(endPos.charAt(1));

        // Get the piece at the starting position
        Piece piece = getPiece(startRow, startCol);


        if (piece == null || isWhiteTurn && !piece.getColor() || !isWhiteTurn && piece.getColor()) {
            return false;
        }

        // Validate the move based on the piece type
        if (!piece.isValidMove(endRow, endCol)) {
            return false;
        }

        // Simulate the move
        Piece capturedPiece = getPiece(endRow, endCol); // Store the captured piece if any
        // Check if it's a pawn move to the last row
        if (piece instanceof Pawn && (endRow == 0 || endRow == 7)) {
            // Promote the pawn
            Pawn pawn = (Pawn) piece;
            piece = pawn.promote(); // Assign the promoted piece back to the variable
        }

        piece.move(endRow, endCol); // Move the piece to the new position


        boolean isInCheck = isCheck(piece.getColor()); // Check if the king is in check
        if (isInCheck) {
            // Move puts the player in check
            // Undo the move
            piece.move(startRow, startCol); // Move the piece back to the starting position
            if (capturedPiece != null) {
                // Restore the captured piece if any
                capturedPiece.move(endRow, endCol);
                setPiece(endRow, endCol, capturedPiece);
            } else {
                // Clear the ending position
                setPiece(endRow, endCol, null);
            }
            return false;
        } else {
            // Update the board array
            setPiece(startRow, startCol, null);
            if (capturedPiece != null) {
                // Capture the piece
                capturedPiece.setCaptured(true);
            }
            setPiece(endRow, endCol, piece);
            return true;
        }
    }

    public static boolean isIsWhiteTurn() {
        return isWhiteTurn;
    }

    public static void setIsWhiteTurn(boolean isWhiteTurn) {
        Board.isWhiteTurn = isWhiteTurn;
    }
}
