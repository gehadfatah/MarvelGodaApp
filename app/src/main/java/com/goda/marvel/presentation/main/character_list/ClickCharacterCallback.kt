package com.goda.marvel.presentation.main.character_list

import android.view.View
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.common.RecyclerViewCallback

interface ClickCharacterCallback:RecyclerViewCallback {
    fun onClicked(view:View,item: Character)

}
