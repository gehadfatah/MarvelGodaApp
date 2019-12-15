package com.goda.marvel.domain.detail

import com.goda.marvel.model.CESSResult
import io.reactivex.Single

val detailListUseCasesDep by lazy {
    DetailListInteractor()
}

interface DetailListUseCases {
    fun getComicsListBy(characterId:Int): Single<List<CESSResult>>
    fun getEventsListBy(characterId:Int): Single<List<CESSResult>>
    fun getStoriesListBy(characterId:Int): Single<List<CESSResult>>
    fun getSeriesListBy(characterId:Int): Single<List<CESSResult>>
}
