// ************************************************************************************************************************
// * Written by Christian Siewert aka Midgard ******************************************************************************
// ****************************************** .___  ___.  __   _______   _______      ___      .______    _______   *********
// ****************************************** |   \/   | |  | |       \ /  _____|    /   \     |   _  \  |       \  **********
// **** Danke an die Entwickler von GTK+ **** |  \  /  | |  | |  .--.  |  |  __     /  ^  \    |  |_)  | |  .--.  | ***********
// ****************************************** |  |\/|  | |  | |  |  |  |  | |_ |   /  /_\  \   |      /  |  |  |  | ************
// ****************************************** |  |  |  | |  | |  '--'  |  |__| |  /  _____  \  |  |\  \-.|  '--'  | ***********
// ****************************************** |__|  |__| |__| |_______/ \______| /__/     \__\ | _| `.__||_______/  **********
// ***** Skillen ist besser als chillen *************************************************************************************
// *************************************************************************************************************************

#include <stdlib.h>
#include <string.h>
#include <time.h>		// Für den Zufallsgenerator
#include <gtk/gtk.h>		// Den Gtk-Header implementieren. Ohne ihn läuft nichts ;-)

#define GUI "gui/tkm.xml"	// Das GUI wird mittels "gtk-builder-convert" von einer glade-Datei in einen XML-Text konvertiert. Hier wird der Pfad zu dieser XML-Datei angegeben.

typedef struct{			// Die Hauptstruktur für das Programm --> besteht aus...

        GtkWidget               *win_main;		// ... Hauptfenster
	GtkWidget		*cmd_start;		// ... Button um die Simulation zu starten
	GtkWidget		*cmd_pause;		// ... Button um die Simulation zu pausieren
	GtkWidget		*cmd_reset;		// ... Button um die Simulation zurückzusetzen
	GtkLabel		*lbl_time;		// ... Label für die Uhrzeit in der Simulation
	GtkRange		*scl_speed;		// ... "Speedometer" ;-)
	GtkWidget		*txt_ausgabe;		// ... Element für Textausgabe
	GtkLabel		*lbl_oelpreis;		// ... Label für den akt. Ölpreis
	GtkLabel		*lbl_einkaufspreis;	// ... Label für den akt. Einkaufspreis (Ölpreis / 158,98)
	GtkLabel		*lbl_asso_preis;	// ... Label für den Preis bei Asso
	GtkLabel		*lbl_asso_kunden;	// ... Label für die Anzahl der Kunden bei Asso
	GtkLabel		*lbl_asso_kunden_std;	// ... Label für die Anzahl der Kunden bei Asso in der letzten Stunde
	GtkLabel		*lbl_scholl_preis;	// ... Label für den Preis bei Scholl
	GtkLabel		*lbl_scholl_kunden;	// ... Label für die Anzahl der Kunden bei Asso
	GtkLabel		*lbl_gewinn_asso;	// ... Label für den Gewinn von Asso
	GtkLabel		*lbl_gewinn_scholl;	// ... Label für den Gewinn von Scholl
	GtkLabel		*lbl_speed;		// ... Label für die Geschwindkeitsanzeige

} Tankomat;			// Name für die Struktur

// --------------------------- Alle globalen Variablen -----------------------

gint interval = 1000;			// Timerinterval wird auf 1000 ms gesetzt
gint random_spotmarkt = 0;		// Eine Zufallszahl für den Spotmarkt
gint random_tanken = 0;			// Eine Zufallszahl für die Tankstellen
gint timer = 0;				// Der Timer für den Tankomat
gint tm_sek = 0;			// Sekunden
gint tm_min = 0;			// Minuten
gint tm_std = 0;			// Stunden
gint tm_tag = 1;			// Tag
gint rand_event = 0;			// Zufallszahl für Ereignisse
gint rand_tanken_wo = 0;		// Zufalls für die Tankstelle wo die Kunden tanken
gint kunden_asso = 0;
gint kunden_asso_std = 0;
gint kunden_scholl = 0;

