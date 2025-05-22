package woowacourse.shopping.view.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter
    private val productsViewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)
        binding.viewModel = productsViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        observeProductsView()
        observeCartButton()
        setupScrollListenerForMoreButton()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupScrollListenerForMoreButton() {
        binding.rvProducts.addOnScrollListener(
            ProductsScrollListener(binding.rvProducts.layoutManager as GridLayoutManager) { canLoadMore ->
                productsViewModel.updateButtonVisibility(canLoadMore)
            },
        )
    }

    private fun initRecyclerView() {
        adapter =
            ProductsAdapter(
                productClickListener = { cartItem -> navigateToProductDetail(cartItem) },
                openQuantitySelectListener = { true },
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

        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
    }

    private fun observeProductsView() {
        productsViewModel.productsInShop.observe(this) { list ->
            adapter.updateProductsView(list)
            list.forEach {
                adapter.updateQuantityView(it.product.id)
            }
        }
//        productsViewModel.quantities.observe(this) { quantities ->
//            quantities.forEach { (id, _) ->
//                adapter.updateQuantityView(id)
//            }
//        }
    }

    private fun observeCartButton() {
        productsViewModel.navigateToCart.observe(this) {
            it.getContentIfNotHandled()?.let {
                startActivity(Intent(this, CartActivity::class.java))
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
