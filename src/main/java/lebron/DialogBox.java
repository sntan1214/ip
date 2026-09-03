package lebron;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a message displayed in the chatbot GUI.
 */
public class DialogBox extends HBox {

    private final Label text;

    /**
     * Creates a dialog box containing the given text.
     *
     * @param text message to display
     */
    public DialogBox(String text) {
        this.text = new Label(text);

        this.text.setWrapText(true);
        this.text.setMaxWidth(280);

        this.setSpacing(10);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().add(this.text);
    }

    /**
     * Flips the dialog box to the left side.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);

        ObservableList<Node> children =
                FXCollections.observableArrayList(this.getChildren());

        FXCollections.reverse(children);

        this.getChildren().setAll(children);
    }

    /**
     * Creates a dialog box for a user message.
     *
     * @param text user's message
     * @return user dialog box
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    /**
     * Creates a dialog box for a Lebron response.
     *
     * @param text Lebron's response
     * @return Lebron dialog box
     */
    public static DialogBox getLebronDialog(String text) {
        DialogBox dialogBox = new DialogBox(text);
        dialogBox.flip();
        return dialogBox;
    }
}
