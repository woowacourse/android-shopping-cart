package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.model.UiProduct

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
    }

    companion object {
        private const val PRODUCT_KEY = "product"
        fun getIntent(context: Context, product: UiProduct): Intent =
            Intent(context, ProductDetailActivity::class.java).putExtra(PRODUCT_KEY, product)
    }
}
