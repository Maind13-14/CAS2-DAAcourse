Highlights - HardwareElectronics
================

Highlights 

Indicate a title that describes the hardware and electronics part of your project. Example: Controlling a toy car with light sensors.


Description
--------

The interaction with the device is managed with a potentiometer and a knob (rotary encoder with button). We used a 10 turns (3600°) potentiometer to map the whole device height. The choice of a potentiometer is because of the actual height is directly related to the device position (with an encoder this wouldn’t be possible). So if the device is switched off the potentiometer value physically correspond to the height.

To turn the potentiometer, a gear rack is fixed on the vertical wood on which a wheel gear attached on the potentiometer shaft can turn.

To calculate the right diameter of the wheel gear the calculation is the following:

radius = length to map / (2 PI * potentiometer turns)

In our device the height to map is 1600mm, then:

radius = 1600 / (2 PI * 10) = 25 mm

The potentiometer is connected to the analog pin A0 of the Arduino MEGA ADK.

The knob contains a rotary encoder (3 pins labeled A, B and C) and a normal button. The encoder is connected as the follow: A and B are connected as digital inputs on pin 2 and 3 of Arduino and C to the ground. The button is connected on the pin 11. Check the schematics for the connections.


Component list
--------

- Ten turn potentiometer (Vishay model 533)
  https://www.distrelec.ch/potentiometer-20-kω-linear-5/vishay/534b1203jcb/715767

- Incremental encoder with button (PEC11)
  http://www.adafruit.com/products/377#Description

- 20 kohm resistor

- Arduino Mega ADK
