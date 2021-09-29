package tools;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import stage.AlertType;
import stage.Overlay;

public class Web {
    public static void openWebpage(URI uri) {
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

    public static void openWebpage(String url) {
        try {
            openWebpage(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
