This is an example Ratpack app that:

1. Is implemented in Java
2. Use Guice for dependency injection
3. Uses the `ratpack` Gradle plugin
4. Uses SpringSource's SpringLoaded for runtime reloading of classes during development

## Getting Started

Check this project out, cd into the directory and run:

    ./gradlew run

This will start the ratpack app in a development mode. In your browser go to `http://localhost:5050`.

The Gradle Ratpack plugin builds on the Gradle Application plugin. This means it's easy to create a standalone
distribution for your app.

Run:

    ./gradlew installApp
    cd build/install/example-ratpack-gradle-java-app
    bin/example-ratpack-gradle-java-app

Your app should now be running (see http://gradle.org/docs/current/userguide/application_plugin.html) for more on what
the Gradle application plugin can do for you.

## Development time reloading

Most application classes can be changed at runtime without needing to restart the application. This is made possible by
[SpringSource's SpringLoaded](https://github.com/SpringSource/spring-loaded) library.

If running the application via `./gradlew run`, you must run `./gradlew classes` in another shell in order for your source
code changes to be compiled.

## IDEA integration

The Ratpack Gradle plugin has special support for IntelliJ IDEA. To open the project in IDEA, run:

    ./gradlew idea

This will generate a `.ipr` file that you can use to open the project in IDEA.

In the “Run” menu, you will find a run configuration for launching the Ratpack app from within your IDE.

Hot reloading is supported in this mode. IDEA does not automatically compile source code changes while the Ratpack app is
running. After making a change you must make the project to have the changes take effect (⌘9 on Mac OS).

## More on Ratpack

To learn more about Ratpack, visit http://www.ratpack.io
