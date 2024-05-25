package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.local.entity.CartProductEntity
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val shoppingHandler: ShoppingHandler,
        private val shoppingActionHandler: ShoppingActionHandler
    ) :
        ShoppingViewHolder(binding.root) {
        private var id: Long = -1

        init {
            binding.root.setOnClickListener {
                shoppingHandler.onClick(id)
            }
        }

        fun bind(item: CartProduct) {
            binding.cartProduct = item
            binding.shoppingActionHandler = shoppingActionHandler
            id = item.productId
        }
    }

    class LoadViewHolder(
        private val binding: ItemLoadBinding,
        private val shoppingHandler: ShoppingHandler,
    ) : ShoppingViewHolder(binding.root) {
        fun bind() {
            binding.btnShowMore.setOnClickListener {
                shoppingHandler.loadMore()
            }
        }
    }
}
