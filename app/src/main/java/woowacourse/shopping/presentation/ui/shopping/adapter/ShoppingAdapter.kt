package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

class ShoppingAdapter(
    private val shoppingActionHandler: ShoppingActionHandler,
) : ListAdapter<CartProduct, ShoppingViewHolder>(DIFF_CALLBACK) {
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - LOADING_OFFSET -> {
                ShoppingViewType.LoadMore.value
            }
            else -> {
                ShoppingViewType.Product.value
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ShoppingViewType.of(viewType)) {
            ShoppingViewType.Product -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.ProductViewHolder(binding, shoppingActionHandler)
            }

            ShoppingViewType.LoadMore -> {
                val binding = ItemLoadBinding.inflate(inflater, parent, false)
                ShoppingViewHolder.LoadViewHolder(binding, shoppingActionHandler)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ShoppingViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ShoppingViewHolder.ProductViewHolder -> {
                holder.bind(getItem(position))
            }
            is ShoppingViewHolder.LoadViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.isEmpty()) 0 else currentList.size + LOADING_OFFSET
    }

    companion object {
        private const val LOADING_OFFSET = 1
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CartProduct>() {
                override fun areItemsTheSame(
                    oldItem: CartProduct,
                    newItem: CartProduct,
                ): Boolean {
                    return oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: CartProduct,
                    newItem: CartProduct,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
