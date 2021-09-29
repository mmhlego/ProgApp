package model;

import java.io.File;
import java.io.FileInputStream;

import javafx.scene.image.Image;

public enum Language {
    Angular(false, "Angular"), Arduino(false, "Arduino"), Cpp(true, "C++", "cpp"), Flutter(false, "Flutter"),
    HTML(true, "HTML"), Java(true, "Java"), JavaScript(false, "JavaScript"), Kotlin(false, "Kotlin"),
    Node(false, "Node.js", "nodejs"), Python(true, "Python"), React(true, "React"), Sass(false, "Sass"),
    Spring(false, "Spring Boot", "spring"), TypeScript(false, "TypeScript"), Vue(false, "Vue.js", "vue");

    private boolean Available;
    private String DisplayName, IconName;

    private Language(boolean available, String displayName) {
        this(available, displayName, displayName.toLowerCase());
    }

    private Language(boolean available, String displayName, String iconName) {
        Available = available;
        DisplayName = displayName;
        IconName = iconName;
    }

    public boolean isAvailable() {
        return Available;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public Image getImage() {
        try {
            return new Image(new FileInputStream(new File("resources/icons/languages/" + IconName + ".png")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String GetAbbreviation() {
        return IconName.toUpperCase();
    }

    public static Language FromString(String lang) {
        for (Language language : Language.values())
            if (lang.equals(language.toString()))
                return language;
        return null;
    }
}
