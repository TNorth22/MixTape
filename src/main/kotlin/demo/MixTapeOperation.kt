package demo


enum class Type {
    ADD_SONG_TO_PLAYLIST,
    ADD_PLAYLIST_TO_USER,
    REMOVE_PLAYLIST
}

data class MixTapeOperation(val type: Type)