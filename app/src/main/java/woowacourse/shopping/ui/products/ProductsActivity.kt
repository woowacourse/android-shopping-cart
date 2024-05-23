package woowacourse.shopping.ui.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.ProductsAdapter

class ProductsActivity : AppCompatActivity() {
    private val binding: ActivityProductsBinding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<ProductsViewModel> {
        ProductsViewModelFactory(
            ProductRepository.getInstance(),
            CartRepository.getInstance(),
        )
    }
    private lateinit var adapter: ProductsAdapter

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val changedProductId =
                result.data?.getLongExtra(CHANGED_PRODUCT_ID_KEY, CHANGED_PRODUCT_ID_DEFAULT_VALUE)
                    ?: return@registerForActivityResult
            viewModel.loadProductUiModel(changedProductId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initializeView()
    }

    private fun initializeView() {
        initializeProductAdapter()
        initializeToolbar()
        initializePage()
    }

    private fun initializeProductAdapter() {
        adapter =
            ProductsAdapter(
                onClickProductItem = { navigateToProductDetailView(it) },
                onIncreaseProductQuantity = { viewModel.increaseQuantity(it) },
                onDecreaseProductQuantity = { viewModel.decreaseQuantity(it) },
            )
        binding.rvProducts.adapter = adapter
        viewModel.productUiModels.observe(this) {
            adapter.insertProducts(it)
        }
        viewModel.changedProductQuantity.observe(this) {
            adapter.replaceProduct(it)
        }
    }

    private fun navigateToProductDetailView(productId: Long) {
        val intent = ProductDetailActivity.newIntent(this, productId)
        activityResultLauncher.launch(intent)
    }

    private fun initializeToolbar() {
        binding.ivProductsCart.setOnClickListener { navigateToCartView() }
        binding.tvProductsCartCount.setOnClickListener { navigateToCartView() }
    }

    private fun navigateToCartView() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun initializePage() {
        val onScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastPosition =
                        (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    viewModel.changeSeeMoreVisibility(lastPosition)
                }
            }
        binding.rvProducts.addOnScrollListener(onScrollListener)
    }

    companion object {
        const val CHANGED_PRODUCT_ID_KEY = "changed_product_id_key"
        const val CHANGED_PRODUCT_ID_DEFAULT_VALUE = -1L
    }
}
