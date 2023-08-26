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
package com.fullstapp.splitit.activities.base

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.commit
import com.fullstapp.appthemehelper.util.VersionUtils
import com.fullstapp.splitit.SWIPE_DOWN_DISMISS
import com.fullstapp.splitit.ADAPTIVE_COLOR_APP
import com.fullstapp.splitit.ALBUM_COVER_STYLE
import com.fullstapp.splitit.ALBUM_COVER_TRANSFORM
import com.fullstapp.splitit.CAROUSEL_EFFECT
import com.fullstapp.splitit.CIRCLE_PLAY_BUTTON
import com.fullstapp.splitit.EXTRA_SONG_INFO
import com.fullstapp.splitit.KEEP_SCREEN_ON
import com.fullstapp.splitit.LIBRARY_CATEGORIES
import com.fullstapp.splitit.NOW_PLAYING_SCREEN_ID
import com.fullstapp.splitit.R
import com.fullstapp.splitit.SCREEN_ON_LYRICS
import com.fullstapp.splitit.SWIPE_ANYWHERE_NOW_PLAYING
import com.fullstapp.splitit.TAB_TEXT_MODE
import com.fullstapp.splitit.TOGGLE_ADD_CONTROLS
import com.fullstapp.splitit.TOGGLE_FULL_SCREEN
import com.fullstapp.splitit.TOGGLE_VOLUME
import com.fullstapp.splitit.activities.PermissionActivity
import com.fullstapp.splitit.databinding.SlidingMusicPanelLayoutBinding
import com.fullstapp.splitit.extensions.currentFragment
import com.fullstapp.splitit.extensions.darkAccentColor
import com.fullstapp.splitit.extensions.dip
import com.fullstapp.splitit.extensions.getBottomInsets
import com.fullstapp.splitit.extensions.hide
import com.fullstapp.splitit.extensions.isColorLight
import com.fullstapp.splitit.extensions.isLandscape
import com.fullstapp.splitit.extensions.keepScreenOn
import com.fullstapp.splitit.extensions.maybeSetScreenOn
import com.fullstapp.splitit.extensions.peekHeightAnimate
import com.fullstapp.splitit.extensions.setLightNavigationBar
import com.fullstapp.splitit.extensions.setLightNavigationBarAuto
import com.fullstapp.splitit.extensions.setLightStatusBar
import com.fullstapp.splitit.extensions.setLightStatusBarAuto
import com.fullstapp.splitit.extensions.setNavigationBarColorPreOreo
import com.fullstapp.splitit.extensions.setTaskDescriptionColor
import com.fullstapp.splitit.extensions.show
import com.fullstapp.splitit.extensions.surfaceColor
import com.fullstapp.splitit.extensions.whichFragment
import com.fullstapp.splitit.fragments.LibraryViewModel
import com.fullstapp.splitit.fragments.NowPlayingScreen
import com.fullstapp.splitit.fragments.NowPlayingScreen.*
import com.fullstapp.splitit.fragments.base.AbsPlayerFragment
import com.fullstapp.splitit.fragments.other.MiniPlayerFragment
import com.fullstapp.splitit.fragments.player.adaptive.AdaptiveFragment
import com.fullstapp.splitit.fragments.player.blur.BlurPlayerFragment
import com.fullstapp.splitit.fragments.player.card.CardFragment
import com.fullstapp.splitit.fragments.player.cardblur.CardBlurFragment
import com.fullstapp.splitit.fragments.player.circle.CirclePlayerFragment
import com.fullstapp.splitit.fragments.player.classic.ClassicPlayerFragment
import com.fullstapp.splitit.fragments.player.color.ColorFragment
import com.fullstapp.splitit.fragments.player.fit.FitFragment
import com.fullstapp.splitit.fragments.player.flat.FlatPlayerFragment
import com.fullstapp.splitit.fragments.player.full.FullPlayerFragment
import com.fullstapp.splitit.fragments.player.gradient.GradientPlayerFragment
import com.fullstapp.splitit.fragments.player.material.MaterialFragment
import com.fullstapp.splitit.fragments.player.md3.MD3PlayerFragment
import com.fullstapp.splitit.fragments.player.normal.PlayerFragment
import com.fullstapp.splitit.fragments.player.peek.PeekPlayerFragment
import com.fullstapp.splitit.fragments.player.plain.PlainPlayerFragment
import com.fullstapp.splitit.fragments.player.simple.SimplePlayerFragment
import com.fullstapp.splitit.fragments.player.tiny.TinyPlayerFragment
import com.fullstapp.splitit.fragments.queue.PlayingQueueFragment
import com.fullstapp.splitit.helper.MusicPlayerRemote
import com.fullstapp.splitit.model.CategoryInfo
import com.fullstapp.splitit.util.PreferenceUtil
import com.fullstapp.splitit.util.ViewUtil
import com.fullstapp.splitit.util.logD
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_DRAGGING
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_SETTLING
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import org.koin.androidx.viewmodel.ext.android.viewModel


