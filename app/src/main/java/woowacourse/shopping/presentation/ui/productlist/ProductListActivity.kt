package woowacourse.shopping.presentation.ui.productlist

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.presentation.base.BaseActivity
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.observeEvent
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter.Companion.PRODUCT_VIEW_TYPE
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapterManager
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.presentation.ui.shoppingcart.UpdatedProducts

class ProductListActivity : BaseActivity<ActivityProductListBinding>() {
    override val layoutResourceId: Int get() = R.layout.activity_product_list

    private val viewModel: ProductListViewModel by viewModels {
        ProductListViewModel.factory(
            (application as ShoppingApplication).productRepository,
            (application as ShoppingApplication).shoppingCartRepository,
        )
    }

    private val adapter: ProductListAdapter by lazy { ProductListAdapter(viewModel, viewModel) }

    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedProducts =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getSerializableExtra(
                            PUT_EXTRA_UPDATED_PRODUCTS,
                            UpdatedProducts::class.java,
                        )
                    } else {
                        result.data?.getSerializableExtra(PUT_EXTRA_UPDATED_PRODUCTS) as UpdatedProducts
                    }

                updatedProducts?.let { products ->
                    viewModel.updateProducts(products)
                }
            }
        }

    override fun initStartView() {
        initDataBinding()
        initAdapter()
        initObserve()
    }

    private fun initDataBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProductListActivity
        }
    }

    private fun initAdapter() {
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager =
            ProductListAdapterManager(this, adapter, 2, PRODUCT_VIEW_TYPE)
    }

    private fun initObserve() {
        viewModel.navigateAction.observeEvent(this) { navigateAction ->
            when (navigateAction) {
                is ProductListNavigateAction.NavigateToProductDetail -> {
                    val intent =
                        ProductDetailActivity.getIntent(this, navigateAction.productId)
                    filterActivityLauncher.launch(intent)
                }

                is ProductListNavigateAction.NavigateToShoppingCart -> {
                    val intent = ShoppingCartActivity.getIntent(this)
                    filterActivityLauncher.launch(intent)
                }
            }
        }

        viewModel.uiState.observe(this) { state ->
            adapter.updateProductList(state.pagingProduct)
            adapter.updateProduct(state.recentlyProductPosition)
        }

        viewModel.message.observeEvent(this) { message ->
            when (message) {
                is MessageProvider.DefaultErrorMessage -> showToastMessage(message.getMessage(this))
            }
        }
    }

    companion object {
        private const val PUT_EXTRA_UPDATED_PRODUCTS = "updatedProducts"

        fun getIntent(
            context: Context,
            updatedProducts: UpdatedProducts,
        ): Intent {
            return Intent(context, ProductListActivity::class.java).apply {
                putExtra(PUT_EXTRA_UPDATED_PRODUCTS, updatedProducts)
            }
        }
    }
}
