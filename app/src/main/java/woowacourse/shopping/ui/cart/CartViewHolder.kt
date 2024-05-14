package woowacourse.shopping.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.model.Product

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val itemRemoveClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.ivRemove.setOnClickListener {
            itemRemoveClickListener(product.id)
        }

        Glide.with(binding.root)
            .load(product.imageUrl)
            .into(binding.ivProductImage)
    }
}
