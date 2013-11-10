ManyQuiz
========
A collection of modules to build many quiz applications
with minimum duplication of code and layout elements.


Setup in Android Studio
-----------------------
1. Download and install Android Studio:
   http://developer.android.com/sdk/installing/studio.html

2. If you don't have the SDK yet, copy the SDK dir from Android Studio
   to somewhere else. It's good to have a separate SDK dir, because
   some upgrades of Android Studio need a clean reinstall. By using a
   separate SDK dir outside of Android Studio you will save time.

3. Set environment variables:

        - `ANDROID_HOME`: the SDK dir

        - `PATH`: add `$ANDROID_HOME/tools`, `$ANDROID_HOME/plaform-tools`

4. Start Android Studio

5. Click **Import project...**

        - Select `settings.gradle` in the project root

        - Select **Use customizable gradle wrapper**

    Note: the first time you do this it may take a long time.
    At the bottom-right corner of Android Studio you should see a
    progress indicator saying something like "3 processes running...".
    Click on it to see details. You will have to wait for this to complete.

6. Connect an Android device via USB
   (See **Troubleshooting USB connections** below for help)

7. Create a run configuration for Programming Quiz LITE
   (See **Creating a run configuration** below for help)

8. Launch the run configuration to install and start the app on your device.
   Note: if you have previously installed the app form Google Play,
   you will have to uninstall it first, because its signature won't match
   the signing key used by your Android Studio.
   An easy way to uninstall is with the command:

        adb uninstall com.manyquiz.programming.lite


Troubleshooting USB connections
-------------------------------
TODO


Creating a run configuration in Android Studio
----------------------------------------------
1. Go to **Run | Edit Configurations...**

2. Select **Android Application** from the list and click on the + icon

3. Give it a name, for example "programming-lite"

4. Adjust the settings:

        + Package: **Deploy default APK**

        + Activity: **Launch default Activity**

        + Target Device: **USB device**

5. Click OK to make this configuration the default, and it should
   appear in a drop-down menu in the top toolbar.
   Click on the play button next to the drop-down to launch the configuration.


Building on the command line
----------------------------
To build the projects on the command line we use Gradle.

1. Install *Android SDK Build-tools* revision 19 using Android Studio

        a. Open **Tools | Android | SDK Manager**

        b. Install or upgrade *Android SDK Tools* to at least revision 22

        c. Quit and restart SDK Manager

        d. Install *Android SDK Build-tools* revision 19

2. Run in the project root:

        ./gradlew assembleDebug


Building in Eclipse
-------------------
1. Import the following projects:

        * manyquiz/ -- common quiz logic and layouts

        * programming/ -- logic and layouts for Programming Quiz

        * programming-lite/ -- the Programming Quiz LITE application

2. Run the Programming Quiz LITE project as Android application


Project layout
--------------
+ manyquiz/

    An Android Library project that contains most of the
    functionality and layout elements of the quiz.

+ programming/

    An Android Library project that contains common
    logic in the Programming Quiz.
    Pretty much empty for now.

+ programming-lite/

    An Android Application project that corresponds to the
    Programming Quiz LITE application on Google Play.
    It depends on the "manyquiz" and "programming" projects.
    Pretty much empty for now.

+ django-quiz/

    A Django project to make it easier to edit the database
    of questions.


Adding more questions
---------------------
The questions in the Android app are stored in a SQLite database.

The `django-quiz` project is to make editing the database a little
bit easier, but it's still quite complicated.

You can edit the questions and update in the application by
following these steps:

0. Follow the First-time setup steps in `django-quiz/README.txt`

1. Edit the questions in the file `django-quiz/export/programming.txt`

2. Import the questions into Django:

        cd django-quiz
        ./manage-programming.sh importq export/programming.txt

3. Generate the SQL statements for the `programming-lite` project:

        ./programming-lite/scripts/gen-sql-create.sh

4. Refresh the project in Eclipse or Android Studio

5. Increment `QuizSQLiteOpenHelper.DATABASE_VERSION`

6. Rebuild and run the project

7. Revert the `QuizSQLiteOpenHelper.DATABASE_VERSION` change


Gradle
------
- User guide: http://tools.android.com/tech-docs/new-build-system/user-guide

- Overview video: http://tools.android.com/tech-docs/new-build-system

- Tips for Android Studio: http://developer.android.com/sdk/installing/studio-tips.html


Creating a new quiz clone
-------------------------
TODO
