package com.goda.marvel.model

import io.reactivex.Single

val charactersRepositoryDep by lazy {
    MarvelCharacterRepositoryImpl()
}

interface CharacterRepository {
    fun getCharacterList(page: Int, limit: Int, apiKey: String): Single<CharactersResponse>
    fun getSearchCharacterList(nameStartsWith: String,page: Int, limit: Int, apiKey: String): Single<CharactersResponse>
    fun getComicsCharacterList(characterId:Int): Single<CessResponse>
    fun getStoriesCharacterList(characterId:Int): Single<CessResponse>
    fun getSeriesCharacterList(characterId:Int): Single<CessResponse>
    fun getEventsCharacterList(characterId:Int): Single<CessResponse>
}