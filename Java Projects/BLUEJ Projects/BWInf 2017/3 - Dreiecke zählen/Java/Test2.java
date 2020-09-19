import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.StringWriter;
/**
 * Beschreiben Sie hier die Klasse Test2.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Test2
{
    public static void main()
    {
        try
        {
            File file = new File("C:/Users/Genc Ahmeti/Google Drive/SCHULE (Unterstufe, Mittelstufe, Oberstufe)/KURSSTUFE/ABI-2018/Klausurenliste/b.pdf");
            PDDocument doc = PDDocument.load(file);

            StringWriter stringWriter = new StringWriter();

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setLineSeparator("\n");
            stripper.writeText(doc, stringWriter);

            String text = stringWriter.toString();

            stringWriter.close();
            doc.close();
            System.out.println(text); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
