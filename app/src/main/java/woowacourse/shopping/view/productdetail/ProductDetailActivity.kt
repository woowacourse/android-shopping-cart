package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
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
import woowacourse.shopping.model.intent.getSerializableExtraCompat
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.viewmodel.productdetail.ProductDetailViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        val intentProductData = intent.getSerializableExtraCompat<Product>("product") ?: return
        binding.product = intentProductData

        binding.closeImageBtn.setOnClickListener {
            finish()
        }

        binding.tvAddToCart.setOnClickListener {
            viewModel.addToCart(intentProductData)
            showAddToCartToastMessage()
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showAddToCartToastMessage() {
        Toast.makeText(this, getString(R.string.add_to_cart_message), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("product", product)
            }
    }
}
