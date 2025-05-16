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
    eventHandler: CartProductEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: ShoppingProduct

    init {
        binding.onRemoveClick =
            OnClickListener { eventHandler.onItemRemoveClick(currentItem) }
    }

    fun bind(product: ShoppingProduct) {
        currentItem = product
        binding.product = product.productId.toProductDomain()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: CartProductEventHandler,
        ): CartProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSelectedProductBinding.inflate(inflater, parent, false)
            return CartProductViewHolder(binding, eventListener)
        }
    }
}
