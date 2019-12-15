package com.goda.marvel.domain.search

import com.goda.marvel.model.Character
import io.reactivex.Single

val searchListUseCasesDep by lazy {
    SearchListInteractor()
}

interface SearchListUseCases {
    fun getSearchListBy(nameStartWith:String,page: Int): Single<List<Character>>
}

