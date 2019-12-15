package com.goda.marvel.domain.detail

import com.goda.marvel.common.mapNetworkErrors
import com.goda.marvel.model.*
import io.reactivex.Single



class DetailListInteractor(private val characterRepository: CharacterRepository = charactersRepositoryDep) : DetailListUseCases {

    override fun getComicsListBy(characterId:Int): Single<List<CESSResult>> {
        return characterRepository.getComicsCharacterList(characterId)
                .mapNetworkErrors()
                .map { response -> response.data.cessresults}

    }

    override fun getEventsListBy(characterId: Int): Single<List<CESSResult>> {
        return characterRepository.getEventsCharacterList(characterId)
                .mapNetworkErrors()
                .map { response -> response.data.cessresults}
    }

    override fun getStoriesListBy(characterId: Int): Single<List<CESSResult>> {
        return characterRepository.getStoriesCharacterList(characterId)
                .mapNetworkErrors()
                .map { response -> response.data.cessresults}
    }

    override fun getSeriesListBy(characterId: Int): Single<List<CESSResult>> {
        return characterRepository.getSeriesCharacterList(characterId)
                .mapNetworkErrors()
                .map { response -> response.data.cessresults}
    }

}