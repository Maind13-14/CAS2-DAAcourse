WEATHER CUCKOO CLOCK
====================

Code Title
—————————-
Display temperature and show different birds with music based on weather read from yahoo.

Description
——————————-
**Arduino YUN**:
The function checks for the rotary knob reading and selects the day for which the forecast is to be shown. The position is transmitted to the arduino Yun using IC2 communication. 
The level of the rotary is continuously read and when the change is stable for 100 seconds the day is calculated and transmitted using the wire library.

Libraries
————————-
