package woowacourse.shopping.presentation.productlist.recentproduct

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.model.ProductModel

class RecentProductItemViewHolder(
    private val binding: ItemRecentProductBinding,
    private val showProductDetail: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var _productModel: ProductModel? = null
    private val productModel get() = _productModel!!

    init {
        itemView.setOnClickListener { showProductDetail(productModel) }
    }

    fun bind(recentProduct: ProductModel) {
        _productModel = recentProduct
        binding.textProductListName.text = recentProduct.name
        Glide.with(binding.imageProductListPoster.context)
            .load(recentProduct.imageUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageProductListPoster)
    }
}
