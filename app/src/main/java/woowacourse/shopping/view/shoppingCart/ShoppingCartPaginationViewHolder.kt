package woowacourse.shopping.view.shoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemShoppingCartPaginationBinding

class ShoppingCartPaginationViewHolder(
    private val binding: ItemShoppingCartPaginationBinding,
    shoppingCartListener: ShoppingCartPaginationClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onShoppingCartPaginationListener = shoppingCartListener
    }

    fun bind(item: ShoppingCartItem.PaginationItem) {
        binding.page = item.page
        binding.shoppingCartPageCountPlusButton.isEnabled = item.nextEnabled
        binding.shoppingCartPageCountMinusButton.isEnabled = item.previousEnabled
        binding.root.isVisible = item.nextEnabled || item.previousEnabled
    }

    companion object {
        fun of(
            parent: ViewGroup,
            shoppingCartListener: ShoppingCartPaginationClickListener,
        ): ShoppingCartPaginationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingCartPaginationBinding.inflate(layoutInflater, parent, false)
            return ShoppingCartPaginationViewHolder(
                binding,
                shoppingCartListener,
            )
        }
    }

    interface ShoppingCartPaginationClickListener {
        fun onMinusPage()

        fun onPlusPage()
    }
}
