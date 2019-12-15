package com.goda.marvel.presentation.main.searchCharachter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goda.marvel.R
import com.goda.marvel.common.loadImage
import com.goda.marvel.domain.characterList.emptyCharacterViewModel
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.common.RecyclerViewCallback
import com.goda.marvel.presentation.main.character_list.ClickCharacterCallback
import com.goda.marvel.presentation.widgets.paginatedRecyclerView.PaginationAdapter
import kotlinx.android.synthetic.main.item_search.view.*


class SearchListRecyclerAdapter (private var clickCallback: RecyclerViewCallback = object : RecyclerViewCallback {}) : PaginationAdapter<Character>() {

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(emptyCharacterViewModel)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is SearchViewHolder) holder.bind(clickCallback as ClickCharacterCallback,dataList[position])
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    fun updateData(newData: List<Character>) {
        val fromIndex = dataList.size
        dataList = newData.toMutableList()
        notifyItemRangeInserted(fromIndex, newData.size)
    }
}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val resources by lazy { itemView.context.resources }

    fun bind(clickCallback: ClickCharacterCallback, item: Character) {
        itemView.apply {
            setOnClickListener {clickCallback.onClicked(this,item)}
            characterImage.apply { loadImage(this,item.thumbnail?.path+".jpg") }
            EName.text=item.name
        }
    }

}
