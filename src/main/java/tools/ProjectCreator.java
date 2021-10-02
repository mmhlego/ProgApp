package tools;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import model.Language;

public class ProjectCreator {

    public static void CreateProject(String path, Language language, String artifact, String version) {
        if (path.charAt(path.length() - 1) != '\\') {
            path += "\\";
        }

        String FullPath = path + artifact;

        if (language.equals(Language.Java)) {
            CopyAndExtractZipFile("java", FullPath);

            ChangeInsideFile(FullPath, "pom.xml", "{name}", artifact);
            ChangeInsideFile(FullPath, "pom.xml", "{version}", version);

            ChangeInsideFile(FullPath, ".classpath", "{name}", artifact);
            ChangeInsideFile(FullPath, ".classpath", "{version}", version);

            ChangeInsideFile(FullPath, ".project", "{name}", artifact);
            ChangeInsideFile(FullPath, ".project", "{version}", version);

        } else if (language.equals(Language.Cpp)) {
            CopyAndExtractZipFile("c++", FullPath);

        } else if (language.equals(Language.Python)) {
            CopyAndExtractZipFile("python", FullPath);

        } else if (language.equals(Language.React)) {
            try {
                Thread.sleep(100);
                Cmd.RunCommand("create-react-app " + artifact, path);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (language.equals(Language.HTML)) {
            CopyAndExtractZipFile("html", FullPath);
        }
    }

    public static void InitializeRepository(String path, String origin) {
        tools.Cmd.RunCommand("git init", path);
        tools.Cmd.RunCommand("git remote add origin " + origin, path);
    }

    public static void CopyAndExtractZipFile(String zipname, String path) {
        Cmd.RunCommand("copy \"" + zipname + ".zip\" \"" + path + "\"",
                System.getProperty("user.dir") + "/resources/templates/");

        Cmd.RunCommand("tar -xf \"" + zipname + ".zip\"", path);
        Cmd.RunCommand("del \"" + zipname + ".zip\"", path);
    }

    private static void ChangeInsideFile(String path, String fileName, String init, String newString) {
        try {
            File file = new File(path, fileName);
            String data = "";
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String current = s.nextLine();
                current = current.replace(init, newString);
                data += current + "\n";
            }
            s.close();

            PrintWriter writer = new PrintWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
