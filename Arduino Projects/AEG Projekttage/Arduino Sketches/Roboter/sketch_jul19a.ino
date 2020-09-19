/******************************* VORBEREITUNG ANFANG *******************************************/

// Arduino Pins definieren und initialisieren

const int TRIGGER = 11;
const int echo = 12;
const int rechtsVorwaerts = 2;
const int rechtsRueckwaerts = 3;
const int linksVorwaerts = 5;
const int linksRueckwaerts = 4;
const int linksMotorStaerke = 9;
const int rechtsMotorStaerke = 10;

// Variablen für Ultraschallsensor definieren und initialisieren

int dauer = 0;
int distanz = 0;

// Arduino Pins genauer festlegen (OUTPUT- oder INPUT-Pins)

void setup() 
{
  pinMode(TRIGGER , OUTPUT);
  pinMode(echo , INPUT);
  pinMode(rechtsVorwaerts , OUTPUT);
  pinMode(rechtsRueckwaerts , OUTPUT);
  pinMode(linksVorwaerts , OUTPUT);
  pinMode(linksRueckwaerts , OUTPUT);
  pinMode(linksMotorStaerke , OUTPUT);
  pinMode(rechtsMotorStaerke , OUTPUT);
  Serial.begin(9600);

}

/******************************* VORBEREITUNG ENDE *******************************************/


/******************************* PROGRAMM ANFANG *******************************************/

// Code, der ständig wiederholt wird (-> loop)

void loop()
{
  distanz = distanzMessen();
  
/******************************* PROGRAMMIERBARER TEIL ANFANG *******************************************/
  
// Ist das Objekt vor dem Sensor mehr als 20 cm entfernt, fährt der Roboter nach vorne
        analogWrite(linksMotorStaerke, 255);
        analogWrite(rechtsMotorStaerke, 255);
    if (distanz >= 20 || distanz <=0)
    {
    digitalWrite(rechtsVorwaerts , HIGH);
    digitalWrite(rechtsRueckwaerts , LOW);
    digitalWrite(linksVorwaerts , HIGH);
    digitalWrite(linksRueckwaerts , LOW);    
    }

// Sonst dreht er sich gegen den Uhrzeigersinn
    
    else
    {
        analogWrite(linksMotorStaerke, 95);
        analogWrite(rechtsMotorStaerke, 95);
        digitalWrite(rechtsVorwaerts , HIGH);
        digitalWrite(rechtsRueckwaerts , LOW);
        digitalWrite(linksVorwaerts , LOW);
        digitalWrite(linksRueckwaerts , HIGH);
        delay(100);
    }

/******************************* PROGRAMMIERBARER TEIL ENDE *******************************************/

}

int distanzMessen()
{
  int distanz;

// Ultraschallsensor schickt Signal
  digitalWrite(TRIGGER, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER , HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER , LOW);

// Ultraschallsensor empfängt Signal wieder + Distanz wird berechnet
  
  dauer = pulseIn(echo , HIGH);
  distanz = (dauer/2) / 28.5 ;
  Serial.println(distanz);
  delay(100);
  return distanz;
}

/******************************* PROGRAMM ENDE *******************************************/
