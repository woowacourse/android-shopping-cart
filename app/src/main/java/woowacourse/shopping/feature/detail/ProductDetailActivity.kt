package woowacourse.shopping.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.viewmodel.CartViewModel
import woowacourse.shopping.viewmodel.CartViewModelFactory
import woowacourse.shopping.viewmodel.ProductViewModel

class ProductDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }
    private val productViewModel by lazy {
        ViewModelProvider(this, ProductViewModelFactory(ProductRepositoryImpl))[ProductViewModel::class.java]
    }
    private val cartViewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory(CartRepositoryImpl))[CartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initializeToolbar()
        initializeAddCardButton()
        updateProduct()
    }

    private fun updateProduct() {
        productViewModel.loadProduct(productId())
        productViewModel.product.observe(this) {
            binding.product = it
        }
    }

    private fun initializeToolbar() {
        binding.toolbarDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_exit -> finish()
            }
            false
        }
    }

    private fun initializeAddCardButton() {
        binding.btnProductDetailAddCart.setOnClickListener {
            cartViewModel.add(productId())
        }
    }

    private fun productId(): Long = intent.getLongExtra(PRODUCT_ID_KEY, PRODUCT_ID_DEFAULT_VALUE)

    companion object {
        private const val PRODUCT_ID_KEY = "product_id_key"
        private const val PRODUCT_ID_DEFAULT_VALUE = -1L

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java)
                .putExtra(PRODUCT_ID_KEY, productId)
        }
    }
}
