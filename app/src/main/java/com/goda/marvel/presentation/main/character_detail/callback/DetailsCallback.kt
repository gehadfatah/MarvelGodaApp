package com.goda.marvel.presentation.main.character_detail.callback

import com.goda.marvel.model.CESSResult
import com.goda.marvel.presentation.common.RecyclerViewCallback

interface DetailsCallback : RecyclerViewCallback {
  fun onClickedItem(item: CESSResult)
}
