# CodingChallengeRetrofit
Coding Challenge using Retrofit, Picasso, and RecyclerView

# Requirements
* Written in Kotlin
* Using RecyclerView UI
* Uses Retrofit for RESTful API
* TODO Search functionality
  * Searches names, and text description for search pattern.
  * Only displays matches
* Clicking on a character name in the list brings up the Detail Fragment
  * Displaying name, image, Description
  * (I'm unsure what you meant by Title, so I displayed FirstURL)
  * Use a placeholder if no image available (The placeholder is the Simpsons Doctor)
* Two build variants are available in addition to debug and release:
  * buildWire
  * buildSimpsons
* Each variant has a different
  * build variant name
  * package name
  * url it pulls from

# Discussion
I started with Basic Views Activity project template in Android Studio. 
I used an Android Studio plugin to create the Data classes needed. https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
Printing the raw data from the Retrofit call is just to the LogCat
Then I started copying content from another sample project I've been building. That sample is to demonstrate a standard state of the art Android architecture and uses:
* Koin (Koin not used for Anywhere Coding Challenge)
* RecyclerView
* MVVM with
  * ViewModel
  * Repository
  * RemoteDataSource (RetroFit)
* Picasso for image download
I also borrowed some of the UI code from the last RecyclerView coding challenge I created about 2 years ago. 
I used packages to separate architecture portions of the project. eg.: UI, DI, model, repository, viewmodel. I propably wouldn't architect it this way in a real project, but it makes a coding challenge easier to read.

# Things I'd like to have done
* Anywhere Branch:
  * Changed the layout of second detail page, per requirements
    * This makes the first page prettier
    * Also addressed the excessive spacing on the RecyclerView lists
  * Fixed the navigation controller bug
  * Added BUILD_TYPE to the details screen
  * 
* I'd rather used Flows rather than LiveData to get the Retrofit Responses back to the UI than a callback in the ViewModel space
* There is no testing
* Initially I was going to use Koin for DI, but something was wrong with the Repository, so rather than take the time to debug, I just removed Koin
* There is a lot of extra "stuff" from the template and the other architecture I started with. For example the FAB, navigation, and the extra event listeners. They need to be removed
* For some reason I'm getting a lot of Retrofit errors on the Pixel 3 emulator. It seems to work once, then not. I'm not sure what is going on.
  * Seems to be working on the Pixel 6 emulator
* The Retrofit call is made every time the FirstFragment is displayed, and this is overkill for an unchanging API. However I left it as backend data can change in other applications. However, a more efficient way would be to just make the call once when the app first comes up, and then just trigger the RecyclerView on search. Some ways to do this include:
  * notifyItemRangeChanged()
  * resetting the RecyclerView adapter



