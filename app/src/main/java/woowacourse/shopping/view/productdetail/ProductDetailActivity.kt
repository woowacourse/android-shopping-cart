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
import woowacourse.shopping.databinding.ActivityDetailProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.getSerializableExtraCompat

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModel.Factory }
    private val product: Product by lazy {
        intent.getSerializableExtraCompat<Product>(KEY_PRODUCT_DETAIL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initView()
        bindData()
    }

    private fun initView() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun bindData() {
        viewModel.fetchData(product)
        viewModel.product.observe(this) { product ->
            binding.product = product
        }
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        binding.toCart = ::addCart
        binding.toExit = ::finish
    }

    private fun addCart() {
        viewModel.addData(product)
        Toast.makeText(this, R.string.detail_product_add_cart_success, Toast.LENGTH_SHORT).show()
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
