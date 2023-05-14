package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiProduct

class BasketAdapter(private val onItemClick: (UiProduct) -> Unit) :
    ListAdapter<UiProduct, BasketViewHolder>(basketDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(parent) { pos -> onItemClick(currentList[pos]) }


    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val basketDiffUtil = object : DiffUtil.ItemCallback<UiProduct>() {
            override fun areItemsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiProduct, newItem: UiProduct): Boolean =
                oldItem == newItem
        }
    }
}
