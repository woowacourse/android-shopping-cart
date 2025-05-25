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
        val container = (application as App).container

        DetailViewModelFactory(
            container.productRepository,
            container.cartRepository,
            container.historyRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, 0L)
        val lastSeenProductId =
            intent.getLongExtra(
                EXTRA_LAST_SEEN_PRODUCT_ID,
                NO_LAST_SEEN_PRODUCT,
            )

        viewModel.load(productId, lastSeenProductId)

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

                is DetailUiEvent.MoveToLastSeenProduct -> {
                    startActivity(newIntent(this, it.productId))
                    finish()
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
        const val NO_LAST_SEEN_PRODUCT = 0L
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val EXTRA_LAST_SEEN_PRODUCT_ID = "extra_last_watched_product_id"

        fun newIntent(
            context: Context,
            productId: Long,
            lastSeenProductId: Long? = null,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                lastSeenProductId?.let {
                    putExtra(EXTRA_LAST_SEEN_PRODUCT_ID, it)
                }
            }
        }
    }
}
