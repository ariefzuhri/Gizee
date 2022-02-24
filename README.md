# Gizee

[![CircleCI][build-shield]][build-url]
[![GitHub release][release-shield]][release-url]
[![MIT License][license-shield]][license-url]

<a><img src="https://i.imgur.com/TySh7fD.png"/></a>

**Calculate calories and nutrients in your meals using Gizee.** This Android-based app is powered by [Nutritionix API](https://developer.nutritionix.com/) and entirely well-written in Kotlin. Gizee is built using Clean Architecture which makes it robust, flexible, and maintainable. This app was developed as a capstone project in [Dicoding](https://www.dicoding.com)'s [Menjadi Android Developer Expert](https://www.dicoding.com/academies/165) (Become an Android Developer Expert) class.

## Download
Check out the [release page](https://github.com/ariefzuhri/Gizee/releases) and download the latest apk.

## MAD Scorecard
<a><img src="https://i.imgur.com/ma8dpGx.png" /></a>

## Architecture and Tech-stack
- Native Android with Kotlin
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) (three layers separation: presentation, domain, and data) with [MVVM pattern](https://developer.android.com/jetpack/guide#recommended-app-arch)
- Android Architecture Components, specifically [Room](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), and [Material Components](https://material.io/develop/android)
- Fetch data from the network and a Room database using [Coroutines Flow](https://developer.android.com/kotlin/coroutines)
- Android library modular architecture include [dynamic-feature](https://developer.android.com/guide/playcore/feature-delivery)
- Continuous integration and delivery with [CircleCI](https://circleci.com/)
- [Retrofit](https://github.com/square/retrofit), REST client framework
- [Moshi](https://github.com/square/moshi), parsing the JSON format
- [Koin](https://github.com/InsertKoinIO/koin), dependency injection framework
- [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics), real-time crash reporter
- [Firebase Analytics](https://firebase.google.com/docs/analytics), provides insight on app usage and user engagement
- [Chucker](https://github.com/ChuckerTeam/chucker), HTTP inspector
- [Timber](https://github.com/JakeWharton/timber) and [Logger](https://github.com/orhanobut/logger), logging utility
- [LeakCanary](https://github.com/square/leakcanary), memory leak detection
- [OkHttp CertificatePinner](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-certificate-pinner/), certificate pinning
- [SQLCipher](https://github.com/sqlcipher/android-database-sqlcipher), database encryption
- [AAChartCore](https://github.com/AAChartModel/AAChartCore-Kotlin), data visualization chart framework
- [Glide](https://github.com/bumptech/glide), image loading and caching
- [Facebook Shimmer](https://github.com/facebook/shimmer-android), shimmering effect on loading screen
- [Lottie](https://github.com/airbnb/lottie-android), parsing animation natively
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView), image view with rounded corners

## Configuration
Firstly, clone this repository and import it into Android Studio (`git clone https://github.com/ariefzuhri/Gizee.git`).

### Setup API Key
1. Get your [Nutritionix API](https://developer.nutritionix.com/) app id and app key.
2. Open`./core` in root directory and create a new file named `keys.properties`.
3. Put your app id and key in `keys.properties` file by adding the following lines:
```
NUTRITIONIX_APP_ID=YOUR_APP_ID
NUTRITIONIX_APP_KEY=YOUR_APP_KEY
```

### Setup Firebase Crashlytics
1. Register the app in the [Firebase Console](https://console.firebase.google.com/).
2. Get your own `google-services.json` file and move it into Android app module root directory (`./app`).

### Setup Certificate Pinning
1. Open your `keys.properties` file in `./core`.
2. Add the following new lines (you can get the public key hashes [here](https://www.ssllabs.com/analyze.html?d=trackapi.nutritionix.com&s=3.214.2.226&latest)):
```
NUTRITIONIX_BASE_URL=https://trackapi.nutritionix.com/v2/
NUTRITIONIX_PUBLIC_KEY_1=fajdlzqjFkH3fU8/NrjW0d4cFANUzh/4HstyvlVaTqM=
NUTRITIONIX_PUBLIC_KEY_2=JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=
NUTRITIONIX_PUBLIC_KEY_3=++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=
NUTRITIONIX_PUBLIC_KEY_4=KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=
```

### Setup Database Encryption
1. Open your `keys.properties` file in `./core`.
2. Add `DATABASE_PASSPHRASE=YOUR_PASSPHRASE`.

### Setup CircleCI
1. You need to fork this project first before setting up your own CircleCI environment. 
2. Follow the instructions [here](https://circleci.com/docs/2.0/getting-started/#setting-up-circleci) to set it up.
3. Set all environment variables in project settings. You can see how to make it works [here](https://circleci.com/docs/2.0/env-vars/#setting-an-environment-variable-in-a-project). The environment variables include all variables defined in `keys.properties` and `keystore.properties` files plus the following 2 variables:
```
DEBUG_KEYSTORE={your own default debug.keystore file encoded in base64}
GOOGLE_SERVICES={your own google-services.json file encoded in base64}
```

### Setup Signing Configuration (optional)
*This step is optional for learning purposes. Since we don't need to publish the app to Google Play, you may skip this step and remove the signing configuration in the app-level build.gradle.*
1. First, you need your own keystore. If you don't already have one, you can read how to create it [here](https://developer.android.com/studio/publish/app-signing#generate-key).
2. Afterwards, create a new file named `keystore.properties` in your root directory.
3. Add the following lines in your `keystore.properties`:
```
STORE_FILE=YOUR_KEYSTORE_PATH
STORE_PASSWORD=YOUR_STORE_PASSWORD
KEY_ALIAS=_YOUR KEY_ALIAS
KEY_PASSWORD=YOUR_KEY_PASSWORD
```
*Use relative path to specify your keystore file path. You can learn [here](https://networkencyclopedia.com/relative-path/).*

## 🤝 Support
Any contributions, issues, and feature requests are welcome.

Give a ⭐️ if you like this project.

## License
This project is licensed under the MIT License. See the [`LICENSE`](https://github.com/ariefzuhri/Gizee/blob/master/LICENSE) file for details.

## Acknowledgments
- [Arman Rokni](https://lottiefiles.com/armanrokni) on [Spaceship Empty Searching](https://lottiefiles.com/4011-spaceship-empty-searching)
- [Ehsan](https://lottiefiles.com/ehsan) on [Empty State – Heart](https://lottiefiles.com/46771-empty-state-heart)
- [Ravi Tamada](https://www.androidhive.info/author/admin/) on [Android Working with Bottom Sheet](https://www.androidhive.info/2017/12/android-working-with-bottom-sheet/)
- [Yuichi Fujiki](https://yfujiki.medium.com/) on [How to Store/Use Sensitive Information in Android Development](https://yfujiki.medium.com/how-to-store-use-sensitive-information-in-android-development-bc352892ece7)
- [CSS Nutrition Facts Label](https://jsfiddle.net/thL6j/)
- [Figma](https://www.figma.com), [Feather Icons](https://www.figma.com/community/plugin/744047966581015514/Feather-Icons), [Material Design Icons (Community)](https://www.figma.com/community/plugin/775671607185029020/Material-Design-Icons-(Community))
- [Freepik](https://www.freepik.com), [pch.vector](https://www.freepik.com/pch-vector)
- [NestedScrollableHost](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)
- [Nutritionix](https://www.nutritionix.com/)
- [Shields.io](https://shields.io/)

## To-do List
- [ ] Add unit tests
- [ ] Add instrumented tests
- [ ] Add shimmer effect in favorites and history
- [ ] Support storing search results (foods)

[release-shield]: https://img.shields.io/github/v/release/ariefzuhri/gizee?include_prereleases&style=for-the-badge
[release-url]: https://github.com/ariefzuhri/Gizee/releases
[build-shield]: https://img.shields.io/circleci/build/github/ariefzuhri/Gizee?style=for-the-badge
[build-url]: https://circleci.com/gh/ariefzuhri/Gizee
[license-shield]: https://img.shields.io/github/license/ariefzuhri/gizee?style=for-the-badge
[license-url]: https://github.com/ariefzuhri/Gizee/blob/master/LICENSE
