package demo.mixtape

import com.beust.klaxon.TypeFor


enum class Type() {
    ADD_SONG_TO_PLAYLIST,
    ADD_PLAYLIST_TO_USER,
    REMOVE_PLAYLIST
}

@TypeFor(field = "type", adapter = OperationAdapter::class)
open class Operation(val type: Type)
data class AddSongToPlaylist(val song: Song, val playlist: Playlist): Operation(Type.ADD_SONG_TO_PLAYLIST)
data class AddPlaylistToUser(val playlist: Playlist, val user: User): Operation(Type.ADD_PLAYLIST_TO_USER)
data class RemovePlaylist(val playlist: Playlist): Operation(Type.REMOVE_PLAYLIST)
