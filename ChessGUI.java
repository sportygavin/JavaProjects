import javax.swing.*;
import java.awt.*;

public class ChessGUI {
    private final JFrame frame;
    private final JPanel boardPanel;
    private final JLabel[][] squareLabels;


    public ChessGUI() {
        // Create the main window frame
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the chess board panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create an array of labels for the squares
        squareLabels = new JLabel[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squareLabels[row][col] = new JLabel();
                squareLabels[row][col].setOpaque(true);
                if ((row + col) % 2 == 0) {
                    squareLabels[row][col].setBackground(Color.WHITE);
                } else {
                    squareLabels[row][col].setBackground(Color.GRAY);
                }
                boardPanel.add(squareLabels[row][col]);
            }
        }

        // Add the board panel to the frame and pack it
        frame.getContentPane().add(boardPanel);
        frame.pack();

        // Show the window
        frame.setVisible(true);
    }

    // Update the GUI to reflect a move of a piece from one square to another
    public void movePiece(int startX, int startY, int endX, int endY) {
        // Get the piece that was moved
        Piece piece = Board.getPiece(endX, endY);

        // Clear the starting square
        squareLabels[startX][startY].setIcon(null);

        // Set the icon for the ending square
        squareLabels[endX][endY].setIcon(new ImageIcon(piece.getFileName()));

        // Repaint the board panel
        boardPanel.repaint();
    }

}
