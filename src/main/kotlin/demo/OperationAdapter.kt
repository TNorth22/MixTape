package demo

import com.beust.klaxon.TypeAdapter
import kotlin.reflect.KClass

class OperationAdapter: TypeAdapter<Operation> {
    override fun classFor(type: Any): KClass<out Operation> = when(type as String) {
        "ADD_SONG_TO_PLAYLIST" -> AddSongToPlaylist::class
        "ADD_PLAYLIST_TO_USER" -> AddPlaylistToUser::class
        "REMOVE_PLAYLIST" -> RemovePlaylist::class
        else -> throw IllegalArgumentException("Unknown type: $type")
    }
}