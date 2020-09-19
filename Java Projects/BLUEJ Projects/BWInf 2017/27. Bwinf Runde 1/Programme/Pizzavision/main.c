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

#include <gtk/gtk.h>		// Den Gtk-Header implementieren. Ohne ihn läuft nichts ;-)


#define GUI "gui/pm.xml"	// Das GUI wird mittels "gtk-builder-convert" von einer glade-Datei in einen XML-Text konvertiert. Hier wird der Pfad zu dieser XML-Datei angegeben.


typedef struct{			// Die Hauptstruktur für das Programm --> besteht aus...

        GtkWidget               *win_main;	// ... Hauptfenster
	GtkWidget		*img_sauce;	// ... Bild für die Soße
	GtkWidget		*img_salami;	// ... Bild für die Salami
	GtkWidget		*img_tomaten;	// ... Bild für die Tomaten usw.
	GtkWidget		*img_ananas;	
	GtkWidget		*img_pilze;	
	GtkWidget		*img_peperoni;	
	GtkWidget		*img_zwiebeln;	
	GtkWidget		*img_paprika;
	GtkWidget		*img_fisch;
	GtkWidget		*img_gewuerze;
	GtkWidget		*img_schinken;
	GtkWidget		*img_oliven;

} PizzaMaker;	// Name für die Struktur

gboolean pm_sauce = FALSE;	// Soße sichtbar?
gboolean pm_salami = FALSE;	// Salami sichtbar?
gboolean pm_tomaten = FALSE;	// Tomaten sichtbar? usw.
gboolean pm_ananas = FALSE;	
gboolean pm_pilze = FALSE;
gboolean pm_peperoni = FALSE;
gboolean pm_zwiebeln = FALSE;
gboolean pm_paprika = FALSE;
gboolean pm_fisch = FALSE;
gboolean pm_gewuerze = FALSE;
gboolean pm_schinken = FALSE;
gboolean pm_oliven = FALSE;

// --------------------------- Funktionsprototypen ---------------------------

GdkColor color = {0, 0xEEFF, 0xEBFF, 0xE6FF};	// Hintergrundfarbe für GtkWindow

int main (int argc, char *argv[]);	// Hauptfunktion

void on_win_main_destroy (GtkObject *object, PizzaMaker *program);	// Funktion für das Beenden des Programms
void on_ckb_sauce_toggled(GtkObject *object, PizzaMaker *program);	// Klick auf CheckBox für die Soße (Übergebe das angeklickte Objekt und die Hauptstruktur)
void on_ckb_salami_toggled(GtkObject *object, PizzaMaker *program);	// Klick auf CheckBox für die Salami
void on_ckb_tomaten_toggled(GtkObject *object, PizzaMaker *program);	// Klick auf CheckBox für die Tomaten usw.
void on_ckb_ananas_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_pilze_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_peperoni_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_zwiebeln_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_paprika_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_thunfisch_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_gewuerze_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_schinken_toggled(GtkObject *object, PizzaMaker *program);
void on_ckb_oliven_toggled(GtkObject *object, PizzaMaker *program);

gboolean build_app (PizzaMaker *program);	// Funktion zum Erstellen der Applikation

// --------------------------- Funktionen -----------------------------------

gboolean build_app (PizzaMaker *program){	// Funktion zum Erstellen der Applikation	

        GtkBuilder *builder;
	builder = gtk_builder_new ();
        gtk_builder_add_from_file (builder, GUI, NULL);

        program->win_main = GTK_WIDGET (gtk_builder_get_object (builder, "win_main"));
	program->img_sauce = GTK_WIDGET (gtk_builder_get_object (builder, "img_sauce"));
	program->img_salami = GTK_WIDGET (gtk_builder_get_object (builder, "img_salami"));
	program->img_tomaten = GTK_WIDGET (gtk_builder_get_object (builder, "img_tomaten"));
	program->img_ananas = GTK_WIDGET (gtk_builder_get_object (builder, "img_ananas"));
	program->img_pilze = GTK_WIDGET (gtk_builder_get_object (builder, "img_pilze"));
	program->img_peperoni = GTK_WIDGET (gtk_builder_get_object (builder, "img_peperoni"));
	program->img_zwiebeln = GTK_WIDGET (gtk_builder_get_object (builder, "img_zwiebeln"));
	program->img_paprika = GTK_WIDGET (gtk_builder_get_object (builder, "img_paprika"));
	program->img_fisch = GTK_WIDGET (gtk_builder_get_object (builder, "img_fisch"));
	program->img_gewuerze = GTK_WIDGET (gtk_builder_get_object (builder, "img_gewuerze"));
	program->img_schinken = GTK_WIDGET (gtk_builder_get_object (builder, "img_schinken"));
	program->img_oliven = GTK_WIDGET (gtk_builder_get_object (builder, "img_oliven"));

	gtk_widget_modify_bg(program->win_main, GTK_STATE_NORMAL, &color);	// Hintergrundfarbe setzen

        gtk_builder_connect_signals (builder, program);
	g_object_unref (G_OBJECT (builder));

        return TRUE;
}

