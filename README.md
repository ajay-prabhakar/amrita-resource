


# Amrita Resource :mortar_board: :school:


[![Watchers](https://img.shields.io/github/watchers/Chromicle/AmritaResource.svg?style=social&label=Watchers&maxAge=2592000)](https://github.com/Chromicle/AmritaResource/watchers/)
[![Star Gazers](https://img.shields.io/github/stars/Chromicle/AmritaResource.svg?style=social&label=Stars&maxAge=2592000)](https://GitHub.com/Chromicle/AmritaResource/stargazers/)
[![Forks](https://img.shields.io/github/forks/Chromicle/AmritaResource.svg?style=social&label=Forks&maxAge=2592000)](https://GitHub.com/Chromicle/AmritaResource/network/members/)

[![Travis CI](https://travis-ci.com/chromicle/AmritaResource.svg?branch=master)](https://travis-ci.com/chromicle/AmritaResource)

[![Android Studio](https://img.shields.io/badge/android%20studio-v3.4.1-blue.svg?cacheSeconds=2592000)](https://developer.android.com/studio/)
[![Gradle](https://img.shields.io/badge/gradle-v5.1.1-green.svg?cacheSeconds=2592000)](https://docs.gradle.org/5.1.1/release-notes.html)


A platform for University Students to share Documents such as Exam Papers and Study Material. Users can Upload/Download Documents shared by Users.


## Pre-requisites

- Android SDK v28
- Android Build Tools v28.0.0
- Android Support Repository v23.3.

These components can be downloaded bundled at the [Android studio IDE](https://developer.android.com/studio)

## Tech Stack
- Java
- Android SDK
- Firebase SDK

## Libraries Used
- [Architecture Components](https://developer.android.com/topic/libraries/architecture/)
- [CustomFloatingActionButton](https://github.com/robertlevonyan/customFloatingActionButton)
- [DebugDB](https://github.com/amitshekhariitbhu/Android-Debug-Database)
- [Dexter](https://github.com/Karumi/Dexter)
- [Firebase-Firestore](https://firebase.google.com/docs/firestore/)
- [Firebase-Storage](https://firebase.google.com/docs/storage/)
- [Firebase-UI](https://firebase.google.com/docs/auth/android/firebaseui)
- [Glide](https://github.com/bumptech/glide) 
- [GSON](https://github.com/google/gson)
- [MaterialDrawer](https://github.com/mikepenz/MaterialDrawer)
- [Logger](https://github.com/orhanobut/logger)


## Screenshots
<table>
     <tr>
          <td><img height="500" src="https://user-images.githubusercontent.com/48018942/69048369-4bcbc500-0a23-11ea-8c98-6e3fd3ead6d3.jpg" /><br /><center><b>Login Screen</b></center></td>
          <td><img height="500" src="https://user-images.githubusercontent.com/48018942/69048366-4b332e80-0a23-11ea-9abd-29606d90a058.jpg" /><br /><center><b>Main Screen</b></center></td>
          <td><img height="500" src="https://user-images.githubusercontent.com/48018942/69048367-4b332e80-0a23-11ea-943b-139033dd3a86.jpg" /><br /><center><b>Upload Document screen</b></center></td>
     </tr>
</table>



### Setting up your development environment

- Download and install Git

- Fork the [Amrita Resource project](https://github.com/Chromicle/AmritaResource)

- Clone your fork of the project locally. At the command line:
    ```
    $ git clone https://github.com/YOUR-GITHUB-USERNAME/AmritaResource
    ```
- To build this project, use the `gradlew build` command

If you prefer not to use the command line, you can use Android Studio to create a new project from version control using
```
https://github.com/YOUR-GITHUB-USERNAME/AmritaResource
```
and use **Import Project** in Android Studio to build it

If there are any missing dependencies, install them first by clicking on the links provided by Android studio. Once the project is built successfully, run the project by clicking on the green arrow at the top of the screen.

## PR Instruction

This project uses [Travis CI](https://travis-ci.org/Chromicle/AmritaResource) for checking pull requests. So before committing your changes, open Terminal via android studio and run the following commands:

For Windows:  
- `gradlew clean` then  
- `gradlew assembleDebug assembleRelease` then  
- `gradlew check` then
- `gradlew build` finally
- `gradlew spotlessCheck`




NOTE: Currently sever is not working but update the features in the app
Currently I am updating the resources for CSE and ECE departments only and that too for semester 3 only. Once I get all the resources, I will update in all departments.
You can freely contribute to the project on the note that 'contributing guidelines' of the project are followed.
New ideas and suggestions are welcomed.
Happy Coding :)
