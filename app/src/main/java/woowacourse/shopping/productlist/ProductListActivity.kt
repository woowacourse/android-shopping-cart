package woowacourse.shopping.productlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productlist.uimodel.LoadProductState
import woowacourse.shopping.productlist.uimodel.ProductListClickAction
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.ViewModelFactory
import woowacourse.shopping.util.showToastMessage

class ProductListActivity : AppCompatActivity(), ProductListClickAction {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productAdapter: ProductListAdapter
    private lateinit var recentAdapter: RecentProductAdapter
    private val viewModel: ProductListViewModel by viewModels { ViewModelFactory() }

    private val activityResultLauncher = activityResultLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityResultLauncher()
        binding.lifecycleOwner = this
        binding.vm = viewModel

        attachAdapter()
        showProducts()
        supportActionBar?.hide()
        loadRecentProduct()

        navigateToShoppingCart()
    }

    private fun activityResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val changedProductId: LongArray =
                    result.data?.getLongArrayExtra(CHANGED_PRODUCT_ID) ?: longArrayOf()
                viewModel.reloadProductOfInfo(changedProductId.toList())

                val isRecentChanged =
                    result.data?.getBooleanExtra(IS_RECENT_CHANGED, false) ?: false
                if (isRecentChanged) viewModel.loadRecentProduct()
            }
        }

    private fun attachAdapter() {
        productAdapter = ProductListAdapter(this)
        binding.rcvProductListMain.itemAnimator = null
        binding.rcvProductListMain.adapter = productAdapter

        recentAdapter = RecentProductAdapter(this)
        binding.rcvProductListRecent.adapter = recentAdapter
    }

    private fun showProducts() {
        viewModel.initProducts()
        viewModel.loadState.observe(this) { loadState ->
            when (loadState) {
                is LoadProductState.ChangeItemCount -> productAdapter.changeProductsInfo(loadState.result)
                is LoadProductState.ShowProducts -> productAdapter.submitItems(loadState.currentProducts.products)
                is LoadProductState.PlusFail -> showToastMessage(R.string.max_cart_item_message)
            }
        }
    }

    override fun onProductClicked(id: Long) {
        activityResultLauncher.launch(ProductDetailActivity.newInstance(this, id))
    }

    override fun onIntoCartClicked(id: Long) {
        viewModel.addProductToCart(id)
    }

    override fun onPlusCountClicked(id: Long) {
        viewModel.plusProductCount(id)
    }

    override fun onMinusCountClicked(id: Long) {
        viewModel.minusProductCount(id)
    }

    private fun navigateToShoppingCart() {
        binding.ibProductListCart.setOnClickListener {
            activityResultLauncher.launch(ShoppingCartActivity.newInstance(this))
        }
    }

    private fun loadRecentProduct() {
        viewModel.loadRecentProduct()
        viewModel.recentProducts.observe(this) {
            recentAdapter.replaceAll(it)
        }
    }

    companion object {
        private const val CHANGED_PRODUCT_ID = "productId"
        private const val IS_RECENT_CHANGED = "isRecentChange"

        fun changedProductIntent(
            context: Context,
            changedProductIds: LongArray,
        ) = Intent(context, ProductListActivity::class.java).apply {
            putExtra(CHANGED_PRODUCT_ID, changedProductIds)
        }

        fun recentInstance(
            context: Context,
            isRecentChanged: Boolean,
        ) = Intent(context, ProductListActivity::class.java).apply {
            putExtra(IS_RECENT_CHANGED, isRecentChanged)
        }

        fun recentAndChangeProductIntent(
            context: Context,
            changedProductIds: LongArray,
            isRecentChanged: Boolean,
        ) = Intent(context, ProductListActivity::class.java).apply {
            putExtra(CHANGED_PRODUCT_ID, changedProductIds)
            putExtra(IS_RECENT_CHANGED, isRecentChanged)
        }
    }
}
