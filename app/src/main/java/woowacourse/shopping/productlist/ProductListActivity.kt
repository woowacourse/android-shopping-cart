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
import woowacourse.shopping.util.ViewModelFactory
import woowacourse.shopping.util.showToastMessage

class ProductListActivity : AppCompatActivity(), ProductListClickAction {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
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
    }

    private fun activityResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val changedProductId =
                    result.data?.getLongExtra(CHANGED_PRODUCT_ID, INVALID_PRODUCT_ID)
                        ?: INVALID_PRODUCT_ID
                viewModel.reloadProductOfInfo(changedProductId)
            }
        }

    private fun attachAdapter() {
        adapter = ProductListAdapter(this)
        binding.rcvProductList.itemAnimator = null
        binding.rcvProductList.adapter = adapter
    }

    private fun showProducts() {
        viewModel.loadProducts()
        viewModel.loadState.observe(this) { loadState ->
            when (loadState) {
                is LoadProductState.ChangeItemCount -> adapter.changeProductInfo(loadState.result)
                is LoadProductState.ShowProducts -> adapter.submitItems(loadState.currentProducts.products)
                is LoadProductState.DeleteProductFromCart -> adapter.changeProductInfo(loadState.result)
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

    companion object {
        private const val CHANGED_PRODUCT_ID = "productId"
        private const val INVALID_PRODUCT_ID = -1L

        fun newInstance(
            context: Context,
            changedProductId: Long,
        ) = Intent(context, ProductListActivity::class.java).apply {
            putExtra(CHANGED_PRODUCT_ID, changedProductId)
        }
    }
}
