package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.datasourceimpl.DefaultCartDataSource
import woowacourse.shopping.data.datasourceimpl.DefaultProductDataSource
import woowacourse.shopping.data.datasourceimpl.DefaultProductHistoryDataSource
import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.db.producthistory.ProductHistoryDatabase
import woowacourse.shopping.data.repository.DefaultCartRepository
import woowacourse.shopping.data.repository.DefaultProductHistoryRepository
import woowacourse.shopping.data.repository.DefaultProductRepository
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModel
import woowacourse.shopping.presentation.detail.viewmodel.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: DetailViewModel
    private val productId by lazy {
        intent.getLongExtra(EXTRA_PRODUCT_ID, DEFAULT_PRODUCT_ID)
    }
    private val showRecent by lazy {
        intent.getBooleanExtra(EXTRA_SHOW_RECENT, DEFAULT_SHOW_RECENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewModel(productId, showRecent)
        initObserver()
        initBinding()
        initToolBar()
    }

    private fun initViewModel(
        productId: Long,
        showRecent: Boolean,
    ) {
        viewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(
                    DefaultProductRepository(DefaultProductDataSource),
                    DefaultCartRepository(
                        DefaultCartDataSource(
                            CartDatabase.getInstance(this),
                        ),
                    ),
                    DefaultProductHistoryRepository(
                        DefaultProductHistoryDataSource(
                            ProductHistoryDatabase.getInstance(this),
                        ),
                    ),
                    productId,
                    showRecent,
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
                resultIntent.putExtra(EXTRA_DETAIL_PRODUCT_ID, productId)
                setResult(DETAIL_RESULT_OK, resultIntent)
            }
        }

        viewModel.moveToRecentProductHistory.observe(this) {
            it.getContentIfNotHandled()?.let { productId ->
                startActivity(
                    newIntentWithFlag(
                        this,
                        productId,
                        false,
                        Intent.FLAG_ACTIVITY_CLEAR_TOP,
                    ),
                )
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
        private const val EXTRA_SHOW_RECENT = "extra_show_recent"
        private const val DEFAULT_PRODUCT_ID = -1L
        private const val DEFAULT_SHOW_RECENT = true
        const val EXTRA_DETAIL_PRODUCT_ID = "extra_detail_product_id"
        const val DETAIL_RESULT_OK = 1000

        fun newIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).putExtra(EXTRA_PRODUCT_ID, productId)
        }

        fun newIntentWithFlag(
            context: Context,
            productId: Long,
            showRecent: Boolean,
            flag: Int,
        ): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_PRODUCT_ID, productId)
                .putExtra(EXTRA_SHOW_RECENT, showRecent)
                .setFlags(flag)
        }
    }
}

@BindingAdapter("app:hasProductName", "app:isShowRecent")
fun CardView.setRecentProductVisibility(
    productName: String?,
    showRecent: Boolean,
) {
    visibility =
        if (productName != null && showRecent) {
            View.VISIBLE
        } else {
            View.GONE
        }
}