int main (int argc, char *argv[]) {			// *** Hauptfunktion
  
        PizzaMaker *program;				// Ableitung der Struktur erstellen
        program = g_slice_new (PizzaMaker);		// Speicher für die Struktur anfordern

        gtk_init (&argc, &argv);			// GTK+ initialisieren

        if (build_app (program) == FALSE) return 1;	// War das Erstellen der Applikation erfolgreich?

        gtk_widget_show (program->win_main);		// Das Hauptfenster (win_main) anzeigen
 
        gtk_main ();					// Den Gtk-Event-Loop ausführen (Kehrt nicht mehr zurück ausser bei Terminierung der Applikation)

        g_slice_free (PizzaMaker, program);		// Die Speicherbereiche die belegt waren wieder freigeben
        
        return 0;
}

void on_win_main_destroy (GtkObject *object, PizzaMaker *program){	// *** Funktion für das Beenden des Programms	

	gtk_main_quit();				// Hier wird die Funktion zum Beenden der Applikation aufgerufen
}

void on_ckb_sauce_toggled(GtkObject *object, PizzaMaker *program){	// Klick auf CheckBox für die Soße

	if(pm_sauce == FALSE) { 	gtk_widget_show (program->img_sauce); 	pm_sauce = TRUE;  }	// Wenn Bild nicht sichtbar dann zeige Bild an
	else 		      {		gtk_widget_hide (program->img_sauce); 	pm_sauce = FALSE; }	// ansonsten mache es unsichtbar
}

void on_ckb_salami_toggled(GtkObject *object, PizzaMaker *program){	// Klick auf CheckBox für die Salami

	if(pm_salami == FALSE) { 	gtk_widget_show (program->img_salami); 	pm_salami = TRUE;  }
	else 		       {	gtk_widget_hide (program->img_salami); 	pm_salami = FALSE; }

}

void on_ckb_tomaten_toggled(GtkObject *object, PizzaMaker *program){	// Klick auf CheckBox für die Tomaten usw.

	if(pm_tomaten == FALSE) { 	gtk_widget_show (program->img_tomaten); pm_tomaten = TRUE;  }
	else 		        {	gtk_widget_hide (program->img_tomaten); pm_tomaten = FALSE; }

}

void on_ckb_ananas_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_ananas == FALSE) { 	gtk_widget_show (program->img_ananas); pm_ananas = TRUE;  }
	else 		       {	gtk_widget_hide (program->img_ananas); pm_ananas = FALSE; }

}

void on_ckb_pilze_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_pilze == FALSE) { 	gtk_widget_show (program->img_pilze); pm_pilze = TRUE;  }
	else 		      {		gtk_widget_hide (program->img_pilze); pm_pilze = FALSE; }

}

void on_ckb_peperoni_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_peperoni == FALSE) { 	gtk_widget_show (program->img_peperoni); pm_peperoni = TRUE;  }
	else 		         {	gtk_widget_hide (program->img_peperoni); pm_peperoni = FALSE; }

}

void on_ckb_zwiebeln_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_zwiebeln == FALSE) { 	gtk_widget_show (program->img_zwiebeln); pm_zwiebeln = TRUE;  }
	else 		         {	gtk_widget_hide (program->img_zwiebeln); pm_zwiebeln = FALSE; }

}

void on_ckb_paprika_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_paprika == FALSE) { 	gtk_widget_show (program->img_paprika); pm_paprika = TRUE;  }
	else 		        {	gtk_widget_hide (program->img_paprika); pm_paprika = FALSE; }

}

void on_ckb_thunfisch_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_fisch == FALSE) { 	gtk_widget_show (program->img_fisch); pm_fisch = TRUE;  }
	else 		      {		gtk_widget_hide (program->img_fisch); pm_fisch = FALSE; }

}

void on_ckb_gewuerze_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_gewuerze == FALSE) { 	gtk_widget_show (program->img_gewuerze); pm_gewuerze = TRUE;  }
	else 		         {	gtk_widget_hide (program->img_gewuerze); pm_gewuerze = FALSE; }

}

void on_ckb_schinken_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_schinken == FALSE) { 	gtk_widget_show (program->img_schinken); pm_schinken = TRUE;  }
	else 		         {	gtk_widget_hide (program->img_schinken); pm_schinken = FALSE; }

}

void on_ckb_oliven_toggled(GtkObject *object, PizzaMaker *program){

	if(pm_oliven == FALSE) { 	gtk_widget_show (program->img_oliven); pm_oliven = TRUE;  }
	else 		         {	gtk_widget_hide (program->img_oliven); pm_oliven = FALSE; }

}

