{\rtf1\ansi\ansicpg1252\cocoartf1187\cocoasubrtf400
{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\froman\fcharset0 TimesNewRomanPSMT;}
{\colortbl;\red255\green255\blue255;\red79\green78\blue78;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\ri720\sl360\slmult1\qc

\f0\b\fs24 \cf0 GRANNY FRAME
\f1\b0 \
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 \
\pard\pardeftab720\ri720\sl360\slmult1

\f0\b \cf0 Lucia Rabago and Orly Golan\
2014
\f1\b0\fs22 \
\
\pard\pardeftab720\ri720\sl360\slmult1

\f0 \cf0 ABSTRACT\
The GrannyNet project is offering a new communication system between grandmas and their family beloved ones. The project consist of two product connected to each other. One is the photo frame, that placed in the grandmother place, and the second one, is the patch blanket with a patch for each family member, placed in the family house. \
Usually, grandmothers keep the family members photographs in their house. Within this project, when the grandmother holds the frame of her beloved one, a notification is sent to the beloved one that grandma is thinking about him/her. If the grandmother caress the center of the frame a notification of \'93grandma wants to talk to you, are you available?\'94 is sent to the beloved one.\
The family members get the different notification on their patch blanket in their house, each one on their own patch (defined by knitted names on the patch). This kind of interaction makes the communication not instant, but holdback, so the family members can pay attention to the notification on their own free time. The new communication system used in this prototype attempt to cross time, language and age. \
\
INTERFACE AND INTERACTION MODALITY\
The GrannyNet project is physical computing project based on Arduino platform, which includes the software development, electronics and physical design of the objects. 
\f1\fs20 \
\

\f0\fs22 There are two main interactions in this communication system. The first is \'93Grandma is thinking about you\'94. This interaction activated by holding the frame. The holding detected using the two capacitive sensor. When the grandma holds the frame the LEDs in the frame turn on and the message of \'93Grandma is thinking about you\'94 sent to the blanket. This message display as lights that turn on on the patch. After the frame sends the message, the LEDs in the frame dim out to low power until the frame gets the notification from the blanket that the person saw that grandma is thinking about him/her. This notification activated by covering the patch while the LEDs on it turns on.\
\
The second interaction is \'93Grandma is checking if you are available\'94. This interaction activated by caressing the center of the frame. The caressing detected by the two IR sensors placed in the frame. When grandma is caressing the photo of the beloved one, the LEDs in the frame turn on and the message of \'93Grandma is checking if you are available\'94is sent to the blanket. The blanket patch will blink, for positive notification the person will embrace the blanket and the message will be sent back to the frame. If the frame gets notification that the person is available, then the light on the frame will dim out, and a phone call will be activated. for a negative reply, the user can not replying at all, or turn the blanket upside down, in this case, a negative message will be sent to the frame, and the frame will blink for couple of seconds.
\f1\fs20 \
\
\pard\pardeftab720\ri720\sl360\slmult1

\f0\b\fs22 \cf0 \ul \ulc0 \
\pard\pardeftab720\ri720\sl360\slmult1

\b0 \cf0 \ulnone USER EXPERIENCE:\
The frame shape is familiar and basic, mainly for addressing the target audience which usually does not embrace new technology. Moreover, we found that is necessary to design as close as possible to the old frame so it wont lose its value as a photo frame. The LEDs turning on and off slowly, with a small delay, for the grandmother to see the differences between off and on. The trigger for the interaction is simple and intuitive. The user experience was adjust after a short user testing. \
The blanket is a platform for more then one user at a time, each user can identify its own patch simply by name. The user experience is easy, intuitive and using the gestures the user already does with the blanket. \
\
LICENSE:
\b \cf2 \
Creative Commons AttributionShareAlike\'a03.0
\b0 .
\f1\fs20 \cf0 \
\
\
\pard\pardeftab720\ri720\sl360\slmult1

\f0\fs22 \cf0 \ul \ulc0 \
\
\pard\pardeftab720\ri720\sl360\slmult1\qc

\b \cf0 \ulnone HARDWARE AND ELECTRONIC\ul \
 Activate capacitive and IR sensors and display them using LEDs on other devices
\b0 \
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 \ulc0 \
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 \ulnone DESCRIPTION:
\b \uc0\u8232 
\b0 The project is based on two Arduino YUN boards, each one is placed in different device and can communicate one with each other. The connection between the two devices is on the wireless network and use MQTT message services. Each devices has its own methods of interaction. 
\f1\fs20 \
\

\f0\fs22 The frame has two IR sensors that can detect when the user caress the frame and  two capacitive sensors under the edge frame which are detect holding the frame with both hands. A set of LEDs which are under the surface of the frame edge turning on and off as a feedback for the user about the different interactions. 
\f1\fs20 \
\

\f0\fs22 The blanket is a patch blanket when one of the patches made out of e-textiles, meaning there is use of conductive fabrics and threads in the patch. The patch has an IR sensor placed on it for detecting if the user covers the patch. In addition, an e-textile switch button is used for sensing the pressure exerted on the fabric for detecting when the user hugs the blanket.\ul \
\
\ulnone COMPONENTS LIST\
\
Frame: \
- 2 IR Sensors \
- 2 Capacitive Sensor \
- 10 White Ultra Bright LED \
- 1 Arduino Yun \
\
Blanket: \
- 7 White LEDs.\
- 1 IR Sensor.\
- 1 Textile push button.\
- 1 Arduino Yun   \
\
REFERENCES TO OTHER CODES AND PROJECT:
\f1\fs20 \

\f0\fs22 [Capacitive Sensors](http://playground.arduino.cc//Main/CapacitiveSensor?from=Main.CapSense "Title")\
[Textile Projects](http://www.kobakant.at/DIY/ "Title")\
[Mosquitto Server](http://mosquitto.org/ "Title")\
[MQTT Server](http://chrislarson.me/blog/using-mqtt-connect-arduino-internet-things "Title")\
[Messenger Library](http://playground.arduino.cc/Code/Messenger "Title")\
\
FILE:\
-\
\
LICENSE
\b \cf2 \
Creative Commons AttributionShareAlike\'a03.0
\b0 .\cf0 \ul \ulc0 \
\
\ulnone TAGS:		\
none\
RELATED PROJECTS:\
none\
CATEGORIES: \
none
\b \
\pard\pardeftab720\ri720\sl360\slmult1\qc
\cf0 \
\
CODE\ul \
Arduino YUN wireless communication
\b0 \
\pard\pardeftab720\ri720
\cf0 \ulc0 \
\pard\pardeftab720\ri720
\cf0 \ulnone DESCRIPTION:
\b \
\pard\pardeftab720\ri720\sl276\slmult1
\cf0 \uc0\u8232 
\b0 The code is developed on the Arduino framework. The project uses two Arduino Yun, one for the frame and the other one for the blanket. For the communicate between the two Arduinos we used the MQTT message services, which means that the Arduinos are using a library call PubSubClients that helps to connect to a server via wireless and publish information in a queue. The YUN can both send and read messages over the queue in the same way. Using the MQTT message services and the queues in both of the objects make it possible to send and receive information even if they are not on the same wireless network. The messages obtained from the queue are processed by a library named 'Messenger Library'. Using that library makes it easier to code the selection of the string and the detection of the message. Both Arduinos execute the same functions for the communication protocol. 
\f1\fs20 \
\
\pard\pardeftab720\ri720\sl276\slmult1

\f0\fs22 \cf0 The frame is programed to detect if the IR sensors or the Capacitive sensors are activated. In the case the IR sensors are activated the frame's LEDs turn on and send the message "calling". Afterwords, the program wait until an answer is given by the blanket. There are two possible answers, the message "notavailable" or "connect". If the message is "connect" the frame doesn't execute anything during this iteration and if the message is  "notavailable" the frame's LEDs blink to indicate to user that the person using the blanket is not available. In case of capacitive sensors activation the LEDs glow. Then the LEDs change the intensity of the light until the program gets answer from the blanket to be turned off. 
\f1\fs20 \
\

\f0\fs22 The blanket is activated by the messages received by the frame. If the frame is activated by the capacitive sensors the blanket receives a message that turns on the LEDs. Afterwords the program waits until the IR sensor in the blanket is activated and then the LEDs turn off  and a message is sent back to the frame. Similar behavior is happened in case of the IR sensors in the the frame are activated. In this case the the frame sends a message to the blanket that makes the blanket's LEDs blink. After the LEDs are blinking there are two options, either the user press the blanket's switch and the blanket send back the message "connect" to the frame or there is no action for seven blinks and then the blanket sends the message "notavailable". Subsequently, both the blanket and the frame return to an idle state and wait for another interaction with the user. \
\
\
\pard\pardeftab720\ri720
\cf0 \
LIBRARIES:	\
\
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 [Capacitive Sensors](http://playground.arduino.cc//Main/CapacitiveSensor?from=Main.CapSense "Title")\
[Textile Projects](http://www.kobakant.at/DIY/ "Title")\
[Mosquitto Server](http://mosquitto.org/ "Title")\
[MQTT Server](http://chrislarson.me/blog/using-mqtt-connect-arduino-internet-things "Title")\
[Messenger Library](http://playground.arduino.cc/Code/Messenger "Title")\
\
\pard\pardeftab720\ri720
\cf0 REFERENCES TO OTHER CODES AND PROJECT:\
\
\pard\pardeftab720\ri720\sl276\slmult1
\cf0 [Arduino trcece svjetlo sa dva 74HC595](http://fritzing.org/projects/arduino-trcece-svjetlo-sa-dva-74hc595/ "Title")\
\pard\pardeftab720\ri720
\cf0 \
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 FILE:\
\
\
LICENSE:
\b \cf2 \
Creative Commons AttributionShareAlike\'a03.0
\b0 .\cf0 \ul \ulc0 \
\
\ulnone TAGS:
\b 		
\b0 \
none\
RELATED PROJECTS:\
none\
CATEGORIES: \
none\ul \
\
\pard\pardeftab720\ri720\sl360\slmult1\qc

\b \cf0 \ulnone DESIGN\ul \
WOODEN FRAME WITH SENSORS AND LIGHTS
\b0 \
\
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 \ulnone DESCRIPTION AND FABRICATION PROCESS:\uc0\u8232 The wooden frame design is minimal and clean, the LEDs are covered by a thin layer of wood and the sensors are also hidden indies. The thick of the frame is cut out of the Yun board. The frame build out of layers so we could create a tunnel for the LEDs and the IR sensors. The picture is immersed in the frame and using the volume of it.\
\
\pard\pardeftab720\ri720\sl360\slmult1

\f1\fs20 \cf0 \
\pard\pardeftab720\ri720\sl360\slmult1

\f0\fs22 \cf0 MACHINES AND TOOLS
\b 		
\b0 \
The frame is made out of plywood of one, four and six mm, and plexiglass cover. \ul \
\
\ulnone LICENSE
\b \cf2 \
Creative Commons AttributionShareAlike\'a03.0
\b0 .\cf0 \ul \ulc0 \
\
\ulnone RELATES PROJECTS:\
 \
CATEGORY: \
\
\
\pard\pardeftab720\ri720\sl360\slmult1\qc

\b \cf0 \ul \ulc0 KNITTED PATCH BLANKET WITH INTERACTIVE PATCH
\b0 \
\
\pard\pardeftab720\ri720\sl360\slmult1
\cf0 \ulnone DESCRIPTION AND FABRICATION PROCESS:\uc0\u8232 The knitted patch blanket is knitted by hand, with the look and feel of the old familiar patch blanket. In the center of the blanket there is an interactive patch, which is an add on to the patch and consist of IR sensor, pressure sensor and LEDs.\
\
\pard\pardeftab720\ri720\sl360\slmult1

\f1\fs20 \cf0 \
\pard\pardeftab720\ri720\sl360\slmult1

\f0\fs22 \cf0 MACHINES AND TOOLS
\b 		
\b0 \
The blanket is knitted by hand using wool. The circuit made out of conductive thread.\ul \
\
\ulnone LICENSE
\b \cf2 \
Creative Commons AttributionShareAlike\'a03.0
\b0 .\cf0 \ul \ulc0 \
\
\ulnone RELATES PROJECTS:\
 \
CATEGORY: \
\
\
\
\
}