gfloat akt_oelpreis = 67.85;		// Aktueller Ölpreis
gfloat event_preis_diff = 0;
gfloat akt_einkaufspreis = 0;		// Aktueller Einkaufspreis
gfloat einkaufspreis_asso = 0;
gfloat einkaufspreis_asso_puffer = 0;
gfloat einkaufspreis_scholl = 0;
const gfloat min_oel_steuer = 65;
const gfloat mwst = 19;
const gfloat oel_ebv = 0.46;
const gfloat marge_asso = 10;
const gfloat marge_scholl = 10;
gfloat benzin_preis_asso = 0;
gfloat benzin_preis_scholl = 0;
gfloat gewinn_asso = 0;
gfloat gewinn_scholl = 0;

gchar akt_zeit[100];			// Die aktuelle Uhrzeit der Simulation
gchar text_ausgabe[100];		// Ausgabe für das Textfeld
gchar akt_oelpreis_ausgabe[100];	// Ausgabe des aktuellen Ölpreises
gchar akt_einkaufspreis_ausgabe[100];	// Ausgabe des aktuellen Einkaufspreises
gchar event_ausgabe[100];
gchar benzin_preis_asso_ausgabe[100];
gchar benzin_preis_scholl_ausgabe[100];
gchar kunden_asso_ausgabe[100];
gchar kunden_asso_std_ausgabe[100];
gchar kunden_scholl_ausgabe[100];
gchar gewinn_asso_ausgabe[100];
gchar gewinn_scholl_ausgabe[100];

gboolean run_timer = FALSE;		// Läuft der Timer?
gboolean sim_pause = FALSE;		// Anwendung pausiert?
gboolean krieg_irak = FALSE;
gboolean krieg_iran = FALSE;
gboolean krise_irak = FALSE;
gboolean krise_iran = FALSE;
gboolean krise_ekuador = FALSE;
gboolean krise_falkland = FALSE;

GdkColor color = {0, 0xEEFF, 0xEBFF, 0xE6FF};	// Hintergrundfarbe für win_main

GtkTextBuffer *buffer;			// Für Ausgabe der Ereignisse

// --------------------------- Funktionsprototypen ---------------------------

int main (int argc, char *argv[]);					// Hauptfunktion

void on_win_main_destroy (GtkObject *object, Tankomat *program);	// Funktion für das Beenden des Programmes
void on_cmd_start_clicked (GtkObject *object, Tankomat *program);
void on_Timer_Func(Tankomat *program);					// Alles was innerhalb eines Intervals passieren soll...
void on_cmd_pause_clicked (GtkObject *object, Tankomat *program);
void on_cmd_reset_clicked(GtkObject *object, Tankomat *program);
void on_scl_speed_change_value (GtkObject *object, Tankomat *program);	// Funktion zum ändern des Intervals
void on_event_show_text (gchar *text);
void ov_event_show_oelpreis (Tankomat *program);
void show_time(Tankomat *program);
void on_event_transportkosten (gboolean steigen);
void on_event_krisen (gchar *gebiet, gboolean krise_aktiv, gboolean steigen);
void on_event_kriege (gchar *gebiet, gboolean krieg_aktiv, gboolean steigen);
void on_event_nachfrage (gchar *gebiet, gboolean steigen);
void on_event_opec(gboolean steigen);
void on_event_wechselkurs(gboolean steigen);
void calc_preis_asso(Tankomat *program);
void akt_kunden_asso(Tankomat *program);
void akt_kunden_scholl(Tankomat *program);
void calc_preis_scholl(Tankomat *program);
void calc_gewinn_asso(Tankomat *program);
void calc_gewinn_scholl(Tankomat *program);

gboolean build_app (Tankomat *program);					// Funktion zum Erstellen der Applikation

// --------------------------- Funktionen -----------------------------------

