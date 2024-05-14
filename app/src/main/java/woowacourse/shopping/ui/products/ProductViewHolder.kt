package woowacourse.shopping.ui.products

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.Product

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product

        Glide.with(binding.root)
            .load(product.imageUrl)
            .into(binding.ivProductImage)
    }
}
