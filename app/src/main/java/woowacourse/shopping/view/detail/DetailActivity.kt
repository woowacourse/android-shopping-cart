package woowacourse.shopping.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.App
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.core.ext.showToast
import woowacourse.shopping.view.core.handler.CartQuantityHandler
import woowacourse.shopping.view.detail.vm.DetailViewModel
import woowacourse.shopping.view.detail.vm.DetailViewModelFactory

class DetailActivity : AppCompatActivity(), CartQuantityHandler {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            (application as App).container.productRepository,
            (application as App).container.cartRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, 0L)
        viewModel.load(productId)

        setUpBinding()
        setUpSystemBars()
        observeViewModel()
    }

    private fun setUpBinding() {
        with(binding) {
            lifecycleOwner = this@DetailActivity
            vm = viewModel
            cartQuantityEventHandler = this@DetailActivity
        }
    }

    private fun setUpSystemBars() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun observeViewModel() {
        viewModel.event.observe(this) {
            when (it) {
                DetailUiEvent.MoveToCart -> navigateToCart()
                is DetailUiEvent.ShowCannotIncrease -> {
                    showToast(
                        getString(R.string.text_over_quantity).format(it.quantity),
                    )
                }

                DetailUiEvent.ShowCannotDecrease -> {
                    showToast(
                        getString(R.string.text_minimum_quantity),
                    )
                }
            }
        }
    }

    private fun navigateToCart() {
        startActivity(CartActivity.newIntent(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bar_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClickIncrease(productId: Long) {
        viewModel.increaseCartQuantity()
    }

    override fun onClickDecrease(productId: Long) {
        viewModel.decreaseCartQuantity()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
}
