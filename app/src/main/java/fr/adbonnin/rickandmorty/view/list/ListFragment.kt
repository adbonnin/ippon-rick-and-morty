package fr.adbonnin.rickandmorty.view.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.adbonnin.rickandmorty.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ListFragment"

@ExperimentalCoroutinesApi
class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharactersAdapter
    private lateinit var viewModel: CharactersViewModel

    var selectCharacterListener = CharactersAdapter.OnSelectCharacterListener { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = defaultViewModelProviderFactory.create(CharactersViewModel::class.java)
        adapter = CharactersAdapter(selectCharacterListener)

        recyclerView = view.findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.characters.collectLatest {
                adapter.submitData(it)
            }
        }
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