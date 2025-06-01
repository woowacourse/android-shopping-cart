package woowacourse.shopping.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartItemDatabase
import woowacourse.shopping.data.cart.CartItemRepositoryImpl
import woowacourse.shopping.data.recent.ViewedItemDatabase
import woowacourse.shopping.data.recent.ViewedItemRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.DetailViewModel.Companion.factory
import woowacourse.shopping.product.detail.event.DetailEventHandlerImpl
import woowacourse.shopping.util.IntentCompat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by lazy { provideViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setupWindowInsets()
        setupToolbar()

        initBinding()
        initObservers()
        initHandlers()
        loadInitialData()
    }

    private fun provideViewModel(): DetailViewModel {
        val cartDao = CartItemDatabase.getInstance(this).cartItemDao()
        val viewedDao = ViewedItemDatabase.getInstance(this).viewedItemDao()
        return ViewModelProvider(
            this,
            factory(CartItemRepositoryImpl(cartDao), ViewedItemRepositoryImpl(viewedDao)),
        )[DetailViewModel::class.java]
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initObservers() {
        viewModel.uiState.observe(this) { state ->
            val msgRes =
                when (state) {
                    CartUiState.SUCCESS -> R.string.text_add_to_cart_success
                    CartUiState.FAIL -> R.string.text_unInserted_toast
                }
            showToast(msgRes)
        }

        viewModel.product.observe(this) {
            binding.product = it
            binding.executePendingBindings()
        }

        viewModel.lastViewed.observe(this) {
            binding.recentItem = it
        }
    }

    private fun initHandlers() {
        val handler =
            DetailEventHandlerImpl(viewModel) { product ->
                startActivity(newIntent(this, product))
                finish()
            }
        binding.handler = handler
        binding.detailHandler = handler
    }

    private fun loadInitialData() {
        productFromIntent()?.let { product ->
            binding.product = product
            viewModel.setProduct(product)
            viewModel.loadLastViewedItem(product.id)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
            title = ""
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun productFromIntent(): ProductUiModel? =
        IntentCompat.getParcelableExtra(intent, KEY_PRODUCT_DETAIL, ProductUiModel::class.java)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_back_menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_detail_back -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        private const val KEY_PRODUCT_DETAIL = "productDetail"

        fun newIntent(
            context: Context,
            product: ProductUiModel,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_DETAIL, product)
            }
    }
}
