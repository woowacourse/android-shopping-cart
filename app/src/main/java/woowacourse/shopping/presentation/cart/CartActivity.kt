package woowacourse.shopping.presentation.cart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageButton
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
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val cartProductAdapter by lazy { CartProductAdapter(::deleteProduct) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initInsets()
        initViews()
        observeViewModel()
    }

    private fun initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.clCart) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        binding.ibBack.setOnClickListener { finish() }
        binding.rvCartProduct.adapter = cartProductAdapter
        binding.btnCartPrevious.setOnClickListener { moveToPreviousPage() }
        binding.btnCartNext.setOnClickListener { moveToNextPage() }
    }

    private fun observeViewModel() {
        viewModel.products.observe(this) {
            cartProductAdapter.setData(it)
            val currentPage = viewModel.currentPage.value ?: 0
            updatePaginationUI(currentPage)
        }
        viewModel.currentPage.observe(this) { currentPage ->
            updatePaginationUI(currentPage)
        }
        viewModel.totalSize.observe(this) { _ ->
            val currentPage = viewModel.currentPage.value ?: 0
            updatePaginationUI(currentPage)
        }
        viewModel.fetchData()
    }

    private fun moveToPreviousPage() {
        if ((viewModel.currentPage.value ?: 0) == 0) {
            showToast(R.string.cart_first_page_toast)
        } else {
            viewModel.changePage(next = false)
        }
    }

    private fun moveToNextPage() {
        val currentPage = viewModel.currentPage.value ?: 0
        val totalPages = viewModel.calculateTotalPages()

        if (currentPage >= totalPages - 1) {
            showToast(R.string.cart_last_page_toast)
        } else {
            viewModel.changePage(next = true)
        }
    }

    private fun updatePaginationUI(currentPage: Int) {
        val totalPages = viewModel.calculateTotalPages()

        binding.btnCartPrevious.setCyanOrGrayTint(isEnabled = currentPage > 0)
        binding.btnCartNext.setCyanOrGrayTint(isEnabled = currentPage < totalPages - 1)
    }

    private fun deleteProduct(product: Product) {
        viewModel.deleteProduct(product)
        showToast(R.string.cart_delete_complete)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun ImageButton.setCyanOrGrayTint(isEnabled: Boolean) {
        val colorRes = if (isEnabled) R.color.cyan else R.color.gray1
        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
