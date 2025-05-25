package woowacourse.shopping.presentation.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemSelectedGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.presentation.util.QuantitySelectorListener

class ShoppingCartViewHolder(
    parent: ViewGroup,
    shoppingCartClickListener: ShoppingCartClickListener,
    quantitySelectorListener: QuantitySelectorListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_goods, parent, false)) {
    private val binding = ItemSelectedGoodsBinding.bind(itemView)

    init {
        binding.shoppingCartClickListener = shoppingCartClickListener
        binding.clGoodsCount.quantityChangeListener = quantitySelectorListener
    }

    fun bind(goods: Goods) {
        binding.goods = goods
    }
}
