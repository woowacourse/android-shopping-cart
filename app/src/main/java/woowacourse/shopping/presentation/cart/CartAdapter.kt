package woowacourse.shopping.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartProductBinding

class CartAdapter(
    private val cartAction: CartAction,
) : ListAdapter<CartProductUi, CartAdapter.CartViewHolder>(DIFF_CALLBACK) {

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
        return CartViewHolder(binding, cartAction)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val cartAction: CartAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductUi) {
            binding.cartProduct = product
            binding.cartAction = cartAction
            binding.ivCartItemDelete.setOnClickListener {
                cartAction.deleteProduct(product)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CartProductUi>() {
                override fun areItemsTheSame(
                    oldItem: CartProductUi,
                    newItem: CartProductUi
                ): Boolean {
                    return oldItem.product.id == newItem.product.id
                }

                override fun areContentsTheSame(
                    oldItem: CartProductUi,
                    newItem: CartProductUi
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
