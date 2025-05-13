package woowacourse.shopping.view.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductUrls.url
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product

class ProductDetailActivity : AppCompatActivity() {
    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productDetailRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.product = Product.dummies[2]
        binding.imageUrl = Product.dummies[2].url
    }
}
