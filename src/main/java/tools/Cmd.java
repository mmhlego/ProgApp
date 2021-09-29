package tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Cmd {
    public static String RunCommand(String command) {
        String ans = "";

        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c " + command);

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                ans += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
