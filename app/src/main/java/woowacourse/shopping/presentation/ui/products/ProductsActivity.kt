package woowacourse.shopping.presentation.ui.products

import LastProductRepositoryImpl
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.databinding.MenuCartCombinedBinding
import woowacourse.shopping.presentation.ui.base.BindingActivity
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel
import woowacourse.shopping.util.DatabaseProvider

@SuppressLint("NotifyDataSetChanged")
class ProductsActivity : BindingActivity<ActivityProductsBinding>(R.layout.activity_products) {
    private val viewModel: ProductsViewModel by viewModels {
        provideProductsViewModelFactory(this)
    }
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsAdapter = ProductsAdapter(createAdapterOnClickHandler(), this, viewModel)
        initViewBinding()
        initObservers()

        if (savedInstanceState == null) {
            viewModel.updateProducts()
            viewModel.updateIsLoadable()
        }
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

    override fun onResume() {
        super.onResume()
        viewModel.fetchLastProducts()

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
            productsAdapter.updateProductItems(products, viewModel.lastProducts.value.orEmpty())
            viewModel.updateIsLoadable()
        }
        viewModel.isLoadingProducts.observe(this) { isLoadable ->
            productsAdapter.updateLoadMoreItem(isLoadable)
        }
        viewModel.lastProducts.observe(this) { lastProducts ->
            productsAdapter.updateProductItems(viewModel.products.value.orEmpty(), lastProducts)
        }
    }

    companion object {
        private const val GRID_LAYOUT_SIZE: Int = 2
        private const val PRODUCT_LAYOUT_SIZE: Int = 1
        private const val LOAD_MORE_LAYOUT_SIZE: Int = 2
        private const val LAST_WATCH_LAYOUT_SIZE: Int = 2
    }
}
fun provideProductsViewModelFactory(context: Context): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val appContext = context.applicationContext

            val productDao = DatabaseProvider.getDatabase(appContext).productDao()
            val cartDao = DatabaseProvider.getDatabase(appContext).cartDao()
            val lastProductDao = DatabaseProvider.getDatabase(appContext).lastProductDao()


            val productRepository = ProductRepositoryImpl(productDao)
            val cartRepository = CartRepositoryImpl(cartDao, productDao)
            val lastProductRepository =  LastProductRepositoryImpl(lastProductDao, productDao)

            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(productRepository, cartRepository,lastProductRepository) as T
        }
    }
}