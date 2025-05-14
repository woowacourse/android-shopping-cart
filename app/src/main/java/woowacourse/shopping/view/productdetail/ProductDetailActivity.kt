package woowacourse.shopping.view.productdetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.intent.getSerializableExtraData
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        val intentProductData = intent.getSerializableExtraData<Product>("product")
        binding.product = intentProductData
        binding.closeImageBtn.setOnClickListener {
            finish()
        }

        binding.tvAddToCart.setOnClickListener {
            val intent =
                Intent(this, CartActivity::class.java).apply {
                    putExtra("product", intentProductData)
                }
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
