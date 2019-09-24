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
*Or if you aren't down with `homebrew` there's a few alternatives [here](https://kotlinlang.org/docs/tutorials/command-line.html) 

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

## Post Mortem

Obviously this solution doesn't scale paricularly well.  File I/O is expensive, as is array-based lookups and the footprint of doing everything in memory has the potential to blow up and/or really slow down.

# Solution #1

Lets assume this app has the live offline without any connectivity to the real world.  We could:
1. Convert `mixtape.json` to a db file instead of shoving it all into an array allowing for less memory overhead and performant lookups
2. We could do the same for `changefile.json` and chunk it up into a temp db
3. Changes could be processed/written one entry at a time or paginated in chunks via the db for negligable memory footprint
4. The same iterative approach could be done with the output file - writing one entry at a time, or for less overall fetches we could leverage a paginated solution

# Solution #2

Assuming the app has internet connectivity:
1.  `Mixtape.json` lives in the cloud
2.  The client could leverage a sync'ing mechanism to keep its local db when offline.  I'd probably leverage a library off the shelf for this like [Firebase](https://firebase.google.com/products/realtime-database)
3.  Depending on size/complexity of change files I may opt to offload the processing to a server somewhere and push the updated mixtape back to the device once complete.
