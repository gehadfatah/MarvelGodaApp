package com.goda.marvel.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.goda.marvel.common.limitCharacterListSizeArrayEmptyCharacterViewModel
import com.goda.marvel.common.mock
import com.goda.marvel.common.whenever
import com.goda.marvel.domain.characterList.CharacterListUseCases
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.main.character_list.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class CharacterListUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val characterListUseCases = mock<CharacterListUseCases>()
    val observerState = mock<Observer<CharacterListState>>()

    val viewmodel by lazy { CharacterListViewModel(characterListUseCases, Schedulers.trampoline(), Schedulers.trampoline()) }

    @Before
    fun initTest() {
        reset(characterListUseCases, observerState)
    }

    @Test
    fun testCharacterList_updateCharacterList_LoadOnePage() {
        val response = arrayListOf(Character())
        whenever(characterListUseCases.getCharactersListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val firstPage = 0
        verify(characterListUseCases).getCharactersListBy(firstPage)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterListState::class.java)
        val expectedLoadingState = LoadingState(firstPage, false, emptyList())
        val expectedDefaultState = DefaultState(firstPage+1, true, response)
        argumentCaptor.run {
            verify(observerState, times(3)).onChanged(capture())
            val (initialState, loadingState, defaultState) = allValues
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }

    @Test
    fun testCharacterList_updateCharacterList_LoadPagination() {
        val response = limitCharacterListSizeArrayEmptyCharacterViewModel()
        whenever(characterListUseCases.getCharactersListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()
        viewmodel.updateCryptoList()

        verify(characterListUseCases, times(2)).getCharactersListBy(ArgumentMatchers.anyInt())

        val expectedFinalResponse = mutableListOf<Character>()
        expectedFinalResponse.addAll(response)
        expectedFinalResponse.addAll(response)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterListState::class.java)
        val expectedPaginatingState = PaginatingState(1, false, response)
        val expectedFinalState = DefaultState(2, false, expectedFinalResponse)
        argumentCaptor.run {
            verify(observerState, times(5)).onChanged(capture())
            val (initialState, loadingState, defaultState, paginatingState, finalState) = allValues
            assertEquals(expectedPaginatingState, paginatingState)
            assertEquals(expectedFinalState, finalState)
        }
    }

    @Test
    fun testCharacterList_updateCharacterList_Error() {
        val errorMessage = "Error response"
        val response = Throwable(errorMessage)
        whenever(characterListUseCases.getCharactersListBy(ArgumentMatchers.anyInt()))
                .thenReturn(Single.error(response))

        viewmodel.stateLiveData.observeForever(observerState)
        viewmodel.updateCryptoList()

        val page = 0
        verify(characterListUseCases).getCharactersListBy(page)

        val argumentCaptor = ArgumentCaptor.forClass(CharacterListState::class.java)
        val expectedLoadingState = LoadingState(page, false, emptyList())
        val expectedErrorState = ErrorState(response, page, false, emptyList())
        argumentCaptor.run {
            verify(observerState, times(3)).onChanged(capture())
            val (initialState, loadingState, errorState) = allValues
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(errorState, expectedErrorState)
        }
    }
}