gboolean build_app (Tankomat *program){					// Funktion zum Erstellen der Applikation	

        GtkBuilder *builder;
	builder = gtk_builder_new ();
        gtk_builder_add_from_file (builder, GUI, NULL);

        program->win_main = GTK_WIDGET (gtk_builder_get_object (builder, "win_main"));
        program->cmd_start = GTK_WIDGET (gtk_builder_get_object (builder, "cmd_start"));
        program->cmd_pause = GTK_WIDGET (gtk_builder_get_object (builder, "cmd_pause"));
        program->cmd_reset = GTK_WIDGET (gtk_builder_get_object (builder, "cmd_reset"));
        program->lbl_time = GTK_LABEL (gtk_builder_get_object (builder, "lbl_time"));
        program->scl_speed = GTK_RANGE (gtk_builder_get_object (builder, "scl_speed"));
        program->txt_ausgabe = GTK_WIDGET (gtk_builder_get_object (builder, "txt_ausgabe"));
        program->lbl_oelpreis = GTK_LABEL (gtk_builder_get_object (builder, "lbl_oelpreis"));
        program->lbl_einkaufspreis = GTK_LABEL (gtk_builder_get_object (builder, "lbl_einkaufspreis"));
        program->lbl_asso_preis = GTK_LABEL (gtk_builder_get_object (builder, "lbl_asso_preis"));
        program->lbl_asso_kunden = GTK_LABEL (gtk_builder_get_object (builder, "lbl_asso_kunden"));
        program->lbl_asso_kunden_std = GTK_LABEL (gtk_builder_get_object (builder, "lbl_asso_kunden_std"));
        program->lbl_scholl_preis = GTK_LABEL (gtk_builder_get_object (builder, "lbl_scholl_preis"));	
        program->lbl_scholl_kunden = GTK_LABEL (gtk_builder_get_object (builder, "lbl_scholl_kunden"));
        program->lbl_gewinn_asso = GTK_LABEL (gtk_builder_get_object (builder, "lbl_gewinn_asso"));
        program->lbl_gewinn_scholl = GTK_LABEL (gtk_builder_get_object (builder, "lbl_gewinn_scholl"));
        program->lbl_speed = GTK_LABEL (gtk_builder_get_object (builder, "lbl_speed"));


	gtk_widget_modify_bg(program->win_main, GTK_STATE_NORMAL, &color);		// Hintergrundfarbe setzen
	buffer = gtk_text_view_get_buffer (GTK_TEXT_VIEW (program->txt_ausgabe));	// Den Buffer mit dem Textfeld verknüpfen

        gtk_builder_connect_signals (builder, program);
	g_object_unref (G_OBJECT (builder));

        return TRUE;
}

int main (int argc, char *argv[]) {			// *** Hauptfunktion
  
        Tankomat *program;				// Ableitung der Struktur erstellen
        program = g_slice_new (Tankomat);		// Speicher für die Struktur anfordern

	srand( time(NULL) );				// Zufallsgenerator initialisieren --> Liefert nur pseudo-Zufallszahlen --> sollte aber ausreichen

        gtk_init (&argc, &argv);			// GTK+ initialisieren

        if (build_app (program) == FALSE) return 1;	// War das Erstellen der Applikation erfolgreich?

        gtk_widget_show (program->win_main);		// Das Hauptfenster (win_main) anzeigen
 
        gtk_main ();					// Den Gtk-Event-Loop ausführen (Kehrt nicht mehr zurück ausser bei Terminierung der Applikation)

	if(run_timer == TRUE) gtk_timeout_remove(timer);// Den Timer entfernen

        g_slice_free (Tankomat, program);		// Die Speicherbereiche die belegt waren wieder freigeben
        
        return 0;
}

void on_win_main_destroy (GtkObject *object, Tankomat *program){	// *** Funktion für das Beenden des Programmes	

	gtk_main_quit();				// Hier wird die Funktion zum Beenden der Applikation aufgerufen

	return;
}

