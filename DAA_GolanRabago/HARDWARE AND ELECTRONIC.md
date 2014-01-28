HARDWARE AND ELECTRONIC
=======================

**DESCRIPTION**

The project is based on two Arduino YUN boards, each one is placed in different device and can communicate one with each other. The connection between the two devices is on the wireless network and use MQTT message services. Each devices has its own methods of interaction. 

The frame has two IR sensors that can detect when the user caress the frame and  two capacitive sensors under the edge frame which are detect holding the frame with both hands. A set of LEDs which are under the surface of the frame edge turning on and off as a feedback for the user about the different interactions. 

The blanket is a patch blanket when one of the patches made out of e-textiles, meaning there is use of conductive fabrics and threads in the patch. The patch has an IR sensor placed on it for detecting if the user covers the patch. In addition, an e-textile switch button is used for sensing the pressure exerted on the fabric for detecting when the user hugs the blanket.

**COMPONENTS LIST**

Frame: 
- 2 IR Sensors 
- 2 Capacitive Sensor 
- 10 White Ultra Bright LED 
- 1 Arduino Yun 

Blanket: 
- 7 White LEDs.
- 1 IR Sensor.
- 1 Textile push button.
- 1 Arduino Yun   

**REFERENCES TO OTHER CODES AND PROJECT**
[Capacitive Sensors](http://playground.arduino.cc//Main/CapacitiveSensor?from=Main.CapSense "Title")
[Textile Projects](http://www.kobakant.at/DIY/ "Title")
[Mosquitto Server](http://mosquitto.org/ "Title")
[MQTT Server](http://chrislarson.me/blog/using-mqtt-connect-arduino-internet-things "Title")
[Messenger Library](http://playground.arduino.cc/Code/Messenger "Title")


**LICENSE**
Creative Commons AttributionShareAlike 3.0.
