package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.view.core.handler.CartQuantityHandler
import woowacourse.shopping.view.main.state.ProductState

class CartAdapter(
    private val handler: Handler,
    private val cartQuantityHandler: CartQuantityHandler,
) : ListAdapter<ProductState, CartViewHolder>(CartDiffer()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, handler, cartQuantityHandler)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    interface Handler : CartViewHolder.Handler
}
