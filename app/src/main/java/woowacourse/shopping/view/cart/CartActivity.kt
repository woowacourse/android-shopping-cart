package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.App
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.viewmodel.cart.CartViewModel
import woowacourse.shopping.viewmodel.cart.CartViewModelFactory

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            (application as App).shoppingCartRepository,
            (application as App).productRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        setupAdapter()
        observeViewModel()
//        setupClickListeners()
//        setupRecyclerView()
    }

    private fun setupAdapter() {
        adapter =
            CartAdapter(
                onProductRemove = { productId ->
                    cartViewModel.removeFromCart(productId)
                },
                onQuantityChange = { productId, quantity ->
                    cartViewModel.updateQuantity(productId, quantity)
                },
            )
    }

    private fun observeViewModel() {
        cartViewModel.currentPageItems.observe(this) { items ->
            adapter.updateCartItemsView(items)
        }

        cartViewModel.pageCount.observe(this) { pageCount ->
            binding.tvPageCount.text = pageCount.toString()
//            updatePageButtons()
        }

//        cartViewModel.cartState.observe(this) { cartState ->
//            if (cartViewModel.isOnlyOnePage()) {
//                binding.layoutPageButtons.visibility = View.GONE
//            } else {
//                binding.layoutPageButtons.visibility = View.VISIBLE
//            }
//        }
    }

//    private fun setupClickListeners() {
//        binding.backImageBtn.setOnClickListener {
//            setResult(RESULT_OK)
//            finish()
//        }
//
//        binding.btnPreviousPage.setOnClickListener {
//            cartViewModel.loadPreviousPage()
//        }
//
//        binding.btnNextPage.setOnClickListener {
//            cartViewModel.loadNextPage()
//        }
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvProductsInCart.adapter = adapter
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//
//    private fun updatePageButtons() {
//        binding.btnPreviousPage.setBackgroundColor(
//            if (cartViewModel.isFirstPage()) getColor(R.color.gray_6) else getColor(R.color.aqua_green),
//        )
//
//        binding.btnNextPage.setBackgroundColor(
//            if (cartViewModel.isLastPage()) getColor(R.color.gray_6) else getColor(R.color.aqua_green),
//        )
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_OK)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
