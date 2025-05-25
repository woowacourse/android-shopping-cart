package woowacourse.shopping.presentation.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemSelectedGoodsBinding
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.presentation.util.QuantityClickListener

class ShoppingCartViewHolder(
    parent: ViewGroup,
    quantityClickListener: QuantityClickListener,
    clickListener: ShoppingCartClickListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_goods, parent, false)) {
    private val binding = ItemSelectedGoodsBinding.bind(itemView)

    init {
        binding.clickListener = clickListener
        binding.quantityClickListener = quantityClickListener
    }

    fun bind(item: ShoppingCartItem) {
        binding.item = item
    }
}
