package woowacourse.shopping.view.inventory

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.item.InventoryItemType

class InventoryActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    InventoryEventHandler {
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shoppingApplication = application as ShoppingApplication
        val factory = InventoryViewModel.createFactory(shoppingApplication.inventoryRepository)
        viewModel = ViewModelProvider(this, factory)[InventoryViewModel::class.java]

        setSupportActionBar(binding.toolbar as Toolbar)
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun initRecyclerview() {
        val gridLayoutManager = GridLayoutManager(this@InventoryActivity, 2)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (binding.rvProductList.adapter?.getItemViewType(position)) {
                        InventoryItemType.PRODUCT.id -> SPAN_SIZE_PRODUCT
                        InventoryItemType.SHOW_MORE.id -> SPAN_SIZE_SHOW_MORE
                        else -> throw IllegalStateException()
                    }
                }
            }

        binding.rvProductList.apply {
            adapter = InventoryAdapter(this@InventoryActivity)
            layoutManager = gridLayoutManager
        }

        with(viewModel) {
            requestPage()
            items.observe(this@InventoryActivity) { items ->
                (binding.rvProductList.adapter as InventoryAdapter).updateProducts(items)
            }
        }
    }

    override fun onProductSelected(product: ProductUiModel) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts() {
        viewModel.requestPage()
    }

    companion object {
        private const val SPAN_SIZE_PRODUCT = 1
        private const val SPAN_SIZE_SHOW_MORE = 2
    }
}
