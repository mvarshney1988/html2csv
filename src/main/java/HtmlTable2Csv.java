import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

public class HtmlTableToCsv {
    public static void main(String[] args) throws IOException {
       String op =  genCSV("src/main/resources/test.html", "jfc3", "class");
       System.out.println(op);
    }

    public static String genCSV(String path, String cssAttr, String selectorType) throws IOException {
        // Load the HTML file
        File f = new File(path);
        // Prepare the writer

        Document doc = Jsoup.parse(f, "UTF-8");

        // Get the tables
        doc.select("table["+selectorType+"=\""+cssAttr+"\"]").forEach(table -> {
try {
    String opFile = table.select("caption").text().replaceAll(" ", "");
    PrintWriter pw = new PrintWriter("src/main/resources/"+opFile+".csv");
    //  Element table = doc.select("table").first();

    // Get the rows
    Elements rows = table.select("tr");
    Element header = table.select("tr").get(0);


    header.children().forEach(th -> pw.write(th.text() + ","));
    pw.write("\n");
    int i = 0;
    // Iterate over the rows
    for (Element row : rows) {
        if (i > 0) {
            Elements cols = row.children();

            // Write the columns to the CSV
            for (Element col : cols) {
                pw.write(col.text() + ",");
            }
            pw.write("\n");
        }
        i++;
    }

    pw.close();
} catch (FileNotFoundException fileNotFoundException) {
    fileNotFoundException.printStackTrace();
}
        });
        return "files generated at src/main/resources";

    }

}
