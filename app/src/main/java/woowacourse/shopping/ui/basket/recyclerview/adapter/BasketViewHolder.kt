package woowacourse.shopping.ui.basket.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemBasketBinding
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct

class BasketViewHolder(parent: ViewGroup, onItemClick: (Int) -> Unit) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
) {
    private val binding = ItemBasketBinding.bind(itemView)

    init {
        binding.closeButton.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(item: UiBasketProduct){
        binding.basketProduct = item
    }
}
