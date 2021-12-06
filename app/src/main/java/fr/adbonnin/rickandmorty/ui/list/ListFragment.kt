package fr.adbonnin.rickandmorty.ui.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.adbonnin.rickandmorty.App
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.api.Character
import fr.adbonnin.rickandmorty.api.RickAndMortyService

private const val TAG = "ListFragment"

class ListFragment : Fragment() {

    private var characters: MutableList<Character> = arrayListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    var selectCharacterListener = ListAdapter.OnSelectCharacterListener { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListAdapter(characters, selectCharacterListener)
        recyclerView.adapter = adapter

        val callback = object : RickAndMortyService.ResponseHandler<List<Character>> {
            override fun onSuccess(value: List<Character>) {
                characters.addAll(value)
                adapter.notifyItemRangeInserted(0, value.size)
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(context, getString(R.string.error_fail_to_load_characters), Toast.LENGTH_SHORT).show()
            }
        }

        App.rickAndMortyService.findByPage(1, callback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_and_filter -> showSortAndFilterDialog()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortAndFilterDialog(): Boolean {
        val dialog = ListFilterDialogFragment()
        dialog.applyListener = ListFilterDialogFragment.OnApplyListener(::onApplyFilter)
        dialog.cancelListener = DialogInterface.OnClickListener(::onCancelFilter)
        dialog.show(childFragmentManager, "ListFilterDialogFragment")
        return true
    }

    private fun onApplyFilter(dialog: DialogInterface, which: Int, filterByGenre: List<String>, filterByStatus: List<String>) {
        Log.i(TAG, "onApplyFilter; filterByGenre: $filterByGenre, filterByStatus: $filterByStatus")
    }

    private fun onCancelFilter(dialog: DialogInterface, which: Int) {
        Log.i(TAG, "onCancelFilter")
    }
}