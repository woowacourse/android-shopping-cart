package woowacourse.shopping.view.inventory

import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.databinding.ToolbarCartCounterBinding
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem
import woowacourse.shopping.view.inventory.item.InventoryItemType
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class InventoryActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    InventoryEventHandler {
    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as ShoppingApplication
        val factory =
            InventoryViewModel.createFactory(
                application.inventoryRepository,
                application.shoppingCartRepository,
                application.recentProductRepository,
            )
        viewModel = ViewModelProvider(this, factory)[InventoryViewModel::class.java]
        initRecyclerview()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)

        val binding =
            DataBindingUtil.inflate<ToolbarCartCounterBinding>(
                layoutInflater,
                R.layout.toolbar_cart_counter,
                null,
                false,
            )

        menu?.findItem(R.id.menu_item_shopping_cart)?.actionView = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.root.setOnClickListener {
            startActivity(ShoppingCartActivity.newIntent(this))
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadPage()
    }

    private fun initRecyclerview() {
        val gridLayoutManager = GridLayoutManager(this@InventoryActivity, MAX_SPAN_SIZE)
        binding.rvProductList.apply {
            adapter = InventoryAdapter(this@InventoryActivity)
            layoutManager = gridLayoutManager
        }
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (binding.rvProductList.adapter?.getItemViewType(position)) {
                        InventoryItemType.PRODUCT.id -> PRODUCT_SPAN_SIZE
                        InventoryItemType.SHOW_MORE.id, InventoryItemType.RECENT_PRODUCTS.id -> MAX_SPAN_SIZE
                        else -> throw IllegalStateException()
                    }
                }
            }
        with(viewModel) {
            val adapter = binding.rvProductList.adapter as InventoryAdapter
            items.observe(this@InventoryActivity) { items ->
                adapter.submitList(items)
                loadCartCount()
            }
            requestPage()
        }
    }

    override fun onSelectProduct(productId: Int) {
        startActivity(ProductDetailActivity.newIntent(this, productId))
    }

    override fun onIncreaseQuantity(
        position: Int,
        product: ProductItem,
    ) {
        viewModel.increaseQuantity(position, product)
    }

    override fun onDecreaseQuantity(
        position: Int,
        product: ProductItem,
    ) {
        viewModel.decreaseQuantity(position, product)
    }

    override fun onShowMore() {
        viewModel.requestPage()
    }

    companion object {
        private const val MAX_SPAN_SIZE = 2
        private const val PRODUCT_SPAN_SIZE = 1
    }
}
