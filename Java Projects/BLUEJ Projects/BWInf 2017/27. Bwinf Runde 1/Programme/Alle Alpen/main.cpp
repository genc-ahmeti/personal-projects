// ************************ Bibliotheken die implementiert werden müssen *******

#include <GL/glut.h>    										// GLUT Bibliothek
#include <GL/gl.h>											// OpenGL32 Bibliothek
#include <GL/glu.h>											// GLu32 Bibliothek
#include <unistd.h>    											// Header sleeping
#include <stdio.h>											// Rudimentäre Dateioperationen, Standard Ein -und ausgabe
#include <stdlib.h>											// Wichtig für malloc(), etc.
#include <string.h>											// String-Implementation
#include <sys/time.h>											// Zeitrechnung (Relevant für FPS)
#include <string>

// ************************ Funktionsprototypen (Void) *************************

void ReSizeGLScene(int Width, int Height);								// Wird aufgerufen wenn die Fenstergröße verändert wird
void InitGL(int Width, int Height);	      								// Wird aufgerufen sobald ein OpenGL Fenster erstellt wurde
void DrawGLScene();											// Haupt Zeichnen-Funktion 
void draw_mountains_100();
void keyPressed(unsigned char key, int x, int y); 							// Wird aufgerufen wenn eine Taste gedrückt wird
void func_key(int key, int x, int y);									// Wird aufgerufen wenn eine Funktionstaste gedrückt wird
void textausgabe (float x, float y, const char *text, void *font);					// Ausgeben von Text auf Monitor
void idle();
void DrawGLSceneSub();
void subReshape (int w, int h);
void cfps();
void zeichne_ks(int laenge,int hoehe, float pos_x, float pos_y);
void zeichne_gebirgszug(int g_punkt[6]);

// ************************ Integer Variablen **********************************

int window;												// Anzahl der Fenster	
int subwindow;												// Anzahl der SubFenster
int res_x = 1400;											// Auflösung - X
int res_y = 800;											// Auflösung - Y
int next_point = 0;											// Nächster Wert für draw_mountains_100
int akt_hoehe[100];
int akt_x = 0;
int rand_next_point = 0;
int g_punkt[7];
int i[5];												// Schleifenvariable für Ausgabe aller G_zuege der Länge 6
int ks_y = 0;

// ************************ Float Variablen ************************************

float beschriftung_x = 0;										// Beschriftung für KS
float beschriftung_y = 0;										// Beschriftung für KS
float x_offset = 0;
float y_offset = 0;

// ************************ Char Variablen *************************************

char strFrameRate[4]   	= {0};										// Speicher für Framerate
char strBeschriftung_x[4] = {0};
char strBeschriftung_y[4] = {0};

// ************************ Boolsche Variablen *********************************

bool draw_100 = false;
bool draw_all_6 = false;
bool hundert_gezeichnet = false;
bool zeichne_zug = true;

// ************************ Funktionen (Void) **********************************

int main(int argc, char **argv){ 									// Hauptfunktion

	srand( time(NULL) );										// Starte den Zufallszahlengenerator	

	glutInit(&argc, argv);  									// Initialisiert GLUT
	
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);							// Typ des Display-Modus auswählen: RGB-Farbmodell und Doulebuffer

	glutInitWindowSize(res_x, res_y);  								// Ein fenster mit 1400 * 800 Pixel

	glutInitWindowPosition(100, 100);								// Das fenster mit abstand von 100*100 (oben, links) anzeigen

	window = glutCreateWindow("Alle Alpen");							// Ein fenster öffnen

		glutDisplayFunc(DrawGLScene);								// DrawGLScene registrieren		

		glutIdleFunc(idle);									// Solange nichts passiert, führe "DrawGLScene" aus

		glutReshapeFunc(ReSizeGLScene);								// ReSizeGLScene registrieren

	 	glutKeyboardFunc(keyPressed);								// KeyPressed registrieren
  
	 	glutSpecialFunc(func_key);								// func_key registrieren
    
	 	InitGL(res_x, res_y);									// Das Fenster initialisieren

	subwindow = glutCreateSubWindow(window,5,5,res_x-10,res_y / 5);					// Ein SubFenster öffnen

		glutDisplayFunc(DrawGLSceneSub);							// Haupt-Zeichnen (SubWindow) Funktion registrieren	

		glutReshapeFunc(subReshape);

	glutMainLoop();											// Den Event-Loop aktivieren 

	return 0;
}

