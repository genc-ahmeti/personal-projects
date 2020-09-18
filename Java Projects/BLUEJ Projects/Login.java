import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class Login
{
    public static void main()throws IOException
    {
        /*
         * 
        String url = "http://www.aeg.rt.bw.schule.de/aktuelles/vertretungsplan/v-plan/ks1/";

        Connection.Response loginForm = Jsoup
        .connect(url)
        .method(Connection.Method.GET)
        .execute();

        Connection.Response response = Jsoup.connect(url)
        .data("uName", "vplan")
        .data("uPassword", "v-planAEG1718")
        .data("rcID", "1177")
        .data("submit", "Anmelden >")
        .cookies(loginForm.cookies())
        .method(Connection.Method.POST)
        .execute();

        String body = response.body();
        System.out.println(body);
         */
        String url = "https://www.aeg-reutlingen.de/moodle2/login/index.php";

        Connection.Response loginForm = Jsoup
            .connect(url)
            .method(Connection.Method.GET)
            .execute();
        try
        {
            Connection.Response response = Jsoup.connect(url)
                .data("username", "ahmetige")
                .data("password", "Rbapr")
                .data("anchor", "")
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

            String body = response.body();
            System.out.println(body);
            
                    response = Jsoup.connect(url)
                .data("username", "ahmetig")
                .data("password", "Rbapr")
                .data("anchor", "")
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

            body = response.body();
            System.out.println("\n\n\n\n\n\n" + body);
        }
        catch(Exception e)
        {}

    }
}
