package fr.adbonnin.rickandmorty.view.main

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.adbonnin.rickandmorty.R
import fr.adbonnin.rickandmorty.data.GetCharactersListFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "ListFragment"

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var refreshLayout: SwipeRefreshLayout

    var selectCharacterListener = CharacterAdapter.OnSelectCharacterListener { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)
        adapter = CharacterAdapter(selectCharacterListener)

        recyclerView = view.findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener(::onRefreshCharacters)

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                val refresh = loadState.refresh
                refreshLayout.isRefreshing = refresh is LoadState.Loading
            }
        }

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

    private fun onRefreshCharacters() {
        adapter.refresh()
    }

    private fun showSortAndFilterDialog(): Boolean {
        val dialog = CharacterFilterDialogFragment()
        dialog.applyListener = CharacterFilterDialogFragment.OnApplyListener(::onApplyFilter)
        dialog.cancelListener = DialogInterface.OnClickListener(::onCancelFilter)
        dialog.filter = viewModel.filter.value
        dialog.show(childFragmentManager, "ListFilterDialogFragment")
        return true
    }

    private fun onApplyFilter(dialog: DialogInterface, which: Int, filter: GetCharactersListFilter) {
        Log.i(TAG, "onApplyFilter; filter: $filter")
        viewModel.filter.value = filter
    }

    private fun onCancelFilter(dialog: DialogInterface, which: Int) {
        Log.i(TAG, "onCancelFilter")
    }
}