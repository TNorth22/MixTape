package demo.mixtape

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import java.io.File

fun main(args: Array<String>) {
    check(args.size == 3) { "Missing command line argument. Arg size was | ${args.size} |"}

    println("Starting to process...")
    val mixTapeData = parseInputFile(args[0])
    val changeData = parseChangeFile(args[1])
    val updatedMixTapeData = processChanges(changeData, mixTapeData)
    generateOutputFile(args[2], updatedMixTapeData)
    println("All done!")
}

fun parseInputFile(inputFile: String): MixTapeData {
    val file = File(inputFile)
    check(file.exists()) { "parseInputFile: Input file at path | $inputFile | doesn't exist or isn't valid"}

    return Klaxon().parse<MixTapeData>(file)!!
}

fun parseChangeFile(changeFile: String): Array<Operation>? {
    val file = File(changeFile)
    check(file.exists()) { "parseChangeFile: Change file at path | $changeFile | doesn't exist or isn't valid"}

    return Klaxon().parseArray<Operation>(File(changeFile))?.toTypedArray()
}

fun processChanges(opsToProcess: Array<Operation>?, inputFileData: MixTapeData): MixTapeData {
    opsToProcess?.forEach {
        when (it.type) {
            Type.ADD_PLAYLIST_TO_USER -> addPlaylistToUser(it as AddPlaylistToUser, inputFileData)
            Type.ADD_SONG_TO_PLAYLIST -> addSongToPlayList(it as AddSongToPlaylist, inputFileData)
            Type.REMOVE_PLAYLIST -> removePlayList(it as RemovePlaylist, inputFileData)
        }
    }
    return inputFileData
}

fun addPlaylistToUser(op: AddPlaylistToUser, data: MixTapeData) {
    val user = data.users.singleOrNull { it.id == op.user.id }
    if (user == null) {
        println("addPlaylistToUser: No user found for id | ${op.user.id} |")
        return
    }

    val existingPlaylist = data.playlists.singleOrNull { it.id == op.playlist.id }
    if (existingPlaylist != null) {
        println("addPlaylistToUser: Existing playlist found with id | ${op.playlist.id} |")
        return
    }

    if (op.playlist.song_ids.isEmpty()) {
        println("addPlaylistToUser: Unable to add playlist | ${op.playlist.id} | had no songs")
        return
    }

    op.playlist.user_id = user.id
    data.addPlaylist(op.playlist)
}

fun addSongToPlayList(op: AddSongToPlaylist, data: MixTapeData) {
    val song = data.songs.singleOrNull { it.id == op.song.id }
    if (song == null) {
        println("addSongToPlayList: Unable to find song | ${op.song.id} | in catalog" )
        return
    }

    val playlist = data.playlists.singleOrNull { it.id == op.playlist.id }
    if (playlist == null) {
        println("addSongToPlayList: Unable to find playlist | ${op.playlist.id} | in catalog")
        return
    }

    data.addSongToPlaylist(song, playlist)
}

fun removePlayList(op: RemovePlaylist, mixTapeData: MixTapeData) {
    val existingPlaylist = mixTapeData.playlists.singleOrNull { op.playlist.id == it.id }

    if (existingPlaylist == null) {
        println("removePlayList: No playlist found to remove for id | ${op.playlist.id} |")
        return
    }

    mixTapeData.removePlaylist(existingPlaylist)
}

fun generateOutputFile(outputFilePath: String, outputData: MixTapeData?) {
    val outputString = StringBuilder(Klaxon().toJsonString(outputData))
    val prettyString = (Parser.default().parse(outputString) as JsonObject).toJsonString(true)

    val file = File(outputFilePath)
    file.writeText(prettyString)
}