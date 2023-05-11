package woowacourse.shopping.presentation.ui.shoppingCart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.model.ProductInCart

class ShoppingCartViewHolder(
    private val onClick: (Int) -> Unit,
    private val binding: ItemShoppingCartProductBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(absoluteAdapterPosition)
        }
    }

    fun bind(product: ProductInCart) {
        binding.product = product
    }

    companion object {
        fun getView(parent: ViewGroup): ItemShoppingCartProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemShoppingCartProductBinding.inflate(layoutInflater, parent, false)
        }
    }
}
