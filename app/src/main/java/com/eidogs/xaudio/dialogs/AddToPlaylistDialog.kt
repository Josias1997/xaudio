/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.eidogs.xaudio.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.eidogs.xaudio.EXTRA_PLAYLISTS
import com.eidogs.xaudio.EXTRA_SONG
import com.eidogs.xaudio.R
import com.eidogs.xaudio.db.PlaylistEntity
import com.eidogs.xaudio.extensions.colorButtons
import com.eidogs.xaudio.extensions.extraNotNull
import com.eidogs.xaudio.extensions.materialDialog
import com.eidogs.xaudio.fragments.LibraryViewModel
import com.eidogs.xaudio.model.Song
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AddToPlaylistDialog : DialogFragment() {
    private val libraryViewModel by activityViewModel<LibraryViewModel>()

    companion object {
        fun create(playlistEntities: List<PlaylistEntity>, song: Song): AddToPlaylistDialog {
            val list: MutableList<Song> = mutableListOf()
            list.add(song)
            return create(playlistEntities, list)
        }

        fun create(playlistEntities: List<PlaylistEntity>, songs: List<Song>): AddToPlaylistDialog {
            return AddToPlaylistDialog().apply {
                arguments = bundleOf(
                    EXTRA_SONG to songs,
                    EXTRA_PLAYLISTS to playlistEntities
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val playlistEntities = extraNotNull<List<PlaylistEntity>>(EXTRA_PLAYLISTS).value
        val songs = extraNotNull<List<Song>>(EXTRA_SONG).value
        val playlistNames = mutableListOf<String>()
        playlistNames.add(requireContext().resources.getString(R.string.action_new_playlist))
        for (entity: PlaylistEntity in playlistEntities) {
            playlistNames.add(entity.playlistName)
        }
        return materialDialog(R.string.add_playlist_title)
            .setItems(playlistNames.toTypedArray()) { dialog, which ->
                if (which == 0) {
                    showCreateDialog(songs)
                } else {
                    libraryViewModel.addToPlaylist(requireContext(), playlistNames[which], songs)
                }
                dialog.dismiss()
            }
            .setNegativeButton(R.string.action_cancel, null)
            .create()
            .colorButtons()
    }

    private fun showCreateDialog(songs: List<Song>) {
        CreatePlaylistDialog.create(songs).show(requireActivity().supportFragmentManager, "Dialog")
    }
}
