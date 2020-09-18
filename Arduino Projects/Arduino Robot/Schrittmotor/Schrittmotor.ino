int MPin1 = 2;  // In 1
int MPin2 = 3;  // In 2
int MPin3 = 4; // In 3
int MPin4 = 5; // In 4
enum Richtung {LINKS, RECHTS};             

const unsigned int LANGSAM  = 10000; // nicht über 16000
const unsigned int SCHNELL =  1500;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(MPin1, OUTPUT);
  pinMode(MPin2, OUTPUT);
  pinMode(MPin3, OUTPUT);
  pinMode(MPin4, OUTPUT);
}


void loop()
{ 
drehen(360, SCHNELL, RECHTS);
digitalWrite(MPin1, LOW);
digitalWrite(MPin4, LOW);
//for(;;){}
}

void MPinDrehen()
{
MPin1 = 5;
MPin2 = 4;
MPin3 = 3;
MPin4 = 2;
}
void drehen(unsigned int grad, unsigned int motorSchnelligkeit, Richtung richtung)
{
 if(richtung == RECHTS)
 MPinDrehen();
 
 double schritteAnzahl = (513 * (grad/360.0)) + 0.5;
 for(int n = 0; n < static_cast<int>(schritteAnzahl); n++)
  
 {
  digitalWrite(MPin1, HIGH);
  digitalWrite(MPin2, LOW);
  digitalWrite(MPin3, LOW);
  digitalWrite(MPin4, LOW);
  delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, HIGH);
  digitalWrite(MPin2, HIGH);
  digitalWrite(MPin3, LOW);
  digitalWrite(MPin4, LOW);
  delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, LOW);
  digitalWrite(MPin2, HIGH);
  digitalWrite(MPin3, LOW);
  digitalWrite(MPin4, LOW);
  delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, LOW);
  digitalWrite(MPin2, HIGH);
  digitalWrite(MPin3, HIGH);
  digitalWrite(MPin4, LOW);
   delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, LOW);
  digitalWrite(MPin2, LOW);
  digitalWrite(MPin3, HIGH);
  digitalWrite(MPin4, LOW);
  delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, LOW);
  digitalWrite(MPin2, LOW);
  digitalWrite(MPin3, HIGH);
  digitalWrite(MPin4, HIGH);
   delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, LOW);
  digitalWrite(MPin2, LOW);
  digitalWrite(MPin3, LOW);
  digitalWrite(MPin4, HIGH);
  delayMicroseconds(motorSchnelligkeit);
  digitalWrite(MPin1, HIGH);
  digitalWrite(MPin2, LOW);
  digitalWrite(MPin3, LOW);
  digitalWrite(MPin4, HIGH);
  }
}
