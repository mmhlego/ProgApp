package tools;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stage.AlertType;
import stage.Overlay;

public class Web {
    public static void OpenWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
                Overlay.ShowAlert("Browser not supported.", AlertType.Error);
            }
        }
    }

    public static void OpenWebpage(String url) {
        try {
            OpenWebpage(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static boolean IsUrlValid(String Url) {
        try {
            URL url = new URL(Url);
            url.toURI();
            return true;
        } catch (Exception e) {
            System.out.println("Invalid URL");
        }
        return false;
    }

    public static boolean IsGitRepository(String Url) {
        Pattern p = Pattern.compile("https?+://github.com/\\w{1,}/\\w{1,}.git");
        Matcher m = p.matcher(Url);
        return m.matches();
    }
}