void on_cmd_start_clicked (GtkObject *object, Tankomat *program){	// *** Hier wird die Simulation gestartet

	timer = gtk_timeout_add (interval, (GtkFunction) on_Timer_Func, program);	//Starten des Timers --> Pro Interval wird einmal on_Timer_Func ausgeführt
	gtk_widget_set_sensitive(program->cmd_pause, TRUE);
	gtk_widget_set_sensitive(program->cmd_start, FALSE);

	show_time(program);	

	if(sim_pause == FALSE)	
		on_event_show_text("Simulation gestartet\t\t[OK]");
	else
		on_event_show_text("Simulation wird fortgesetzt\t[OK]");

	ov_event_show_oelpreis(program);

	if(sim_pause == FALSE){
		einkaufspreis_asso = akt_einkaufspreis;
		einkaufspreis_asso_puffer = akt_einkaufspreis;
		einkaufspreis_scholl = akt_einkaufspreis; }

	benzin_preis_asso = (einkaufspreis_asso + min_oel_steuer + ((akt_einkaufspreis + min_oel_steuer) / 100 * mwst) + marge_asso + oel_ebv) / 100;
	benzin_preis_scholl = (einkaufspreis_scholl + 5 + min_oel_steuer + ((akt_einkaufspreis + min_oel_steuer) / 100 * mwst) + marge_scholl + oel_ebv) / 100;

	sprintf(benzin_preis_asso_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_asso);
	sprintf(benzin_preis_scholl_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_scholl);

	gtk_label_set_markup(program->lbl_asso_preis, benzin_preis_asso_ausgabe);
	gtk_label_set_markup(program->lbl_scholl_preis, benzin_preis_scholl_ausgabe);

	sim_pause = FALSE;	
	run_timer = TRUE;

	return;
}

