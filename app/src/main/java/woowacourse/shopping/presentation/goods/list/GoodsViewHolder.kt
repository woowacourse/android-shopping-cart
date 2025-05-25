package woowacourse.shopping.presentation.goods.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemGoodsBinding
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.presentation.util.QuantityClickListener

class GoodsViewHolder(
    parent: ViewGroup,
    quantityClickListener: QuantityClickListener,
    goodsClickListener: GoodsClickListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)) {
    private val binding = ItemGoodsBinding.bind(itemView)

    init {
        binding.goodsClickListener = goodsClickListener
        binding.quantityClickListener = quantityClickListener
    }

    fun bind(item: ShoppingCartItem) {
        binding.item = item
    }
}
