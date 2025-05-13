package woowacourse.shopping

import android.os.Bundle
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.util.getParcelableExtraCompat

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val product: Product = intent.getParcelableExtraCompat<Product>("PRODUCT") ?: throw IllegalArgumentException()
        binding.product = product
    }
}
