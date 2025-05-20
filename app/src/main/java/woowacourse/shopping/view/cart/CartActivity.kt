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
import woowacourse.shopping.uimodel.CartItem

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val cartProductAdapter: CartProductAdapter by lazy { CartProductAdapter(::deleteProduct) }
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initView()
        bindData()
        initAdapter()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.toBack = ::finish
        binding.toPrevious = ::navigateToPrevious
        binding.toNext = ::navigateToNext
    }

    private fun initView() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initAdapter() {
        binding.rvCartProduct.adapter = cartProductAdapter
    }

    private fun bindData() {
        viewModel.fetchInfo()
        viewModel.fetchData()
        viewModel.currentPage.observe(this) {
            viewModel.hasNext()
            viewModel.hasPrevious()
        }
        viewModel.products.observe(this) { cartProductAdapter.setData(it) }
    }

    private fun deleteProduct(
        cartItem: CartItem,
        position: Int,
    ) {
        cartProductAdapter.removeProduct(position)
        viewModel.deleteProduct(cartItem)
        Toast
            .makeText(this, R.string.cart_product_delete, Toast.LENGTH_SHORT)
            .show()
    }

    private fun navigateToPrevious() {
        viewModel.minusPage()
        viewModel.fetchData()
    }

    private fun navigateToNext() {
        viewModel.plusPage()
        viewModel.fetchData()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
