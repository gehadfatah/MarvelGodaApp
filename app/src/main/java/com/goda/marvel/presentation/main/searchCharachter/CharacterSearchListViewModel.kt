package com.goda.marvel.presentation.main.searchCharachter

import androidx.lifecycle.MutableLiveData
import com.goda.marvel.common.androidMainThreadScheduler
import com.goda.marvel.common.schedulerIo
import com.goda.marvel.domain.characterList.LIMIT_Character_LIST
import com.goda.marvel.presentation.common.BaseViewModel
import com.goda.marvel.common.addTo
import com.goda.marvel.domain.search.SearchListUseCases
import com.goda.marvel.domain.search.searchListUseCasesDep
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.main.character_list.*
import io.reactivex.Scheduler


private val TAG = CharacterSearchListViewModel::class.java.name

class CharacterSearchListViewModel( private val searchListUseCases: SearchListUseCases = searchListUseCasesDep, private val subscribeOnScheduler: Scheduler = schedulerIo, private val observeOnScheduler: Scheduler = androidMainThreadScheduler) : BaseViewModel() {

    val stateLiveData = MutableLiveData<CharacterListState>()

    init {
        stateLiveData.value = DefaultState(0, false, emptyList())
    }

    fun updateCryptoList(nameStartWith: String) {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = if (pageNum == 0)
            LoadingState(pageNum, false, obtainCurrentData())
        else
            PaginatingState(pageNum, false, obtainCurrentData())
        getCryptoList(nameStartWith,pageNum)
    }



    fun resetCryptoList() {
        stateLiveData.value = LoadingState(0, false, emptyList())
        updateCryptoList("?")
    }

    fun restoreCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = DefaultState(pageNum, false, obtainCurrentData())
    }

    private fun getCryptoList(nameStartWith:String,page: Int) {
        searchListUseCases.getSearchListBy(nameStartWith, page)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onCryptoListReceived, this::onError).addTo(disposable)
    }

    private fun onCryptoListReceived(cryptoList: List<Character>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = cryptoList.size < LIMIT_Character_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultState(currentPageNum, areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = ErrorState(error, pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}