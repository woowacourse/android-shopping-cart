package woowacourse.shopping.shopping.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductViewHolder(
    private val binding: ItemProductListBinding,
    onItemViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { onItemViewClick(adapterPosition) }
    }

    fun bind(product: ProductModel) {
        Glide.with(binding.root.context)
            .load(product.picture)
            .centerCrop()
            .into(binding.productListPicture)
        binding.productListTitle.text = product.title
        binding.productListPrice.text =
            binding.root.context.getString(R.string.product_price, product.price)
    }
}
