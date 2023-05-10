package woowacourse.shopping.shopping

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.databinding.ItemProductListBinding

class ProductViewHolder(
    private val binding: ItemProductListBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductModel) {
        Glide.with(binding.root.context)
            .load(product.picture)
            .into(binding.productListPicture)
        binding.productListTitle.text = product.title
        binding.productListPrice.text = product.price.toString()
    }
}