void ReSizeGLScene(int Width, int Height){								// Wird aufgerufen wenn die Fenstergröße verändert wird

	if (Height==0)											// Verhindere eine Division durch 0 weil das Fenster zu klein ist
		Height=1;

	glViewport(0, 0, Width, Height);								// Setze Blickwinkel und perspektivische Transformation zurück

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(45.0f,(GLfloat)Width/(GLfloat)Height,0.1f,100.0f);
	glMatrixMode(GL_MODELVIEW);
}

void InitGL(int Width, int Height){	      								// Wird aufgerufen sobald ein OpenGL Fenster erstellt wurde

	glEnable(GL_TEXTURE_2D);									// Aktiviere Texturierung
	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);								// Lösche den Hintergrund zur farbe schwarz
  			
	glClearDepth(1.0);										// Aktiviert löschung des tiefen-puffers

	glMatrixMode(GL_PROJECTION);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); 

  	gluPerspective(0.0f,(GLfloat)Width/(GLfloat)Height,0.1f,100.0f);				// Seitenverhältniss berechnen
  	glMatrixMode(GL_MODELVIEW);

	glColor3f(1.0f,1.0f,1.0f);
}

void DrawGLScene(){											// Haupt Zeichnen-Funktion 

	glClear(GL_COLOR_BUFFER_BIT);									// Bildschirm löschen
  	glLoadIdentity();										// Sicht resetten

	glTranslatef(0.0f,-27.0f,-75.0f);

	y_offset = 0;
	x_offset = 0;
	ks_y = 0;

	if(draw_100 == true){										// Zeichne Gebirgskette der Länge 100

	// *** Zeichne das Koordinatensystem inkl. Beschriftung ***

		beschriftung_x = -50;
		beschriftung_y = 0;

		glColor3f(1.0f,1.0f,1.0f);
		textausgabe(beschriftung_x-1.5, beschriftung_y-1.5, "0", GLUT_BITMAP_HELVETICA_12);

		glColor3f(0.035f,0.14f,0.047f);		

		glBegin(GL_QUADS);									// Zeichne "Backframe"
			glVertex2i(-50,0);
			glVertex2i(50,0);
			glVertex2i(50,40);
			glVertex2i(-50,40);
		glEnd();
	
		for(int x = -50; x <= 50; x++){								// Zeichne Karos
				glColor3f(0.4f,0.4f,0.4f);
				glBegin(GL_LINES);
					glVertex2i(x,0);
					glVertex2i(x,40);
				glEnd();
				
				beschriftung_x++;
				if((int) beschriftung_x % 10 == 0){					// Zeichne Beschriftung
					glColor3f(1.0f,1.0f,1.0f);
					sprintf(strBeschriftung_x,"%2.f",(beschriftung_x+50));
					textausgabe(beschriftung_x-0.6, beschriftung_y-1.5, strBeschriftung_x, GLUT_BITMAP_HELVETICA_12);	
				}				
		}

		beschriftung_x = -51.5;

		for(int y = 0; y <= 40; y++){								// Zeichne Karos

				glColor3f(0.4f,0.4f,0.4f);
				glBegin(GL_LINES);
					glVertex2i(-50,y);
					glVertex2i(50,y);
				glEnd();

				beschriftung_y++;
				if((int) beschriftung_y % 10 == 0){					// zeichne Beschriftung
					glColor3f(1.0f,1.0f,1.0f);
					sprintf(strBeschriftung_y,"%2.f", beschriftung_y);
					textausgabe(beschriftung_x-0.8, beschriftung_y-0.3, strBeschriftung_y, GLUT_BITMAP_HELVETICA_12);
				}
		}

	// *** Koordinatensystem komplett ***
	
	draw_mountains_100();

	}

	if(draw_all_6 == true){	// Zeichne alle g_zuege der Länge 6

		g_punkt[0] = 0;
		g_punkt[6] = 0;

		for(i[0] = 0; i[0] <= 1; i[0]++)
		for(i[1] = 0; i[1] <= 2; i[1]++)			
		for(i[2] = 0; i[2] <= 3; i[2]++)
		for(i[3] = 0; i[3] <= 2; i[3]++){
		for(i[4] = 0; i[4] <= 1; i[4]++){

			for(int x = 1; x <= 5; x++)
				g_punkt[x] = i[x-1];

			for(int x = 1; x <= 5; x++) if((g_punkt[x+1] - g_punkt[x] > 1) || (g_punkt[x+1] - g_punkt[x] < -1)) zeichne_zug = false;

			if(zeichne_zug == true){					
				zeichne_ks(6,3,-47.0f + x_offset,10.0f - y_offset);
				ks_y++;
				zeichne_gebirgszug(g_punkt);
				y_offset = y_offset + 5.0f;
				if(ks_y == 8){ x_offset = x_offset + 10.0f; y_offset = 0; ks_y = 0; }
			}

				zeichne_zug = true;
		}
		}
	}

    	cfps();			// Zeige Frames pro Sekunde
	glutSwapBuffers();	// Vertausche Vorder -und Hintergrund-Puffer (DoubleBuffer)
}

