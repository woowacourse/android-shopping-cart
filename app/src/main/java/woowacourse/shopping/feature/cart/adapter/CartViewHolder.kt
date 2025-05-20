package woowacourse.shopping.feature.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.domain.model.Goods

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartClickListener: CartClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.cartClickListener = cartClickListener
    }

    fun bind(goods: Goods) {
        binding.goods = goods
    }

    companion object {
        fun from(
            parent: ViewGroup,
            cartClickListener: CartClickListener,
        ): CartViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
            return CartViewHolder(binding, cartClickListener)
        }
    }

    interface CartClickListener {
        fun onClickDeleteButton(goods: Goods)
    }
}
