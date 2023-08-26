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
package com.fullstapp.splitit.appshortcuts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.fullstapp.splitit.appshortcuts.shortcuttype.LastAddedShortcutType
import com.fullstapp.splitit.appshortcuts.shortcuttype.ShuffleAllShortcutType
import com.fullstapp.splitit.appshortcuts.shortcuttype.TopTracksShortcutType
import com.fullstapp.splitit.extensions.extraNotNull
import com.fullstapp.splitit.model.Playlist
import com.fullstapp.splitit.model.smartplaylist.LastAddedPlaylist
import com.fullstapp.splitit.model.smartplaylist.ShuffleAllPlaylist
import com.fullstapp.splitit.model.smartplaylist.TopTracksPlaylist
import com.fullstapp.splitit.service.MusicService
import com.fullstapp.splitit.service.MusicService.Companion.ACTION_PLAY_PLAYLIST
import com.fullstapp.splitit.service.MusicService.Companion.INTENT_EXTRA_PLAYLIST
import com.fullstapp.splitit.service.MusicService.Companion.INTENT_EXTRA_SHUFFLE_MODE
import com.fullstapp.splitit.service.MusicService.Companion.SHUFFLE_MODE_NONE
import com.fullstapp.splitit.service.MusicService.Companion.SHUFFLE_MODE_SHUFFLE

class AppShortcutLauncherActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (extraNotNull(KEY_SHORTCUT_TYPE, SHORTCUT_TYPE_NONE).value) {
            SHORTCUT_TYPE_SHUFFLE_ALL -> {
                startServiceWithPlaylist(
                    SHUFFLE_MODE_SHUFFLE, ShuffleAllPlaylist()
                )
                DynamicShortcutManager.reportShortcutUsed(this, ShuffleAllShortcutType.id)
            }

            SHORTCUT_TYPE_TOP_TRACKS -> {
                startServiceWithPlaylist(
                    SHUFFLE_MODE_NONE, TopTracksPlaylist()
                )
                DynamicShortcutManager.reportShortcutUsed(this, TopTracksShortcutType.id)
            }

            SHORTCUT_TYPE_LAST_ADDED -> {
                startServiceWithPlaylist(
                    SHUFFLE_MODE_NONE, LastAddedPlaylist()
                )
                DynamicShortcutManager.reportShortcutUsed(this, LastAddedShortcutType.id)
            }
        }
        finish()
    }

    private fun startServiceWithPlaylist(shuffleMode: Int, playlist: Playlist) {
        val intent = Intent(this, MusicService::class.java)
        intent.action = ACTION_PLAY_PLAYLIST

        val bundle = bundleOf(
            INTENT_EXTRA_PLAYLIST to playlist,
            INTENT_EXTRA_SHUFFLE_MODE to shuffleMode
        )

        intent.putExtras(bundle)

        startService(intent)
    }

    companion object {
        const val KEY_SHORTCUT_TYPE = "com.eidogs.xaudio.appshortcuts.ShortcutType"
        const val SHORTCUT_TYPE_SHUFFLE_ALL = 0L
        const val SHORTCUT_TYPE_TOP_TRACKS = 1L
        const val SHORTCUT_TYPE_LAST_ADDED = 2L
        const val SHORTCUT_TYPE_NONE = 4L
    }
}
