package woowacourse.shopping.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartBinding
import woowacourse.shopping.presentation.model.ProductModel

class CartItemViewHolder(
    private val binding: ItemCartBinding,
    deleteItem: (ProductModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var _productModel: ProductModel? = null
    private val productModel get() = _productModel!!

    init {
        binding.imageCartDelete.setOnClickListener { deleteItem(productModel) }
    }

    fun bind(product: ProductModel) {
        _productModel = product
        binding.textCartProductName.text = product.name
        binding.textCartProductPrice.text =
            binding.textCartProductPrice.context.getString(R.string.price_format, product.price)
        setImage(product.imageUrl)
    }

    private fun setImage(productUrl: String) {
        Glide.with(binding.imageCartProductPoster.context)
            .load(productUrl)
            .error(R.drawable.default_image)
            .centerCrop()
            .into(binding.imageCartProductPoster)
    }
}
