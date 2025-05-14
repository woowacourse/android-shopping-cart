package woowacourse.shopping.presentation.productdetail

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
import woowacourse.shopping.data.CartMapper.toEntity
import woowacourse.shopping.data.db.CartDatabase
import woowacourse.shopping.databinding.ActivityDetailProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.getSerializableExtraCompat
import kotlin.concurrent.thread

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT_DETAIL)

        viewModel.fetchData(product)
        viewModel.product.observe(this) {
            binding.product = it
        }
        binding.btnProductDetailAddCart.setOnClickListener {
            thread {
                val db = CartDatabase.getInstance(this)
                val cartEntity = product.toEntity()
                db.cartDao().insertProduct(cartEntity)
            }
            Toast.makeText(this, "상품이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show()
        }
        binding.ibExit.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val KEY_PRODUCT_DETAIL = "product_data"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java)
                .apply { putExtra(KEY_PRODUCT_DETAIL, product) }
    }
}
