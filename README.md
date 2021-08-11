# Gizee
[![CircleCI][build-shield]][build-url]
[![MIT License][license-shield]][license-url]

<a href="#"><img src="https://i.imgur.com/TySh7fD.png" /></a>

**Calculate calories and nutrients in your meals using Gizee.** This Android-based app is powered by [Nutritionix API](https://developer.nutritionix.com/) and fully well-written in Kotlin. Gizee is built using Clean Architecture which makes it robust, flexible, and maintainable. This app was developed as a capstone project in [Dicoding](https://www.dicoding.com)'s [Menjadi Android Developer Expert](https://www.dicoding.com/academies/165) (Become an Android Developer Expert) class.

## Download
Check out the [release page](https://github.com/ariefzuhri/Gizee/releases) and download the latest apk.

## MAD Scorecard
<a href="#"><img src="https://i.imgur.com/ma8dpGx.png" /></a>

## Architecture and Tech-stack
- Clean Architecture (three layers separation; presentation, domain, and data) with [MVVM](https://developer.android.com/jetpack/guide)
- 100% Kotlin
- Android Minimum SDK API 21
- Android Jetpack ([Room](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), and [Material Components](https://material.io/develop/android))
- Fetch data from the network and a Room database using [Coroutines Flow](https://developer.android.com/kotlin/coroutines)
- Android library modular architecture with [dynamic-feature](https://developer.android.com/guide/playcore/feature-delivery)
- [Retrofit](https://github.com/square/retrofit), REST client framework
- [Moshi](https://github.com/square/moshi), parsing the JSON format
- [OkHttp](https://github.com/square/okhttp), certificate pinning
- [Koin](https://github.com/InsertKoinIO/koin), dependency injection framework
- [Firebase Crashlytics](https://github.com/firebase/firebase-android-sdk/tree/master/firebase-crashlytics), real-time crash reporter
- [Chucker](https://github.com/ChuckerTeam/chucker), HTTP inspector
- [Timber](https://github.com/JakeWharton/timber), logging utility
- [Logger](https://github.com/orhanobut/logger), logging utility for debug only
- [LeakCanary](https://github.com/square/leakcanary), memory leak detection
- [SQLCipher](https://github.com/sqlcipher/sqlcipher), database encryption
- [AAChartCore](https://github.com/AAChartModel/AAChartCore-Kotlin), data visualization chart framework
- [Glide](https://github.com/bumptech/glide), image loading and caching
- [Facebook Shimmer](https://github.com/facebook/shimmer-android), shimmering effect on loading screen
- [Lottie](https://github.com/airbnb/lottie-android), parsing animation natively
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView), image view with rounded corners

## Configuration
Firstly, clone this repository and import it into Android Studio (`git clone https://github.com/ariefzuhri/Gizee.git`).

### Setup API Key
1. Get your [Nutritionix API](https://developer.nutritionix.com/) app id and app key
2. Open`./core` in root directory and create a new file named `keys.properties`
3. Put your app id and key in `keys.properties` file by adding the following lines
```
NUTRITIONIX_APP_ID=YOUR_APP_ID
NUTRITIONIX_APP_KEY=YOUR_APP_KEY
```

### Setup Firebase Crashlytics
1. Register the app in the [Firebase Console](https://console.firebase.google.com/)
2. Get your own `google-services.json` file and move it into Android app module root directory (`./app`)

### Setup Certificate Pinning
1. Open your `keys.properties` file in `./core`
2. Add the following new lines (you can get the public key hashes [here](https://www.ssllabs.com/analyze.html?d=trackapi.nutritionix.com&s=3.214.2.226&latest)):
```
NUTRITIONIX_BASE_URL=https://trackapi.nutritionix.com/v2/
NUTRITIONIX_PUBLIC_KEY_1=fajdlzqjFkH3fU8/NrjW0d4cFANUzh/4HstyvlVaTqM=
NUTRITIONIX_PUBLIC_KEY_2=JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=
NUTRITIONIX_PUBLIC_KEY_3=++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=
NUTRITIONIX_PUBLIC_KEY_4=KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=
```

### Setup Database Encryption
1. Open your `keys.properties` file in `./core`
2. Add `DATABASE_PASSPHRASE=YOUR_DATABASE_PASSPHRASE`

## License
This project is licensed under the MIT License. See the [`LICENSE`](https://github.com/ariefzuhri/Gizee/blob/master/LICENSE) file for details.

## Acknowledgments
- [CSS Nutrition Facts Label](http://jsfiddle.net/thL6j/)
- [Freepik](https://www.freepik.com)
- [How to store/use sensitive information in Android development](https://yfujiki.medium.com/how-to-store-use-sensitive-information-in-android-development-bc352892ece7)
- [NestedScrollableHost](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)
- [Nutritionix](https://www.nutritionix.com/)
- [pch.vector](https://www.freepik.com/pch-vector)
- [Shields.io](https://shields.io/)

## 🤝 Support
Any contributions, issues, and feature requests are welcome.

Give a ⭐️ if you like this project.

[build-shield]: https://img.shields.io/circleci/build/github/ariefzuhri/Gizee?style=for-the-badge
[build-url]: https://circleci.com/gh/ariefzuhri/Gizee
[license-shield]: https://img.shields.io/github/license/ariefzuhri/gizee?style=for-the-badge
[license-url]: https://github.com/ariefzuhri/Gizee/blob/master/LICENSE
