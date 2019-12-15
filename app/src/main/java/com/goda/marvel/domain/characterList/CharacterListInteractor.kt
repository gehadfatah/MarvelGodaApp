package com.goda.marvel.domain.characterList

import com.goda.marvel.common.mapNetworkErrors
import com.goda.marvel.model.Character
import com.goda.marvel.model.CharacterRepository
import com.goda.marvel.model.charactersRepositoryDep
import io.reactivex.Single

const val LIMIT_Character_LIST = 10
const val API_KEY = "7762cfbc02255884a47028f5bd6fb7e6"


class CharacterListInteractor(private val characterRepository: CharacterRepository = charactersRepositoryDep) : CharacterListUseCases {

    override fun getCharactersListBy(page: Int): Single<List<Character>> {
        return characterRepository.getCharacterList(page, LIMIT_Character_LIST, API_KEY)
                .mapNetworkErrors()
                .map { response -> response.data.characters}

    }

}