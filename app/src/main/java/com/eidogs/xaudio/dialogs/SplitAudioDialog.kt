package com.eidogs.xaudio.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.eidogs.xaudio.EXTRA_SONG
import com.eidogs.xaudio.R
import com.eidogs.xaudio.STEMS_TYPE
import com.eidogs.xaudio.activities.MainActivity
import com.eidogs.xaudio.extensions.colorButtons
import com.eidogs.xaudio.extensions.findNavController
import com.eidogs.xaudio.extensions.materialDialog
import com.eidogs.xaudio.model.Song
import com.google.android.material.bottomsheet.BottomSheetBehavior

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