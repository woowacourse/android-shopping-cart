package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val cartAdapter: CartAdapter by lazy { initAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        applyWindowInsets()
        setOnBackPressedCallback()

        initViews()
        initObserve()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            },
        )
    }

    private fun initViews() {
        initAppbar()
        setupBindings()
    }

    private fun initAppbar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun setupBindings() {
        binding.cartRecyclerView.adapter = cartAdapter
        binding.viewModel = viewModel
    }

    private fun initAdapter(): CartAdapter {
        return CartAdapter(
            items = mutableListOf(),
            cartClickListener = { product ->
                viewModel.deleteProduct(product)
            },
        )
    }

    private fun initObserve() {
        viewModel.products.observe(this) {
            cartAdapter.updateItems(it)
            setPaginationVisibility()
            setPaginationButtonTint()
        }

        viewModel.pageNumber.observe(this) { pageNumber ->
            binding.tvPageNumber.text = pageNumber.toString()
            viewModel.update()
        }
    }

    private fun setPaginationVisibility() {
        if (!viewModel.isVisiblePagination()) {
            binding.btnNext.visibility = GONE
            binding.btnPrevious.visibility = GONE
            binding.tvPageNumber.visibility = GONE
        }
    }

    private fun setPaginationButtonTint() {
        binding.btnPrevious.backgroundTintList =
            ColorStateList.valueOf(getColor(if (viewModel.isFirstPage) R.color.button_inactive else R.color.button_active))
        binding.btnNext.backgroundTintList =
            ColorStateList.valueOf(getColor(if (viewModel.isLastPage) R.color.button_inactive else R.color.button_active))
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
