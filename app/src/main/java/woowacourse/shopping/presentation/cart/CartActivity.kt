package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.Product

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartProductAdapter: CartProductAdapter
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_cart)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibBack.setOnClickListener {
            finish()
        }
        initAdapter()
        viewModel.products.observe(this) {
            cartProductAdapter.setData(it)
        }
        viewModel.fetchData()

        binding.btnCartPrevious.setOnClickListener {
            val currentPage = viewModel.currentPage.value ?: 0
            if (currentPage == 0) {
                Toast.makeText(this, "첫 페이지입니다.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.changePage(next = false)
            }
        }

        binding.btnCartNext.setOnClickListener {
            val totalSize = viewModel.totalSize.value ?: 0
            val pageSize = 5
            val totalPages = (totalSize + pageSize - 1) / pageSize

            val currentPage = viewModel.currentPage.value ?: 0
            if (currentPage >= totalPages - 1) {
                Toast.makeText(this, "마지막 페이지입니다.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.changePage(next = true)
            }
        }

        viewModel.currentPage.observe(this) { currentPage ->
            if (currentPage == 0) {
                binding.btnCartPrevious.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray1))
            }
            val totalSize = viewModel.totalSize.value ?: 0
            val pageSize = 5
            val totalPages = (totalSize + pageSize - 1) / pageSize
            if (currentPage == totalPages) {
                binding.btnCartNext.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray1))
            }
        }
    }

    private fun initAdapter() {
        cartProductAdapter = CartProductAdapter { product -> delete(product) }
        binding.rvCartProduct.adapter = cartProductAdapter
    }

    private fun delete(product: Product) {
        viewModel.deleteProduct(product)
        Toast.makeText(this, getString(R.string.cart_delete_complete), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
