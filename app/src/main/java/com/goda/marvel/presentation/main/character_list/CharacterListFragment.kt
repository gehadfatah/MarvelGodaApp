package com.goda.marvel.presentation.main.character_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goda.marvel.R
import com.goda.marvel.common.showMessage
import com.goda.marvel.domain.characterList.LIMIT_Character_LIST
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.widgets.paginatedRecyclerView.PaginationScrollListener
import kotlinx.android.synthetic.main.character_list_fragment.*
import kotlinx.android.synthetic.main.character_list_fragment.view.*


val Character_LIST_FRAGMENT_TAG = CharacterListFragment::class.java.name

private val TAG = CharacterListFragment::class.java.name

fun newCharacterListFragment() = CharacterListFragment()

class CharacterListFragment : Fragment(), ClickCharacterCallback {

    private lateinit var viewModel: CharacterListViewModel

    private val characterListAdapter by lazy { CharacterListRecyclerAdapter(this) }
    private var isLoading = false
    private var isLastPage = false

    private val stateObserver = Observer<CharacterListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is DefaultState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false

                    characterListAdapter.updateData(filterData(it.data))
                }
                is LoadingState -> {
                    swipeRefreshLayout.isRefreshing = true
                    isLoading = true
                }
                is PaginatingState -> {
                    isLoading = true
                }
                is ErrorState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    characterListAdapter.removeLoadingViewFooter()
                    showMessage((it as ErrorState).error)
                }
            }
        }
    }

    private fun filterData(data: List<Character>): List<Character> {
        var list = ArrayList<Character>()
        for (characher in data) {
            if (!characher.thumbnail.path.contains("image_not_available")) list.add(characher)
        }
        return list
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
        observeViewModel()
        savedInstanceState?.let {
            viewModel.restoreCryptoList()
        } ?: viewModel.updateCryptoList()
    }

    private fun searchConfigClickListner(view: View) {
        view.searchimg.setOnClickListener {
            /*  searchimg.visibility = View.GONE
              RelafterSearch.visibility = View.VISIBLE
              cancelTv.visibility = View.VISIBLE*/
            findNavController().navigate(CharacterListFragmentDirections.actionCharactersToSearch())

        }


    }


    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.character_list_fragment, container, false)
        searchConfigClickListner(view)
        initializeRecyclerView(view)
        initializeSwipeToRefreshView(view)
        return view
    }


    private fun initializeRecyclerView(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = characterListAdapter
            addOnScrollListener(OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeSwipeToRefreshView(view: View) {
        view.swipeRefreshLayout.setOnRefreshListener { viewModel.resetCryptoList() }
    }

    private fun loadNextPage() {
        characterListAdapter.addLoadingViewFooter()
        viewModel.updateCryptoList()
    }


    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = LIMIT_Character_LIST
        override fun isLastPage() = isLastPage
    }

    override fun onClicked(view:View,item: Character) {

       findNavController().navigate(CharacterListFragmentDirections.actionCharactersToDetail(item))


    }
}