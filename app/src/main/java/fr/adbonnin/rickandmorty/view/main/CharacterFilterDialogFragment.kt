package fr.adbonnin.rickandmorty.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.ChipGroup
import com.google.common.collect.ImmutableBiMap
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
            check(GENDER_IDS[filter.gender] ?: R.id.all_gender)

            setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == View.NO_ID) {
                    group.check(GENDER_IDS[filter.gender] ?: R.id.all_gender)
                }
                else {
                    val gender = GENDER_IDS.inverse()[checkedId] ?: Gender.ALL
                    filter = filter.copy(gender = gender)
                }
            }
        }

        view.findViewById<ChipGroup>(R.id.status_chips).apply {
            check(STATUS_IDS[filter.status] ?: R.id.all_status)

            setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == View.NO_ID) {
                    group.check(STATUS_IDS[filter.status] ?: R.id.all_status)
                }
                else {
                    val status = STATUS_IDS.inverse()[checkedId] ?: Status.ALL
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

    companion object {
        val GENDER_IDS: ImmutableBiMap<Gender, Int> = ImmutableBiMap.builder<Gender, Int>()
            .put(Gender.MALE, R.id.male_gender)
            .put(Gender.FEMALE, R.id.female_gender)
            .put(Gender.UNKNOWN, R.id.unknown_gender)
            .build()

        val STATUS_IDS: ImmutableBiMap<Status, Int> = ImmutableBiMap.builder<Status, Int>()
            .put(Status.ALIVE, R.id.alive_status)
            .put(Status.DEAD, R.id.dead_status)
            .put(Status.UNKNOWN, R.id.unknown_status)
            .build()
    }
}