void draw_mountains_100(){

	glColor3f(1.0f,1.0f,1.0f);

	next_point = 0;
	akt_x = 0;

	if(hundert_gezeichnet == false){
	
			for(int i = -49; i <= 50; i++){

				rand_next_point = (rand () % 3) + 1;

				if(rand_next_point  == 1){
					next_point = next_point - 1;
					if(next_point < 0) next_point = 0;
				}

				else if(rand_next_point == 2)
					next_point  = next_point + 1;

				else ;	

				while(next_point >= (100 - akt_x))
					next_point--;					

				akt_x++;
				akt_hoehe[i+49] = next_point;
			}	

		akt_hoehe[99] = 0;

		hundert_gezeichnet = true;
	}

	else{
		glBegin(GL_LINE_LOOP);
	
			glVertex2i(-50,0);
			for(int i = -49; i <= 50; i++) glVertex2i(i,akt_hoehe[i+49]);

		glEnd();
	}
}

void keyPressed(unsigned char key, int x, int y){    							// Wird aufgerufen wenn eine Taste gedrückt wird

  	switch(key){
	
		case  27: { glutDestroyWindow(window); close(0); break; }
		case 110: { hundert_gezeichnet = false; break; }
 	}
}		 


void func_key(int key, int x, int y){									// Wird aufgerufen wenn eine Funktionstaste gedrückt wird
		
	switch(key){

		case GLUT_KEY_F1: {									// Beim Druck auf F1 --> Fullscreen (funktioniert nicht)
			if(draw_100 == false) { draw_all_6 = false; draw_100 = true; }
			else draw_100 = false;
			break; }
		case GLUT_KEY_F2: {
			draw_100 = false;
			if(draw_all_6 == false) { draw_100 = false; draw_all_6 = true; }
			else draw_all_6 = false;
			break; }
	};
}

void textausgabe(float x, float y, const char *text, void *font){

	int laenge;
	
	glRasterPos3f(x,y,0.0f);

	laenge = (int) strlen(text);

	for(int i = 0; i < laenge; i++)
		glutBitmapCharacter(font, text[i]);
}

void idle(){

	glutSetWindow (window);
	glutPostRedisplay ();

	glutSetWindow (subwindow);
	glutPostRedisplay ();
}

