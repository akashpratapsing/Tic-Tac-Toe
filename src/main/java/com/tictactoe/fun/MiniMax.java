package com.tictactoe.fun;

import java.util.Random;

public class MiniMax {

    private static final int MAX_DEPTH = 9;
    private static final int INFINITY = 1000;

    private String playerSymbol;
    private String computerSymbol;

    public MiniMax(String playerSymbol, String computerSymbol) {
        this.playerSymbol = playerSymbol;
        this.computerSymbol = computerSymbol;
    }

    public int[] getMoves(String[][] boardState) {
        int bestScore = -INFINITY;
        int[] bestMoves = null;

        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j].isEmpty()) {
                    boardState[i][j] = computerSymbol;
                    int score = minimax(boardState, 0, false, -INFINITY, INFINITY);
                    boardState[i][j] = "";
                    if (score > bestScore) {
                        bestScore = score;
                        bestMoves = new int[]{i, j};
                    }
                }
            }
        }
        if (bestMoves == null) {
            Random rand = new Random();
            int row, col;
            do {
                row = rand.nextInt(boardState.length);
                col = rand.nextInt(boardState[0].length);
            } while (!boardState[row][col].isEmpty());
            bestMoves = new int[]{row, col};
        }
        return bestMoves;
    }

    // Minimax algorithm which uses alpha-beta puring for enhancement
    private int minimax(String[][] boardState, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == MAX_DEPTH || isGameOver(boardState)) {
            return evaluateBoardState(boardState);
        }

        if (isMaximizing) {
            int bestScore = -INFINITY;
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j].isEmpty()) {
                        boardState[i][j] = computerSymbol;
                        int score = minimax(boardState, depth + 1, false, alpha, beta);
                        boardState[i][j] = "";
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = INFINITY;
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j].isEmpty()) {
                        boardState[i][j] = playerSymbol;
                        int score = minimax(boardState, depth + 1, true, alpha, beta);
                        boardState[i][j] = "";
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    // Checks if game over
    private boolean isGameOver(String[][] boardState) {
        return isWinningState(boardState, computerSymbol) || isWinningState(boardState, playerSymbol) || isBoardFull(boardState);
    }

    //checks the board is full or not
    private boolean isBoardFull(String[][] boardState) {
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    // Method for checking Win of computer or player
    public boolean isWinningState(String[][] boardState, String symbol) {
        return isRowWin(boardState, symbol) || isColWin(boardState, symbol) || isDiagonalWin(boardState, symbol);
    }

    // checks both diagonal
    private boolean isDiagonalWin(String[][] boardState, String symbol) {
        return  ((boardState[0][0].equals(symbol) && boardState[1][1].equals(symbol) && boardState[2][2].equals(symbol)) ||
                (boardState[0][2].equals(symbol) && boardState[1][1].equals(symbol) && boardState[2][0].equals(symbol)));
    }

    // Iterates through all the columns for checking win
    private boolean isColWin(String[][] boardState, String symbol) {
        for (int i = 0; i < boardState.length; i++) {
            if (boardState[0][i].equals(symbol) && boardState[1][i].equals(symbol) && boardState[2][i].equals(symbol)){
                return true;
            }
        }
        return false;
    }

    // Iterates through all the rows for checking win
    private boolean isRowWin(String[][] boardState, String symbol) {
        for (int i = 0; i < boardState.length; i++) {
            if (boardState[i][0].equals(symbol) && boardState[i][1].equals(symbol) && boardState[i][2].equals(symbol)){
                return true;
            }
        }
        return false;
    }

    // Evaluate the winning state of board
    private int evaluateBoardState(String[][] boardState) {
        if (isWinningState(boardState, computerSymbol)){ // If Computer Wins
            return 1;
        }else if (isWinningState(boardState, playerSymbol)){ // Player Wins
            return -1;
        }else {
            return 0;
        }
    }
}