package woowacourse.shopping.product.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.databinding.ActivityCatalogBinding
import woowacourse.shopping.databinding.MenuCartLayoutBinding
import woowacourse.shopping.product.detail.DetailActivity

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatalogBinding
    private lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalog)
        applyWindowInsets()

        setViewModel()
        setProductAdapter()
        setRecentlyViewedProductAdapter()
        observeCatalogProducts()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCatalogUntilCurrentPage()
        viewModel.loadCartItemSize()
        viewModel.loadRecentlyViewedProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu_item, menu)

        val menuItem = menu?.findItem(R.id.action_cart)
        val binding =
            DataBindingUtil.inflate<MenuCartLayoutBinding>(
                LayoutInflater.from(this),
                R.layout.menu_cart_layout,
                null,
                false,
            )
        binding.layoutCartMenu.setOnClickListener {
            startActivity(CartActivity.newIntent(this))
        }
        binding.vm = viewModel
        binding.lifecycleOwner = this

        menuItem?.actionView = binding.root
        return true
    }

    private fun setViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                CatalogViewModelFactory(application as ShoppingApplication),
            )[CatalogViewModel::class.java]
    }

    private fun setProductAdapter() {
        val adapter =
            ProductAdapter(
                products = emptyList(),
                productActionListener =
                    object : ProductActionListener {
                        override fun onProductClick(product: ProductUiModel) {
                            val intent = DetailActivity.newIntent(this@CatalogActivity, product)
                            startActivity(intent)
                        }

                        override fun onLoadButtonClick() {
                            viewModel.loadNextCatalogProducts()
                        }

                        override fun onQuantityAddClick(product: ProductUiModel) {
                            viewModel.increaseQuantity(product)
                        }
                    },
                quantityControlListener = { event, product ->
                    if (event == 1) {
                        viewModel.increaseQuantity(product)
                    } else {
                        viewModel.decreaseQuantity(product)
                    }
                },
            )

        binding.recyclerViewProducts.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = spanSizeByPosition(position, adapter.itemCount)
            }
        binding.recyclerViewProducts.layoutManager = gridLayoutManager
    }

    private fun setRecentlyViewedProductAdapter() {
        val adapter =
            RecentlyViewedProductAdapter(
                products = emptyList(),
            ) {
                val intent = DetailActivity.newIntent(this@CatalogActivity, it)
                startActivity(intent)
            }
        binding.recyclerViewRecentlyViewedProducts.adapter = adapter
    }

    private fun observeCatalogProducts() {
        viewModel.catalogItems.observe(this) { value ->
            (binding.recyclerViewProducts.adapter as ProductAdapter).setItems(value)
        }
        viewModel.updatedItem.observe(this) { product ->
            if (product != null) {
                (binding.recyclerViewProducts.adapter as ProductAdapter).updateItem(product)
            }
        }
        viewModel.recentlyViewedProducts.observe(this) { products ->
            (binding.recyclerViewRecentlyViewedProducts.adapter as RecentlyViewedProductAdapter).setItems(
                products,
            )
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