void DrawGLSceneSub(){											// Haupt - Zeichnen Funktionen für Sub-Window

	glutSetWindow (subwindow);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	glTranslatef(-1.0f,-1.0f,0.0f);

	glColor3f(1.0f, 0.5f, 0.2f);
	textausgabe(0.91f,1.5f, "Alle Alpen",GLUT_BITMAP_HELVETICA_18);

	glColor3f(0.21f, 0.75f, 1.0f);
	textausgabe(0.86f, 1.0f, "Christian Siewert", GLUT_BITMAP_HELVETICA_18); 

	if(draw_100 == false) glColor3f(1.0f, 1.0f, 1.0f);
	else{ 
		glColor3f(0.0f, 1.0f, 0.0f);
		textausgabe(1.72f,1.5f,"n   -   Nochmal zeichnen", GLUT_BITMAP_HELVETICA_12);
		glColor3f(0.0f, 1.0f, 0.0f);
	}
	
	textausgabe(0.07f, 1.5f, "F1    -   Zeichne Gebirgskette der Laenge 100", GLUT_BITMAP_HELVETICA_12);

	if(draw_all_6 == false) glColor3f(1.0f, 1.0f, 1.0f);
	else glColor3f(0.0f,1.0f,0.0f);

	textausgabe(0.07f, 1.0f, "F2    -   Zeige alle Gebirgsketten der Laenge 6", GLUT_BITMAP_HELVETICA_12);	

	glColor3f(1.0f, 1.0f, 1.0f);

	textausgabe(0.07f, 0.5f, "ESC  -   Programm beenden", GLUT_BITMAP_HELVETICA_12);

	glColor3f(1.0f, 0.81f, 0.06f);
	textausgabe(0.9f, 0.5f, "FPS:", GLUT_BITMAP_HELVETICA_18);
	textausgabe(1.0f, 0.5f, strFrameRate, GLUT_BITMAP_HELVETICA_18);	

	glutSwapBuffers ();
}

void subReshape (int w, int h){									// Reshape falls Fenstergröße von  verändert wird

	glViewport (0, 0, w, h);
	glMatrixMode (GL_PROJECTION);
	glLoadIdentity ();
	gluOrtho2D (0.0F, 1.0F, 0.0F, 1.0F);
}

void cfps(){											// Berechnet die FPS

    static float framesPerSecond    = 0.0f;        						// Speichere die FPS
    static long lastTime            = 0;           						// Zeit nach dem letzten Frame
    
    struct timeval currentTime;									// Struktur für Zeitberechnung
	currentTime.tv_sec  = 0;
	currentTime.tv_usec = 0; 

    gettimeofday(&currentTime, NULL);	  							// Millisekunden seit Programmstart  

    ++framesPerSecond;										// Inkrementiere Frame - Counter

    if( currentTime.tv_sec - lastTime >= 1 ){							// Wenn eine Sekunde vergangen ist...
        lastTime = currentTime.tv_sec;

        sprintf(strFrameRate, "%d", int(framesPerSecond));					// Kopiere FPS nach strFrameRate

	framesPerSecond = 0;									// Setze FPS zurück

    }
}

void zeichne_ks(int laenge,int hoehe, float pos_x, float pos_y){

  	glLoadIdentity();
	glTranslatef(pos_x,pos_y,-75.0f);

	glColor3f(0.035f,0.14f,0.047f);		

	glBegin(GL_QUADS);									// Zeichne "Backframe"
		glVertex2i(-laenge / 2 ,0);
		glVertex2i(laenge / 2,0);
		glVertex2i(laenge / 2, hoehe);
		glVertex2i(-laenge / 2, hoehe);
	glEnd();
	
		for(int x = -(laenge / 2); x <= (laenge / 2); x++){				// Zeichne Karos
				glColor3f(0.4f,0.4f,0.4f);
				glBegin(GL_LINES);
					glVertex2i(x,0);
					glVertex2i(x,hoehe);
				glEnd();
		}

		for(int y = 0; y <= hoehe; y++){						// Zeichne Karos

				glColor3f(0.4f,0.4f,0.4f);
				glBegin(GL_LINES);
					glVertex2i(-(laenge / 2),y);
					glVertex2i(laenge / 2,y);
				glEnd();
		}
}

void zeichne_gebirgszug(int g_punkt[7]){

	glLoadIdentity();
	glTranslatef(-50.0f + x_offset,10.0f - y_offset,-75.0f);

	glColor3f(1.0f,1.0f,1.0f);

	glBegin(GL_LINE_LOOP);
		glVertex2i(0,g_punkt[0]);
		for(int i = 0; i <= 6; i++) glVertex2i(i,g_punkt[i]);
	glEnd();		
}
