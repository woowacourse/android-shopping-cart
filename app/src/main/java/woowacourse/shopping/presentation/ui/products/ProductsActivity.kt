package woowacourse.shopping.presentation.ui.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.databinding.MenuCartCombinedBinding
import woowacourse.shopping.presentation.ui.base.BaseActivity
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel

@SuppressLint("NotifyDataSetChanged")
class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsAdapter = ProductsAdapter(createAdapterOnClickHandler(), this, viewModel)
        initViewBinding()
        initObservers()
        viewModel.updateProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_products, menu)
        val view = menu.findItem(R.id.item_cart_combined)
        val actionView = view.actionView
        MenuCartCombinedBinding.bind(actionView!!).apply {
            viewModel = this@ProductsActivity.viewModel
            lifecycleOwner = this@ProductsActivity
        }
        return true
    }

    private fun createAdapterOnClickHandler() =
        object : ProductsAdapter.OnClickHandler {
            override fun onProductClick(id: Int) {
                navigateToProductDetail(id)
            }

            override fun onInsertCartClick(id: Int) {
                viewModel.upCount(id)
                productsAdapter.refreshItemById(id)
            }

            override fun onPlusClick(id: Int) {
                viewModel.upCount(id)
                productsAdapter.refreshItemById(id)
            }

            override fun onMinusClick(id: Int) {
                viewModel.downCount(id)
                productsAdapter.refreshItemById(id)
            }

            override fun onLoadMoreClick() {
                viewModel.updateProducts()
            }
        }

    private fun navigateToProductDetail(id: Int) {
        val intent = ProductDetailActivity.Companion.newIntent(this, id)
        startActivity(intent)
    }

    fun navigateToCart(view: View) {
        val intent = CartActivity.Companion.newIntent(this)
        startActivity(intent)
    }

    private fun initViewBinding() {
        binding.rvProducts.adapter = productsAdapter
        binding.rvProducts.layoutManager = createLayoutManager()
        binding.lifecycleOwner = this
    }

    private fun createLayoutManager(): GridLayoutManager =
        GridLayoutManager(this, GRID_LAYOUT_SIZE).apply {
            spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val viewType = productsAdapter.getItemViewType(position)
                        return when (ProductsItemViewType.entries[viewType]) {
                            ProductsItemViewType.PRODUCT -> PRODUCT_LAYOUT_SIZE
                            ProductsItemViewType.LOAD_MORE -> LOAD_MORE_LAYOUT_SIZE
                            ProductsItemViewType.LAST_WATCH -> LAST_WATCH_LAYOUT_SIZE
                            ProductsItemViewType.LAST_WATCH_TITLE -> LAST_WATCH_LAYOUT_SIZE
                        }
                    }
                }
        }

    private fun initObservers() {
        viewModel.products.observe(this) { products ->
            productsAdapter.updateProductItems(products)
            viewModel.updateIsLoadable()
        }
        viewModel.isLoadingProducts.observe(this) { isLoadable ->
            productsAdapter.updateLoadMoreItem(isLoadable)
            productsAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val GRID_LAYOUT_SIZE: Int = 2
        private const val PRODUCT_LAYOUT_SIZE: Int = 1
        private const val LOAD_MORE_LAYOUT_SIZE: Int = 2
        private const val LAST_WATCH_LAYOUT_SIZE: Int = 2
    }
}
