package com.goda.marvel.domain.search

import com.goda.marvel.common.mapNetworkErrors
import com.goda.marvel.model.Character
import com.goda.marvel.model.CharacterRepository
import com.goda.marvel.model.charactersRepositoryDep
import io.reactivex.Single

const val LIMIT_search_LIST = 10
const val API_KEY = "7762cfbc02255884a47028f5bd6fb7e6"


class SearchListInteractor(private val characterRepository: CharacterRepository = charactersRepositoryDep) : SearchListUseCases {

    override fun getSearchListBy(nameStartWith:String,page: Int): Single<List<Character>> {
        return characterRepository.getSearchCharacterList(nameStartWith,page, LIMIT_search_LIST, API_KEY)
                .mapNetworkErrors()
                .map { response -> response.data.characters}

    }
}