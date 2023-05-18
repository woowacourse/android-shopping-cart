package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct

class BasketAdapter(private val onDeleteClick: (UiBasketProduct) -> Unit) :
    ListAdapter<UiBasketProduct, BasketViewHolder>(basketDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(parent) { pos -> onDeleteClick(currentList[pos]) }


    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val basketDiffUtil = object : DiffUtil.ItemCallback<UiBasketProduct>() {
            override fun areItemsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct): Boolean =
                oldItem == newItem
        }
    }
}
