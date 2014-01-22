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


 int flag_count = 0;
  int flag_not = 0;
int G1 = 9;

//byte server[] = { 178,209,52,5}; Massimo Server
byte server[] = {192,168,240,1};

// Instantiate Messenger object with the message function and the default separator (the space character)
Messenger message = Messenger();



YunClient yunClient;
//PubSubClient client(server, 8883, callback, yunClient); Massimo Server
PubSubClient client(server, 1883, callback, yunClient);

// Define messenger function
void messageCompleted() {

  if ( message.checkString("calling") ) { // Write pin (analog or digital)
    digitalWrite(13, HIGH);
    calling();
    delay(500);
  }
  if ( message.checkString("Thinking") ) { // Write pin (analog or digital)
    digitalWrite(13, HIGH);
    thinking();
    delay(500);
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

void thinking () {
  int i = 0;
  for (i = 0; i <= 255 ; i++)
  {
    analogWrite(G1, i);
    delay(10);
  }
  analogWrite(G1, 255);
  int sensor1 = analogRead(A0);
  while (sensor1 <= 150) {

    sensor1 = analogRead(A0);

    delay(10);
  }

  client.publish("/devices/blanket/online", "reply");
  for (i = 255; i >= 0 ; i--)
  {
    analogWrite(G1, i);
    delay(10);
  }
  analogWrite(G1, 0);
}

void calling() {

  int i = 0;
  for (i = 0; i <= 255 ; i++)
  {
    analogWrite(G1, i);
    delay(10);
  }
  analogWrite(G1, 255);
  int sensor2 = analogRead(A5);
  flag_count = 0;
  flag_not = 0;
  while ((sensor2 <= 450)) {
    int p = 0;
    for (p = 0; p <= 255 ; p++)
    {
      analogWrite(G1, p);
      delay(5);
    }
    for (p = 255; p >= 0 ; p--)
    {
      analogWrite(G1, p);
      delay(5);
    }
    flag_count=flag_count+1;
    sensor2 = analogRead(A5);
    Serial.print(flag_count);
    if(flag_count>=5)
    { sensor2=500;
     client.publish("/devices/blanket/online","notb");
     client.publish("/devices/blanket/online","notb");
    }
  
  }
  for (i = 255; i >= 0 ; i--)
  {
    analogWrite(G1, i);
    delay(10);
  }

if(flag_count<=4){client.publish("/devices/blanket/online","connect");}
}


void setup()
{
  Bridge.begin();
  message.attach(messageCompleted);
  Serial.begin(9600);

  if (client.connect("blanket")) {
    client.publish("/devices/blanket/online", "hello world");
    client.subscribe("/devices/frame/online");
  }

  pinMode(13, OUTPUT);
}

void loop()
{ if (client.connect("blanket")) {
    client.subscribe("/devices/frame/online");
  }
  client.loop();
  //analogWrite(G1, 255);
  /*
  client.publish("/devices/blanket/online","on a");
  delay(1000);
  client.publish("/devices/blanket/online","on b");
  delay(1000);
  */
  digitalWrite(13, LOW);
  int sensorValue = analogRead(A0);
  int sensorValue2 = analogRead(A5);
  /*
  Serial.print("IR1: " );
  Serial.print( sensorValue);
  Serial.print("\t");   // print sensor output 2
  Serial.print("IR2: " );
  Serial.println(sensorValue2);
  delay(10);
  */

}





