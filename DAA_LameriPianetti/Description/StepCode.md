Code
====================

##Code Title##
Step Prototype Code

##Description##

The code for this project is divided in two parts. Since the project needs to record and play many files at the same time we used a computer to have a complete working prototype.
In the future all of the record and play will be menaged inside the object but for the moment, considering the time and knowledge limitations the project relies on an Arduino Leonardo communicating with processing.


 **Arduino Code**
The code has been compiled on arduino 1.5.5.

The arduino needs to read 25 CNY70 IR sensors, a potentiometer and a button. This information is sent to a computer vis Serial connection. The numbers are sent separed by a comma and a "println" defines the end of each group of information.

The arduino also receives information from the Processing sketch in order to know wich led he has to light up. This will assure the coordination between light and sound.

In order to have 25 analog in pins and 8 digital out pins we used a MUX SHIELD II and the related MuxShield.h library.



**Processing Code**:
The code runs on Processing 2.0.3 32bit, on Windows. It might run in another OS or Processing version but we encountered memory problems running it on OSX.

The Processing sketch it's used to perform for most of the project actions. It menages the record and play, the calibration of the IR Sensors, the timer used to define the length of each step, to send this information to the arduino in order to syncronize the leds with the sound, and it also runs a simulation of a second board.

To menage the communication with the Arduino we used Processing's Serial default library. This information is trimmed and splitted into an array that we check in the moment a sound should be played.

The record and play it's all menaged by minim library. This part does still have some memory leaks sometimes, since we need to load audio files hundred times a minute.
The recording starts when the arduino sends information about the button being pressed. Processing sketch stops all sounds and start's recording, the name of the file is related to the color of the tag inserted in the recording spot in that moment.

The timer checks the time on every loop and deceides when is the moment to play the sounds according to the information sent by the arduino's potentiometer. A function then checks if there is something that should be played in according to the position and plays it if needed. 



##Libraries##
a.Arduino: MuxShield.h

b.Processing: Serial Library

c.Processing: minim Library


##References to other codes and projects##
1. http://processing.org/discourse/beta/num_1276782247.html
2. http://forum.processing.org/two/discussion/260/use-minim-and-arduino-for-led-light-show/p1

##Tags##
step, sequencer, MuxShield, minim, loop, CNY70, serial

##License##
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">step</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="https://github.com/Maind13-14/CAS2-DAAcourse/tree/master/DAA_PeethambaranAkin/Code" property="cc:attributionName" rel="cc:attributionURL">Lameri&Pianetti</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Attribution 4.0 International License</a>

##Category##
Music instrument, Arduino 

