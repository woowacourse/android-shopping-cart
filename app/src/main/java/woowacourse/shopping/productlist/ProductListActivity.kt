package woowacourse.shopping.productlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productlist.ProductListActivity.ResultActivity.Companion.ACTIVITY_TYPE
import woowacourse.shopping.productlist.ProductListActivity.ResultActivity.Companion.INVALID_ACTIVITY_ID
import woowacourse.shopping.productlist.uimodel.ProductChangeEvent
import woowacourse.shopping.productlist.uimodel.ProductListClickAction
import woowacourse.shopping.productlist.uimodel.ProductUiState
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

        viewModel.initProducts()

        attachAdapter()
        observeProductsState()
        observeProductEvent()
        supportActionBar?.hide()
        loadRecentProduct()

        navigateToShoppingCart()
    }

    private fun activityResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    val activityType =
                        ResultActivity.values()[it.getIntExtra(ACTIVITY_TYPE, INVALID_ACTIVITY_ID)]
                    when (activityType) {
                        ResultActivity.DETAIL -> handleDetailActivityResult(it)
                        ResultActivity.CART -> handleShoppingCartResult(it)
                    }
                }
            }
        }

    private fun handleDetailActivityResult(intent: Intent) {
        val changedProductId: LongArray =
            intent.getLongArrayExtra(ProductDetailActivity.CHANGED_PRODUCT_ID) ?: longArrayOf()
        viewModel.reloadProductOfInfo(changedProductId.toList())

        val isRecentChanged = intent.getBooleanExtra(ProductDetailActivity.IS_RECENT_CHANGED, false)
        if (isRecentChanged) viewModel.loadRecentProduct()
    }

    private fun handleShoppingCartResult(intent: Intent) {
        val changedProductId: LongArray =
            intent.getLongArrayExtra(ShoppingCartActivity.CHANGED_PRODUCT_ID) ?: longArrayOf()
        viewModel.reloadProductOfInfo(changedProductId.toList())
    }

    private fun attachAdapter() {
        productAdapter = ProductListAdapter(this)
        binding.rcvProductListMain.itemAnimator = null
        binding.rcvProductListMain.adapter = productAdapter

        recentAdapter = RecentProductAdapter(this)
        binding.rcvProductListRecent.adapter = recentAdapter
    }

    private fun observeProductsState() {
        viewModel.productUiState.observe(this) { state ->
            when (state) {
                is ProductUiState.Init -> productAdapter.replaceItems(state.currentProducts)
                is ProductUiState.Change -> {}
                is ProductUiState.Error -> showToastMessage(R.string.network_error)
                is ProductUiState.Loading -> showToastMessage(R.string.loading_message)
            }
        }
    }

    private fun observeProductEvent() {
        viewModel.productChangeEvent.observe(this) { event ->
            when (event) {
                is ProductChangeEvent.AddProducts -> productAdapter.addItems(event.result)
                is ProductChangeEvent.ChangeItemCount -> productAdapter.changeProductsInfo(event.result)
                ProductChangeEvent.PlusFail -> showToastMessage(R.string.max_cart_item_message)
            }
        }
    }

    override fun onProductClicked(id: Long) {
        activityResultLauncher.launch(ProductDetailActivity.newIntent(this, id))
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

    enum class ResultActivity {
        DETAIL,
        CART, ;

        companion object {
            const val ACTIVITY_TYPE = "activityType"
            const val INVALID_ACTIVITY_ID = -1
        }
    }
}
