# Scandinavia-Capital-Guider
This conversational system is a travel guide for Scandinavian capitals. It is able to provide some trivia as well as aid the user in booking different activities in each city.

## Getting Started

The project is **not** contained and unfortunately, no easier way of running the conversational system than using IntelliJ is present. Therefore, make sure you fulfill the prerequisites before proceeding with the usual instructions.

##### Prerequisites
- Install IntelliJ (see Course page)
- Install Furhat SDK (see Course page)

##### Running the system
After making sure you have all the prerequisites, continue with the following steps.

1. Clone the repository within the /skills/ directory in your Furhat SDK directory
``` bash
cd ~/.furhat/launcher/SDK/skills
git clone https://github.com/IdrissRio/Scandinavia-Famous-Capital-Guider.git
```
2. Open the project in IntelliJ (make sure to open /skill/CaptialGuide/, **not** the repo root).
3. Launch the Furhat SDK.
4. Run the project in IntelliJ (green play button *should* work)
5. Have fun.

##### Potential complications
You might run into problems running the project in IntelliJ. If so, try these fixes.

1. Build the project manually before running it (Ctrl+F9 or Build --> Build project). Then run the project as usual.
2. Enter a custom configuration (Run --> Edit Configurations). Make sure the "Use classpath of module" is set to `CaptialGuide.main` and that the "Main class" is set to `furhatos.app.captialguide.MainKt`. Then run the project as usual.


# TODO
 - [x] Fix simpleintent (Idriss)
    - [x] Fix text representation
    - [x] Add city information per booked activity

 - [ ] Remove booked activities (Ciwan)
 - [x] Add more intent matches/sentences (Ciwan)
 - [x] More varied sentences (not always "Which city?") (Yu)
 - [ ] (Multiple users)
 - [x] List cities with information (Anton)
 - [x] Add more cities and facts (Mandy)
 - [x] Go straight to information or booking from choose city (Anton)

 - [ ] Make video
 - [ ] A slide deck (Google slides, Mandy starts)
 - [ ] A report (Google doc)
 - [ ] Test with other group
 - [ ] Document with experience
