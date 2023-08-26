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
package com.fullstapp.splitit.fragments.player.simple

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.fullstapp.appthemehelper.util.ToolbarContentTintHelper
import com.fullstapp.splitit.R
import com.fullstapp.splitit.databinding.FragmentSimplePlayerBinding
import com.fullstapp.splitit.extensions.colorControlNormal
import com.fullstapp.splitit.extensions.drawAboveSystemBars
import com.fullstapp.splitit.extensions.whichFragment
import com.fullstapp.splitit.fragments.base.AbsPlayerFragment
import com.fullstapp.splitit.fragments.player.PlayerAlbumCoverFragment
import com.fullstapp.splitit.helper.MusicPlayerRemote
import com.fullstapp.splitit.model.Song
import com.fullstapp.splitit.util.color.MediaNotificationProcessor

/**
 * @author Hemanth S (h4h13).
 */

class SimplePlayerFragment : AbsPlayerFragment(R.layout.fragment_simple_player) {

    private var _binding: FragmentSimplePlayerBinding? = null
    private val binding get() = _binding!!

    override fun playerToolbar(): Toolbar {
        return binding.playerToolbar
    }

    private var lastColor: Int = 0
    override val paletteColor: Int
        get() = lastColor

    private lateinit var controlsFragment: SimplePlaybackControlsFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSimplePlayerBinding.bind(view)
        setUpSubFragments()
        setUpPlayerToolbar()
        playerToolbar().drawAboveSystemBars()
    }

    private fun setUpSubFragments() {
        val playerAlbumCoverFragment: PlayerAlbumCoverFragment =
            whichFragment(R.id.playerAlbumCoverFragment)
        playerAlbumCoverFragment.setCallbacks(this)
        controlsFragment = whichFragment(R.id.playbackControlsFragment)
    }

    override fun onShow() {
        controlsFragment.show()
    }

    override fun onHide() {
        controlsFragment.hide()
    }

    override fun toolbarIconColor() = colorControlNormal()

    override fun onColorChanged(color: MediaNotificationProcessor) {
        lastColor = color.backgroundColor
        libraryViewModel.updateColor(color.backgroundColor)
        controlsFragment.setColor(color)
        ToolbarContentTintHelper.colorizeToolbar(
            binding.playerToolbar,
            colorControlNormal(),
            requireActivity()
        )
    }

    override fun onFavoriteToggled() {
        toggleFavorite(MusicPlayerRemote.currentSong)
    }

    override fun toggleFavorite(song: Song) {
        super.toggleFavorite(song)
        if (song.id == MusicPlayerRemote.currentSong.id) {
            updateIsFavorite()
        }
    }

    private fun setUpPlayerToolbar() {
        binding.playerToolbar.inflateMenu(R.menu.menu_player)
        binding.playerToolbar.setNavigationOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.playerToolbar.setOnMenuItemClickListener(this)
        ToolbarContentTintHelper.colorizeToolbar(
            binding.playerToolbar,
            colorControlNormal(),
            requireActivity()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
