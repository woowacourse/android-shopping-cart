package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ProductItemBinding

class ProductViewHolder(
    private val binding: ProductItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        binding.product = product
        bindImage(product)
    }

    private fun bindImage(product: ProductUiModel) {
        Glide
            .with(binding.root)
            .load(product.imageUrl)
            .placeholder(R.drawable.iced_americano)
            .fallback(R.drawable.iced_americano)
            .error(R.drawable.iced_americano)
            .into(binding.imageViewProduct)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onProductClick: ProductClickListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            binding.clickListener = onProductClick
            return ProductViewHolder(binding)
        }
    }
}
