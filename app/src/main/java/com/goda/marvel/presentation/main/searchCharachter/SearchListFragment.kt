package com.goda.marvel.presentation.main.searchCharachter

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goda.marvel.R
import com.goda.marvel.common.hideKeyboard
import com.goda.marvel.common.showMessage
import com.goda.marvel.domain.characterList.LIMIT_Character_LIST
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.main.character_list.*
import com.goda.marvel.presentation.widgets.paginatedRecyclerView.PaginationScrollListener
import kotlinx.android.synthetic.main.search_fragment.*


val SEARCH_LIST_FRAGMENT_TAG = SearchListFragment::class.java.name

private val TAG = SearchListFragment::class.java.name

fun newSearchListFragment() = SearchListFragment()

class SearchListFragment : Fragment(), ClickCharacterCallback {

    private lateinit var viewModel: CharacterSearchListViewModel
    var filterSt: String = "00"
    private val cryptoListAdapter by lazy { SearchListRecyclerAdapter(this) }
    private var isLoading = false
    private var isLastPage = false

    private val stateObserver = Observer<CharacterListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is DefaultState -> {
                    isLoading = false

                    cryptoListAdapter.updateData(filterData(it.data))
                }
                is LoadingState -> {
                    isLoading = true
                }
                is PaginatingState -> {
                    isLoading = true
                }
                is ErrorState -> {
                    isLoading = false
                    cryptoListAdapter.removeLoadingViewFooter()
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
        viewModel = ViewModelProviders.of(this).get(CharacterSearchListViewModel::class.java)
        observeViewModel()
        if (filterSt != "00" && !TextUtils.isEmpty(filterSt))
            savedInstanceState?.let {
                viewModel.restoreCryptoList()
            } ?: viewModel.updateCryptoList(filterSt)
    }

    private fun searchConfigClickListner() {

        cancelTv.setOnClickListener {
            /*   searchimg.visibility = View.VISIBLE
               RelafterSearch.visibility = View.GONE
               cancelTv.visibility = View.GONE*/
            context?.hideKeyboard(this.view)
            findNavController().navigate(SearchListFragmentDirections.actionSearchToList())
        }
        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                filterSt = charSequence.toString()
                filterSearch()

            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun filterSearch() {
        if (filterSt != "00" && !TextUtils.isEmpty(filterSt))
            viewModel.updateCryptoList(filterSt)
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        //initializeToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchConfigClickListner()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerSearchView.apply {
            layoutManager = linearLayoutManager
            adapter = cryptoListAdapter
            addOnScrollListener(OnScrollListener(linearLayoutManager))
        }
    }


    private fun loadNextPage() {
        cryptoListAdapter.addLoadingViewFooter()
        viewModel.updateCryptoList(filterSt)
    }


    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = LIMIT_Character_LIST
        override fun isLastPage() = isLastPage
    }

    override fun onClicked(view: View, item: Character) {

        findNavController().navigate(SearchListFragmentDirections.actionSearchToDetail(item))


    }
}