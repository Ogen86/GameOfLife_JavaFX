package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpBox {

    public void display() {
        Stage help = new Stage();

        help.setTitle("rules");
        help.setMinHeight(300);
        help.setMinWidth(500);

        Label label = new Label();
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setText("A játék John Horton Conway Game of life játékán alapszik, és annak szabályait használja. \n" +
                "Az élő sejtek a feketék. A start gomb megnyomása után a játék elindul és a felhasználó által\n" +
                "élőnek beállított sejtek élete megkezdődik az alábbi szabályok alapján: \n" +
                "\n" +
                "1. minden élő sejt, akinek kettő vagy kevesebb szomszédja van meghal, túl ritka népesség miatt \n" +
                "2. minden élő sejt akinek több mint három szomszédja van meghal, túlnépesedés miatt. \n" +
                "3. minden élő sejt akinek kett vagy három szomszédja van, tovább él. \n" +
                "4. minden halott sejt életre kel ha három szomszédja van. \n" +
                "\n" +
                "A start gomb megnyomása után már nem állíthatóak a sejtek manuálisan. \n" +
                "Ehhez állítsa meg a játékot újra" +
                "Jó szórakozást!");

        Button closeButton = new Button("close");
        closeButton.setOnAction(e -> help.close());
        VBox layout = new VBox();
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout);
        help.setScene(scene);
        help.show();
    }
}