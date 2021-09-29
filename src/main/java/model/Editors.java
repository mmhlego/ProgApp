package model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.scene.image.Image;
import tools.Cmd;

public enum Editors {
    VSCode("Visual Studio Code", "vscode", "code"), Atom("Atom", "atom", "atom"),
    Sublime("Sublime Text", "sublime", "subl");

    String FullName, IconName, Prefix;

    private Editors(String fullName, String iconName, String prefix) {
        FullName = fullName;
        IconName = iconName;
        Prefix = prefix;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPrefix() {
        return Prefix;
    }

    public Image GetImage() {
        try {
            return new Image(new FileInputStream(new File("resources/icons/editors/" + IconName + ".png")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Editors[] GetAvailablEditors() {
        ArrayList<Editors> ans = new ArrayList<>();

        for (Editors editor : Editors.values()) {
            String CmdResult = Cmd.RunCommand(editor.getPrefix() + " --version").replaceAll("\n", "");

            if (!CmdResult.equals("\n") && CmdResult.length() != 0 && !CmdResult.contains("not recognized")) {
                System.out.println("\t> " + editor.getFullName() + " found");
                ans.add(editor);
            }
        }

        Editors[] ansArray = new Editors[ans.size()];

        for (int i = 0; i < ans.size(); i++) {
            ansArray[i] = ans.get(i);
        }

        return ansArray;
    }
}