abstract class AbsSlidingMusicPanelActivity : AbsMusicServiceActivity(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        val TAG: String = AbsSlidingMusicPanelActivity::class.java.simpleName
    }

    var fromNotification = false
    private var windowInsets: WindowInsetsCompat? = null
    protected val libraryViewModel by viewModel<LibraryViewModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var playerFragment: AbsPlayerFragment
    private var miniPlayerFragment: MiniPlayerFragment? = null
    private var nowPlayingScreen: NowPlayingScreen? = null
    private var taskColor: Int = 0
    private var paletteColor: Int = Color.WHITE
    private var navigationBarColor = 0

    private val panelState: Int
        get() = bottomSheetBehavior.state
    private lateinit var binding: SlidingMusicPanelLayoutBinding
    private var isInOneTabMode = false

    private var navigationBarColorAnimator: ValueAnimator? = null
    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Handle back press ${bottomSheetBehavior.state}")
            if (!handleBackPress()) {
                remove()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private val bottomSheetCallbackList by lazy {
        object : BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                setMiniPlayerAlphaProgress(slideOffset)
                navigationBarColorAnimator?.cancel()
                setNavigationBarColorPreOreo(
                    argbEvaluator.evaluate(
                        slideOffset,
                        surfaceColor(),
                        navigationBarColor
                    ) as Int
                )
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                onBackPressedCallback.isEnabled = newState == STATE_EXPANDED
                when (newState) {
                    STATE_EXPANDED -> {
                        onPanelExpanded()
                        if (PreferenceUtil.lyricsScreenOn && PreferenceUtil.showLyrics) {
                            keepScreenOn(true)
                        }
                    }

                    STATE_COLLAPSED -> {
                        onPanelCollapsed()
                        if ((PreferenceUtil.lyricsScreenOn && PreferenceUtil.showLyrics) || !PreferenceUtil.isScreenOnEnabled) {
                            keepScreenOn(false)
                        }
                    }

                    STATE_SETTLING, STATE_DRAGGING -> {
                        if (fromNotification) {
                            binding.navigationView.bringToFront()
                            fromNotification = false
                        }
                    }

                    STATE_HIDDEN -> {
                        MusicPlayerRemote.clearQueue()
                    }

                    else -> {
                        logD("Do a flip")
                    }
                }
            }
        }
    }

    fun getBottomSheetBehavior() = bottomSheetBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermissions()) {
            startActivity(Intent(this, PermissionActivity::class.java))
            finish()
        }
        binding = SlidingMusicPanelLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setOnApplyWindowInsetsListener { _, insets ->
            windowInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
            insets
        }
        chooseFragmentForTheme()
        setupSlidingUpPanel()
        setupBottomSheet()
        updateColor()
        if (!PreferenceUtil.materialYou) {
            binding.slidingPanel.backgroundTintList = ColorStateList.valueOf(darkAccentColor())
            navigationView.backgroundTintList = ColorStateList.valueOf(darkAccentColor())
        }

        navigationBarColor = surfaceColor()

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = from(binding.slidingPanel)
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallbackList)
        bottomSheetBehavior.isHideable = PreferenceUtil.swipeDownToDismiss
        bottomSheetBehavior.significantVelocityThreshold = 300
        setMiniPlayerAlphaProgress(0F)
    }

    override fun onResume() {
        super.onResume()
        PreferenceUtil.registerOnSharedPreferenceChangedListener(this)
        if (nowPlayingScreen != PreferenceUtil.nowPlayingScreen) {

        }
        if (bottomSheetBehavior.state == STATE_EXPANDED) {
            setMiniPlayerAlphaProgress(1f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallbackList)
        PreferenceUtil.unregisterOnSharedPreferenceChangedListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SWIPE_DOWN_DISMISS -> {
                bottomSheetBehavior.isHideable = PreferenceUtil.swipeDownToDismiss
            }

            TOGGLE_ADD_CONTROLS -> {
                miniPlayerFragment?.setUpButtons()
            }

            NOW_PLAYING_SCREEN_ID -> {
                chooseFragmentForTheme()
                binding.slidingPanel.updateLayoutParams<ViewGroup.LayoutParams> {
                    height = if (nowPlayingScreen != Peek) {
                        ViewGroup.LayoutParams.MATCH_PARENT
                    } else {
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                    onServiceConnected()
                }
            }

            ALBUM_COVER_TRANSFORM, CAROUSEL_EFFECT,
            ALBUM_COVER_STYLE, TOGGLE_VOLUME, EXTRA_SONG_INFO, CIRCLE_PLAY_BUTTON,
            -> {
                chooseFragmentForTheme()
                onServiceConnected()
            }

            SWIPE_ANYWHERE_NOW_PLAYING -> {
                playerFragment.addSwipeDetector()
            }

            ADAPTIVE_COLOR_APP -> {
                if (PreferenceUtil.nowPlayingScreen in listOf(Normal, Material, Flat)) {
                    chooseFragmentForTheme()
                    onServiceConnected()
                }
            }

            LIBRARY_CATEGORIES -> {
                updateTabs()
            }

            TAB_TEXT_MODE -> {
                navigationView.labelVisibilityMode = PreferenceUtil.tabTitleMode
            }

            TOGGLE_FULL_SCREEN -> {
                recreate()
            }

            SCREEN_ON_LYRICS -> {
                keepScreenOn(bottomSheetBehavior.state == STATE_EXPANDED && PreferenceUtil.lyricsScreenOn && PreferenceUtil.showLyrics || PreferenceUtil.isScreenOnEnabled)
            }

            KEEP_SCREEN_ON -> {
                maybeSetScreenOn()
            }
        }
    }

    fun collapsePanel() {
        bottomSheetBehavior.state = STATE_COLLAPSED
    }

    fun expandPanel() {
        bottomSheetBehavior.state = STATE_EXPANDED
    }

    private fun setMiniPlayerAlphaProgress(progress: Float) {
        if (progress < 0) return
        val alpha = 1 - progress
        miniPlayerFragment?.view?.alpha = 1 - (progress / 0.2F)
        miniPlayerFragment?.view?.isGone = alpha == 0f
        if (!isLandscape) {
            binding.navigationView.translationY = progress * 500
            binding.navigationView.alpha = alpha
        }
        binding.playerFragmentContainer.alpha = (progress - 0.2F) / 0.2F
    }

    private fun animateNavigationBarColor(color: Int) {
        if (VersionUtils.hasOreo()) return
        navigationBarColorAnimator?.cancel()
        navigationBarColorAnimator = ValueAnimator
            .ofArgb(window.navigationBarColor, color).apply {
                duration = ViewUtil.RETRO_MUSIC_ANIM_TIME.toLong()
                interpolator = PathInterpolator(0.4f, 0f, 1f, 1f)
                addUpdateListener { animation: ValueAnimator ->
                    setNavigationBarColorPreOreo(
                        animation.animatedValue as Int
                    )
                }
                start()
            }
    }

    open fun onPanelCollapsed() {
        setMiniPlayerAlphaProgress(0F)
        // restore values
        animateNavigationBarColor(surfaceColor())
        setLightStatusBarAuto()
        setLightNavigationBarAuto()
        setTaskDescriptionColor(taskColor)
        //playerFragment?.onHide()
    }

    open fun onPanelExpanded() {
        setMiniPlayerAlphaProgress(1F)
        onPaletteColorChanged()
        //playerFragment?.onShow()
    }

    private fun setupSlidingUpPanel() {
        binding.slidingPanel.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.slidingPanel.viewTreeObserver.removeOnGlobalLayoutListener(this)
                if (nowPlayingScreen != Peek) {
                    binding.slidingPanel.updateLayoutParams<ViewGroup.LayoutParams> {
                        height = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                }
                when (panelState) {
                    STATE_EXPANDED -> onPanelExpanded()
                    STATE_COLLAPSED -> onPanelCollapsed()
                    else -> {
                        // playerFragment!!.onHide()
                    }
                }
            }
        })
    }

    val navigationView get() = binding.navigationView

    val slidingPanel get() = binding.slidingPanel

    val isBottomNavVisible get() = navigationView.isVisible && navigationView is BottomNavigationView

    override fun onServiceConnected() {
        super.onServiceConnected()
        hideBottomSheet(false)
    }

    override fun onQueueChanged() {
        super.onQueueChanged()
        // Mini player should be hidden in Playing Queue
        // it may pop up if hideBottomSheet is called
        if (currentFragment(R.id.fragment_container) !is PlayingQueueFragment) {
            hideBottomSheet(MusicPlayerRemote.playingQueue.isEmpty())
        }
    }

    private fun handleBackPress(): Boolean {
        if (panelState == STATE_EXPANDED) {
            collapsePanel()
            return true
        }
        return false
    }

    private fun onPaletteColorChanged() {
        if (panelState == STATE_EXPANDED) {
            navigationBarColor = surfaceColor()
            setTaskDescColor(paletteColor)
            val isColorLight = paletteColor.isColorLight
            if (PreferenceUtil.isAdaptiveColor && (nowPlayingScreen == Normal || nowPlayingScreen == Flat || nowPlayingScreen == Material)) {
                setLightNavigationBar(true)
                setLightStatusBar(isColorLight)
            } else if (nowPlayingScreen == Card || nowPlayingScreen == Blur || nowPlayingScreen == BlurCard) {
                animateNavigationBarColor(Color.BLACK)
                navigationBarColor = Color.BLACK
                setLightStatusBar(false)
                setLightNavigationBar(true)
            } else if (nowPlayingScreen == Color || nowPlayingScreen == Tiny || nowPlayingScreen == Gradient) {
                animateNavigationBarColor(paletteColor)
                navigationBarColor = paletteColor
                setLightNavigationBar(isColorLight)
                setLightStatusBar(isColorLight)
            } else if (nowPlayingScreen == Full) {
                animateNavigationBarColor(paletteColor)
                navigationBarColor = paletteColor
                setLightNavigationBar(isColorLight)
                setLightStatusBar(false)
            } else if (nowPlayingScreen == Classic) {
                setLightStatusBar(false)
            } else if (nowPlayingScreen == Fit) {
                setLightStatusBar(false)
            }
        }
    }

    private fun setTaskDescColor(color: Int) {
        taskColor = color
        if (panelState == STATE_COLLAPSED) {
            setTaskDescriptionColor(color)
        }
    }

    fun updateTabs() {
        binding.navigationView.menu.clear()
        val currentTabs: List<CategoryInfo> = PreferenceUtil.libraryCategory
        for (tab in currentTabs) {
            if (tab.visible) {
                val menu = tab.category
                binding.navigationView.menu.add(0, menu.id, 0, menu.stringRes)
                    .setIcon(menu.icon)
            }
        }
        if (binding.navigationView.menu.size() == 1) {
            isInOneTabMode = true
            binding.navigationView.isVisible = false
        } else {
            isInOneTabMode = false
        }
    }

    private fun updateColor() {
        libraryViewModel.paletteColor.observe(this) { color ->
            this.paletteColor = color
            onPaletteColorChanged()
        }
    }

    fun setBottomNavVisibility(
        visible: Boolean,
        animate: Boolean = false,
        hideBottomSheet: Boolean = MusicPlayerRemote.playingQueue.isEmpty(),
    ) {
        if (isInOneTabMode) {
            hideBottomSheet(
                hide = hideBottomSheet,
                animate = animate,
                isBottomNavVisible = false
            )
            return
        }
        if (visible xor navigationView.isVisible) {
            val mAnimate = animate && bottomSheetBehavior.state == STATE_COLLAPSED
            if (mAnimate) {
                if (visible) {
                    binding.navigationView.bringToFront()
                    binding.navigationView.show()
                } else {
                    binding.navigationView.hide()
                }
            } else {
                binding.navigationView.isVisible = visible
                if (visible && bottomSheetBehavior.state != STATE_EXPANDED) {
                    binding.navigationView.bringToFront()
                }
            }
        }
        hideBottomSheet(
            hide = hideBottomSheet,
            animate = animate,
            isBottomNavVisible = visible && navigationView is BottomNavigationView
        )
    }

    fun hideBottomSheet(
        hide: Boolean,
        animate: Boolean = false,
        isBottomNavVisible: Boolean = navigationView.isVisible && navigationView is BottomNavigationView,
    ) {
        val heightOfBar = windowInsets.getBottomInsets() + dip(R.dimen.mini_player_height)
        val heightOfBarWithTabs = heightOfBar + dip(R.dimen.bottom_nav_height)
        if (hide) {
            bottomSheetBehavior.peekHeight = -windowInsets.getBottomInsets()
            bottomSheetBehavior.state = STATE_COLLAPSED
            libraryViewModel.setFabMargin(
                this,
                if (isBottomNavVisible) dip(R.dimen.bottom_nav_height) else 0
            )
        } else {
            if (MusicPlayerRemote.playingQueue.isNotEmpty()) {
                binding.slidingPanel.elevation = 0F
                binding.navigationView.elevation = 5F
                if (isBottomNavVisible) {
                    logD("List")
                    if (animate) {
                        bottomSheetBehavior.peekHeightAnimate(heightOfBarWithTabs)
                    } else {
                        bottomSheetBehavior.peekHeight = heightOfBarWithTabs
                    }
                    libraryViewModel.setFabMargin(
                        this,
                        dip(R.dimen.bottom_nav_mini_player_height)
                    )
                } else {
                    logD("Details")
                    if (animate) {
                        bottomSheetBehavior.peekHeightAnimate(heightOfBar).doOnEnd {
                            binding.slidingPanel.bringToFront()
                        }
                    } else {
                        bottomSheetBehavior.peekHeight = heightOfBar
                        binding.slidingPanel.bringToFront()
                    }
                    libraryViewModel.setFabMargin(this, dip(R.dimen.mini_player_height))
                }
            }
        }
    }

    fun setAllowDragging(allowDragging: Boolean) {
        bottomSheetBehavior.isDraggable = allowDragging
        hideBottomSheet(false)
    }

    private fun chooseFragmentForTheme() {
        nowPlayingScreen = PreferenceUtil.nowPlayingScreen

        val fragment: AbsPlayerFragment = when (nowPlayingScreen) {
            Blur -> BlurPlayerFragment()
            Adaptive -> AdaptiveFragment()
            Normal -> PlayerFragment()
            Card -> CardFragment()
            BlurCard -> CardBlurFragment()
            Fit -> FitFragment()
            Flat -> FlatPlayerFragment()
            Full -> FullPlayerFragment()
            Plain -> PlainPlayerFragment()
            Simple -> SimplePlayerFragment()
            Material -> MaterialFragment()
            Color -> ColorFragment()
            Gradient -> GradientPlayerFragment()
            Tiny -> TinyPlayerFragment()
            Peek -> PeekPlayerFragment()
            Circle -> CirclePlayerFragment()
            Classic -> ClassicPlayerFragment()
            MD3 -> MD3PlayerFragment()
            else -> PlayerFragment()
        } // must extend AbsPlayerFragment
        supportFragmentManager.commit {
            replace(R.id.playerFragmentContainer, fragment)
        }
        supportFragmentManager.executePendingTransactions()
        playerFragment = whichFragment(R.id.playerFragmentContainer)
        miniPlayerFragment = whichFragment<MiniPlayerFragment>(R.id.miniPlayerFragment)
        miniPlayerFragment?.view?.setOnClickListener { expandPanel() }
    }
}
