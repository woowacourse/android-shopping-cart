package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var productModel: ProductModel
    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    fun bind(product: ProductModel) {
        productModel = product
        binding.textProductListName.text = product.name
        binding.textProductListPrice.text =
            binding.textProductListPrice.context.getString(R.string.price_format, product.price)
        setImage(product.imageUrl)
    }

    private fun setImage(productUrl: String) {
        Glide.with(binding.imageProductListPoster.context)
            .load(productUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageProductListPoster)
    }
}
