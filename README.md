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
supports portrait orientation only. The ultimate goal is to be able to provide sheet music creation in real time without having to wait for processing.

## Getting Started
### Prerequisites
* Android API 21 (min)
* Application only supports portrait orientation

### Installing
* Click [here](https://github.com/iespi7/SheetMusic/blob/master/docs/installation.md) for build instructions

### Using the App
* Click [here](https://github.com/iespi7/SheetMusic/blob/master/docs/instructions.md) for instructions on how to use the app's current features

## Tests
Testing for this app has been completed on Google Pixel 2XL running on Oreo 8.1.0 (API 27). The minimum API version supported is Android 
API 21.


## Built With
* [Gradle](https://gradle.org/)
* [Stetho](http://facebook.github.io/stetho/)
* [Retrofit2](http://square.github.io/retrofit/)
* [Sonic API](http://www.sonicapi.com/docs)
* [Google Sign-In](https://developers.google.com/identity/sign-in/android/)

## Links to Additional Documents
* [Wireframe](https://github.com/iespi7/SheetMusic/blob/master/docs/User%20Story.docx)
* [User Story](https://github.com/iespi7/SheetMusic/blob/master/docs/sheet_music_app.pdf)
* [Javadoc]
* [ERD](https://github.com/iespi7/SheetMusic/blob/master/docs/sheet%20music%20erd.pdf)

## License 
* This project is licensed under Creative Commons Attribution-ShareAlike 4.0 International Public License. [more](https://github.com/iespi7/SheetMusic/blob/master/license)
* SonicApi's [Terms of Service](http://www.sonicapi.com/terms-of-service)

## Acknowledgements
Special thanks to [Nick Bennett](https://github.com/nick-bennett) for his assistance throughout the process and to [Chris Hughes](https://github.com/cfhughes) for his assistance with database creation.



