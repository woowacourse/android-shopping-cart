package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.ProductViewType.ProductItem

class ProductItemViewHolder(
    private val binding: ItemProductBinding,
    showProductDetail: (ProductModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var _productModel: ProductModel? = null
    private val productModel get() = _productModel!!

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    fun bind(productItem: ProductItem) {
        _productModel = productItem.productModel
        binding.textProductListName.text = productModel.name
        binding.textProductListPrice.text =
            binding.textProductListPrice.context.getString(
                R.string.price_format,
                productModel.price,
            )
        setImage(productModel.imageUrl)
    }

    private fun setImage(productUrl: String) {
        Glide.with(binding.imageProductListPoster.context)
            .load(productUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageProductListPoster)
    }
}
