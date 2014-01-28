Highlights - Code
================

Arduino-Android 
--------
Taking and viewing pictures on Android using a physical interface managed by Arduino.

Description
--------

Keywords:


Camera preview: is the interface seen when the camera is activated.

View mode: is the interface in which the user view the pictures already captured by the device.

Main mode: refers to the camera preview, in which the picture would be saved to the main folder. Those pictures are both stored in the cloud and will be displayed on the device.

Random mode: refers to the camera preview, in which the picture would be saved to the random folder. Those pictures are only stored in the cloud and not accessible from the device.



The code for this project is generated using 2 main platforms: Arduino and Eclipse.

In Arduino, the code is created to read the potentiometer values as well as the encoder and send the readings to Android.
On the other hand on Android, an application had to be compiled on Eclipse in order to listen to the readings of the Arduino and execute the functions accordingly.


Arduino:












Eclipse:

An android application on Eclipse needs different resources inside of the project to be compiled and create a working Android application. Below is a description of the main parts of the code that was created for this project, however for more details please refer to the Android developer website.

Android Manifest:

All the permissions needed for the code to access the hardware of the camera on the Android, as well as to write and read Files from the local directories has to be declared and added in the Java Manifest of the application.

Accessory filter:
(res/xml/accessory_filter.xml)

This file contains the information to filter all usb accessories and connect to the one with the corresponding identity.

Application Layout:
(res/layout/activity_main.xml)

The layout of the application consists mainly of a relative layout an ImageView object used to display the bitmap pictures already taken and saved in the local storage.


The java file:
This is the core part of the program with all the logics of the application. The main class used is an extension of the super class activity and the program is divided into 4 main parts:

1- Using the ADK library to create the connection between Arduino and Android. (+Runnable)

















2- Accessing and managing Android’s directories in the local storage. 

The application once installed checks the local storage environment of the android device on which it was downloaded. If the directories needed for the application to function correctly and to store the pictures does not exist, the program automatically creates them. The main folders are: “Highlights” a folder with the project name directly created on the main external storage of the device. Inside of it, there is 2 other folders: “Random”, and “Main”. In the “Random” folder, all pictures taken in the random mode of the program are stored inside of this folder. For each day a folder is created automatically and inside of it the pictures are named according to the time they were created. As for the other folder “Main”, this is the directory in which the users are created. Each time a new user is created, a new folder named “newUserxx” is created. The “xx” in the folder name refers to the number of users created. Before adding any new users, the program checks for all directories inside the “Main” folder, meaning all the other users already created. By this method, if one user is deleted, the program automatically adjust itself to the current local state of the directories and not according to the count of users created, this make the application more reliable and adjustable.

In order to switch between users or even to add a new user, the application first extract all files inside the main folder. If the file is a folder, that means its a user folder on the device. The program retrieve all the names of the folders to display the right one when a user is switched. When a new user is created, the user number is defined depending on how many user folders already exist in the main folder. That way the folders are always updates and reliable.

3- Accessing the Camera on Android, and taking pictures.

The camera is accessed through the Camera class of java. This allows the application to access the frontal camera of the tablet. Using the SurfaceHolder class, the camera preview is displayed on the screen of the tablet once triggered. The camera preview can be accessed in two modes “Random” and “Main”. The mode is defined by the interaction of the user with the knob. 

From the view mode, a simple click on the knob, the “camera preview” will be in the “Random mode”. Else if the knob was pressed for 3 seconds, the camera preview will be in “Main” mode. Each time the knob is pressed in the “camera preview”, a picture is saved in the right directory according to the mode of the program “Random” or “Main”. 



4- Displaying images in “View mode” of the device.

In the view mode of the program, the picture are displayed according to the potentiometer exact position at the time of the image capture. As the potentiometer’s value is saved as the name of the picture, each time a new picture has to be displayed on the screen, the application do the following: first, it collects all files inside the current user, check if it is a jpeg, and extract the potentiometer values from each. The program then sort all the pictures in an ascending order according to their potentiometer values. Each time a picture is viewed, the one with a lower value and one higher are stored in the program as the minimum and maximum values. Those numbers are the boundaries to trigger the right moment to update the picture (according to the current potentiometer value). That means that the program does not constantly update the pictures displayed, but only if the current potentiometer value is below or above the minimum and maximum values. Else, the program will do the same process to update the viewed picture and the minimum and maximum boundaries. The selected image will then be displayed on the image view object of the layout. If no images are present to be displayed, the visibility of the image view object goes to GONE.

Libraries
--------
List the external libraries to include in the code project

For Eclipse:
Android SDK
Apache ANT
JDK/Java

For Arduino:
USB Host Shield
AndroidAccessory
Encoder

Reference to other codes and projects
--------
Indicate other projects codes and provide the links to the source page, if any.

CustomCameraAndroid - by ??


License
--------
Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.

File
--------
Upload the source files of your application

