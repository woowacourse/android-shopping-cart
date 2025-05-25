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
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartItemDatabase
import woowacourse.shopping.data.cart.CartItemRepositoryImpl
import woowacourse.shopping.data.product.MockProducts
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.product.catalog.CatalogViewModel.Companion.factory
import woowacourse.shopping.product.catalog.event.CatalogEventHandlerImpl
import woowacourse.shopping.product.catalog.viewHolder.CartActionViewHolder
import woowacourse.shopping.product.detail.DetailActivity.Companion.newIntent
import woowacourse.shopping.product.recent.ViewedItemAdapter

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: CatalogViewModel by lazy {
        provideViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        applyWindowInsets()

        initRecyclerView()
        observePagingData()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun provideViewModel(): CatalogViewModel {
        val db = CartItemDatabase.getInstance(this)
        val repository = CartItemRepositoryImpl(db.cartItemDao())
        return ViewModelProvider(
            this,
            factory(MockProducts, repository),
        )[CatalogViewModel::class.java]
    }

    private fun initRecyclerView() {
        binding.recyclerViewProducts.apply {
            adapter = createAdapter()
            layoutManager = createGridLayoutManager(adapter as ProductAdapter)
        }

        binding.recyclerViewRecentView.apply {
            adapter = createViewedAdapter()
        }
    }

    private fun createAdapter(): ProductAdapter {
        val handler =
            CatalogEventHandlerImpl(viewModel) { product ->
                startActivity(newIntent(this, product))
            }
        return ProductAdapter(emptyList(), handler, handler)
    }

    private fun createViewedAdapter(): ViewedItemAdapter = ViewedItemAdapter(emptyList())

    private fun createGridLayoutManager(adapter: ProductAdapter): GridLayoutManager =
        GridLayoutManager(this, 2).apply {
            spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = if (adapter.isLoadMoreButtonPosition(position)) 2 else 1
                }
        }

    private fun observePagingData() {
        viewModel.pagingData.observe(this) { paging ->
            (binding.recyclerViewProducts.adapter as ProductAdapter).apply {
                setData(paging.products)
                setLoadButtonVisible(paging.hasNext)
            }
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
}
