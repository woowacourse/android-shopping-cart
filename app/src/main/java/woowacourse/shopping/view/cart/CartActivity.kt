package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.CartItem

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val cartProductAdapter: CartProductAdapter by lazy {
        CartProductAdapter(
            onDeleteClick = ::deleteProduct,
            onIncrease = { cartViewModel.increaseProductCount(it) },
            onDecrease = { cartViewModel.decreaseProductCount(it) },
        )
    }
    private val cartViewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initDataBinding()
        bindData()
        initAdapter()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initDataBinding() {
        binding.apply {
            lifecycleOwner = this@CartActivity
            cartVM = cartViewModel
            toPrevious = ::navigateToPrevious
            toNext = ::navigateToNext
        }
    }

    private fun initAdapter() {
        binding.rvCartProduct.adapter = cartProductAdapter
    }

    private fun bindData() {
        cartViewModel.currentPage.observe(this) {
            cartViewModel.hasNext()
            cartViewModel.hasPrevious()
        }
        cartViewModel.products.observe(this) { product ->
            cartProductAdapter.setData(product)
        }
        cartViewModel.exitEvent.observe(this) {
            finish()
        }
    }

    private fun deleteProduct(
        cartItem: CartItem,
        position: Int,
    ) {
        cartProductAdapter.removeProduct(position)
        cartViewModel.deleteProduct(cartItem)
        Toast
            .makeText(this, R.string.cart_product_delete, Toast.LENGTH_SHORT)
            .show()
    }

    private fun navigateToPrevious() {
        cartViewModel.minusPage()
        cartViewModel.fetchData()
    }

    private fun navigateToNext() {
        cartViewModel.plusPage()
        cartViewModel.fetchData()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