void on_Timer_Func(Tankomat *program){					// *** Pro Timerinterval wird diese Funktion ausgeführt

	tm_sek++;

	if(tm_sek == 60){
		tm_sek = 0;
		tm_min++; }
	if(tm_min == 60){
		if(tm_std % 6 == 0){						// Ölpreis beim Spotmarkt abfragen und dementsprechend reagieren
			einkaufspreis_asso_puffer = akt_einkaufspreis;
			einkaufspreis_scholl = akt_einkaufspreis; }
		tm_min = 0;
		tm_std++;
		calc_preis_asso(program);				// Preis für Asso berechnen
		calc_preis_scholl(program); }				// Preis für Scholl berechnen
	if(tm_std == 24){
		tm_std = 0;
		tm_tag++; }

	show_time(program);	

	rand_event = (rand () % 100) + 1;
	
	if(rand_event == 36){						// Mit 1 prozentiger Wahrscheinlichkeit wird ein Ereignis ausgelöst
		random_spotmarkt = (rand () % 1000) + 1;
		if(random_spotmarkt == 10) on_event_transportkosten(TRUE);
		else if(random_spotmarkt == 20) on_event_transportkosten(FALSE);			
		else if(random_spotmarkt == 30) on_event_krisen("Irak", krise_irak, TRUE);	
		else if(random_spotmarkt == 40) on_event_krisen("Iran", krise_iran, TRUE);	
		else if(random_spotmarkt == 50) on_event_krisen("Ekuador", krise_ekuador, TRUE);	
		else if(random_spotmarkt == 60) on_event_krisen("Falkland", krise_falkland, TRUE);
		else if(random_spotmarkt == 70) on_event_krisen("Irak", krise_irak, FALSE);	
		else if(random_spotmarkt == 80) on_event_krisen("Iran", krise_iran, FALSE);	
		else if(random_spotmarkt == 90) on_event_krisen("Ekuador", krise_ekuador, FALSE);	
		else if(random_spotmarkt == 100) on_event_krisen("Falkland", krise_falkland, FALSE);
		else if(random_spotmarkt == 110) on_event_kriege("Irak", krieg_irak, TRUE);
		else if(random_spotmarkt == 120) on_event_kriege("Iran", krieg_iran, TRUE);
		else if(random_spotmarkt == 130) on_event_kriege("Irak", krieg_irak, FALSE);
		else if(random_spotmarkt == 140) on_event_kriege("Iran", krieg_iran, FALSE);
		else if(random_spotmarkt == 150) on_event_nachfrage("China", TRUE);
		else if(random_spotmarkt == 160) on_event_nachfrage("Indien", TRUE);	
		else if(random_spotmarkt == 170) on_event_nachfrage("Brasilien", TRUE);
		else if(random_spotmarkt == 180) on_event_nachfrage("China", FALSE);
		else if(random_spotmarkt == 190) on_event_nachfrage("Indien", FALSE);	
		else if(random_spotmarkt == 200) on_event_nachfrage("Brasilien", FALSE);
		else if(random_spotmarkt == 210) on_event_opec(TRUE);
		else if(random_spotmarkt == 220) on_event_opec(FALSE);
		else if(random_spotmarkt == 230) on_event_wechselkurs(TRUE);
		else if(random_spotmarkt == 240) on_event_wechselkurs(FALSE);
		
		ov_event_show_oelpreis(program);	// Zeige den aktuellen Ölpreis an
	}
	
	random_tanken = (rand () % 60) + 1;

	if(random_tanken == 30){			// Mit einer Wahrscheinlichkeit von 1 / 60 tankt ein vorbeifahrendes Auto
		
		rand_tanken_wo = (rand () % 10) + 1;

		if((rand_tanken_wo >= 1) && (rand_tanken_wo <= 6)){	// 60% der Tankenden biegen zur preisgünstigeren Tankstelle ein

			if(benzin_preis_asso < benzin_preis_scholl){
				kunden_asso++;
				kunden_asso_std++;
				akt_kunden_asso(program);
				calc_gewinn_asso(program); }
			else if(benzin_preis_asso > benzin_preis_scholl){
				kunden_scholl++;
				akt_kunden_scholl(program); 
				calc_gewinn_scholl(program); }
		}

		if((rand_tanken_wo == 7) || (rand_tanken_wo == 8)){	// 20% der Tankenden biegen zu Asso ein

			kunden_asso++;
			kunden_asso_std++;
			akt_kunden_asso(program);
			calc_gewinn_asso(program);
		}

		if((rand_tanken_wo == 9) || (rand_tanken_wo == 10)){	// 20% der Tankenden biegen zu Scholl ein

			kunden_scholl++;
			akt_kunden_scholl(program);
			calc_gewinn_scholl(program);
		}
	}

	return;
}

void on_cmd_pause_clicked (GtkObject *object, Tankomat *program){	// *** Funktion für das Pausieren des Timers

	sim_pause = TRUE;
	gtk_timeout_remove(timer);
	gtk_widget_set_sensitive(program->cmd_pause, FALSE);
	gtk_widget_set_sensitive(program->cmd_start, TRUE);
	on_event_show_text("Simulation unterbrochen\t[OK]");
	run_timer = FALSE;

	return;
}

void on_cmd_reset_clicked(GtkObject *object, Tankomat *program){

	if(run_timer == TRUE) gtk_timeout_remove(timer);
	sim_pause = FALSE;
	run_timer = FALSE;
	gtk_widget_set_sensitive(program->cmd_pause, FALSE);
	gtk_widget_set_sensitive(program->cmd_start, TRUE);
	tm_sek = 0;			
	tm_min = 0;			
	tm_std = 0;			
	tm_tag = 1;
	kunden_asso = 0;
	kunden_asso_std = 0;
	kunden_scholl = 0;
	akt_oelpreis = 67.85;
	krieg_irak = FALSE;
	krieg_iran = FALSE;
	krise_irak = FALSE;
	krise_iran = FALSE;
	krise_ekuador = FALSE;
	krise_falkland = FALSE;
	benzin_preis_asso = 0;
	benzin_preis_scholl = 0;
	gewinn_asso = 0;
	gewinn_scholl = 0;
	gtk_text_buffer_set_text (buffer, "", -1);
	akt_kunden_asso(program);
	akt_kunden_scholl(program);
	calc_gewinn_asso(program);
	calc_gewinn_scholl(program);
	ov_event_show_oelpreis(program);
	sprintf(benzin_preis_asso_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_asso);
	sprintf(benzin_preis_scholl_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_scholl);
	gtk_label_set_markup(program->lbl_asso_preis, benzin_preis_asso_ausgabe);
	gtk_label_set_markup(program->lbl_scholl_preis, benzin_preis_scholl_ausgabe);
	show_time(program);
}

