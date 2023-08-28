package com.fullstapp.splitit.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.fullstapp.splitit.EXTRA_SONG
import com.fullstapp.splitit.R
import com.fullstapp.splitit.STEMS_TYPE
import com.fullstapp.splitit.activities.MainActivity
import com.fullstapp.splitit.extensions.colorButtons
import com.fullstapp.splitit.extensions.findNavController
import com.fullstapp.splitit.extensions.materialDialog
import com.fullstapp.splitit.model.Song
import com.fullstapp.splitit.util.logD
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.search.SearchView.Behavior

class SplitAudioDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val song: Song? =
            BundleCompat.getParcelable(requireArguments(), EXTRA_SONG, Song::class.java)
        val listening: String =
            String.format(
                getString(R.string.currently_listening_to_x_by_x),
                song?.title,
                song?.artistName
            )
        return materialDialog(R.string.choose_audio_stems)
            .setItems(
                arrayOf(
                    "2 stems ()",
                    "4 stems (Drums, Vocals, Piano, Guitar Bass",
                    "5 stems (Drums, Vocals, Piano, Guitar Bass, Accompaniments)"
                )
            ) { _, which ->
                withAction(which, song, listening)
            }
            .setNegativeButton(R.string.action_cancel, null)
            .create()
            .colorButtons()
    }

    private fun withAction(
        which: Int,
        song: Song?,
        currentlyListening: String
    ) {
        goToSplitAudio(requireActivity(), which)
    }

    private fun goToSplitAudio(activity: Activity, type: Int) {
        if(activity !is MainActivity) return
        activity.apply {
            setBottomNavVisibility(false)
            if(getBottomSheetBehavior().state == BottomSheetBehavior.STATE_EXPANDED) {
                collapsePanel()
            }

            findNavController(R.id.fragment_container).navigate(
                R.id.split_audio_fragment,
                bundleOf(STEMS_TYPE to type)
            )
        }
    }

    companion object {
        fun create(song: Song): SplitAudioDialog {
            return SplitAudioDialog().apply {
                arguments = bundleOf(
                    EXTRA_SONG to song
                )
            }
        }
    }
}