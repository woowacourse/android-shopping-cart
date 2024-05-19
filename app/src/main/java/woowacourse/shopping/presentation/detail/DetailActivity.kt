package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.datasource.DefaultCart
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.home.HomeViewModel
import woowacourse.shopping.presentation.home.HomeViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }

    private val viewModel: DetailViewModel by viewModels {
        val application = application as ShoppingApplication
        DetailViewModelFactory(
            application.productRepository,
            application.cartRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            initializeProductInformation()
        }
        initializeBindingVariables()
        initializeToolbar()
        observeEvents()
    }

    private fun initializeProductInformation() {
        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, DEFAULT_PRODUCT_ID)
        viewModel.loadProductInformation(productId)
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initializeBindingVariables() {
        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeEvents() {
        viewModel.message.observe(this) { event ->
            if (event.hasBeenHandled) return@observe
            showToastMessage(
                getString(
                    event.getContentIfNotHandled()?.stringResourceId ?: return@observe
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val DEFAULT_PRODUCT_ID = -1L

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}
