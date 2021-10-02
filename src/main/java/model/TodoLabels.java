package model;

public enum TodoLabels {
    NotImportant("Not Important", "#61BD4F"), Important("Important", "#FF9F1A"), Critical("Critical", "#EB5A46"),
    NeedHelp("Need Some Help", "#C377DF"), Todo("Todo", "#51E899"), Doing("Doing", "#01C2E0"),
    Finished("Finished", "#344563");

    String Text, Color;

    private TodoLabels(String text, String color) {
        Text = text;
        Color = color;
    }

    public String getColor() {
        return Color;
    }

    public String getText() {
        return Text;
    }
}
