//This example shows how to use the Mux Shield for analog inputs

#include <MuxShield.h>




const int  ledPin[] = {0, 1, 2, 3, 4, 5, 6, 7};
const int buttonPin = 5 ;
int ledOn[8];
int incByte;

int buttonState;
int oldButtonState;

//Initialize the Mux Shield
MuxShield muxShield;

void setup()
{
    //Set I/O 1, I/O 2, and I/O 3 as analog inputs
    muxShield.setMode(1,ANALOG_IN);
    muxShield.setMode(2,ANALOG_IN);
    muxShield.setMode(3,DIGITAL_OUT);
    
     for (int i=0; i<8; i++){
    ledOn[i]=0;}  
    
    Serial.begin(28800);
}

//Arrays to store analog values after recieving them
int IO1AnalogVals[16];
int IO2AnalogVals[16];
int IO3AnalogVals[16];

void loop()
{
  
   while(Serial.available()){
    incByte = Serial.read();
    ledOn[incByte-1] = 1;
      for (int i=0; i<8; i++){
      if (ledOn[i] == 1){
        muxShield.digitalWriteMS(3,ledPin[i],LOW);
        }
        
        else{
          muxShield.digitalWriteMS(3,ledPin[i], HIGH);
        }
          ledOn[i] = 0;
          }
    
    }
    
  for (int i=0; i<16; i++)
  {
    //Analog read on all 16 inputs on IO1, IO2, and IO3
    IO1AnalogVals[i] = muxShield.analogReadMS(1,i);
    IO2AnalogVals[i] = muxShield.analogReadMS(2,i);
   // IO3AnalogVals[i] = muxShield.analogReadMS(3,i);
  }
  
  //Print IO1 values for inspection
 
  for (int i=0; i<16; i++)
  {
    Serial.print(IO1AnalogVals[i]);
    Serial.print(',');
  }
 
  
  
   //Print IO1 values for inspection
 
  for (int i=0; i<10; i++)
  {
    Serial.print(IO2AnalogVals[i]);
    Serial.print(',');
  }
  
  
buttonState =  digitalRead(buttonPin);

if (buttonState == HIGH && oldButtonState == LOW){
  Serial.print(1);}
  else if (buttonState == LOW && oldButtonState == HIGH){
  Serial.print(2);}
  else { Serial.print(0);}
  
  Serial.print(',');
  
  Serial.println();
  

    
    delay(30);
    
    oldButtonState = buttonState;  
  

}
