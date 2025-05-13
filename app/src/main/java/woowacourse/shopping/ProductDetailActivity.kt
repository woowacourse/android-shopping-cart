package woowacourse.shopping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.shopping.base.BaseActivity
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val product: Product =
            intent.extras?.getParcelableCompat(KEY_PRODUCT) ?: run {
                finish()
                return
            }
        binding.product = product
    }

    companion object {
        private const val KEY_PRODUCT = "product"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }
        }
    }
}
