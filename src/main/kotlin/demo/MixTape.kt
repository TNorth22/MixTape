package demo

import com.beust.klaxon.Klaxon
import java.io.File
import demo.Type.*

fun main(args: Array<String>) {
    check(args.size == 3) { "Missing command line argument. Arg size was | ${args.size} |"}

    val mixTapeData = parseInputFile(args[0])
    val changeData = parseChangeFile(args[1])
    val updatedMixTapeData = processChanges(changeData, mixTapeData)
    generateOutputFile(args[2], updatedMixTapeData)

    println("All Done!")
}


fun parseInputFile(inputFile: String): MixTapeData {
    println("Parsing input file $inputFile...")

    val file = File(inputFile)
    check(file.exists()) { "Input file at path | $inputFile | doesn't exist or isn't valid"}

    return Klaxon().parse<MixTapeData>(file)!!
}

fun parseChangeFile(changeFile: String): Array<Operation>? {
    println("Parsing change file $changeFile...")

    val file = File(changeFile)
    check(file.exists()) { "Change file at path | $changeFile | doesn't exist or isn't valid"}

    return Klaxon().parseArray<Operation>(File(changeFile))?.toTypedArray()
}

fun processChanges(opsToProcess: Array<Operation>?, inputFileData: MixTapeData): MixTapeData {
    println("Processing changes...")

    var processedData = inputFileData
    opsToProcess?.forEach {
        when (it.type) {
            ADD_PLAYLIST_TO_USER -> addPlaylistToUser(it as AddPlaylistToUser, processedData)
            ADD_SONG_TO_PLAYLIST -> addSongToPlayList(it as AddSongToPlaylist, processedData)
            REMOVE_PLAYLIST -> removePlayList(it as RemovePlaylist, processedData)
        }
    }
    return processedData
}

fun addPlaylistToUser(op: AddPlaylistToUser, mixTapeData: MixTapeData) {
    val user = mixTapeData.users.single { it.id == op.user.id }

    assert(op.playlist.song_ids!!.isNotEmpty()) { "Unable to add playlist | ${op.playlist.id} | had no songs"}
    assert(user != null) { "No user found for | ${op.user.id} |"}

    op.playlist.user_id = user.id
    mixTapeData.addPlaylist(op.playlist)
}

fun addSongToPlayList(op: AddSongToPlaylist, data: MixTapeData) {
    val song = data.songs.single() { it.id == op.song.id }
    val playlist = data.playlists.single() { it.id == op.playlist.id }

    assert(song != null) { "Unable to find song | ${song.id} | in catalog" }
    assert (playlist != null) { "Unable to find playlist | ${playlist.id} | in catalog"}

    data.addSongToPlaylist(song, playlist)
}

fun removePlayList(op: RemovePlaylist, mixTapeData: MixTapeData) {
    val playlist = mixTapeData.playlists.single { op.playlist.id == it.id }

    assert(playlist != null) { "No playlist found to remove for id | ${op.playlist.id} |"}

    mixTapeData.removePlaylist(playlist)
}

fun generateOutputFile(outputFilePath: String, outputData: MixTapeData?) {
    println("Generating output file $outputFilePath...")

    val output = Klaxon().toJsonString(outputData)
    val file = File(outputFilePath)
    file.writeText(output)
}