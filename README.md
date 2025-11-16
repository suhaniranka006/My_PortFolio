# My Portfolio App (Jetpack Compose)

A dynamic, fully native Android portfolio app built with Jetpack Compose. This app displays my projects, skills, and experience with interactive, animated UI components—a practical showcase of modern Android development.

## 🚩 Key Features

- **Animated Splash Screen**: Sound, custom glow, and multiple sequential animations.
- **Diamond Menu Home Screen**: 4-button custom animated layout for navigation.
- **3D Carousel (Milestones)**: Coverflow-style HorizontalPager for achievements and education.
- **Video Projects**: Showcase with in-app video player (AndroidView interop, lifecycle management).
- **Expandable Timeline**: LazyColumn with smooth expanding cards for app updates.
- **Typewriter Contact**: Typewriter text effect, intent-based contact actions (Email, WhatsApp, LinkedIn, GitHub), animated "View Resume" button.
- **Skills Grid**: Dynamic two-column LazyVerticalGrid with spanning category headers.

## 🛠 Tech Stack

| Category            | Technology & Libraries                                         |
|---------------------|---------------------------------------------------------------|
| UI                  | Jetpack Compose, Canvas, AnimatedVisibility, animateContentSize|
| Navigation          | Jetpack Navigation for Compose                                |
| State Management    | MVVM Style, Kotlin Coroutines                                 |
| Animation           | animateFloatAsState, rememberInfiniteTransition, graphicsLayer |
| Layout              | HorizontalPager, LazyVerticalGrid, LazyColumn                 |
| Media/Interop       | AndroidView, MediaPlayer, Intent                              |
| Lifecycle           | LaunchedEffect, DisposableEffect                              |
