package fr.adbonnin.rickandmorty.view.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.view.list.ListFilterDialogFragment.OnApplyListener

class ListFilterDialogFragment : DialogFragment() {

    var applyListener = OnApplyListener { _, _, _, _ -> }

    var cancelListener = DialogInterface.OnClickListener { _, _ -> }

    fun interface OnApplyListener {
        fun onApply(dialog: DialogInterface, which: Int, filterByGenre: List<String>, filterByStatus: List<String>)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setView(activity?.layoutInflater?.inflate(R.layout.dialog_list_filter, null))
            .setPositiveButton(getString(R.string.validate_button)) { dialog, which -> applyListener.onApply(dialog, which, arrayListOf(), arrayListOf()) }
            .setNegativeButton(getString(R.string.cancel_button), cancelListener)
            .setTitle(getString(R.string.filters_title))
            .create()
    }
}