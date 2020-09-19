package app.com.example.genc_ahmeti.aeg_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VertretungSeite extends AppCompatActivity {

    private Button btnAktualisieren;
    private static TextView tvHeuteStunde[];
    private static TextView tvMorgenStunde[];
    private static String[] tvHeuteStundeStrCopy;
    private static String[] tvMorgenStundeStrCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertretung_seite);

     /*   stundenplanGehen = (Button) findViewById(R.id.bt_go_back);
        stundenplanGehen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VertretungSeite.this, StundenplanSeite.class));
            }});
*/
        tvHeuteStunde = new TextView[11];
        tvMorgenStunde = new TextView[11];
        tvHeuteStundeStrCopy = new String[11];
        tvMorgenStundeStrCopy = new String[11];

        tvSetText(0, R.id.textHeuteStunde1, R.id.textMorgenStunde1);
        tvSetText(1, R.id.textHeuteStunde2, R.id.textMorgenStunde2);
        tvSetText(2, R.id.textHeuteStunde3, R.id.textMorgenStunde3);
        tvSetText(3, R.id.textHeuteStunde4, R.id.textMorgenStunde4);
        tvSetText(4, R.id.textHeuteStunde5, R.id.textMorgenStunde5);
        tvSetText(5, R.id.textHeuteStunde6, R.id.textMorgenStunde6);
        tvSetText(6, R.id.textHeuteStunde7, R.id.textMorgenStunde7);
        tvSetText(7, R.id.textHeuteStunde8, R.id.textMorgenStunde8);
        tvSetText(8, R.id.textHeuteStunde9, R.id.textMorgenStunde9);
        tvSetText(9, R.id.textHeuteStunde10, R.id.textMorgenStunde10);
        tvSetText(10, R.id.textHeuteStunde11, R.id.textMorgenStunde11);

        btnAktualisieren = new Button(VertretungSeite.this);
        btnAktualisieren = (Button) findViewById(R.id.btnAktualisieren);
        btnAktualisieren.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(VertretungCreator.internetverbindungIstAktiv(VertretungSeite.this))
                    new VertretungExtrahieren(VertretungSeite.this).execute();
            }
        });
    }

    public static TextView getTvVertretung(int morgenOderHeute, int stunde) {
        TextView tv = null;
        switch (morgenOderHeute) {
            case 0:
                tv = tvHeuteStunde[stunde];
                break;
            case 1:
                tv = tvMorgenStunde[stunde];
                break;
        }
        return tv;
    }

    public static String getTvVertretungStrCopy(int morgenOderHeute, int stunde) {
        String str = null;
        switch (morgenOderHeute) {
            case 0:
                str = tvHeuteStundeStrCopy[stunde];
                break;
            case 1:
                str = tvMorgenStundeStrCopy[stunde];
                break;
        }
        return str;
    }

    public boolean istLeereStunde(String str)
    {
        return (str.equals("") ||
                str.equals("") ||
                str.equals("") ||
                str.equals("") ||
                str.equals(""));
    }

    public void tvSetText(int stunde, int id1, int id2) {
        String temp;

        tvHeuteStunde[stunde] = (TextView) findViewById(id1);
        temp = StundenplanSeite.getStundenplan(StundenplanSeite.getWochentag(0) - 1, stunde);
        if(!istLeereStunde(temp))
            tvHeuteStunde[stunde].setText(temp);
        else
            tvHeuteStunde[stunde].setText("");
        tvHeuteStundeStrCopy[stunde] = tvHeuteStunde[stunde].getText().toString();

        tvMorgenStunde[stunde] = (TextView) findViewById(id2);
        temp = StundenplanSeite.getStundenplan(StundenplanSeite.getWochentag(1) - 1, stunde);
        if(!istLeereStunde(temp))
            tvMorgenStunde[stunde].setText(temp);
        else
            tvMorgenStunde[stunde].setText("");
        tvMorgenStundeStrCopy[stunde] = tvMorgenStunde[stunde].getText().toString();
    }
}
