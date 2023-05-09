package woowacourse.shopping.presentation.productlist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel) {
        binding.textProductListName.text = product.name
        binding.textProductListPrice.text =
            binding.textProductListPrice.context.getString(R.string.price_format, product.price)

        Glide.with(binding.imageProductListPoster.context)
            .load(product.imageUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageProductListPoster)
    }
}
