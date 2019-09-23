package demo.mixtape

data class MixTapeData(var users: ArrayList<User>, var playlists: ArrayList<Playlist>, var songs: ArrayList<Song>) {

    fun addPlaylist(playlist: Playlist) {
        playlists.add(playlist)
    }

    fun removePlaylist(playlist: Playlist) {
        playlists.remove(playlist)
    }

    fun addSongToPlaylist(song: Song, playlist: Playlist) {
        playlists.single() { it.id == playlist.id}.song_ids?.add(song.id)
    }
}