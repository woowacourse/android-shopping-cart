package woowacourse.shopping.product.catalog

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartItemDatabase
import woowacourse.shopping.data.cart.CartItemRepositoryImpl
import woowacourse.shopping.data.product.MockProducts
import woowacourse.shopping.data.recent.ViewedItemDatabase
import woowacourse.shopping.data.recent.ViewedItemRepositoryImpl
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.product.catalog.CatalogViewModel.Companion.factory
import woowacourse.shopping.product.catalog.event.CatalogEventHandlerImpl
import woowacourse.shopping.product.catalog.viewHolder.CartActionViewHolder
import woowacourse.shopping.product.detail.DetailActivity.Companion.newIntent
import woowacourse.shopping.product.recent.ViewedItemAdapter

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var viewedAdapter: ViewedItemAdapter

    private val viewModel: CatalogViewModel by lazy {
        provideViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        enableEdgeToEdge()
        applyWindowInsets()

        initRecyclerView()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeViewModel()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun provideViewModel(): CatalogViewModel {
        val cartDao = CartItemDatabase.getInstance(this).cartItemDao()
        val viewedDao = ViewedItemDatabase.getInstance(this).viewedItemDao()
        return ViewModelProvider(
            this,
            factory(
                MockProducts,
                CartItemRepositoryImpl(cartDao),
                ViewedItemRepositoryImpl(viewedDao),
            ),
        )[CatalogViewModel::class.java]
    }

    private fun initRecyclerView() {
        val handler = createHandler()

        setupProductRecyclerView(handler)
        setupRecentViewedRecyclerView(handler)
    }

    private fun setupProductRecyclerView(handler: CatalogEventHandlerImpl) {
        productAdapter = ProductAdapter(emptyList(), handler, handler)
        binding.recyclerViewProducts.apply {
            this.adapter = productAdapter
            layoutManager =
                createGridLayoutManager(productAdapter)
        }
    }

    private fun createGridLayoutManager(adapter: ProductAdapter): GridLayoutManager =
        GridLayoutManager(this, 2).apply {
            spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = if (adapter.isLoadMoreButtonPosition(position)) 2 else 1
                }
        }

    private fun setupRecentViewedRecyclerView(handler: CatalogEventHandlerImpl) {
        viewedAdapter = ViewedItemAdapter(handler)
        binding.recyclerViewRecentView.apply {
            layoutManager = LinearLayoutManager(this@CatalogActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = viewedAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.pagingData.observe(this) { paging ->
            productAdapter.apply {
                setData(paging.products)
                setLoadButtonVisible(paging.hasNext)
            }
        }
        viewModel.recentViewedItems.observe(this) { recentItems ->
            viewedAdapter.setData(recentItems)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu_item, menu)
        setupCartActionView(menu)
        return true
    }

    private fun setupCartActionView(menu: Menu?) {
        val menuItem = menu?.findItem(R.id.menu_cart) ?: return
        val holder = CartActionViewHolder(this, this, viewModel)
        menuItem.actionView = holder.rootView
    }

    private fun createHandler(): CatalogEventHandlerImpl =
        CatalogEventHandlerImpl(viewModel) { product ->
            startActivity(newIntent(this, product))
        }

    override fun onResume() {
        super.onResume()
        viewModel.loadRecentViewedItems()
    }
}
