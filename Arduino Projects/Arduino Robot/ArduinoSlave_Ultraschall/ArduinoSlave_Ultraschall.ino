
// PROBLEM: FALSCHE DATEN (KOMMA FALSCH) BEI DISTANZEN GROSSER ALS 100 cm)


#include <Wire.h> ;                                   // I2C
const uint8_t SLAVE_ADDRESS = 0x40;                   // Slave-Adresse
char data_char[6];                                    // Char Array, wo die Distanz gespeichert wird
int index = 0;
double cm;
const unsigned int TRIGGER = 8; 
const unsigned int ECHO = 9;

void setup() 
{
Serial.begin(9600);


                                            
  Wire.begin(SLAVE_ADDRESS);                            // I2C initialisieren als Slave
         
  Wire.onRequest(sendData);                             // Slave soll nur Daten senden, nicht empfangen 
}

void loop() 
{
double laenge;

pinMode(TRIGGER,OUTPUT);                   // Distanzmessung
digitalWrite(TRIGGER, LOW); 
delayMicroseconds(2); 
digitalWrite(TRIGGER, HIGH); 
delayMicroseconds(10); 
digitalWrite(TRIGGER, LOW); 

pinMode(ECHO, INPUT);
laenge = pulseIn(ECHO, HIGH); 

cm = microsecondsToCentimeters(laenge);    // KOnvertierung von Zeit in Länge

Serial.println(runden(cm));
String data_string = String(runden(cm));           // Strings zuweisen
String data_invalid = "2.00";

if (optimalAbstand(cm))                                                        //Hier wird entschieden, was geschickt werden sollte
  data_string.toCharArray(data_char, sizeof(data_string));
else if (cm < 2.00)
  data_invalid.toCharArray(data_char, sizeof(data_invalid));
else
{
  data_invalid = "400.0";
  data_invalid.toCharArray(data_char, sizeof(data_invalid));
}
 
delay(500);

} 

double runden (double wert)                                              // Runden-Funktion
{
wert *= 100;
wert = static_cast<int>(wert);
wert /= 100;
return wert;
}

double microsecondsToCentimeters(double microseconds)                     // Konvertierung-Funktion
{ 
return microseconds / 29 / 2;                                             // genauer: microseconds / 29.4117647058823529 / 2
}

bool optimalAbstand(double abstand)                                       // Gültigkeitsprüfung
{
if (abstand < 2.0 || abstand > 400)
return false;
else
return true;
}

void sendData()                                                           //Funktion, die aufgerufen wird, wenn RPi Daten haben will
{
    Wire.write(data_char[index]);                                         // schickt einzelne Characters
    ++index;
    if (index >= sizeof(data_char))                                          
    {
         index = 0;
    }
}


 


