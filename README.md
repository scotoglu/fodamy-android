## Table of Content
- [Abstract](https://github.com/scotoglu/fodamy-android#abstract)
- [Arts](https://github.com/scotoglu/fodamy-android#arts)
- [Features](https://github.com/scotoglu/fodamy-android#features)
- [Branches](https://github.com/scotoglu/fodamy-android#branches)
- [Modules](https://github.com/scotoglu/fodamy-android#modules)
- [Package Structure](https://github.com/scotoglu/fodamy-android#package-structure)
- [Arhitecture](https://github.com/scotoglu/fodamy-android#architecture)
- [Tech Stack](https://github.com/scotoglu/fodamy-android/#tech-stack)
- [Author](https://github.com/scotoglu/fodamy-android#author)

# Abstract

The fodamy-android project was developed during the training process at [Mobillium](https://www.mobillium.com). During the training, the architecture and technologies used by the company were tried to be implemented.
# Arts
 Login | Sign Up | Reset
 --- | --- | ---
 ![](https://github.com/scotoglu/fodamy-android/blob/master/art/login.jpg) | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/signup.jpg) | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/reset_password.jpg)
 
 Home | Favorites | Details | Profile | Draft |  Add Recipe | Publish
 ---| --- | --- | --- | --- | --- | --- | 
 ![](https://github.com/scotoglu/fodamy-android/blob/master/art/home.jpg) | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/favorites.jpg) | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/details.jpg)|![](https://github.com/scotoglu/fodamy-android/blob/master/art/profile.jpg)  | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/drafts.jpg) | ![](https://github.com/scotoglu/fodamy-android/blob/master/art/new_recipe.jpg) |![](https://github.com/scotoglu/fodamy-android/blob/master/art/publish_draft.jpg)  
# Features
:white_check_mark:Displays recipes <br>
:white_check_mark:Displays recipes by category <br>
:white_check_mark:Displays user profile <br>
:white_check_mark:Allow to user adding new recipes <br>
:white_check_mark:Allow to user creating recipe drafts and store them <br>
:white_check_mark:Allow to user adding images to recipes <br>
:white_check_mark:Allow to user like/dislike recipes <br>
:white_check_mark:Allow to user follow/unfollow to recipe owner <br>
:white_check_mark:Allow to user comment about recipe 

# Branches
During development I have tried to follow [Git Guidelines](https://github.com/mobillium/MobilliumGitGuidelines). Mostly epic and feature branches are created.
- <ins> master: </ins> store latest codebase
- <ins> develop:</ins> store latest development codebase and each new branch created from here.
- <ins> epic/:</ins>  if changes are large then branch start with epic.
- <ins>feature/:</ins> if changes are small then branch start with feature.

# Modules
- [App](https://github.com/scotoglu/fodamy-android/tree/master/app)
- [Data](https://github.com/scotoglu/fodamy-android/tree/master/data)
- [Domain](https://github.com/scotoglu/fodamy-android/tree/master/domain)
- [BuildSrc](https://github.com/scotoglu/fodamy-android/tree/master/buildSrc)


# Package Structure
``` 
├──app                          # View Module 
    ├── di                      # hilt di package 
    ├── ext                     # extension functions
    ├── helper                  # helper classes
    ├── ui                      # view package
    │   ├── add_recipe          # main folder for adding recipe flow
    |   │   ├── choose_photo    # gets images from camera and gallery
    |   |   |── drafts          # create recipe drafts before publishing
    |   |   |── new_recipe      # create new recipe
    |   |   |── publish_draft   # publish drafts
    |   ├── auth                # main folder for auth
    |   │   ├── login           # login process
    |   |   |── reset_password  # reset passwords
    |   |   |── signup          # new user
    |   ├── base                # base fragment, viewmodel and event 
    |   ├── category_recipes    # recipes by category
    |   ├── comments            # add and displays recipe comments
    |   ├── dialog              # dialogs for required action
    |   ├── favorites           # all favorites categories
    │   ├── home                # main folder for home screen
    |   │   ├── adapter         # common adapter used in home
    |   |   |── last_added      # displays last added recipes
    |   |   |── editor_choices  # displays editor choices recipes
    |   ├── image_popup         # displays recipe images 
    |   ├── profile             # user profile
    |   ├── recipe_details      # display recipe information
    |   ├── splash              # splash
    |   ├── walkthrough         # application usage
    |   ├── MainActivity        # single activity architecture, navigation management.
```
```
├── data                        # data module
    ├── di                      # hilt di package
    ├── local                   # room package
    |   ├── converters          # type convertes
    |   ├── dao                 # data access objects
    |   ├── database            # app database
    |   ├── local_dto           # models that used in room database
    ├── remote                  # retrofit package
    |   ├── exceptions          # custom exception based on http exception codes
    |   ├── remote_dto          # models that used in retrofit
    |   ├── services            # Api's
    ├── mapper                  # converts local and remote models to domain models
    ├── repositories            # repository implementations
    ├── utils                   # utility classes and methods
```
```
├── domain                      # domain module
    ├── models                  # models that used in app module
    ├── repositories            # repository interfaces
    ├── usecase                 # 
    ├── utils                   # utility classes and methods
```
```
├── buildSrc                    # gradle 
    ├── ConfigData              # defaultConfig datas
    ├── Dependencies            # dependencies
    ├── Versions                # dependencies versions
    ├── Plugins                 # gradle plugins
```

# Architecture
- Single Activity Architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage navigation operations between fragments.
- [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) facilitating a [seperation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface
- [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) desing principles
- Modular App Architecture allows to be developed modules in isolation, independently from other modules
# Tech Stack
- [Jetpack](https://developer.android.com/jetpack)
  - [AndroidX](https://developer.android.com/jetpack/androidx)
  - [Data-Binding](https://developer.android.com/topic/libraries/data-binding)
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) 
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  
  - [Navigation](https://developer.android.com/guide/navigation/) 
  - [Room](https://developer.android.com/topic/libraries/architecture/room)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) 
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
  - [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
 - [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
 - [Retrofit](https://square.github.io/retrofit/)
 - [Glide](https://github.com/bumptech/glide)
 - [Dagger-Hilt](https://dagger.dev/hilt/)
 - [Firebase-Crashlytics](https://firebase.google.com/docs/crashlytics/get-started?platform=android) - 
 - [FCM](https://firebase.google.com/docs/cloud-messaging) - Push Notification Services
 - [Material-Design](https://material.io/components?platform=android)
 - [Lottie-Animation](https://airbnb.io/lottie/#/android)
 - [Ktlint](https://github.com/JLLeitschuh/ktlint-gradle)
 
 # Author
 [![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/sefacotoglu/) [![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@scotoglu)
