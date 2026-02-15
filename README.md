This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

---

## LinkedIn Post

KMP + Compose Multiplatform, From a Native iOS + Android Perspective 📱

Being a native iOS and native Android developer shapes how I evaluate cross-platform stacks. SwiftUI and Android XML/Jetpack give full platform depth, and that still matters. Native quality and control are always the benchmark. 🧭

With that mindset, Kotlin Multiplatform (KMP) was the first step that felt truly practical: keep native UI, share business logic. 🔁

Now with Compose Multiplatform (CMP), the story changes again—because UI can be shared too, without giving up mobile-first structure. 🧩

This project used:

- Kotlin Multiplatform (shared logic) ⚙️
- Compose Multiplatform (shared UI) 🎨
- Clean Architecture (data / domain / presentation) 🏗️
- MVVM + StateFlow 🔄
- Ktor + Kotlinx Serialization 🌐
- SQLDelight (offline favorites) 💾
- Coroutines + Flow ⛓️
- Voyager navigation 🧭
- Material 3 UI 🧱

✅ Why native still matters  
Native development remains the top tier for:

- full platform access 🔓
- advanced UI behaviors 🎯
- platform-specific optimizations ⚡
- deep ecosystem support 🌍

That foundation doesn’t change. 🧱

✅ What KMP + CMP adds on top  
From a native mindset, CMP feels like shared native UI, not a web wrapper.  
It brings real benefits:

- Shared UI + logic in one Kotlin codebase 🧠
- Consistent UX across platforms 🤝
- Faster iteration on features and layouts 🚀
- Less UI drift over time 📉
- Strong architecture compatibility (Clean Architecture, MVVM, Flow, etc.) 🏛️

Native iOS + Android are still the foundation and always valuable. 📲  
KMP started by sharing business logic.  
Now KMP + Compose Multiplatform makes it possible to share UI + logic without losing the mobile mindset. 🧭

For anyone who has built native apps and wants to reduce duplication while keeping quality high, this stack is worth attention 👀

🔗 Portfolio: [malindu21.github.io/devfolio](https://malindu21.github.io/devfolio/)  
💻 Source code: [github.com/malindu21/bookmanager-cmp](https://github.com/malindu21/bookmanager-cmp)
