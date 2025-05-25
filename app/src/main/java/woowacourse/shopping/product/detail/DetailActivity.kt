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

    private val viewModel by lazy {
        val db = CartItemDatabase.getInstance(this)
        val repository = CartItemRepositoryImpl(db.cartItemDao())
        val viewedDb = ViewedItemDatabase.getInstance(this)
        val viewedRepository = ViewedItemRepositoryImpl(viewedDb.viewedItemDao())
        ViewModelProvider(
            this,
            factory(repository, viewedRepository),
        )[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        applyWindowInsets()
        setSupportActionBar()

        observeCartUiState()

        val product: ProductUiModel? = productFromIntent()
        product?.let {
            binding.product = it
            viewModel.setProduct(it)
        }

        binding.handler =
            DetailEventHandlerImpl(
                viewModel,
            )
        binding.detailHandler = DetailEventHandlerImpl(viewModel)
        observeProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_back_menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_detail_back -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun observeCartUiState() {
        viewModel.uiState.observe(this) { value ->
            when (value) {
                CartUiState.SUCCESS -> showToastMessage(getString(R.string.text_add_to_cart_success))
                CartUiState.FAIL -> showToastMessage(getString(R.string.text_unInserted_toast))
            }
        }
        binding.lifecycleOwner = this
    }

    private fun observeProduct() {
        viewModel.product.observe(this) { product ->
            binding.product = product
            binding.executePendingBindings()
        }
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = ""
    }

    private fun showToastMessage(message: String) {
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun productFromIntent(): ProductUiModel? =
        IntentCompat.getParcelableExtra(intent, KEY_PRODUCT_DETAIL, ProductUiModel::class.java)

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
