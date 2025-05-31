package woowacourse.shopping.view.products

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.view.recentproduct.RecentProductsAdapter

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var recentProductsAdapter: RecentProductsAdapter
    private val productsViewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        binding.viewModel = productsViewModel
        binding.lifecycleOwner = this
        initRecentProductsRecyclerView()
        initProductsRecyclerView()
        observeRecentProductsView()
        observeProductsView()
        observeCartButton()
        productsViewModel.observeToastMessage(this)
        setupScrollListenerForMoreButton()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        productsViewModel.reloadPage()
    }

    override fun onPause() {
        super.onPause()
        productsViewModel.updateQuantity()
    }

    private fun setupScrollListenerForMoreButton() {
        binding.rvProducts.addOnScrollListener(
            ProductsScrollListener(binding.rvProducts.layoutManager as GridLayoutManager) { canLoadMore ->
                productsViewModel.updateButtonVisibility(canLoadMore)
            },
        )
    }

    private fun initProductsRecyclerView() {
        productsAdapter =
            ProductsAdapter(
                productClickListener = { cartItem ->
                    navigateToProductDetail(cartItem)
                    productsViewModel.addRecentProduct(cartItem)
                },
                openQuantitySelectListener = { cartItem ->
                    productsViewModel.openQuantitySelectAndAddToCart(cartItem)
                },
                quantitySelectButtonListener =
                    object : QuantitySelectButtonListener {
                        override fun increase(productId: Long) {
                            productsViewModel.increaseQuantity(productId)
                        }

                        override fun decrease(productId: Long) {
                            productsViewModel.decreaseQuantity(productId)
                        }
                    },
            )

        binding.rvProducts.adapter = productsAdapter
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
    }

    private fun initRecentProductsRecyclerView() {
        recentProductsAdapter = RecentProductsAdapter()
        binding.rvRecentProduct.adapter = recentProductsAdapter
        binding.rvRecentProduct.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun observeProductsView() {
        productsViewModel.productsInShop.observe(this) { list ->
            productsAdapter.notifyProductsChanged(list)
            list.forEach {
                productsAdapter.notifyQuantityChanged(it.product.id)
            }
        }
    }

    private fun observeRecentProductsView() {
        productsViewModel.recentProducts.observe(this) { recentProducts ->
            recentProductsAdapter.updateRecentProductsView(recentProducts)
        }
    }

    private fun observeCartButton() {
        productsViewModel.navigateToCart.observe(this) {
            it.getContentIfNotHandled()?.let {
                val intent = CartActivity.getIntent(this)
                startActivity(intent)
            }
        }
    }

    private fun navigateToProductDetail(cartItem: CartItem) {
        val intent = ProductDetailActivity.getIntent(this, cartItem)
        startActivity(intent)
    }

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
    }
}
