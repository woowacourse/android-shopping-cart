package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.util.PriceFormatter

class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.product = product
        binding.textPrice.text = binding.root.context.getString(R.string.korean_won, PriceFormatter.format(product.price.price))
        Glide.with(binding.root.context).load(product.imageUrl).into(binding.imgProduct)
    }
}
