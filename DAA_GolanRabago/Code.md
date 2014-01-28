Arduino YUN wireless communication
==================================

**DESCRIPTION**

The code is developed on the Arduino framework. The project uses two Arduino Yun, one for the frame and the other one for the blanket. For the communicate between the two Arduinos we used the MQTT message services, which means that the Arduinos are using a library call PubSubClients that helps to connect to a server via wireless and publish information in a queue. The YUN can both send and read messages over the queue in the same way. Using the MQTT message services and the queues in both of the objects make it possible to send and receive information even if they are not on the same wireless network. The messages obtained from the queue are processed by a library named 'Messenger Library'. Using that library makes it easier to code the selection of the string and the detection of the message. Both Arduinos execute the same functions for the communication protocol. 

The frame is programed to detect if the IR sensors or the Capacitive sensors are activated. In the case the IR sensors are activated the frame's LEDs turn on and send the message "calling". Afterwords, the program wait until an answer is given by the blanket. There are two possible answers, the message "notavailable" or "connect". If the message is "connect" the frame doesn't execute anything during this iteration and if the message is  "notavailable" the frame's LEDs blink to indicate to user that the person using the blanket is not available. In case of capacitive sensors activation the LEDs glow. Then the LEDs change the intensity of the light until the program gets answer from the blanket to be turned off. 

The blanket is activated by the messages received by the frame. If the frame is activated by the capacitive sensors the blanket receives a message that turns on the LEDs. Afterwords the program waits until the IR sensor in the blanket is activated and then the LEDs turn off  and a message is sent back to the frame. Similar behavior is happened in case of the IR sensors in the the frame are activated. In this case the the frame sends a message to the blanket that makes the blanket's LEDs blink. After the LEDs are blinking there are two options, either the user press the blanket's switch and the blanket send back the message "connect" to the frame or there is no action for seven blinks and then the blanket sends the message "notavailable". Subsequently, both the blanket and the frame return to an idle state and wait for another interaction with the user. 



**LIBRARIES**	

[Capacitive Sensors](http://playground.arduino.cc//Main/CapacitiveSensor?from=Main.CapSense "Title")
[Textile Projects](http://www.kobakant.at/DIY/ "Title")
[Mosquitto Server](http://mosquitto.org/ "Title")
[MQTT Server](http://chrislarson.me/blog/using-mqtt-connect-arduino-internet-things "Title")
[Messenger Library](http://playground.arduino.cc/Code/Messenger "Title")

**REFERENCES TO OTHER CODES AND PROJECT**

[Arduino trcece svjetlo sa dva 74HC595](http://fritzing.org/projects/arduino-trcece-svjetlo-sa-dva-74hc595/ "Title")


**LICENSE**
Creative Commons AttributionShareAlike 3.0.
