package woowacourse.shopping.ui.productdetail

import androidx.core.view.isVisible
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.productdetail.uistate.LastViewedProductUIState
import woowacourse.shopping.utils.PRICE_FORMAT

class LastViewedProductViewHolder(
    private val binding: ActivityProductDetailBinding,
    private val onClick: (Long) -> Unit
) {

    fun bind(product: LastViewedProductUIState?) {
        if (product == null) {
            binding.layoutLastViewedProduct.isVisible = false
            return
        }
        binding.layoutLastViewedProduct.isVisible = true
        binding.tvLastViewedProductName.text = product.name
        binding.tvLastViewedProductPrice.text =
            binding.root.resources.getString(R.string.product_price).format(
                PRICE_FORMAT.format(product.price)
            )
        binding.layoutLastViewedProduct.setOnClickListener { onClick(product.id) }
    }
}