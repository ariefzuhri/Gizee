# Gizee

[![CircleCI][build-shield]][build-url]
[![GitHub release][release-shield]][release-url]
[![MIT License][license-shield]][license-url]

<img src="https://i.imgur.com/TySh7fD.png" />

**Calculate calories and nutrients in your meals using Gizee.** This Android-based app is powered by [Nutritionix API](https://developer.nutritionix.com/) and entirely well-written in Kotlin. Gizee is built using Clean Architecture which makes it robust, flexible, and maintainable. This app was developed as a capstone project in [Dicoding](https://www.dicoding.com)'s [Become an Android Developer Expert](https://www.dicoding.com/academies/165) *(Menjadi Android Developer Expert)* class.

## Download
Check out the [release page](https://github.com/ariefzuhri/Gizee/releases) and download the latest apk.

## MAD Scorecard
<img src="https://i.imgur.com/ma8dpGx.png" />

## Architecture and Tech-stack
- [Kotlin](https://developer.android.com/kotlin), modern, concise, and safe programming language, [recommended by Google](https://developer.android.com/kotlin/first).
- Architectural and design patterns:
  - [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), makes our apps highly testable and independent of any framework by separating the code into three layers, such as presentation, domain, and data.
  - [MVVM pattern](https://developer.android.com/jetpack/guide#recommended-app-arch), handles how data is displayed to the user from the use case.
  - [Modular architecture](https://developer.android.com/topic/modularization), makes features more independent and each feature serves a clear purpose (including the use of [dynamic-feature](https://developer.android.com/guide/playcore/feature-delivery)).
  - [Single-activity architecture](https://www.youtube.com/watch?v=2k8x8V77CrU), better user experience with a single activity.
- [Android Jetpack](https://developer.android.com/jetpack), a set of libraries to simplify the code and focus on building a robust and useful app, specifically:
  - [Coroutines Flow](https://developer.android.com/kotlin/flow), handle streams of data asynchronously used when fetching data from the network and local.
  - [Room](https://developer.android.com/topic/libraries/architecture/room), a robust database that provides an abstraction layer over the SQLite.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), persists UI state in a lifecycle-aware way.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), observes data changes in a lifecycle-aware way.
  - [Material Design 2](https://m2.material.io/develop/android), provides the UI components and the design guidelines.
- Third-party libraries:
  - [Retrofit](https://github.com/square/retrofit), REST client framework.
  - [Moshi](https://github.com/square/moshi), parsing the JSON format.
  - [Koin](https://github.com/InsertKoinIO/koin), dependency injection framework.
  - [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics), real-time crash reporter.
  - [Firebase Analytics](https://firebase.google.com/docs/analytics), provides insight on app usage and user engagement.
  - [Chucker](https://github.com/ChuckerTeam/chucker), HTTP inspector.
  - [Timber](https://github.com/JakeWharton/timber), [Logger](https://github.com/orhanobut/logger), logging utility.
  - [LeakCanary](https://github.com/square/leakcanary), memory leak detection.
  - [OkHttp CertificatePinner](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-certificate-pinner), certificate pinning.
  - [SQLCipher](https://github.com/sqlcipher/android-database-sqlcipher), database encryption.
  - [AAChartCore](https://github.com/AAChartModel/AAChartCore-Kotlin), data visualization chart framework.
  - [Coil](https://github.com/coil-kt/coil), image loading and caching.
  - [Facebook Shimmer](https://github.com/facebook/shimmer-android), shimmering effect on loading screen.
  - [Lottie](https://github.com/airbnb/lottie-android), parsing animation natively.
- Tools:
  - [Android Studio](https://developer.android.com/studio), the official IDE for Android development.
  - [Figma](https://www.figma.com), a design tool used to develop the app descriptions and design the UI.
  - [CircleCI](https://circleci.com), the continuous integration & delivery tools.
- Resources:
  - [Feather Icons](https://www.figma.com/community/plugin/744047966581015514/Feather-Icons), [Material Design Icons (Community)](https://www.figma.com/community/plugin/775671607185029020/Material-Design-Icons-(Community)), provides all the icon needs throughout the app.
  - [LottieFiles](https://lottiefiles.com), provides free Lottie animations used for empty history and empty favorites.
  - [Freepik](https://www.freepik.com), provides the illustration on the homepage.
  
## Project Configuration
Firstly, clone this repository and import it into Android Studio (`git clone https://github.com/ariefzuhri/Gizee.git`).

### Setup API Key
1. Get your [Nutritionix API](https://developer.nutritionix.com) app id and app key.
2. Create a new file named `secrets.properties` in the project root directory.
3. Put your app id and key in `secrets.properties` file by adding the following lines:
```
NUTRITIONIX_APP_ID=YOUR_APP_ID
NUTRITIONIX_APP_KEY=YOUR_APP_KEY
```

### Setup Firebase Crashlytics
To set up Crashlytics, you just need to register this app with the Firebase Project.

#### Create a Firebase Project ####
1. Open the [Firebase Console](https://console.firebase.google.com/) and click **Add project** (or select your existing Firebase project).
2. When creating a new Firebase project, just follow the instructions and keep the **Enable Google Analytics for this project** option switched on.
3. When using an existing Firebase project, ensure that Google Analytics is enabled (see the instructions in number 2 [here](https://firebase.google.com/docs/crashlytics/get-started?platform=android#before-you-begin)).

#### Register the App ####
1. In **Project Overview**, click **Add app** button and select the **Android** icon to register your Android app.
2. Make sure the **Android package name** matches your app ID in the Android Studio project and you are not required to include the **Debug signing certificate SHA-1**.
3. Download the `google-services.json` file and move it into Android app module root directory (`./app`).
4. Skip the third step about **Add Firebase SDK** as the project already has the dependencies.

### Setup Certificate Pinning
1. Open the `secrets.properties` file.
2. Add the following new lines (you can get the public key hashes [here](https://www.ssllabs.com/analyze.html?d=trackapi.nutritionix.com)):
```
NUTRITIONIX_BASE_URL=https://trackapi.nutritionix.com/v2/
NUTRITIONIX_PUBLIC_KEY_1=fajdlzqjFkH3fU8/NrjW0d4cFANUzh/4HstyvlVaTqM=
NUTRITIONIX_PUBLIC_KEY_2=JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=
NUTRITIONIX_PUBLIC_KEY_3=++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=
NUTRITIONIX_PUBLIC_KEY_4=KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=
```

### Setup Database Encryption
1. Open the `secrets.properties` file.
2. Add the following new line:
```
DATABASE_PASSPHRASE=YOUR_ANY_PASSPHRASE
```

### Setup CircleCI
1. You need to fork this project first before setting up your own CircleCI environment.
2. After that, [register](https://circleci.com/signup) or [connect](https://circleci.com/docs/github-integration/#connect-a-github-account) your CircleCI account with GitHub.
3. Follow the instructions [here](https://circleci.com/docs/getting-started/#connect-code) to set it up. In step number 2, select **Fastest** instead of **Fast**.
4. Set all environment variables in project settings. You can see how to make it works [here](https://circleci.com/docs/set-environment-variable/#set-an-environment-variable-in-a-project). The environment variables include all variables defined in `secrets.properties` file plus the following 2 variables:
```
DEBUG_KEYSTORE={your own default debug.keystore file encoded in base64}
GOOGLE_SERVICES={your own google-services.json file encoded in base64}
```
*To encode files in base64 you can use OpenSSL by downloading from [here](https://slproweb.com/products/Win32OpenSSL.html) (the light version is sufficient) and see how to run the command [here](https://superuser.com/a/120815).*

### Setup Signing Configuration (optional)
*This step is optional for learning purposes. Since we don't need to publish the app to Google Play, you may skip this step and comment/remove the signing configuration in the app-level build.gradle.*
1. First, you need your own keystore. If you don't already have one, you can read how to create it [here](https://developer.android.com/studio/publish/app-signing#generate-key).
2. Open the `secrets.properties` file and add the following lines:
```
STORE_FILE=YOUR_KEYSTORE_PATH
STORE_PASSWORD=YOUR_STORE_PASSWORD
KEY_ALIAS=_YOUR KEY_ALIAS
KEY_PASSWORD=YOUR_KEY_PASSWORD
```
*To specify a file path, you can write its absolute (the full path) or just its relative path (the short version). See the details [here](https://web.archive.org/web/20220918144205/https://networkencyclopedia.com/relative-path).*

## 🤝 Support
Any contributions, issues, and feature requests are welcome.

Give a ⭐️ if you like this project.

## License
This project is licensed under the MIT License. See the [`LICENSE`](https://github.com/ariefzuhri/Gizee/blob/master/LICENSE) file for details.

## Acknowledgments
- — on [CSS Nutrition Facts Label](https://jsfiddle.net/thL6j)
- [Android Open Source Project](https://source.android.com) on [NestedScrollableHost](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)
- [Arman Rokni](https://lottiefiles.com/armanrokni) on [Spaceship Empty Searching](https://lottiefiles.com/4011-spaceship-empty-searching)
- [Ehsan](https://lottiefiles.com/ehsan) on [Empty State – Heart](https://lottiefiles.com/46771-empty-state-heart)
- [Igor Wojda](https://medium.com/@igorwojda) on [Multiple Ways of Defining Clean Architecture Layers](https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a)
- [pch.vector](https://www.freepik.com/pch-vector) on diet composition illustration
- [Ravi Tamada](https://www.androidhive.info/author/admin) on [Android Working with Bottom Sheet](https://www.androidhive.info/2017/12/android-working-with-bottom-sheet)
- [Yuichi Fujiki](https://yfujiki.medium.com) on [How to Store/Use Sensitive Information in Android Development](https://yfujiki.medium.com/how-to-store-use-sensitive-information-in-android-development-bc352892ece7)

[release-shield]: https://img.shields.io/github/v/release/ariefzuhri/gizee?include_prereleases&style=for-the-badge
[release-url]: https://github.com/ariefzuhri/Gizee/releases
[build-shield]: https://img.shields.io/circleci/build/github/ariefzuhri/Gizee?style=for-the-badge
[build-url]: https://circleci.com/gh/ariefzuhri/Gizee
[license-shield]: https://img.shields.io/github/license/ariefzuhri/gizee?style=for-the-badge
[license-url]: https://github.com/ariefzuhri/Gizee/blob/master/LICENSE
