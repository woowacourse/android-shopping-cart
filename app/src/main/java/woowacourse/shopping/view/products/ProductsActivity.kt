package woowacourse.shopping.view.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductsBinding
import woowacourse.shopping.model.products.CartState
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity
import woowacourse.shopping.viewmodel.cart.CartViewModel
import woowacourse.shopping.viewmodel.products.ProductsViewModel

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapter: ProductsAdapter

    private val productsViewModel: ProductsViewModel by viewModels { ProductsViewModel.Factory }
    private val cartViewModel: CartViewModel by viewModels { CartViewModel.Factory }

    private val cartLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                cartViewModel.refreshCartState()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)

        setupAdapter()
        observeViewModels()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupAdapter() {
        adapter =
            ProductsAdapter(
                object : ProductsClickListener {
                    override fun onClick(product: Product) {
                        val intent = ProductDetailActivity.newIntent(this@ProductsActivity, product)
                        startActivity(intent)
                    }

                    override fun onAddClick(
                        product: Product,
                        quantity: Int,
                    ) {
                        if (quantity > 0) {
                            cartViewModel.addToCart(product)
                        } else {
                            val currentQuantity = cartViewModel.getQuantity(product.id)
                            cartViewModel.updateQuantity(product.id, currentQuantity - 1)
                        }
                    }
                },
                object : LoadMoreClickListener {
                    override fun onClick() {
                        productsViewModel.loadNextPage()
                    }
                },
            )
    }

    private fun observeViewModels() {
        productsViewModel.productsInShop.observe(this) { list ->
            adapter.updateProductsView(list)
        }

        cartViewModel.cartState.observe(this) { cartState ->
            adapter.updateCartState(cartState)
        }
    }

    private fun refreshQuantities() {
        val currentCartState = cartViewModel.cartState.value ?: CartState()
        adapter.updateCartState(currentCartState)
    }

    private fun setupRecyclerView() {
        binding.rvProducts.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING_DP))
        binding.rvProducts.adapter = adapter

        binding.rvProducts.layoutManager =
            GridLayoutManager(this, 2).apply {
                spanSizeLookup =
                    object : SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = binding.rvProducts.adapter?.getItemViewType(position)
                            return when (viewType) {
                                R.layout.item_product -> 1
                                else -> 2
                            }
                        }
                    }
            }
    }

    private fun setupClickListeners() {
        binding.cartImageBtn.setOnClickListener {
            val intent = CartActivity.newIntent(this@ProductsActivity)
            cartLauncher.launch(intent)
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            product: Product,
        ): Intent =
            Intent(context, ProductsActivity::class.java).apply {
                putExtra("product", product)
            }

        private const val SPAN_COUNT = 2
        private const val SPACING_DP = 12f
    }
}
