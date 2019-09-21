package demo

import com.beust.klaxon.JsonArray

data class Playlist(var id: String, var user_id: String, var song_ids: List<String>)