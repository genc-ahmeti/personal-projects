/* ULTRASCHALL PINS
 *  
 * const int TRIGGER = 11; 
 * const int echo = 12;
 * 
*/

const int rechtsVorwaerts = 2;
const int rechtsRueckwaerts = 3;
const int linksVorwaerts = 4;
const int linksRueckwaerts = 5;
const int linksMotorStaerke = 9;
const int rechtsMotorStaerke = 10;

char data = 0;            

void setup() 
{
 /* pinMode(TRIGGER , OUTPUT);
  pinMode(echo , INPUT);*/
  pinMode(rechtsVorwaerts , OUTPUT);
  pinMode(rechtsRueckwaerts , OUTPUT);
  pinMode(linksVorwaerts , OUTPUT);
  pinMode(linksRueckwaerts , OUTPUT);
  pinMode(linksMotorStaerke , OUTPUT);
  pinMode(rechtsMotorStaerke , OUTPUT);
  Serial.begin(9600);

  analogWrite(linksMotorStaerke, 255);
  analogWrite(rechtsMotorStaerke, 255);

}

void loop()
{
   if(Serial.available() > 0)      // Send data only when you receive data:
   {
      data = Serial.read();        //Read the incoming data & store into data
      Serial.print(data);          //Print Value inside data in Serial monitor
      Serial.print("\n");        
      
      if(data == '1')              
          vorwaertsFahren();  
      else if(data == '2')        
          rueckwaertsFahren();
      else if(data == '3')  
          linksDrehen();
      else if(data == '4')
          rechtsDrehen();
      else
          stoppen();
   }
}

//ULTRASCHALLSENSOR ---------------------------------------------------------------------

/*int distanzMessen()
{
  int distanz;

// Ultraschallsensor schickt Signal
  digitalWrite(TRIGGER, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER , HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER , LOW);

// Ultraschallsensor 
  
  dauer = pulseIn(echo , HIGH);
  distanz = (dauer/2) / 28.5 ;
  Serial.println(distanz);
  delay(100);
  return distanz;
}*/

//ROBOTER --------------------------------------------------------------------------------

void vorwaertsFahren()
{
    analogWrite(linksMotorStaerke, 255);
    analogWrite(rechtsMotorStaerke, 255);
    digitalWrite(rechtsVorwaerts , HIGH);
    digitalWrite(rechtsRueckwaerts , LOW);
    digitalWrite(linksVorwaerts , HIGH);
    digitalWrite(linksRueckwaerts , LOW);    
}

void rueckwaertsFahren()
{
    analogWrite(linksMotorStaerke, 255);
    analogWrite(rechtsMotorStaerke, 255);
    digitalWrite(rechtsVorwaerts , 0);
    digitalWrite(rechtsRueckwaerts , 1);
    digitalWrite(linksVorwaerts , 0);
    digitalWrite(linksRueckwaerts , 1);    
}

void linksDrehen()
{
    analogWrite(rechtsMotorStaerke, 95);
    digitalWrite(rechtsVorwaerts , HIGH);
    digitalWrite(rechtsRueckwaerts , LOW);
    digitalWrite(linksVorwaerts , 0);
    digitalWrite(linksRueckwaerts , 0);    
}

void rechtsDrehen()
{
    analogWrite(linksMotorStaerke, 95);
    digitalWrite(rechtsVorwaerts , 0);
    digitalWrite(rechtsRueckwaerts , 0);
    digitalWrite(linksVorwaerts , 1);
    digitalWrite(linksRueckwaerts , 0);    
}

void stoppen()
{
    digitalWrite(rechtsVorwaerts , 0);
    digitalWrite(rechtsRueckwaerts , 0);
    digitalWrite(linksVorwaerts , 0);
    digitalWrite(linksRueckwaerts , 0);   
}
