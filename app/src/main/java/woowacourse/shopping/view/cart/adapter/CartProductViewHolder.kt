package woowacourse.shopping.view.cart.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.mapper.toProductDomain
import woowacourse.shopping.databinding.ItemSelectedProductBinding
import woowacourse.shopping.domain.ShoppingProduct

class CartProductViewHolder(
    private val binding: ItemSelectedProductBinding,
    eventHandler: EventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: ShoppingProduct

    init {
        binding.onRemoveClick =
            OnClickListener { eventHandler.onProductRemoveClick(currentItem) }
    }

    fun bind(product: ShoppingProduct) {
        currentItem = product
        binding.product = product.productId.toProductDomain()
    }

    interface EventHandler {
        fun onProductRemoveClick(item: ShoppingProduct)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: EventHandler,
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(binding, eventListener)
        }
    }
}
