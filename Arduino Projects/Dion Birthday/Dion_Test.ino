



int index = 0;



#include <Wire.h> ;                                   // I2C
const uint8_t SLAVE_ADDRESS = 0x40;                   // Slave-Adresse
char data_char[6];                                    // Char Array, wo die Distanz gespeichert wird

#include <Servo.h>
Servo servo;

// TASTE PIN
const int tastePin = 2;
// LED PIN
const int ledPin = 3;
// Ende PIN
const int endePin = 4;



boolean tasteWurdeGedrueckt = false;
boolean endeWurdeGedrueckt = false;
void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);       
servo.attach(5);
pinMode(tastePin, INPUT);
pinMode(endePin, INPUT);
pinMode(ledPin, OUTPUT);
                        
  Wire.begin(SLAVE_ADDRESS);                            // I2C initialisieren als Slave
  Wire.onRequest(sendData);                             // Slave soll nur Daten senden, nicht empfangen 

}

boolean servoDrehen = false;
String data_string;

void loop() {

  
  tasteWurdeGedrueckt = digitalRead(tastePin) == HIGH;
endeWurdeGedrueckt = digitalRead(endePin) == HIGH;

 
 if (tasteWurdeGedrueckt)
 {
  data_string = "0";
  data_string.toCharArray(data_char, sizeof(data_string));
  servoDrehen = true;
  digitalWrite(ledPin, HIGH);
   delay(500);
 }
 
 if (endeWurdeGedrueckt)
 {
  data_string = "1";
  data_string.toCharArray(data_char, sizeof(data_string));
  servoDrehen = false;
  digitalWrite(ledPin, LOW);
  delay(500);
 }
 
}

void sendData()                                                           //Funktion, die aufgerufen wird, wenn RPi Daten haben will
{
    Wire.write(data_char[index]);            
    ++index;
    if (index >= sizeof(data_char))                                          
    {
         index = 0;
    }// schickt einzelne Characters
}
