package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.shoppingcart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity() {

    private val product: ProductUiModel by lazy { intent.getSerializableCompat(PRODUCT_KEY)!! }
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        setUpView()
    }

    fun setUpView() {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.imageProductDetail)

        binding.textProductName.text = product.name
        binding.textProductPrice.text = product.price.toString()
        binding.imageCancel.setOnClickListener { finish() }
        binding.buttonPutToShoppingCart.setOnClickListener {
            val intent = ShoppingCartActivity.getIntent(this, product)
            startActivity(intent)
        }
    }

    companion object {
        private const val PRODUCT_KEY = "product"

        fun getIntent(context: Context, product: ProductUiModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            return intent
        }
    }
}
