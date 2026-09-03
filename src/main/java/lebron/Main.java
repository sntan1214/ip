package lebron;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main JavaFX application for Lebron.
 */
public class Main extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;

    private final Lebron lebron =
            new Lebron("data", "lebron.txt");

    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();

        dialogContainer = new VBox();
        dialogContainer.setSpacing(10);

        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setPromptText("Enter command here...");

        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();

        mainLayout.getChildren().addAll(
                scrollPane,
                userInput,
                sendButton
        );

        Scene scene = new Scene(mainLayout);

        stage.setTitle("Lebron");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(398.0, 535.0);
        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.ALWAYS
        );

        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(
                Region.USE_COMPUTED_SIZE
        );

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(70.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setLeftAnchor(scrollPane, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);

        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        dialogContainer.heightProperty().addListener(
                observable -> scrollPane.setVvalue(1.0)
        );

        dialogContainer.getChildren().add(
                DialogBox.getLebronDialog(
                        "Hello! I'm Lebron.\nWhat can I do for you?"
                )
        );

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles a command entered by the user.
     */
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        String response = lebron.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getLebronDialog(response)
        );

        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay =
                    new PauseTransition(Duration.millis(700));

            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
