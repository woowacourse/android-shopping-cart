package woowacourse.shopping.presentation.ui.shopping

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.presentation.base.BindingActivity
import woowacourse.shopping.presentation.ui.EventObserver
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.UpdateUiModel
import woowacourse.shopping.presentation.ui.ViewModelFactory
import woowacourse.shopping.presentation.ui.cart.CartActivity
import woowacourse.shopping.presentation.ui.detail.ProductDetailActivity
import woowacourse.shopping.presentation.ui.shopping.adapter.RecentAdapter
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingAdapter
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingViewType
import woowacourse.shopping.utils.getParcelableExtraCompat

class ShoppingActivity : BindingActivity<ActivityShoppingBinding>(), ShoppingHandler {
    override val layoutResourceId: Int
        get() = R.layout.activity_shopping

    private val viewModel: ShoppingViewModel by viewModels { ViewModelFactory() }

    private val shoppingAdapter: ShoppingAdapter by lazy { ShoppingAdapter(this, viewModel) }
    private val recentAdapter: RecentAdapter by lazy { RecentAdapter(this) }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun initStartView() {
        supportActionBar?.hide()
        initAdapter()
        initData()
        initObserver()
        initLauncher()

        binding.ivCart.setOnClickListener {
            resultLauncher.launch(
                CartActivity.createIntent(this),
            )
        }
    }

    private fun initLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getParcelableExtraCompat<UpdateUiModel>(
                        EXTRA_UPDATED_PRODUCT
                    )
                        ?.let {
                            viewModel.updateCartProducts(it)
                        }
                }
                viewModel.findAllRecent()
            }
    }

    private fun initData() {
        viewModel.loadProductByOffset()
        viewModel.findAllRecent()
        viewModel.getItemCount()
    }

    private fun initObserver() {
        viewModel.products.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> {
                    shoppingAdapter.updateList(it.data)
                }
            }
        }
        viewModel.cartCount.observe(this) {
            binding.tvCartCount.text = it.toString()
        }
        viewModel.recentProducts.observe(this) {
            when (it) {
                is UiState.None -> {}
                is UiState.Success -> {
                    recentAdapter.submitList(it.data) {
                        binding.rvRecents.scrollToPosition(0)
                    }
                }
            }
        }
        viewModel.errorHandler.observe(this, EventObserver {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, GRIDLAYOUT_COL)
        layoutManager.spanSizeLookup = spanManager
        binding.rvShopping.layoutManager = layoutManager
        binding.rvShopping.adapter = shoppingAdapter

        binding.rvRecents.adapter = recentAdapter
    }

    private val spanManager =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (shoppingAdapter.getItemViewType(position) == ShoppingViewType.Product.value) {
                    ShoppingViewType.Product.span
                } else {
                    ShoppingViewType.LoadMore.span
                }
            }
        }

    override fun onClick(productId: Long) {
        resultLauncher.launch(
            ProductDetailActivity.createIntent(this, productId),
        )
    }

    override fun loadMore() {
        viewModel.loadProductByOffset()
    }

    companion object {
        const val GRIDLAYOUT_COL = 2
        const val EXTRA_UPDATED_PRODUCT = "updatedProduct"
    }
}
