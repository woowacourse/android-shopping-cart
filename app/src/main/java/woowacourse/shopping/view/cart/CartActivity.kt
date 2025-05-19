package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        binding.toBack = ::finish
        binding.currentPage = viewModel.currentPage.value ?: 1
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
        viewModel.totalProductsCount.observe(this) { totalProductsCount ->
            isPaginationButtonVisible(totalProductsCount)
        }
        viewModel.currentPage.observe(this) { currentPage ->
            if (currentPage == 1) {
                Log.d("test", "$currentPage 작동함")
//                binding.btnCartPrevious.setBackgroundColor(R.color.gray5)
            }
//            if (currentPage < viewModel.totalPage) {
//                binding.btnCartNext.setBackgroundResource(R.color.cyan)
//            }
        }
        viewModel.products.observe(this) { cartProductAdapter.setData(it) }
    }

    private fun isPaginationButtonVisible(totalProductsCount: Int) {
        if (totalProductsCount <= A_PAGE_ITEM_COUNT) {
            binding.btnCartPrevious.visibility = View.GONE
            binding.btnCartNext.visibility = View.GONE
            binding.tvCartPage.visibility = View.GONE
        } else {
            binding.btnCartPrevious.visibility = View.VISIBLE
            binding.btnCartNext.visibility = View.VISIBLE
            binding.tvCartPage.visibility = View.VISIBLE
        }
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
        val currentPage = viewModel.currentPage.value ?: 1
        cartProductAdapter.notifyItemRangeChanged(
            (currentPage - 1) * A_PAGE_ITEM_COUNT,
            A_PAGE_ITEM_COUNT,
        )
    }

    private fun navigateToNext() {
        viewModel.plusPage()
        viewModel.fetchData()
        val currentPage = viewModel.currentPage.value ?: 1
        cartProductAdapter.notifyItemRangeChanged(
            (currentPage - 1) * A_PAGE_ITEM_COUNT,
            A_PAGE_ITEM_COUNT,
        )
    }

    companion object {
        private const val A_PAGE_ITEM_COUNT = 5

        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