void on_scl_speed_change_value (GtkObject *object, Tankomat *program){	// *** Funktion zum ändern des Intervals

	gdouble akt_value;

	akt_value = gtk_range_get_value (program->scl_speed);		// aktuellen Wert des Schiebereglers auslesen

	if(akt_value == 1){
		gtk_label_set_markup(program->lbl_speed, "Echtzeit"); interval = 1000; }
	else if(akt_value == 2){
		gtk_label_set_markup(program->lbl_speed, "Schnell"); interval = 100; }
	else if(akt_value == 3){
		gtk_label_set_markup(program->lbl_speed, "Sehr schnell"); interval = 5; }
	else if(akt_value == 4){
		gtk_label_set_markup(program->lbl_speed, "Ultraschnell"); interval = 1; }
	
	if(run_timer == TRUE){						// alten Timer entfernen und neuen setzen aber nur wenn der Timer bereits läuft
		gtk_timeout_remove(timer);
		timer = gtk_timeout_add (interval, (GtkFunction) on_Timer_Func, program);
	}

	return;
}

void on_event_show_text (gchar *text){					// *** Funktion zum Ausgeben von Text

	sprintf (text_ausgabe, "[%.4d - %.2d:%.2d:%.2d] - %s\n",tm_tag, tm_std, tm_min, tm_sek, text);
	gtk_text_buffer_insert_at_cursor(buffer, text_ausgabe, -1);

	return;
}

void ov_event_show_oelpreis (Tankomat *program){

	sprintf (akt_oelpreis_ausgabe, "<big><big><span foreground=\"#ff0000\">Ölpreis: %3.2f Euro je Barrel</span></big></big>", akt_oelpreis);
	gtk_label_set_markup(program->lbl_oelpreis, akt_oelpreis_ausgabe);

	akt_einkaufspreis = (akt_oelpreis / 158.98) * 100;
	
	sprintf (akt_einkaufspreis_ausgabe, "<big><big><span foreground=\"#008312\">Einkaufspreis: %3.2f Cent je Liter</span></big></big>", akt_einkaufspreis);
	gtk_label_set_markup(program->lbl_einkaufspreis, akt_einkaufspreis_ausgabe);

	return;
}

void show_time(Tankomat *program){

	sprintf (akt_zeit, "<big><big><big><span foreground=\"#000000\">Uhrzeit: %.2d:%.2d:%.2d</span></big></big></big>",tm_std, tm_min, tm_sek);
	gtk_label_set_markup(program->lbl_time, akt_zeit);

	return;
}

void on_event_transportkosten (gboolean steigen){

	event_preis_diff = (rand () % 10) + 1;
	event_preis_diff = event_preis_diff / 10;

	if(steigen)	sprintf(event_ausgabe,"Transportkosten steigen um %1.1f Cent", event_preis_diff);
	else		sprintf(event_ausgabe,"Transportkosten sinken um %1.1f Cent", event_preis_diff);

	on_event_show_text(event_ausgabe);
	event_preis_diff = event_preis_diff / 100;

	if(steigen) 	akt_oelpreis = akt_oelpreis + event_preis_diff;
	else		akt_oelpreis = akt_oelpreis - event_preis_diff;		

	return;
}

