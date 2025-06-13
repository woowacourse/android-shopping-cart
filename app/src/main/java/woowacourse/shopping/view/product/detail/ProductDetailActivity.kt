package woowacourse.shopping.view.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.utils.getSerializableExtraCompat
import woowacourse.shopping.utils.showToast
import woowacourse.shopping.view.DefaultQuantityControlListener
import woowacourse.shopping.view.product.catalog.recentproducts.OnRecentProductEventListener
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductDetailViewModel
    private val binding by lazy { ActivityProductDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.rootProductDetail)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootProductDetail) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val product = intent.getSerializableExtraCompat<Product>(KEY_PRODUCT) ?: return
        initViewModel(product)
        initObservers()
        initBindingQuantityController()
        viewModel.getLastViewedProduct()
    }

    private fun initViewModel(product: Product) {
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModel.provideFactory(product),
            )[ProductDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lastViewedProduct.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.onRecentProductEventListener =
            OnRecentProductEventListener { product ->
                val intent = newIntent(this@ProductDetailActivity, product)
                startActivity(intent)
                viewModel.deleteMostRecentProduct()
            }
    }

    private fun initObservers() {
        viewModel.event.observe(this) { event: ProductDetailEvent ->
            when (event) {
                ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS -> {
                    showToast(R.string.product_detail_add_shopping_cart_success_message)
                    val intent = ShoppingCartActivity.newIntent(this)
                    startActivity(intent)
                }

                ProductDetailEvent.ADD_SHOPPING_CART_FAILURE ->
                    showToast(R.string.product_detail_add_shopping_cart_error_message)

                ProductDetailEvent.RECORD_RECENT_PRODUCT_FAILURE ->
                    showToast(R.string.product_detail_record_recent_products_error_message)
            }
        }
        viewModel.quantity.observe(this) { value ->
            binding.initQuantityControl.quantity = value
        }
    }

    private fun initBindingQuantityController() {
        binding.initQuantityControl.onClick =
            DefaultQuantityControlListener(
                onPlus = viewModel::increaseItemQuantity,
                onMinus = viewModel::decreaseItemQuantity,
            )
        binding.initQuantityControl.productId = 0L
    }

    companion object {
        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT, product)
            }

        private const val KEY_PRODUCT = "product"
    }
}
