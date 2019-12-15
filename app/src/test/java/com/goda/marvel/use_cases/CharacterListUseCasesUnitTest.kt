package com.goda.marvel.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goda.marvel.common.cpojOmodel
import com.goda.marvel.common.mock
import com.goda.marvel.common.whenever
import com.goda.marvel.domain.characterList.API_KEY
import com.goda.marvel.domain.characterList.CharacterListInteractor
import com.goda.marvel.model.CharacterRepository
import com.goda.marvel.model.CharactersResponse
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt


class CharacterListUseCasesUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val characterRepository = mock<CharacterRepository>()

    val characterListUseCases by lazy { CharacterListInteractor(characterRepository) }

    @Test
    fun testCharacterListUseCases_getCharacterList_Completed() {
        whenever(characterRepository.getCharacterList(anyInt(), anyInt(), API_KEY))
                .thenReturn(Single.just(CharactersResponse()))

        characterListUseCases.getCharactersListBy(0)
                .test()
                .assertComplete()
    }

    @Test
    fun testCharacterListUseCases_getCharacterList_Error() {
        val response = Throwable("Error response")
        whenever(characterRepository.getCharacterList(anyInt(), anyInt(), API_KEY))
                .thenReturn(Single.error(response))

        characterListUseCases.getCharactersListBy(0)
                .test()
                .assertError(response)

    }

    @Test
    fun testCharacterListUseCases_getCharacterList_response() {
        val response = arrayListOf(cpojOmodel())
        whenever(characterListUseCases.getCharactersListBy(anyInt()))
                .thenReturn(Single.just(response))

        val expectedList = arrayListOf(/*cryptoViewModelFrom(*/cpojOmodel())

        characterListUseCases.getCharactersListBy(0)
                .test()
                .assertValue(expectedList)
    }
}