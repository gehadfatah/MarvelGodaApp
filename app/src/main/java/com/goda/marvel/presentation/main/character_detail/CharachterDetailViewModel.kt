package com.goda.marvel.presentation.main.character_detail

import androidx.lifecycle.MutableLiveData
import com.goda.marvel.common.addTo
import com.goda.marvel.common.androidMainThreadScheduler
import com.goda.marvel.common.schedulerIo

import com.goda.marvel.domain.detail.DetailListUseCases
import com.goda.marvel.domain.detail.detailListUseCasesDep
import com.goda.marvel.model.CESSResult
import com.goda.marvel.presentation.common.BaseViewModel
import io.reactivex.Scheduler

class CharachterDetailViewModel(private val detailListUseCases: DetailListUseCases = detailListUseCasesDep, private val subscribeOnScheduler: Scheduler = schedulerIo, private val observeOnScheduler: Scheduler = androidMainThreadScheduler) : BaseViewModel() {

    val comicResultList: MutableLiveData<List<CESSResult>> = MutableLiveData()
    val serisResultList: MutableLiveData<List<CESSResult>> = MutableLiveData()
    val eventsResultList: MutableLiveData<List<CESSResult>> = MutableLiveData()
    val storiesResultList: MutableLiveData<List<CESSResult>> = MutableLiveData()

     fun getComicsList(characterId: Int) {
        detailListUseCases.getComicsListBy(characterId)
               /* .doOnSubscribe{setLoading(true)}*/
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe({
                    it?.let {
                        comicResultList.value = it
                    }
                   // setLoading(false)
                }, {
                   // setLoading(false)
                  //  setError(it)
                }).addTo(disposable)
    }
    fun getEventsList(characterId: Int) {
        detailListUseCases.getEventsListBy(characterId)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe({
                    it?.let {
                        eventsResultList.value = it
                    }
                    //setLoading(false)
                }, {
                    //setLoading(false)
                   // setError(it)
                }).addTo(disposable)
    }
    fun getSeriesList(characterId: Int) {
        detailListUseCases.getSeriesListBy(characterId)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe({
                    it?.let {
                        serisResultList.value = it
                    }
                    //setLoading(false)
                }, {
                   // setLoading(false)
                   // setError(it)
                }).addTo(disposable)
    }
    fun getStoriesList(characterId: Int) {
        detailListUseCases.getStoriesListBy(characterId)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe({
                    it?.let {
                        storiesResultList.value = it
                    }
                    /*setLoading(false)*/
                }, {
                  /*  setLoading(false)
                    setError(it)*/
                }).addTo(disposable)
    }

}
