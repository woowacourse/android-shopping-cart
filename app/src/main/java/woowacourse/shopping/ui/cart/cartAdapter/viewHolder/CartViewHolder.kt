package woowacourse.shopping.ui.cart.cartAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener

class CartViewHolder private constructor(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener
) : CartItemViewHolder(binding.root) {
    var count: Int = 1
        get() = binding.tvProductCount.text.toString().toInt()
        set(value) {
            field = cartListener.onItemUpdate(binding.product!!.id, value)
            binding.tvProductCount.text = field.toString()
        }

    init {
        binding.listener = cartListener
    }
    override fun bind(data: CartItemType) {
        if (data !is CartItemType.Cart) return
        binding.product = data.product
        count = data.product.count
        binding.tvProductCountMinus.setOnClickListener { count -= 1 }
        binding.tvProductCountPlus.setOnClickListener { count += 1 }
        binding.cbProductCheck.isChecked = data.product.selected
        binding.cbProductCheck.setOnCheckedChangeListener { _, isChecked ->
            cartListener.onItemSelectChanged(data.product.id, isChecked)
        }
    }

    companion object {
        fun from(parent: ViewGroup, cartListener: CartListener): CartViewHolder {
            val binding = ItemCartBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CartViewHolder(binding, cartListener)
        }
    }
}
