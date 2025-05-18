package woowacourse.shopping.view.productdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.intent.getSerializableExtraData
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.products.ProductsActivity.Companion.PRODUCT_DATA_KEY

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.viewModel = viewModel

        val intentProductData = intent.getSerializableExtraData<Product>(PRODUCT_DATA_KEY) ?: return
        binding.product = intentProductData

        setCloseButtonClickListener()
        observeAddToCart()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observeAddToCart() {
        viewModel.addToCart.observe(this) {
            showAddToCartToastMessage()
            finish()
        }
    }

    private fun setCloseButtonClickListener() {
        binding.closeImageBtn.setOnClickListener {
            finish()
        }
    }

    private fun showAddToCartToastMessage() {
        Toast.makeText(this, getString(R.string.add_to_cart_message), Toast.LENGTH_SHORT).show()
    }
}
