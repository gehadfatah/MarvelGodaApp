package com.goda.marvel.presentation.main.character_list

import com.goda.marvel.model.Character

sealed class CharacterListState {
    abstract val pageNum: Int
    abstract val loadedAllItems: Boolean
    abstract val data: List<Character>
}

data class DefaultState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<Character>) : CharacterListState()
data class LoadingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<Character>) : CharacterListState()
data class PaginatingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<Character>) : CharacterListState()
data class ErrorState(val error: Throwable, override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<Character>) : CharacterListState()