void on_event_krisen (gchar *gebiet, gboolean krise_aktiv, gboolean steigen){

	if(steigen){ 

		event_preis_diff = (rand () % 100) + 1;	
		event_preis_diff = event_preis_diff / 10;

		if(krise_aktiv == FALSE){
			sprintf(event_ausgabe,"%s-Krise: Ölpreis steigt um %1.1f Cent",gebiet, event_preis_diff);
			if(strcmp(gebiet,"Irak")== 0)		krise_irak = TRUE;
			else if(strcmp(gebiet,"Iran") == 0)	krise_iran = TRUE;
			else if(strcmp(gebiet,"Ekuador") == 0)	krise_ekuador = TRUE;
			else if(strcmp(gebiet,"Falkland")== 0)	krise_falkland = TRUE;
		} 
		else 	sprintf(event_ausgabe,"%s-Krise verschärft sich: Ölpreis steigt um %1.1f Cent",gebiet, event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis + event_preis_diff;
	}

	else{

		event_preis_diff = (rand () % 700) + 1;	
		event_preis_diff = event_preis_diff / 10;

		if(krise_aktiv == TRUE){
			sprintf(event_ausgabe,"%s-Krise entspannt sich: Ölpreis fällt um %1.1f Cent",gebiet, event_preis_diff);
			if(strcmp(gebiet,"Irak")== 0)		krise_irak = FALSE;
			else if(strcmp(gebiet,"Iran") == 0)	krise_iran = FALSE;
			else if(strcmp(gebiet,"Ekuador") == 0)	krise_ekuador = FALSE;
			else if(strcmp(gebiet,"Falkland")== 0)	krise_falkland = FALSE;

			on_event_show_text(event_ausgabe);
			event_preis_diff = event_preis_diff / 100;
			akt_oelpreis = akt_oelpreis - event_preis_diff;
		}
	}	

	return;
}

void on_event_kriege (gchar *gebiet, gboolean krieg_aktiv, gboolean steigen){

	if(steigen){

		event_preis_diff = (rand () % 1000) + 1;
		event_preis_diff = event_preis_diff / 10;	

		if(krieg_aktiv == FALSE){	
	
			sprintf(event_ausgabe, "Krieg im %s. Ölpreis steigt um %1.1f Cent", gebiet, event_preis_diff);
			if(strcmp(gebiet,"Irak")== 0)		krieg_irak = TRUE;
			else if(strcmp(gebiet,"Iran") == 0)	krieg_iran = TRUE;
		}
		else 	sprintf(event_ausgabe, "Ölplattform explodiert im %s-Krieg. Ölpreis steigt um %1.1f Cent", gebiet, event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis + event_preis_diff;
	}
	
	else{

		event_preis_diff = (rand () % 1000) + 1;
		event_preis_diff = event_preis_diff / 10;	

		if(krieg_aktiv){
			sprintf(event_ausgabe,"%s-Krieg beendet. Ölpreis fällt um %1.1f Cent",gebiet, event_preis_diff);
			if(strcmp(gebiet,"Irak")== 0)		krieg_irak = FALSE;
			else if(strcmp(gebiet,"Iran") == 0)	krieg_iran = FALSE;

			on_event_show_text(event_ausgabe);
			event_preis_diff = event_preis_diff / 100;
			akt_oelpreis = akt_oelpreis - event_preis_diff;
		}
	}

	return;
}

void on_event_nachfrage (gchar *gebiet, gboolean steigen){

	event_preis_diff = (rand () % 100) + 1;	
	event_preis_diff = event_preis_diff / 10;

	if(steigen){ 

		sprintf(event_ausgabe,"Erhöhte Nachfrage aus %s: Ölpreis steigt um %1.1f Cent",gebiet, event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis + event_preis_diff;
	}

	else{
		sprintf(event_ausgabe,"Geringere Nachfrage aus %s: Ölpreis fällt um %1.1f Cent",gebiet, event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis - event_preis_diff;
	}

	return;
}

void on_event_opec(gboolean steigen){

	event_preis_diff = (rand () % 100) + 1;	
	event_preis_diff = event_preis_diff / 10;

	if(steigen){ 

		sprintf(event_ausgabe,"OPEC prognostiziert steigende Preise. Ölpreis steigt um %1.1f Cent", event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis + event_preis_diff;
	}

	else{
		sprintf(event_ausgabe,"OPEC prognostiziert sinkende Preise. Ölpreis fällt um %1.1f Cent", event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis - event_preis_diff;
	}

	return;
}

void on_event_wechselkurs(gboolean steigen){

	event_preis_diff = (rand () % 10) + 1;	
	event_preis_diff = event_preis_diff / 10;

	if(steigen){ 

		sprintf(event_ausgabe,"Dollarkurs schwächelt. Ölpreis fällt um %1.1f Cent", event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis - event_preis_diff;
	}

	else{
		sprintf(event_ausgabe,"Dollarkurs wird stärker. Ölpreis steigt um %1.1f Cent", event_preis_diff);

		on_event_show_text(event_ausgabe);
		event_preis_diff = event_preis_diff / 100;
		akt_oelpreis = akt_oelpreis + event_preis_diff;
	}

	return;
}

void calc_preis_asso(Tankomat *program){

	if(kunden_asso_std > 35)				// Bei mehr als 35 Kunden...
		einkaufspreis_asso = einkaufspreis_asso + 1;	
	else if(kunden_asso_std < 25)
		einkaufspreis_asso = einkaufspreis_asso - 1;	// Bei weniger als 25 Kunden...

	if(einkaufspreis_asso < einkaufspreis_asso_puffer)
		einkaufspreis_asso = einkaufspreis_asso_puffer;

	benzin_preis_asso = (einkaufspreis_asso + min_oel_steuer + ((akt_einkaufspreis + min_oel_steuer) / 100 * mwst) + marge_asso + oel_ebv) / 100;

	sprintf(benzin_preis_asso_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_asso);

	gtk_label_set_markup(program->lbl_asso_preis, benzin_preis_asso_ausgabe);

	kunden_asso_std = 0; 

	return;
}

void akt_kunden_asso(Tankomat *program){

	sprintf(kunden_asso_ausgabe, "Anzahl Kunden: %d", kunden_asso);
	sprintf(kunden_asso_std_ausgabe, "Anzahl Kunden letzte Stunde: %d", kunden_asso_std);

	gtk_label_set_markup(program->lbl_asso_kunden, kunden_asso_ausgabe);
	gtk_label_set_markup(program->lbl_asso_kunden_std, kunden_asso_std_ausgabe);

	return;
}

void akt_kunden_scholl(Tankomat *program){

	sprintf(kunden_scholl_ausgabe, "Anzahl Kunden: %d", kunden_scholl);
	gtk_label_set_markup(program->lbl_scholl_kunden, kunden_scholl_ausgabe);	

	return;	
}

void calc_preis_scholl(Tankomat *program){

	benzin_preis_scholl = (einkaufspreis_scholl + 5 + min_oel_steuer + ((akt_einkaufspreis + min_oel_steuer) / 100 * mwst) + marge_scholl + oel_ebv) / 100;	
	sprintf(benzin_preis_scholl_ausgabe, "Aktueller Preis: %1.2f Euro", benzin_preis_scholl);
	gtk_label_set_markup(program->lbl_scholl_preis, benzin_preis_scholl_ausgabe);

	return;
}

void calc_gewinn_asso(Tankomat *program){

	gewinn_asso = gewinn_asso + benzin_preis_asso;
	sprintf(gewinn_asso_ausgabe, "Gewinn: %.2f Euro", gewinn_asso);
	gtk_label_set_markup(program->lbl_gewinn_asso, gewinn_asso_ausgabe);

	return;
}

void calc_gewinn_scholl(Tankomat *program){

	gewinn_scholl = gewinn_scholl + benzin_preis_scholl;
	sprintf(gewinn_scholl_ausgabe, "Gewinn: %.2f Euro", gewinn_scholl);
	gtk_label_set_markup(program->lbl_gewinn_scholl, gewinn_scholl_ausgabe);

	return;
}
