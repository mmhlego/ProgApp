package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class Beautifier extends AnchorPane {
    String Code;
    ArrayList<String> Splited = new ArrayList<>();
    ArrayList<Type> Types = new ArrayList<>();

    public Beautifier(String code) {
        Code = code;
        Split();
        setStyle("-fx-background-color:#282c34; -fx-background-radius:5;");
    }

    private void AddCopy() {
        try {
            ImageView imageView = new ImageView(
                    new Image(new FileInputStream(new File("resources/icons/usefulCodes/Copy.png"))));
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            AnchorPane pane = new AnchorPane(imageView);
            AnchorPane.setRightAnchor(pane, 10.0);
            pane.setLayoutY(10);
            getChildren().add(pane);
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(
                    e -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Code), null));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Beautify() {
        getChildren().clear();
        AddCopy();
        Split();

        int level = 1;
        double dw = 25.0, dh = 25.0;
        double x = level * dw, y = dh / 2;

        for (int i = 0; i < Splited.size(); i++) {
            String current = Splited.get(i);
            Type type = Types.get(i);

            if (i > 0 && Types.get(i - 1).equals(Type.Symbol) && type.equals(Type.Word)) {
            } else if (type.equals(Type.Word) && i > 0 && !Splited.get(i - 1).equals("\n")) {
                x += 8.0;
            }

            if (current.equals("}")) {
                level--;
                y += dh;
                x = level * dw;
            } else if (current.equals("\n")) {
                y += dh;
                x = level * dw;
            } else if (current.equals(" ")) {
                x += 4.0;
                continue;
            }

            Label label = new Label(current);
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.setFont(new Font("Consolas", 17));
            label.setStyle("-fx-text-fill:" + CodeStyles.GetColor(current, type));

            x += GetLabelWidth(label);

            getChildren().add(label);

            if (current.equals("{")) {
                level++;
                x = level * dw;
            } else if (current.equals("}")) {
                x = level * dw;
            }
            setMinHeight(y + dh / 2);
        }
    }

    public void Simple(String before) {
        getChildren().clear();
        AddCopy();

        double dw = 25.0, dh = 25.0;
        double x = dw, y = dh / 2;

        Splited.clear();
        Types.clear();

        String All = before + Code;

        for (String current : All.split("\n")) {
            Label label = new Label(current);
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.setStyle("-fx-text-fill:white");
            y += dh;
            label.setFont(new Font("Consolas", 17));
            getChildren().add(label);

            setMinHeight(y + dh / 2);

            if (!before.equals("")) {
                x = 2 * dw;
            }
        }
    }

    public void Simple() {
        Simple("");
    }

    private double GetLabelWidth(Label label) {
        HBox h = new HBox();
        h.getChildren().add(label);
        new Scene(h);
        label.impl_processCSS(true);
        return label.prefWidth(-1);
    }

    private void Split() {
        Splited.clear();
        Types.clear();

        String CurrentWord = "";

        Code = Code.replace("\t", "");

        for (String CurrentRow : Code.split("\n")) {
            boolean IsAWord = false, CommentStart = false;

            for (int i = 0; i < CurrentRow.length(); i++) {
                char c = CurrentRow.charAt(i);

                if (c == '/' && !CommentStart) {
                    CommentStart = true;
                } else if (CommentStart && c == '/') {
                    Splited.add(CurrentRow.substring(i - 1));
                    Types.add(Type.Comment);
                    break;
                } else {
                    CommentStart = false;
                }

                if (c == '\"' || c == '\'') {
                    int index = CurrentRow.indexOf(c, i + 1);
                    Splited.add(CurrentRow.substring(i, index + 1));
                    Types.add(Type.String);
                    i = index;
                    continue;
                }

                if (CharachterType.GetCharachterType(c).equals(CharachterType.Alphabet)
                        || CharachterType.GetCharachterType(c).equals(CharachterType.Number)) {
                    CurrentWord += c;
                    IsAWord = true;
                } else if (CharachterType.GetCharachterType(c).equals(CharachterType.Symbol)) {
                    if (IsAWord) {
                        Splited.add(CurrentWord);
                        Types.add(Type.Word);
                        CurrentWord = "";
                        IsAWord = false;
                    }
                    Splited.add(c + "");
                    Types.add(Type.Symbol);
                } else if (c == ' ') {
                    if (IsAWord) {
                        Splited.add(CurrentWord);
                        Types.add(Type.Word);
                        CurrentWord = "";
                        IsAWord = false;
                    }
                }
            }

            if (IsAWord) {
                Splited.add(CurrentWord);
                Types.add(Type.Word);
                CurrentWord = "";
                IsAWord = false;
            }

            Splited.add("\n");
            Types.add(Type.NewLine);
        }
    }

    public String getCode() {
        return Code;
    }

    enum Type {
        Word, String, Comment, Symbol, NewLine, NONE;

        String Color;
    }

    enum CharachterType {
        Number, Symbol, Alphabet, Extra, None;

        public static CharachterType GetCharachterType(char c) {
            if (c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == ':' || c == ';' || c == '<'
                    || c == '>' || c == '=' || c == '+' || c == '*' || c == '-' /*|| c == '/'*/ || c == '%' || c == '.'
                    || c == '&' || c == '|'
            /*|| c == '\'' || c == '\"'*/) {
                return CharachterType.Symbol;
            } else if ((c <= 122 && c >= 97) || (c <= 90 && c >= 65) || c == '\"' || c == '\'') {
                return CharachterType.Alphabet;
            } else if (c >= 48 && c <= 57) {
                return CharachterType.Number;
            } else if (/*c == ' ' ||*/ c == '\t' || c == '\r' /*|| c == '\n'*/) {
                return CharachterType.Extra;
            }
            return CharachterType.None;
        }
    }
}
