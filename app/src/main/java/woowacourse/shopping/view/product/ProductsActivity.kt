package woowacourse.shopping.view.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.productDetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingCart.ShoppingCartActivity
import woowacourse.shopping.view.showToast

class ProductsActivity : AppCompatActivity() {
    private val productAdapter: ProductAdapter =
        ProductAdapter(emptyList(), ::navigateToProductDetail)
    private val binding: ActivityProductsBinding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDataBinding()
        handleEvents()
        bindData()
        viewModel.updateProducts()
    }

    private fun initDataBinding() {
        binding.adapter = productAdapter
        binding.onClickShoppingCartButton = ::navigateToShoppingCart
    }

    private fun handleEvents() {
        viewModel.event.observe(this) { event: ProductsEvent ->
            when (event) {
                ProductsEvent.UPDATE_PRODUCT_FAILURE -> showToast(getString(R.string.products_update_products_error_message))
            }
        }
    }

    private fun bindData() {
        viewModel.products.observe(this) { products: List<Product> ->
            productAdapter.submitList(products)
        }
    }

    private fun navigateToShoppingCart() {
        startActivity(ShoppingCartActivity.newIntent(this))
    }

    private fun navigateToProductDetail(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }
}
