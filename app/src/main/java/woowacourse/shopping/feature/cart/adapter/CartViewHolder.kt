package woowacourse.shopping.feature.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.feature.GoodsUiModel

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartClickListener: CartClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(goods: GoodsUiModel) {
        binding.goods = goods
        itemView.setOnClickListener { cartClickListener.onClickDeleteButton(goods) }
    }

    interface CartClickListener {
        fun onClickDeleteButton(goods: GoodsUiModel)
    }
}
