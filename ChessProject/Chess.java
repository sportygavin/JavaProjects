import javax.swing.*;
import java.util.Scanner;

// Try and give the program a file that contains moves for a chess game and then see if the program can make the moves the chess game shows.
// MODIFY THE ISVALIDMOVE METHOD TO CHECK AND SEE IF THE PIECE IS PINNED, and IF SO, EVALUATE THE METHOD TO ALWAYS BE FALSE
// The issue is that the king can take the piece attacking it even if it is defended. So i think the issue is that a king can take a piece even if it is defended

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();

        Board.printBoard();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(Board.isIsWhiteTurn())
                System.out.println("It is white's turn");
            else
                System.out.println("It is black's turn");

            System.out.print("Enter a move in chess notation (e.g. e2-e4): ");
            String move = scanner.nextLine();

            if (move.equals("exit")) {
                break;
            }

            if (Board.makeMove(move)) {
                System.out.println("Move successful.");
                Board.printBoard();
                Board.setIsWhiteTurn(!Board.isIsWhiteTurn());
            } else {
                System.out.println("Invalid move. Try again.");
                Board.printBoard();
            }

            if (Board.isCheckmate(Board.isIsWhiteTurn())) {
                if (Board.isIsWhiteTurn()) {
                    System.out.print("Checkmate! Black wins!");
                } else {
                    System.out.print("Checkmate! White wins!");
                }
                break;
            }

            if (Board.isCheck(Board.isIsWhiteTurn())) {
                if (Board.isIsWhiteTurn()) {
                    System.out.println("White is in Check");
                } else {
                    System.out.println("Black is in Check");
                }
            }

            if (Board.isStalemate(Board.isIsWhiteTurn())) {
                System.out.println("Stalemate! It's a draw!");
                break;
            }
        }
    }
}
