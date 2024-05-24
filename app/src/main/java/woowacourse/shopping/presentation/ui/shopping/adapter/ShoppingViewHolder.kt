package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.local.entity.CartProduct
import woowacourse.shopping.databinding.ItemLoadBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.shopping.ShoppingHandler

sealed class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val shoppingHandler: ShoppingHandler,
    ) :
        ShoppingViewHolder(binding.root) {
        private var id: Long = -1

        init {
            binding.root.setOnClickListener {
                shoppingHandler.onClick(id)
            }
        }

        fun bind(item: CartProduct) {
            id = item.productId
            binding.tvName.text = item.name
            binding.tvPrice.text = binding.root.context.getString(R.string.won, item.price)
            Glide.with(binding.root.context)
                .load(item.imgUrl)
                .into(binding.imgItem)
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
