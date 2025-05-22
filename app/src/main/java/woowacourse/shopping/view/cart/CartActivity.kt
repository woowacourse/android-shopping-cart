package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.viewmodel.cart.CartViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.backImageBtn.setOnClickListener { finish() }
        adapter =
            CartAdapter(onProductRemoveClickListener = { product -> viewModel.removeToCart(product) })

        viewModel.loadedItems.observe(this) {
            adapter.updateProductsView(it)
        }
        viewModel.productsInCart.observe(this) {
            adapter.updateProductsView(it)
            if (viewModel.isOnlyOnePage()) {
                binding.layoutPageButtons.visibility = View.GONE
            }
        }

        viewModel.pageCount.observe(this) { pageCount ->
            binding.tvPageCount.text = pageCount.toString()
            if (viewModel.isFirstPage(pageCount)) {
                binding.btnPreviousPage.setBackgroundColor(
                    (
                        binding.btnPreviousPage.context.getColor(
                            R.color.gray_6,
                        )
                    ),
                )
            } else {
                binding.btnPreviousPage.setBackgroundColor(
                    (
                        binding.btnPreviousPage.context.getColor(
                            R.color.aqua_green,
                        )
                    ),
                )
            }

            if (viewModel.isLastPage(pageCount)) {
                binding.btnNextPage.setBackgroundColor(
                    (
                        binding.btnNextPage.context.getColor(
                            R.color.gray_6,
                        )
                    ),
                )
            } else {
                binding.btnNextPage.setBackgroundColor(
                    (binding.btnNextPage.context.getColor(R.color.aqua_green)),
                )
            }
        }

        binding.btnPreviousPage.setOnClickListener {
            viewModel.loadPreviousPage()
        }

        binding.btnNextPage.setOnClickListener {
            viewModel.loadNextPage()
        }

        binding.rvProductsInCart.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
