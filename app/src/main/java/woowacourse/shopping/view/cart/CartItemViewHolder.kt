package woowacourse.shopping.view.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.databinding.ItemCartPaginationBinding
import woowacourse.shopping.model.CartProductModel

sealed class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class CartProductViewHolder(private val binding: ItemCartBinding) :
        CartItemViewHolder(binding.root) {
        fun bind(product: CartProductModel, onItemClick: CartAdapter.OnItemClick) {
            binding.cartProduct = product
            Log.d("test", "bind 진입")
            binding.onItemClick = onItemClick
        }
    }

    class CartPaginationViewHolder(
        private val binding: ItemCartPaginationBinding,
        onItemClick: CartAdapter.OnItemClick,
    ) : CartItemViewHolder(binding.root) {
        init {
            binding.onItemClick = onItemClick
        }

        fun bind(count: String, isExistUndo: Boolean, isExistNext: Boolean) {
            binding.textPageCount.text = count
            binding.btnUndoPage.isEnabled = isExistUndo
            binding.btnNextPage.isEnabled = isExistNext
        }
    }

    companion object {
        fun of(
            parent: ViewGroup,
            type: CartViewType,
            onItemClick: CartAdapter.OnItemClick,
        ): CartItemViewHolder {
            Log.d("test", "onCreateViewHolder의 of 진입")
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                CartViewType.CART_PRODUCT_ITEM -> CartProductViewHolder(ItemCartBinding.bind(view))
                CartViewType.PAGINATION_ITEM -> CartPaginationViewHolder(
                    ItemCartPaginationBinding.bind(
                        view,
                    ),
                    onItemClick,
                )
            }
        }
    }
}
