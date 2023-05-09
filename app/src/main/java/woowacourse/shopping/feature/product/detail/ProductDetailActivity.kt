package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.extension.showToast
import woowacourse.shopping.feature.model.ProductState
import java.text.DecimalFormat

class ProductDetailActivity : AppCompatActivity() {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding: ActivityProductDetailBinding
        get() = _binding!!

    private val product: ProductState? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (product == null) {
            showToast(getString(R.string.error_intent_message))
            finish()
        }

        binding.product = product

        product?.let { product ->
            Glide.with(this)
                .load(product.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(binding.productImage)

            binding.productPrice.text = "${DecimalFormat("#,###").format(product.price)}원"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PRODUCT_KEY = "product"

        fun startActivity(context: Context, product: ProductState) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            context.startActivity(intent)
        }
    }
}
