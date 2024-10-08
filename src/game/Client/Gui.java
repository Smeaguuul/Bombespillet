package game.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

import static game.Client.ClientGameLogic.placeBomb;
import static game.Client.ClientGameLogic.playerMoved;

public class Gui extends Application {

    public static final int size = 30;
    public static final int scene_height = size * 20 + 50;
    public static final int scene_width = size * 20 + 200;
    public static Image image_floor;
    public static Image image_wall;
    public static Image hero_right, hero_left, hero_up, hero_down;
    private static Image chest;
    private static Image bomb;
    private static Image explosion;


    private static Label[][] fields;
    private static TextArea scoreList;
    private static Map<String, Image> imageType;


    // -------------------------------------------
    // | Maze: (0,0)              | Score: (1,0) |
    // |-----------------------------------------|
    // | boardGrid (0,1)          | scorelist    |
    // |                          | (1,1)        |
    // -------------------------------------------

    @Override
    public void start(Stage primaryStage) {
        try {
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(0, 10, 0, 10));

            Text mazeLabel = new Text("Maze:");
            mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Text scoreLabel = new Text("Score:");
            scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            scoreList = new TextArea();

            GridPane boardGrid = new GridPane();

            image_wall = new Image(getClass().getResourceAsStream("Image/wall4.png"), size, size, false, false);
            image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"), size, size, false, false);

            hero_right = new Image(getClass().getResourceAsStream("Image/heroRight.png"), size, size, false, false);
            hero_left = new Image(getClass().getResourceAsStream("Image/heroLeft.png"), size, size, false, false);
            hero_up = new Image(getClass().getResourceAsStream("Image/heroUp.png"), size, size, false, false);
            hero_down = new Image(getClass().getResourceAsStream("Image/heroDown.png"), size, size, false, false);
            chest = new Image(getClass().getResourceAsStream("Image/chest.png"), size, size, false, false);
            bomb = new Image(getClass().getResourceAsStream("Image/bomb.png"), size, size, false, false);
            explosion = new Image(getClass().getResourceAsStream("Image/explosion.png"), size, size, false, false);

            imageType = new HashMap<>();
            imageType.put("chest", chest);
            imageType.put("bomb", bomb);
            imageType.put("explosion", explosion);
            imageType.put("heroRight", hero_right);
            imageType.put("heroLeft", hero_left);
            imageType.put("heroUp", hero_up);
            imageType.put("heroDown", hero_down);

            fields = new Label[20][20];
            for (int j = 0; j < 20; j++) {
                for (int i = 0; i < 20; i++) {
                    switch (Generel.board[j].charAt(i)) {
                        case 'w': fields[i][j] = new Label("", new ImageView(image_wall)); break;
                        case ' ': fields[i][j] = new Label("", new ImageView(image_floor)); break;
                        default: throw new Exception("Illegal field value: " + Generel.board[j].charAt(i));
                    }
                    boardGrid.add(fields[i][j], i, j);
                }
            }
            scoreList.setEditable(false);


            grid.add(mazeLabel, 0, 0);
            grid.add(scoreLabel, 1, 0);
            grid.add(boardGrid, 0, 1);
            grid.add(scoreList, 1, 1);

            Scene scene = new Scene(grid, scene_width, scene_height);
            primaryStage.setScene(scene);
            primaryStage.show();

            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                switch (event.getCode()) {
                    case UP: playerMoved(0, -1, "up"); break;
                    case DOWN: playerMoved(0, +1, "down"); break;
                    case LEFT: playerMoved(-1, 0, "left"); break;
                    case RIGHT: playerMoved(+1, 0, "right"); break;
                    case SPACE: placeBomb(); break;
                    case ESCAPE: System.exit(0);
                    default: break;
                }
            });

            // Putting default players on screen
            for (int i = 0; i < ClientGameLogic.playerList.size(); i++) {
                fields[ClientGameLogic.playerList.get(i).getXpos()][ClientGameLogic.playerList.get(i).getYpos()].setGraphic(new ImageView(hero_up));
            }
            scoreList.setText(getScoreList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeObjectOnScreen(Pair oldpos) {
        Platform.runLater(() -> {
            fields[oldpos.getX()][oldpos.getY()].setGraphic(new ImageView(image_floor));
        });
    }

    public static void removeItems(ArrayList<Item> Items) {
        for (Item location : Items) {
            removeObjectOnScreen(location.getLocation());
        }
    }

    public static void updateGUI(List<Item> items) {
        for (Item item : items) {
            placeItemOnScreen(item);
        }
        updateScoreTable();
    }

    private static void placeItemOnScreen(Item item) {
        Platform.runLater(() -> {
            Pair location = item.getLocation();
            String type = item.getType();
            Image image = imageType.get(type);
            fields[location.getX()][location.getY()].setGraphic(new ImageView(image));
        });
    }

    public static void updateScoreTable() {
        Platform.runLater(() -> {
            scoreList.setText(getScoreList());
        });
    }

    public static String getScoreList() {
        StringBuffer b = new StringBuffer(100);
        for (Player p : ClientGameLogic.playerList) {
            b.append(p + "\r\n");
        }
        return b.toString();
    }
}