package woowacourse.shopping.view.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.productDetail.ProductDetailActivity
import woowacourse.shopping.view.shoppingCart.ShoppingCartActivity
import woowacourse.shopping.view.common.showToast

class ProductsActivity : AppCompatActivity() {
    private val binding: ActivityProductsBinding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductsViewModel by viewModels()
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(::navigateToProductDetail, viewModel::updateProducts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.productsRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initDataBinding()
        handleEventsFromViewModel()
        bindData()
        setupAdapter()
        viewModel.updateProducts()
    }

    private fun initDataBinding() {
        binding.adapter = productAdapter
        binding.onClickShoppingCartButton = ::navigateToShoppingCart
        binding.lifecycleOwner = this
    }

    private fun handleEventsFromViewModel() {
        viewModel.event.observe(this) { event: ProductsEvent ->
            when (event) {
                ProductsEvent.UPDATE_PRODUCT_FAILURE -> showToast(getString(R.string.products_update_products_error_message))
            }
        }
    }

    private fun bindData() {
        viewModel.products.observe(this) { products: List<ProductsItem> ->
            productAdapter.submitList(products)
        }
    }

    private fun setupAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                when (ProductsItem.ItemType.from(productAdapter.getItemViewType(position))) {
                    ProductsItem.ItemType.PRODUCT -> 1
                    ProductsItem.ItemType.MORE -> 2
                }
        }

        binding.products.layoutManager = gridLayoutManager
    }

    private fun navigateToShoppingCart() {
        startActivity(ShoppingCartActivity.newIntent(this))
    }

    private fun navigateToProductDetail(product: Product) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }
}
