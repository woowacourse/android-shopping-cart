package woowacourse.shopping.view.productDetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.showToast

class ProductDetailActivity :
    AppCompatActivity(),
    ProductDetailClickListener {
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

        initViewModel()
        bindViewModel()
        handleEvents()
    }

    private fun initViewModel() {
        val product =
            intent.getProductExtra() ?: run {
                Toast
                    .makeText(
                        this,
                        getString(R.string.product_not_provided_error_message),
                        Toast.LENGTH_SHORT,
                    ).show()
                return finish()
            }
        viewModel.updateProduct(product)
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
        binding.productDetailEventListener = this
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event: ProductDetailEvent ->
            @StringRes
            val messageResourceId: Int =
                when (event) {
                    ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS -> R.string.product_detail_add_shopping_cart_success_message
                    ProductDetailEvent.ADD_SHOPPING_CART_FAILURE -> R.string.product_detail_add_shopping_cart_error_message
                }

            showToast(getString(messageResourceId))
        }
    }

    override fun close() {
        finish()
    }

    override fun addToShoppingCart() {
        viewModel.addToShoppingCart()
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
