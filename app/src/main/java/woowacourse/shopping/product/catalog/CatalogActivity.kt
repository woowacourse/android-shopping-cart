package woowacourse.shopping.product.catalog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.data.MockProducts
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.product.catalog.CatalogViewModel.Companion.factory
import woowacourse.shopping.product.detail.DetailActivity.Companion.newIntent

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: CatalogViewModel by lazy {
        ViewModelProvider(
            this,
            factory(MockProducts),
        )[CatalogViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        applyWindowInsets()

        setProductAdapter()
        observePagingData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun setProductAdapter() {
        val handler =
            CatalogEventHandlerImpl(
                viewModel = viewModel,
                onNavigateToDetail = { product ->
                    startActivity(newIntent(this, product))
                },
            )

        val adapter =
            ProductAdapter(
                emptyList(),
                handler = handler,
            )

        binding.recyclerViewProducts.adapter = adapter

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (adapter.isLoadMoreButtonPosition(position)) 2 else 1
            }
        binding.recyclerViewProducts.layoutManager = gridLayoutManager
    }

    private fun observePagingData() {
        viewModel.pagingData.observe(this) { paging ->
            (binding.recyclerViewProducts.adapter as ProductAdapter).apply {
                setData(paging.products)
                setLoadButtonVisible(paging.hasNext)
            }
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
