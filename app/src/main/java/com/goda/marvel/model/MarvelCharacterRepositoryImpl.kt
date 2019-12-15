package com.goda.marvel.model

import io.reactivex.Single

class MarvelCharacterRepositoryImpl(private val marvelCharacterApi: MarvelCharacterApi = MARVEL_CHARACTER_API_DEP) : CharacterRepository {
    override fun getCharacterList(page: Int, limit: Int, apiKey: String): Single<CharactersResponse> = marvelCharacterApi.getCharactersList(limit,page*limit , apiKey)
    override fun getComicsCharacterList(characterId:Int): Single<CessResponse> = marvelCharacterApi.getComicsCharacterList(characterId)
    override fun getEventsCharacterList(characterId:Int): Single<CessResponse> = marvelCharacterApi.getEventsCharacterList(characterId)
    override fun getSeriesCharacterList(characterId:Int): Single<CessResponse> = marvelCharacterApi.getSeriesCharacterList(characterId)
    override fun getStoriesCharacterList(characterId:Int): Single<CessResponse> = marvelCharacterApi.getStoriesCharacterList(characterId)
    override fun getSearchCharacterList(nameStartsWith: String, page: Int, limit: Int, apiKey: String): Single<CharactersResponse> {
       return marvelCharacterApi.getSearchCharacterList(nameStartsWith,limit,page*limit , apiKey)
    }
}