package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.datasourceimpl.DefaultCart
import woowacourse.shopping.data.datasourceimpl.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.db.cart.CartDatabase
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModel
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, DEFAULT_PRODUCT_ID)
        initViewModel(productId)
        initObserver()
        initBinding()
        initToolBar()
    }

    private fun initViewModel(productId: Long) {
        viewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(
                    ProductRepositoryImpl(DefaultProducts),
                    CartRepositoryImpl(
                        DefaultCart(
                            CartDatabase.getInstance(this),
                        ),
                    ),
                    productId,
                ),
            )[DetailViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.addComplete.observe(this) {
            it.getContentIfNotHandled()?.let { productId ->
                Toast.makeText(
                    this,
                    getString(R.string.message_add_to_cart_complete),
                    Toast.LENGTH_SHORT,
                ).show()

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_DETAIL_CART_ITEM, productId)
                setResult(DETAIL_RESULT_OK, resultIntent)
            }
        }
    }

    private fun initBinding() {
        binding.detailViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initToolBar() {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val DEFAULT_PRODUCT_ID = -1L
        const val EXTRA_DETAIL_CART_ITEM = "extra_detail_cart_item"
        const val DETAIL_RESULT_OK = 1000

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}
