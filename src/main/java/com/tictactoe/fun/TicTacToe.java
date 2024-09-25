package com.tictactoe.fun;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicTacToe extends Application {

    // initializing board size and symbols
    private static final int BOARD_SIZE = 3;
    private static final String PLAYER_SYMBOL = "X";
    private static final String COMPUTER_SYMBOL = "O";

    // Creating components of game
    private Button[][] board;
    private Label gameOver;
    private boolean isGameOver;
    private MiniMax computer;
    private Button resetButton;
    private Button computerWin;
    private Button playerWIn;
    private Button draw;

    // variables for counting wins and ties
    private int countComputerWins = 0;
    private int countPlayerWins = 0;
    private int drawCount = 0;

    private Label playerLabel;
    private Label computerLabel;
    private Label drawLabel;


    @Override
    public void start(Stage stage) throws IOException {
        computer = new MiniMax(PLAYER_SYMBOL, COMPUTER_SYMBOL);

        // Using components to design the layout
        GridPane boardGrid = new GridPane();
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setPadding(new Insets(20,80,10,90));

        board = new Button[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = new Button("");
                button.setPrefSize(60, 60);
                //button.setStyle();
                int finalJ = j;
                int finalI = i;
                button.setOnAction(event -> handlePlayerMove(finalI, finalJ));
                boardGrid.add(button, j, i);
                board[i][j] = button;
            }
        }

        gameOver = new Label("");
        gameOver.setStyle("-fx-font-size: 24px;");

        resetButton = new Button();
        resetButton.setOnAction(actionEvent -> resetGame());
        resetButton.setPrefSize(100, 40);
        resetButton.setText("Reset Game");

        computerWin = new Button();
        computerWin.setPrefSize(50, 50);
        computerWin.setText("0");
        computerWin.setText(Integer.toString(getCountComputerWins()));

        playerWIn = new Button();
        playerWIn.setPrefSize(50, 50);
        playerWIn.setText("0");

        draw = new Button();
        draw.setPrefSize(50, 50);
        draw.setText("0");

        playerLabel = new Label();
        playerLabel.setText("Player");
        playerLabel.setStyle("-fx-font-size: 15px;");

        computerLabel = new Label();
        computerLabel.setText("Computer");
        computerLabel.setStyle("-fx-font-size: 15px;");

        drawLabel = new Label();
        drawLabel.setText("Ties");
        drawLabel.setStyle("-fx-font-size: 15px;");

        HBox gameOverBox = new HBox();
        gameOverBox.getChildren().add(gameOver);
        gameOverBox.setAlignment(Pos.CENTER);

        HBox scoreLabel = new HBox();
        scoreLabel.getChildren().addAll(computerLabel,playerLabel, drawLabel);
        scoreLabel.setPadding(new Insets(40,80,10,80));
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setSpacing(30);

        HBox scoreBox = new HBox();
        scoreBox.getChildren().addAll(computerWin, playerWIn,draw);
        scoreBox.setPadding(new Insets(0,80,30,90));
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setSpacing(30);

        VBox root = new VBox();
        root.getChildren().addAll(boardGrid, gameOverBox,scoreLabel,scoreBox, resetButton);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #0479b0;");
        //root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    // Method to handle players move
    private void handlePlayerMove(int row, int col) {
        if (isGameOver){
            return;
        }
        Button button = board[row][col];
        if (button.getText().isEmpty()){
            button.setText(PLAYER_SYMBOL);
            if (isPlayerWin()){
                gameOver.setText("Player Wins");
                countPlayerWins++;
                playerWIn.setText(Integer.toString(getCountPlayerWins()));
                isGameOver = true;
            } else if (!isBoardFull()) {
                makeComputerMove();
            }else {
                if (!isPlayerWin() && !isComputerWin()) {
                    gameOver.setText("It's a draw!");
                    drawCount++;
                    draw.setText(Integer.toString(getDrawCount()));
                    isGameOver = true;
                }
            }
        }
    }

    // Method for Computer to make it's move
    private void makeComputerMove() {
        int[] moves = computer.getMoves(getBoardState());
        board[moves[0]][moves[1]].setText(COMPUTER_SYMBOL);
        if (isComputerWin()){
            gameOver.setText("COMPUTER WINS !");
            countComputerWins++;
            computerWin.setText(Integer.toString(getCountComputerWins()));
        }else if (isBoardFull()) {
            gameOver.setText("It's a draw!");
            drawCount++;
            draw.setText(Integer.toString(getDrawCount()));
            isGameOver = true;
        }
    }

    // checks if player wins.
    // Tip: This is not going to happen
    private boolean isPlayerWin() {
        return computer.isWinningState(getBoardState(), PLAYER_SYMBOL);
    }

    // Checks if computer wins
    private boolean isComputerWin() {
        return computer.isWinningState(getBoardState(), COMPUTER_SYMBOL);
    }

    //Returns the current state of board
    private String[][] getBoardState() {
        String[][] boardState = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardState[i][j] = board[i][j].getText();
            }
        }
        return boardState;
    }

    // Checks if board is full or not
    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Clears the text of board
    private void resetGame() {
        isGameOver = false;
        gameOver.setText("");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setText("");
            }
        }
    }

    // It fetches the counts number of time computer won
    private int getCountComputerWins(){
        return countComputerWins;
    }

    // It fetches the counts number of ties
    private int getDrawCount(){
        return drawCount;
    }

    // It fetches the counts number of time player won
    private int getCountPlayerWins(){
        return countPlayerWins;
    }

    public static void main(String[] args) {
        launch();
    }
}