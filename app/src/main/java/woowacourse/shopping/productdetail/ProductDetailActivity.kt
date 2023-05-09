package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.ProductUIModel.Companion.PRODUCT_KEY
import woowacourse.shopping.utils.getSerializable

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        binding.product = getIntentProductData()
    }

    private fun getIntentProductData(): ProductUIModel? =
        intent.extras?.getSerializable<ProductUIModel>(PRODUCT_KEY)

    companion object {
        fun from(context: Context, productUIModel: ProductUIModel): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(
                PRODUCT_KEY,
                productUIModel
            )
        }
    }
}
