package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.data.CartsImpl

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels { CartViewModelFactory(CartsImpl) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        showPageNumber()

        setOnPreviousButtonClickListener()
        setOnNextButtonClickListener()

        loadItems()
        setCartAdapter()

        observeCartItems()

        changePreviousButtonEnabled()
        changeNextButtonEnabled()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun changeNextButtonEnabled() {
        viewModel.canMoveNextPage.observe(this) {
            binding.btnNext.isClickable = it
            binding.btnNext.isEnabled = it
        }
    }

    private fun changePreviousButtonEnabled() {
        viewModel.canMovePreviousPage.observe(this) {
            binding.btnPrevious.isClickable = it
            binding.btnPrevious.isEnabled = it
        }
    }

    private fun setOnNextButtonClickListener() {
        binding.btnNext.setOnClickListener {
            viewModel.plusPageNum()
            viewModel.loadCartItems()
        }
    }

    private fun setOnPreviousButtonClickListener() {
        binding.btnPrevious.setOnClickListener {
            viewModel.minusPageNum()
            viewModel.loadCartItems()
        }
    }

    private fun showPageNumber() {
        viewModel.pageNumber.observe(this) {
            binding.pageNumber = it.toString()
        }
    }

    private fun loadItems() {
        viewModel.loadCartItems()
    }

    private fun observeCartItems() {
        viewModel.cart.observe(this) {
            adapter.setData(it)
        }
    }

    private fun setCartAdapter() {
        adapter =
            CartAdapter { productId ->
                viewModel.removeCartItem(productId)
            }
        binding.rvCart.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun startActivity(context: Context) =
            Intent(context, CartActivity::class.java).run {
                context.startActivity(this)
            }
    }
}
