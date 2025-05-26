package woowacourse.shopping.view.productDetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.showToast

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailActionsListener {
    private val viewModel: ProductDetailViewModel by viewModels()
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

        val product: Product =
            intent.getProductExtra() ?: run {
                showToast(R.string.product_not_provided_error_message)
                return finish()
            }
        initViewModel(product)
        bindViewModel()
        handleEvents()
    }

    private fun initViewModel(cartItem: Product) {
        viewModel.updateProduct(cartItem)
    }

    private fun Intent.getProductExtra(): Product? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtra(EXTRA_PRODUCT, Product::class.java)
        } else {
            getSerializableExtra(EXTRA_PRODUCT) as? Product
        }

    private fun bindViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.productDetailActionsListener = this
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event: ProductDetailEvent ->
            when (event) {
                ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS -> {
                    showToast(R.string.product_detail_add_shopping_cart_success_message)
                    setResult(RESULT_OK)
                    finish()
                }

                ProductDetailEvent.ADD_SHOPPING_CART_FAILURE ->
                    showToast(R.string.product_detail_add_shopping_cart_error_message)
            }
        }
    }

    override fun onClose() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onAddToShoppingCart() {
        viewModel.addToShoppingCart()
    }

    override fun onPlusProductQuantity() {
        viewModel.plusProductQuantity()
    }

    override fun onMinusProductQuantity() {
        viewModel.minusProductQuantity()
    }

    override fun onSelectLatestViewedProduct() {
        val intent =
            newIntent(this, viewModel.latestViewedProduct.value ?: return).apply {
                flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
            }
        startActivity(intent)
        finish()
    }

    companion object {
        private const val EXTRA_PRODUCT = "woowacourse.shopping.EXTRA_PRODUCT"

        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).putExtra(
                EXTRA_PRODUCT,
                product,
            )
    }
}
