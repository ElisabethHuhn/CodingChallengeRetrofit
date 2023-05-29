# CodingChallengeRetrofit
Coding Challenge using Retrofit and RecyclerView

# Requirements
* https://github.com/Guidebook/code-challenges/tree/master/android
* In short, make a retrofit call, and display the results in a RecyclerView

# Discussion
I started with Basic Views Activity project template in Android Studio. 
I used an Android Studio plugin to create the Data classes needed. https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
Printing the raw data from the Retrofit call is just to the LogCat
Then I started copying content from another sample project I've been building. That sample is to demonstrate a standard state of the art Android architecture and uses:
* Koin
* Compose
* MVVM with
  * ViewModel
  * Repository
  * LocalDataSource (ROOM)
  * RemoteDataSource (RetroFit)
I also borrowed some of the UI code from the last RecyclerView coding challenge I created about 2 years ago. 
I used packages to separate architecture portions of the project. eg.: UI, DI, model, repository, viewmodel. I propably wouldn't architect it this way in a real project, but it makes a coding challenge easier to read.
All that took me about an hour and a half. From there I started debugging the app, fixing what I needed to get it up.
All in all, I spent about 2 1/2 hours to get this far.

# Things I'd like to have done
* Enhanced Branch:
  * Moved StartDate, EndDate and URL to the second detail page
    * This makes the first page prettier
    * Also addressed the excessive spacing on the RecyclerView lists
  * Fixed the navigation controller bug
  * Added BUILD_TYPE to the details screen
  * Took about an hour
  * 
* I'd rather used Flows to get the Retrofit Responses back to the UI than a callback in the ViewModel space, but I ran out of time to figure it out.
* There is no testing
* Initially I was going to use Koin for DI, but something was wrong with the Repository, so rather than take the time to debug, I just removed Koin
* There is a lot of extra "stuff" from the template and the other architecture I started with. For example the FAB, navigation, and the extra event listeners. They need to be removed
* For some reason I'm getting a lot of Retrofit errors on the Pixel 3 emulator. It seems to work once, then not. I'm not sure what is going on.
  * Seems to be working on the Pixel 6 emulator
* Fixed Bugs:
  * The RecyclerView item is ugly.
  * There was a problem with the navigation, so rather than take the time to figure out what I'd done to break it, I commented it out
* Partially addressed bugs:
  * The BUILD_TYPE is underneath the RecyclerView. I ran out of time before I could figure out what I was doing wrong with the ConstraintLayout



