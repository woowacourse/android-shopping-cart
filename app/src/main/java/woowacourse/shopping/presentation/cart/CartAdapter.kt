package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.presentation.util.ItemDiffCallback

class CartAdapter(
    private val cartProductListener: CartProductListener,
) : ListAdapter<CartProductUi, CartAdapter.CartViewHolder>(cartProductComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartViewHolder {
        val binding =
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CartViewHolder(binding, cartProductListener)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val cartProductListener: CartProductListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductUi) {
            binding.cartProduct = product
            binding.listener = cartProductListener
        }
    }

    companion object {
        private val cartProductComparator =
            ItemDiffCallback<CartProductUi>(
                onItemsTheSame = { old, new -> old.product.id == new.product.id },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
