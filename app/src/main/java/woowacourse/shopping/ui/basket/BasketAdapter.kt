package woowacourse.shopping.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.ui.model.UiBasketProduct

class BasketAdapter(private val onRemoveItemClick: (UiBasketProduct, List<UiBasketProduct>) -> Unit) :
    ListAdapter<UiBasketProduct, BasketViewHolder>(basketDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding =
            ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding) { onRemoveItemClick(it, currentList) }
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val basketDiffUtil = object : DiffUtil.ItemCallback<UiBasketProduct>() {
            override fun areItemsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct):
                Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UiBasketProduct, newItem: UiBasketProduct):
                Boolean = oldItem == newItem
        }
    }
}
