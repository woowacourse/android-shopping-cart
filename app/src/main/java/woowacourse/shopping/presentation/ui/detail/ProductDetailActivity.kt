package woowacourse.shopping.presentation.ui.detail

import android.content.Context
import android.content.Intent
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.base.BaseActivity

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_product_detail

    override fun initStartView() {
    }

    companion object {
        private const val PUT_EXTRA_KEY = "키"

        fun start(
            context: Context,
            productId: Long,
        ) {
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PUT_EXTRA_KEY, productId)
                context.startActivity(this)
            }
        }
    }
}
