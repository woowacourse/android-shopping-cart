package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.fashionlist.FashionProductListActivity
import woowacourse.shopping.ui.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    private val viewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        applyWindowInsets()

        initAppbar()
        initAdapter()
        initObserve()
    }

    private fun initAppbar() {
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    private fun initAdapter() {
        cartAdapter =
            CartAdapter(
                items = emptyList(),
                cartClickListener =
                    object : CartClickListener {
                        override fun onClick(product: Product) {
                            viewModel.deleteProduct(product)
                        }
                    },
            )

        binding.rvCart.adapter = cartAdapter
        binding.btnPrevious.setOnClickListener { viewModel.moveToPrevious() }
        binding.btnNext.setOnClickListener { viewModel.moveToNext() }
    }

    private fun initObserve() {
        viewModel.products.observe(this) {
            cartAdapter.updateItem(it)
        }

        viewModel.pageNumber.observe(this) {
            binding.tvPageNumber.text = it.toString()
            updateButtonTint(it)
        }
    }

    private fun updateButtonTint(it: Int?) {
        val page = it ?: 1
        binding.btnPrevious.backgroundTintList =
            ColorStateList.valueOf(getColor(if (page == 1) R.color.button_inactive else R.color.button_active))

        binding.btnNext.backgroundTintList =
            ColorStateList.valueOf(getColor(if (page != 1 && viewModel.isLastPage()) R.color.button_inactive else R.color.button_active))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(FashionProductListActivity.newIntent(this))
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
