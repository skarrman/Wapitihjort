# Wapitihjort 
# Collaborators:
    Jakob Erlandsson    - JakobErlandsson
    Jakob Wall          - jakwall
    Simon Kärrman       - skarrman
    Anton Hägermalm     - antonhager
# To start Tank Revolution:
First of all, the gdx-setup.jar needs to be downloaded from here: http://libgdx.badlogicgames.com/download.html.

Now open the setup. In ”Game class” put ”GameHolder”. 
In destination, put where you want the final project to be located. 
In Android SDK, locate your Android SDK. 
The rest doesn’t really matter

Now start IntelliJ IDEA and pick ”import project” in the menu.
Locate the libGDX project.
Next up, pick Gradle.
In the next menu, make sure ”use default gradle module” is selected and now finish.

Now clone down this repository to your computer.
Open up both the folder with the newly created libGDX project and the folder of the cloned repository.
Drag everything from the cloned into the newly created one, when warings come up, proceed anyway.
Start the project.
Try to run the Desktop Launcher in desktop.src.tankrevolution.controller.desktop.
When failed go to Run -> Edit Configurations… and set ”working directory” to Wapitihjort.android.assets.
Now run again.
