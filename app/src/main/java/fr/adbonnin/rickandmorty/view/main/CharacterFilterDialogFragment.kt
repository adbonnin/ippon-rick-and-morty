package fr.adbonnin.rickandmorty.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.ChipGroup
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.data.GetCharactersListFilter
import fr.adbonnin.rickandmorty.data.GetCharactersListFilter.Gender
import fr.adbonnin.rickandmorty.data.GetCharactersListFilter.Status
import fr.adbonnin.rickandmorty.view.main.CharacterFilterDialogFragment.OnApplyListener

class CharacterFilterDialogFragment : DialogFragment() {

    var applyListener = OnApplyListener { _, _, _ -> }

    var cancelListener = DialogInterface.OnClickListener { _, _ -> }

    var filter = GetCharactersListFilter()

    fun interface OnApplyListener {
        fun onApply(dialog: DialogInterface, which: Int, charactersFilter: GetCharactersListFilter)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_character_filter, null)

        view.findViewById<ChipGroup>(R.id.gender_chips).apply {
            check(toGenderId(filter.gender))

            setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == View.NO_ID) {
                    group.check(toGenderId(filter.gender))
                }
                else {
                    val gender = toGender(checkedId)
                    filter = filter.copy(gender = gender)
                }
            }
        }

        view.findViewById<ChipGroup>(R.id.status_chips).apply {
            check(toStatusId(filter.status))

            setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == View.NO_ID) {
                    group.check(toStatusId(filter.status))
                }
                else {
                    val status = toStatus(checkedId)
                    filter = filter.copy(status = status)
                }
            }
        }

        return AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton(getString(R.string.validate_button)) { dialog, which -> applyListener.onApply(dialog, which, filter) }
            .setNegativeButton(getString(R.string.cancel_button), cancelListener)
            .setTitle(getString(R.string.filters_title))
            .create()
    }

    private fun toGender(id: Int): Gender = when (id) {
        R.id.male_gender -> Gender.MALE
        R.id.female_gender -> Gender.FEMALE
        R.id.unknown_gender -> Gender.UNKNOWN
        else -> Gender.ALL
    }

    private fun toGenderId(gender: Gender): Int = when (gender) {
        Gender.MALE -> R.id.male_gender
        Gender.FEMALE -> R.id.female_gender
        Gender.UNKNOWN -> R.id.unknown_gender
        else -> R.id.all_gender
    }

    private fun toStatus(id: Int): Status = when (id) {
        R.id.alive_status -> Status.ALIVE
        R.id.dead_status -> Status.DEAD
        R.id.unknown_status -> Status.UNKNOWN
        else -> Status.ALL
    }

    private fun toStatusId(gender: Status): Int = when (gender) {
        Status.ALIVE -> R.id.alive_status
        Status.DEAD -> R.id.dead_status
        Status.UNKNOWN -> R.id.unknown_status
        else -> R.id.all_status
    }
}