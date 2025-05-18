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
import woowacourse.shopping.view.model.InventoryItem.ProductUiModel
import woowacourse.shopping.view.model.InventoryItemType

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
        binding.rvProductList.apply {
            adapter = ProductsAdapter(this@InventoryActivity)
            val gridLayoutManager = GridLayoutManager(this@InventoryActivity, 2)
            gridLayoutManager.spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when ((viewModel.items.value ?: emptyList())[position].type) {
                            InventoryItemType.PRODUCT -> 1
                            InventoryItemType.SHOW_MORE -> 2
                        }
                    }
                }
            layoutManager = gridLayoutManager
            viewModel.apply {
                requestPage()
                items.observe(this@InventoryActivity) { items ->
                    (binding.rvProductList.adapter as ProductsAdapter).updateProducts(items)
                }
            }
        }
    }

    override fun onProductSelected(product: ProductUiModel) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onLoadMoreProducts() {
        viewModel.requestPage()
    }
}
