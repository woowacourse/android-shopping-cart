package woowacourse.shopping.ui.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.databinding.LayoutProductsCartQuantityBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.common.DataBindingActivity
import woowacourse.shopping.ui.model.ResultCode
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.history.HistoryProductAdapter
import woowacourse.shopping.ui.products.adapter.product.ProductsAdapter
import woowacourse.shopping.ui.products.adapter.product.ProductsAdapter.OnClickHandler
import woowacourse.shopping.ui.products.adapter.product.ProductsLayoutManager

class ProductsActivity : DataBindingActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }
    private val productsAdapter: ProductsAdapter = ProductsAdapter(createAdapterOnClickHandler())
    private val historyProductAdapter: HistoryProductAdapter = HistoryProductAdapter { id -> navigateToProductDetail(id) }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewBinding()
        initObservers()
        initActivityResultLauncher()
        viewModel.loadCartProducts()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistoryProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_products, menu)

        val binding =
            DataBindingUtil.inflate<LayoutProductsCartQuantityBinding>(
                LayoutInflater.from(this),
                R.layout.layout_products_cart_quantity,
                null,
                false,
            )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        menu?.findItem(R.id.itemCart)?.actionView = binding.root
        binding.root.setOnClickListener { navigateToCart() }

        return true
    }

    private fun createAdapterOnClickHandler() =
        object : OnClickHandler {
            override fun onProductClick(id: Int) {
                navigateToProductDetail(id)
            }

            override fun onIncreaseClick(id: Int) {
                viewModel.increaseCartProduct(id)
            }

            override fun onDecreaseClick(id: Int) {
                viewModel.decreaseCartProduct(id)
            }

            override fun onLoadMoreClick() {
                viewModel.loadCartProducts()
            }
        }

    private fun navigateToCart() {
        val intent = CartActivity.newIntent(this)
        activityResultLauncher.launch(intent)
    }

    private fun navigateToProductDetail(
        id: Int,
        isRecentHistoryProductShown: Boolean = true,
    ) {
        val intent = ProductDetailActivity.newIntent(this, id, isRecentHistoryProductShown)
        activityResultLauncher.launch(intent)
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initProductsView()
        initHistoryProductsView()
    }

    private fun initProductsView() {
        binding.productItemsContainer.adapter = productsAdapter
        binding.productItemsContainer.layoutManager = ProductsLayoutManager(this, productsAdapter)
        binding.productItemsContainer.itemAnimator = null
    }

    private fun initHistoryProductsView() {
        binding.productsHistoryItemsContainer.adapter = historyProductAdapter
        binding.productsHistoryItemsContainer.itemAnimator = null
    }

    private fun initObservers() {
        viewModel.catalogProducts.observe(this) { products ->
            productsAdapter.submitItems(products.products, products.hasMore)
        }

        viewModel.historyProducts.observe(this) { products ->
            historyProductAdapter.submitItems(products)
        }
    }

    private fun initActivityResultLauncher() {
        activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                when (result.resultCode) {
                    ResultCode.PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED.code ->
                        navigateToProductDetail(
                            result.data?.getIntExtra(ResultCode.PRODUCT_DETAIL_HISTORY_PRODUCT_CLICKED.key, 0) ?: 0,
                            false,
                        )

                    ResultCode.PRODUCT_DETAIL_CART_UPDATED.code ->
                        viewModel.loadCartProduct(
                            result.data?.getIntExtra(ResultCode.PRODUCT_DETAIL_CART_UPDATED.key, 0) ?: 0,
                        )

                    ResultCode.CART_PRODUCT_EDITED.code ->
                        viewModel.loadCartProducts(
                            result.data?.getIntegerArrayListExtra(ResultCode.CART_PRODUCT_EDITED.key)?.toList() ?: emptyList(),
                        )
                }
            }
    }
}
