package woowacourse.shopping.product.catalog

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.product.detail.DetailActivity

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private val viewModel: CatalogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        applyWindowInsets()

        setProductAdapter()
        observeCatalogProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(CartActivity.newIntent(this))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun setProductAdapter() {
        val adapter =
            ProductAdapter(
                products = emptyList(),
                totalDataSize = viewModel.allProductsSize,
                onProductClick =
                    ProductClickListener { product ->
                        val intent = DetailActivity.newIntent(this, product)
                        startActivity(intent)
                    },
                onLoadButtonClick = viewModel::loadNextCatalogProducts,
            )

        binding.recyclerViewProducts.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = spanSizeByPosition(position, adapter.itemCount)
            }
        binding.recyclerViewProducts.layoutManager = gridLayoutManager
    }

    private fun observeCatalogProducts() {
        viewModel.catalogProducts.observe(this) { value ->
            Log.d("CatalogViewModel", "OBSERVED")
            (binding.recyclerViewProducts.adapter as ProductAdapter).setData(value)
        }
        binding.lifecycleOwner = this
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun spanSizeByPosition(
        position: Int,
        itemCount: Int,
    ): Int = if (position == itemCount - 1 && itemCount % 20 == 1) 2 else 1
}
