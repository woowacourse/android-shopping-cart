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
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.InventoryItemType

class InventoryActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    InventoryEventHandler {
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shoppingApplication = application as ShoppingApplication
        val factory =
            InventoryViewModel.createFactory(
                shoppingApplication.inventoryRepository,
                shoppingApplication.shoppingCartRepository2,
            )
        viewModel = ViewModelProvider(this, factory)[InventoryViewModel::class.java]

        setSupportActionBar(binding.toolbar as Toolbar)
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun initRecyclerview() {
        val gridLayoutManager = GridLayoutManager(this@InventoryActivity, MAX_SPAN_SIZE)
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (binding.rvProductList.adapter?.getItemViewType(position)) {
                        InventoryItemType.PRODUCT.id -> PRODUCT_SPAN_SIZE
                        InventoryItemType.SHOW_MORE.id -> MAX_SPAN_SIZE
                        else -> throw IllegalStateException()
                    }
                }
            }

        viewModel.requestPage()

        binding.rvProductList.apply {
            adapter = InventoryAdapter(this@InventoryActivity)
            layoutManager = gridLayoutManager
        }

        with(viewModel) {
            items.observe(this@InventoryActivity) { items ->
                (binding.rvProductList.adapter as InventoryAdapter).updateProducts(items)
            }
        }
    }

    override fun onProductSelected(product: InventoryProduct) {
        startActivity(ProductDetailActivity.newIntent(this, product))
    }

    override fun onIncreaseQuantity(
        position: Int,
        product: InventoryProduct,
    ) {
        viewModel.increaseQuantity(position, product)
    }

    override fun onDecreaseQuantity(
        position: Int,
        product: InventoryProduct,
    ) {
        viewModel.decreaseQuantity(position, product)
    }

    override fun onLoadMoreProducts() {
        viewModel.requestPage()
    }

    companion object {
        private const val MAX_SPAN_SIZE = 2
        private const val PRODUCT_SPAN_SIZE = 1
    }
}
