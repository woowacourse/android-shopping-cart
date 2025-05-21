package woowacourse.shopping.presentation.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemSelectedGoodsBinding
import woowacourse.shopping.presentation.model.GoodsUiModel

class ShoppingCartViewHolder(
    parent: ViewGroup,
    clickListener: ShoppingCartClickListener,
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_selected_goods, parent, false)) {
    private val binding = ItemSelectedGoodsBinding.bind(itemView)

    init {
        binding.clickListener = clickListener
        binding.clGoodsCount.clickListener = clickListener
    }

    fun bind(goods: GoodsUiModel) {
        binding.goods = goods
        binding.clGoodsCount.quantity = goods.quantity
        binding.clGoodsCount.position = adapterPosition
    }
}
