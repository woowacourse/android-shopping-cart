package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.model.UiBasketProduct

class BasketViewHolder(
    parent: ViewGroup,
    onDelete: (Int) -> Unit,
    onSelect: (Int) -> Unit,
    onUnselect: (Int) -> Unit,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
) {
    private val binding = ItemBasketBinding.bind(itemView)

    init {
        binding.onDelete = { onDelete(bindingAdapterPosition) }
        binding.onSelect = { onSelect(bindingAdapterPosition) }
        binding.onUnselect = { onUnselect(bindingAdapterPosition) }
        binding.onIncrease = { onIncrease(bindingAdapterPosition) }
        binding.onDecrease = { onDecrease(bindingAdapterPosition) }
    }

    fun bind(item: UiBasketProduct) {
        binding.basketProduct = item
    }
}
