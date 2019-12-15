package com.goda.marvel.common

import com.goda.marvel.domain.characterList.LIMIT_Character_LIST
import com.goda.marvel.model.*
import java.util.*
import kotlin.collections.ArrayList


fun oneSizeArrayEmptyCharacterViewModel(): List<Character> =
        ArrayList(Collections.nCopies(1, Character()))

fun limitCharacterListSizeArrayEmptyCharacterViewModel(): List<Character> =
        ArrayList(Collections.nCopies(LIMIT_Character_LIST, Character()))

fun cpojOmodel() =
        //Character("1", "name", "sy", 10, "100", "0.1", "100", "1000", "0", "500", "5", "10", "-10", "")
Character(1, "", "",  Thumbnail("", ""), Comics(0, "", emptyList(), 0),
Series(0, "", emptyList(), 0),
Stories(0, "", emptyList(), 0),
Events(0, "", emptyList(), 0)
, emptyList())
/*

fun cryptoViewModelFrom(crypto: Crypto) =
        CryptoViewModel(crypto.id, crypto.name, crypto.symbol, crypto.rank, crypto.priceUsd.toFloat(), crypto.priceBtc.toFloat(), crypto.percentChange24h.toFloat())*/
