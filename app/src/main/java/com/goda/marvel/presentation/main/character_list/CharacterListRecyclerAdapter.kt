package com.goda.marvel.presentation.main.character_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.goda.marvel.R
import com.goda.marvel.common.formatTo
import com.goda.marvel.common.loadImage
import com.goda.marvel.domain.characterList.CryptoViewModel
import com.goda.marvel.domain.characterList.emptyCharacterViewModel
import com.goda.marvel.model.Character
import com.goda.marvel.presentation.common.RecyclerViewCallback
import com.goda.marvel.presentation.widgets.paginatedRecyclerView.PaginationAdapter
import kotlinx.android.synthetic.main.charcter_list_item.view.*


private const val DECIMALS_FIAT = 4
private const val DECIMALS_BTC = 7
private const val DECIMALS_CHANGE = 2

class CharacterListRecyclerAdapter(private var clickCallback: RecyclerViewCallback = object : RecyclerViewCallback {}) : PaginationAdapter<Character>() {

    override fun addLoadingViewFooter() {
        addLoadingViewFooter(emptyCharacterViewModel)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is CryptoViewHolder) holder.bind(clickCallback as ClickCharacterCallback,dataList[position])
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.charcter_list_item, parent, false)
        return CryptoViewHolder(view)
    }

    fun updateData(newData: List<Character>) {
        val fromIndex = dataList.size
        dataList = newData.toMutableList()
        notifyItemRangeInserted(fromIndex, newData.size)
    }
}

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val resources by lazy { itemView.context.resources }

    fun bind(clickCallback: ClickCharacterCallback,item: Character) {
        itemView.apply {
            setOnClickListener {clickCallback.onClicked(this,item)}
            charactrImage.apply {
                loadImage(this, item.thumbnail?.path + ".jpg")
            }
            tvName.text = item.name
//            tvPrice.text = bindPrice(item)
//            tvChange24h.text = bindChangeText(item)
//            tvChange24h.setTextColor(bindChangeColor(item))
        }
    }

    /* fun bindPrice(item: Character) =
             if (item.isBtc()) resources.getString(R.string.price_btc, item.priceFiat.formatTo(DECIMALS_FIAT))
             else resources.getString(R.string.price_alts, item.priceFiat.formatTo(DECIMALS_FIAT), item.priceBtc.formatTo(DECIMALS_BTC))
    */ fun bindChangeColor(item: CryptoViewModel) =
            ContextCompat.getColor(itemView.context, if (item.change > 0) R.color.change_positive else R.color.change_negative)

    fun bindChangeText(item: CryptoViewModel) =
            resources.getString(R.string.change_percent, item.change.formatTo(DECIMALS_CHANGE))
}
