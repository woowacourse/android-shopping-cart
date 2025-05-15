package woowacourse.shopping.feature.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Goods

class CartViewHolder(
    private val binding: ItemCartBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(goods: Goods) {
        binding.goods = goods
    }

    interface CartClickListener {
        fun onClickDeleteButton(goods: Goods)
    }
}
