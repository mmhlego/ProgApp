package tools;

import tools.Beautifier.Type;

public class CodeStyles {
    public static String GetColor(String string, Type type) {
        if (type.equals(Type.String)) {
            return "#98c379";
        } else if (type.equals(Type.Comment)) {
            return "#7f848e";
        } else if (type.equals(Type.Symbol) && IsOperational(string)) {
            return "#56b6c2";
        } else if (type.equals(Type.Word)) {
            if (KeyWords().contains(string)) {
                return "#c678dd";
            } else if (IsNumber(string)) {
                return "#d19a66";
            } else if (YellowKeyWords().contains(string)) {
                return "#e5c07b";
            } else if (BlueKeyWords().contains(string)) {
                return "#61afef";
            } else if (RedKeywords().contains(string)) {
                return "#e06c75";
            } else {
                return "white";
                // return "#e06c75";
            }
        }

        return "white";
    }

    private static boolean IsOperational(String string) {
        if (string.length() == 1) {
            return (string.equals(">") || string.equals("<") || string.equals("&") || string.equals("|")
                    || string.equals("=") || string.equals("-") || string.equals("+") || string.equals("*")
                    || string.equals("/") || string.equals("%"));
        }
        return false;
    }

    private static boolean IsNumber(String string) {
        boolean Conflict = false, FloatingPoint = false;

        for (char c : string.toCharArray()) {
            if (c == '.') {
                if (FloatingPoint) {
                    return false;
                }
                FloatingPoint = true;
            } else if (c >= 48 && c <= 57) {
            } else {
                Conflict = true;
                break;
            }
        }

        return !Conflict;
    }

    private static String KeyWords() {
        String WholeSequence = "for while do if else continue break switch int double float long package public private protected class enum let var final import";

        return WholeSequence;
    }

    private static String YellowKeyWords() {
        return "System out in String Integer Long Float Double ArrayList";
    }

    private static String BlueKeyWords() {
        return "cin cout length size add print println";
    }

    private static String RedKeywords() {
        return "dependency groupId artifactId version";
    }
}
