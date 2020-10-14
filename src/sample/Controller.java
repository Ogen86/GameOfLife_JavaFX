package sample;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public Button startButton;
    public Pane gamePane;

    private int tileSize = 20;
    private int width = 1200;
    private int height = 1200;

    private int colTiles = width / tileSize;
    private int rowTiles = height / tileSize;

    private boolean isGameRun = true;

    private Tile[][] grid = new Tile[colTiles][rowTiles];

    public void startButtonClicked() throws InterruptedException {
        if (startButton.getText().equals("Start")) {
            startButton.setText("Stop");
            isGameRun = true;
            new Thread(() -> {
                try {
                    gameRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            startButton.setText("Start");
            isGameRun = false;
        }
    }

    public void exitButtonClicked() {
        Platform.exit();
    }

    public void rulesButtonClicked() {
        HelpBox helpbox = new HelpBox();
        helpbox.display();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeContent();
    }


    private class Tile extends StackPane {
        private int x;
        private int y;
        private boolean isAlive;

        private Rectangle border = new Rectangle(tileSize - 2, tileSize - 2);

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
            setAlive(false);

            border.setStroke(Color.LIGHTGRAY);

            getChildren().addAll(border);

            setTranslateX(x * tileSize);
            setTranslateY(y * tileSize);

            setOnMouseClicked(e -> {
                if (startButton.getText().equals("Start")) {
                    if (isAlive) {
                        setAlive(false);
                    } else {
                        setAlive(true);
                    }
                }
            });
        }

        public void setAlive(boolean alive) {
            isAlive = alive;
            if (isAlive) {
                border.setFill(Color.BLACK);
            } else {
                border.setFill(Color.WHITE);
            }
        }
    }

    private Parent initializeContent() {
        Pane root = gamePane;

        for (int y = 0; y < rowTiles; y++) {
            for (int x = 0; x < colTiles; x++) {
                Tile tile = new Tile(x, y);

                grid[x][y] = tile;
                root.getChildren().add(tile);

            }

        }

        return root;
    }


    public void gameRun() throws InterruptedException {
        while (isGameRun) {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    int neighbours = countNeighbours(row, col);
                    boolean isAlive = isAliveCheck(row, col);

                    if (neighbours == 3) {
                        grid[row][col].setAlive(true);
                    } else if (isAlive && neighbours == 2) {
                        grid[row][col].setAlive(true);
                    } else {
                        grid[row][col].setAlive(false);
                    }
                }
            }
            Thread.sleep(500);
        }

    }


    private int countNeighbours(int row, int col) {
        int counter = 0;
        int[][] possibleDirections = new int[][]{
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1},
        };

        for (int[] possibleDirection : possibleDirections) {
            int currentRowToCheck = row + possibleDirection[0];
            int currentColumnToCheck = col + possibleDirection[1];

            boolean isWithinBorder = currentRowToCheck >= 1
                    && currentColumnToCheck >= 1
                    && currentRowToCheck < rowTiles - tileSize
                    && currentColumnToCheck < colTiles - tileSize;

            if (isWithinBorder && isAliveCheck(currentRowToCheck, currentColumnToCheck)) {
                counter++;
            }
        }
        return counter;
    }

    protected Boolean isAliveCheck(int y, int x) {
        return grid[y][x].isAlive;
    }


}