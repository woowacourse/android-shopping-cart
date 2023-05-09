package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.ShoppingDBAdapter
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.shoppingcart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding
    private val product: ProductUiModel by lazy { intent.getSerializableCompat(PRODUCT_KEY)!! }
    private val presenter: ProductDetailPresenter by lazy {
        ProductDetailPresenter(
            view = this,
            product = product,
            repository = ShoppingDBAdapter(
                shoppingDao = ShoppingDao(this),
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.presenter = presenter
        setUpProductDetailView()
    }

    private fun setUpProductDetailView() {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.imageProductDetail)

        binding.textProductName.text = product.name
        binding.textProductPrice.text = product.price.toString()
        binding.imageCancel.setOnClickListener { finish() }
    }

    override fun navigateToShoppingCartView() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
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
