/*
 Basic MQTT example

  - connects to an MQTT server
  - publishes "hello world" to the topic "out" of yun02
  - subscribes to the topic "in"

  modified for Arduino Yun by mbanzi

  PubSubLibrary https://github.com/knolleary/pubsubclient
  Messenger Library https://www.dropbox.com/s/lmux68kbtq9k4lq/Messenger.zip
http://playground.arduino.cc/Code/Messenger
*/

#include <Bridge.h>
#include <YunClient.h>
#include <PubSubClient.h>
#include <Messenger.h>
#include <CapacitiveSensor.h>

int ledPin = 10;
int analogInPin = A4;
int analogInPin2 = A3;
int oldsensorValue = 0;
int oldsensorValue2 = 0;
int sensorValue = 0;
int sensorValue2 = 0;
int flag_imon=0;
//byte server[] = { 178,209,52,5}; Massimo Server
byte server[] = {192,168,240,1};

CapacitiveSensor   cs_4_2 = CapacitiveSensor(4, 2);       // 10M resistor between pins 4 & 2, pin 2 is sensor pin, add a wire and or foil if desired
CapacitiveSensor   cs_4_6 = CapacitiveSensor(4, 6);       // 10M resistor between pins 4 & 6, pin 6 is sensor pin, add a wire and or foil


// Instantiate Messenger object with the message function and the default separator (the space character)
Messenger message = Messenger();



YunClient yunClient;
//PubSubClient client(server, 8883, callback, yunClient); Massimo Server
PubSubClient client(server, 1883, callback, yunClient);

// Define messenger function
void messageCompleted() {

  if ( message.checkString("notb") ) {
     flag_imon = 0;
    int o;
    for (o = 0; o <= 5; o++) {
      int p = 0;
      for (p = 0; p <= 255 ; p++)
      {
        analogWrite(ledPin, p);
        delay(10);
      }
      analogWrite(ledPin, 255);
      delay(100);
      for (p = 255; p >= 0 ; p--)
      {
        analogWrite(ledPin, p);
        delay(10);
      }
      analogWrite(ledPin, 0);
      delay(100);
    }
  }
  if ( message.checkString("reply") ) {
    flag_imon = 0; 
    int i = 0;
    for (i = 255; i <= 0 ; i--)
    {
      analogWrite(ledPin, i);
      delay(40);
    }
    analogWrite(ledPin, 0);
  }
  if ( message.checkString("connect") ) {
    flag_imon = 0;
    int q = 255;
    for (q = 255; q >= 0 ; q--)
    {
      analogWrite(ledPin, q);
      delay(10);
    }
    analogWrite(ledPin, 0);
  }
}
// Callback function
void callback(char* topic, byte* payload, unsigned int length) {
  // message received, let's parse it.
  payload[length] = 13;
  // The following line is the most effective way of
  // feeding the serial data to Messenger
  for (int i = 0; i <= length; i++) {
    message.process(payload[i]);
  }

}



void setup()
{
  Bridge.begin();
  message.attach(messageCompleted);
  
  // pinMode(ledPin, OUTPUT);
  if (client.connect("frame")) {
    client.publish("/devices/frame/online", "hello world");
    client.subscribe("/devices/blanket/online");
  }
 
  cs_4_2.set_CS_AutocaL_Millis(0xFFFFFFFF);     // turn off autocalibrate on channel 1 - just as an example

}

void loop()
{  if (client.connect("frame")) {
    client.subscribe("/devices/blanket/online");
  }
  client.loop();

  sensorValue = analogRead(analogInPin);
  sensorValue2 = analogRead(analogInPin2);

  //Serial.print("Old Value-new ");
  //Serial.println(abs(oldsensorValue - sensorValue));

  long start = millis();
  long total1 =  cs_4_2.capacitiveSensor(30);
  long total2 =  cs_4_6.capacitiveSensor(30);


  Serial.print(millis() - start);        // check on performance in milliseconds
  Serial.print("\t");                    // tab character for debug windown spacing


  Serial.print("TS1: ");
  Serial.print(total1);                  // print sensor output 1
  Serial.print("\t");
  Serial.print("T2: ");
  Serial.print(total2);
  Serial.print("\t");   // print sensor output 2
  Serial.print("IR1: " );
  Serial.print( sensorValue);
  Serial.print("\t");   // print sensor output 2
  Serial.print("IR2: " );
  Serial.println(sensorValue2);

  if(flag_imon == 0)
 { 

 
  if ((total1 >= 300) && (total2 >= 300)) {
    int i = 0;
    for (i = 0; i <= 255 ; i++)
    {
      analogWrite(ledPin, i);
      delay(10);
    }
    analogWrite(ledPin, 255);
    Serial.println("Thinking");
    client.publish("/devices/frame/online", "Thinking");
    delay(500);
    for (i = 255; i <= 30 ; i--)
    {
      analogWrite(ledPin, i);
      delay(20);
    }
    analogWrite(ledPin, 30);
     flag_imon = 1; 
  }
  
   if ((sensorValue <= 100)  ) {
    int p = 0;
    for (p = 0; p <= 255 ; p++)
    {
      analogWrite(ledPin, p);
      delay(10);
    }
    analogWrite(ledPin, 255);
    Serial.println("Calling");
    client.publish("/devices/frame/online", "calling");
    flag_imon = 1; 
  }

  delay(100);
  //oldsensorValue = sensorValue;
  //oldsensorValue2 = sensorValue2;

}

}



