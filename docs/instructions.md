# Basic Instructions

## After Installation
1. Run app from IntelliJ IDEA. Make sure to run on a physical device and not on an emulator. Recording function will not work on an emulator.
2. Google sign-in request will appear. Select the account you wish to sign-in with. \(Optional\) You may test the Log out function by selecting the options menu on the top right of the screen.
3. Permissions requests will appear. Please select allow for both.
4. Once logged in, you will be taken to the home page. From here, please use the Navigation Drawer on the top left corner to navigate the application.
5. Currently, the only option that works is the record screen.
6. Once in the **Record** screen, click on the Record button and begin recording audio.
7. To stop recording, press Stop button.
8. A list of recordings should begin to populate below the buttons.
9. Delete button on this screen is not yet functional.
10. A copy of your recording has been written to your storage device.


## Testing with Stetho
1. In order to view the database results, please use open Google Chrome and visit Chrome://inspect.
2. Make sure your device is listed and click the **inspect** link below your device's description.
3. A Dev Tools window should open.
4. Navigate to **Web SQL**, **recording_db**, **Recording**, and a list of your recordings should appear.
