module com.tictactoe.fun {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tictactoe.fun to javafx.fxml;
    exports com.tictactoe.fun;
}