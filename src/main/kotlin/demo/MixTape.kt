package demo

import com.beust.klaxon.Klaxon
import java.io.File

fun main(args: Array<String>) {
    require(args.size == 3) { "Missing command line argument. Arg size was | ${args.size} |"}

    val inputFileData = parseInputFile(args[0])




    val changeFileData = parseChangeFile(args[1])
    val output = processChanges(changeFileData, inputFileData)



    generateOutputFile(args[2], output)

}


fun parseInputFile(inputFile: String): MixTapeData? {
    return Klaxon().parse<MixTapeData>(File(inputFile))
}

fun parseChangeFile(changeFile: String): MixTapeData? {



    
 return null
}

fun processChanges(changeFileData: MixTapeData?, inputFileData: MixTapeData?): MixTapeData? {
    return inputFileData
}

fun generateOutputFile(outputFilePath: String, outputData: MixTapeData?) {
    val output = Klaxon().toJsonString(outputData)
    val file = File(outputFilePath)
    file.writeText(output)
}

fun MixTapeData.addSongToPlaylist(song: Song, playlist: Playlist) {

}

fun MixTapeData.addPlaylistToUser() {
}
