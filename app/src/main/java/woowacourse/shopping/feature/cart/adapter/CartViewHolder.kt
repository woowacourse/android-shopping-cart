package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Goods

class CartViewHolder(
    parent: ViewGroup,
    private val cartClickListener: CartClickListener,
) : RecyclerView.ViewHolder(
        ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false).root,
    ) {
    private val binding = ItemCartBinding.bind(itemView)

    init {
        binding.cartClickListener = cartClickListener
    }

    fun bind(goods: Goods) {
        binding.goods = goods
    }

    interface CartClickListener {
        fun onClickDeleteButton(goods: Goods)
    }
}
