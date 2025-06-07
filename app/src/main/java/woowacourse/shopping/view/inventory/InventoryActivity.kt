package woowacourse.shopping.view.inventory

import android.os.Bundle
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.databinding.ToolbarCartCounterBinding
import woowacourse.shopping.view.ActivityResult
import woowacourse.shopping.view.base.BaseActivity
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.inventory.adapter.InventoryAdapter
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ViewType
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

class InventoryActivity :
    BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    InventoryEventHandler {
    private lateinit var viewModel: InventoryViewModel

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            when (result.resultCode) {
                ActivityResult.CART_ITEM_ADDED.hashCode() -> {
                    val addedProductId =
                        result.data?.getIntExtra(ActivityResult.CART_ITEM_ADDED.key, 0) ?: 0
                    viewModel.loadUpdatedProductInfo(listOf(addedProductId))
                }

                ActivityResult.CART_ITEM_MODIFIED.hashCode() -> {
                    val modifiedProductIds =
                        result.data?.getIntegerArrayListExtra(ActivityResult.CART_ITEM_MODIFIED.key)
                    viewModel.loadUpdatedProductInfo(modifiedProductIds.orEmpty())
                }
            }
        }

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
            activityResultLauncher.launch(ShoppingCartActivity.newIntent(this))
        }
        return true
    }

    private fun initRecyclerview() {
        val gridLayoutManager = GridLayoutManager(this@InventoryActivity, MAX_SPAN_SIZE)
        binding.rvProductList.apply {
            adapter = InventoryAdapter(this@InventoryActivity)
            itemAnimator = null
            layoutManager = gridLayoutManager
        }
        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val adapter = binding.rvProductList.adapter ?: return MAX_SPAN_SIZE
                    val viewType = adapter.getItemViewType(position)
                    return when (ViewType.entries[viewType]) {
                        ViewType.PRODUCT -> PRODUCT_SPAN_SIZE
                        ViewType.SHOW_MORE, ViewType.RECENT_PRODUCTS -> MAX_SPAN_SIZE
                    }
                }
            }
        with(viewModel) {
            val adapter = binding.rvProductList.adapter as InventoryAdapter
            items.observe(this@InventoryActivity) { items ->
                adapter.submitList(items)
                loadCartCount()
            }
            inventoryUpdateEvent.observe(this@InventoryActivity) { item ->
                adapter.updateProduct(item)
            }
            requestPage()
        }
    }

    override fun onSelectProduct(productId: Int) {
        activityResultLauncher.launch(ProductDetailActivity.newIntent(this, productId))
    }

    override fun onIncreaseQuantity(product: ProductUiModel) {
        viewModel.increaseQuantity(product)
    }

    override fun onDecreaseQuantity(product: ProductUiModel) {
        viewModel.decreaseQuantity(product)
    }

    override fun onShowMore() {
        viewModel.requestPage()
    }

    companion object {
        private const val MAX_SPAN_SIZE = 2
        private const val PRODUCT_SPAN_SIZE = 1
    }
}
