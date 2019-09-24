## MixTape Demo

This application is a simple command line playlist demo app that consumes a json playlist, accepts a limited set of operations to be performed and produces an output file.

## Setup

This project is written in `kotlin` and leverages `gradle` build tools.  As such there are a few things you'll need to get started if you'd like to compile the code.  For convience reasons I've also included a prebuilt executable jar file.  If you'd like to skip to just running the application skip over to _Running the application_.

# Environment Setup
1.  If for some reason you aren't running some semi-recent version of JRE to get it [here](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
2.  Install `kotlin`
```
$ brew install kotlin
```
Or if you aren't down with `homebrew` there's a few alternatives [here](https://kotlinlang.org/docs/tutorials/command-line.html) 

## Running the application
The application takes 3 parameters to run:
1.  A mixtape data file -> `mixtape.json`
2.  A change file -> `changefile.json`
3.  An output file (you can name it whatever you want but the output will be json format)

Currently missing req'd files or malformed json will cause a program exit but it will do its best to handle permutations from 
currently supported operations which include:

```
1.  Add an existing song to an existing playlist.
2.  Add a new playlist for an existing user; the playlist should contain at least one existing song.
3.  Remove an existing playlist.
```

# Executing from prebuilt jar
```
$ java -jar mixtape.jar mixtape.json changefile.json <your_output_file_name.json>
```

# Compiling/building via gradle
```
$ ./gradlew run --args='mixtape.json changefile.json <your_output_file_name.json>'
```

# Post Mortem



