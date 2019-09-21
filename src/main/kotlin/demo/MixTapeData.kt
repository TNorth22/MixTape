package demo

import com.beust.klaxon.JsonArray

data class MixTapeData(val users: List<User>, val playlists: List<Playlist>, val songs: List<Song>)