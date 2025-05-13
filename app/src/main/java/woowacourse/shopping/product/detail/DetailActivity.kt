package woowacourse.shopping.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.IntentCompat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        applyWindowInsets()

        val product: ProductUiModel? = productFromIntent()
        product?.let {
            binding.product = it
            showImage(it)
        }

        binding.clickListener =
            AddToCartClickListener { product ->
                showToastMessage()
            }
    }

    private fun showToastMessage() {
        Toast.makeText(this, "장바구니에 상품이 추가되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun productFromIntent(): ProductUiModel? =
        IntentCompat.getParcelableExtra(intent, KEY_PRODUCT_DETAIL, ProductUiModel::class.java)

    private fun showImage(product: ProductUiModel) {
        Glide
            .with(binding.root)
            .load(product.imageUrl)
            .placeholder(R.drawable.iced_americano)
            .fallback(R.drawable.iced_americano)
            .error(R.drawable.iced_americano)
            .into(binding.imageViewProductDetail)
    }

    companion object {
        private const val KEY_PRODUCT_DETAIL = "productDetail"

        fun newIntent(
            context: Context,
            product: ProductUiModel,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_DETAIL, product)
            }
    }
}
