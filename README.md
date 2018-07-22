# SheetMusic
SheetMusic is an application designed to facilitate the transcription of musical notation. The aim of this application is to allow
musicians to play any melody on their preferred instrument and obtain sheet music as the resulting product. Ultimately, SheetMusic
provides more convenient access to transcription software and reduces the amount of time needed to write sheet music.

## Current State
SheetMusic is not yet complete. The most significant achievement until now is the discovery of a service called SonicAPI. We are able 
to obtain a detailed analysis of monophonic melodies through the use of SonicAPI's high quality audio processing. In return, we will obtain
an XML file detailing the results including midi pitch, key, and note duration. The framework for utilization of SonicAPI's service
is set up, but the calls to the service are not yet operational. The plan for the future is to utilize an XSL Transform in order to convert
the resulting XML file into a MusicXML file. From here (in the immediate future) users will be able to export this file to their preferred
sheet music editors, such as Flat or MuseScore. Many improvements still need to be made, especially in the areas of UI/UX. Application
supports portrait orientation only.

## Getting Started
### Prerequisites
* Android API 21 (min)
* Application only supports portrait orientation

### Installing
* Click here for build instructions

### Using the App
* Click here for instructions on how to use the app's current features

## Tests
Testing for this app has been completed on Google Pixel 2XL running on Oreo 8.1.0 (API 27). The minimum API version supported is Android 
API 21.


## Built With

## Links to Additional Documents
* Wireframe
* User Story
* Javadoc

## License 
* This project is licensed under .... Link

## Acknowledgements



