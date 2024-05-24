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
import woowacourse.shopping.data.datasourceimpl.DefaultProductHistoryDataSource
import woowacourse.shopping.data.datasourceimpl.DefaultProductDataSource
import woowacourse.shopping.data.repository.DefaultCartRepository
import woowacourse.shopping.data.repository.DefaultProductHistoryRepository
import woowacourse.shopping.data.repository.`Default ProductRepository`
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.db.cart.CartDatabase
import woowacourse.shopping.db.producthistory.ProductHistoryDatabase
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
        val showRecent = intent.getBooleanExtra(EXTRA_SHOW_RESULT, DEFAULT_SHOW_RESULT)
        initViewModel(productId, showRecent)
        initObserver()
        initBinding()
        initToolBar()
    }

    private fun initViewModel(productId: Long, showRecent: Boolean) {
        viewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(
                    `Default ProductRepository`(DefaultProductDataSource),
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
                resultIntent.putExtra(EXTRA_DETAIL_CART_ITEM, productId)
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
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                    )
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
        private const val EXTRA_SHOW_RESULT = "extra_show_result"
        private const val DEFAULT_PRODUCT_ID = -1L
        private const val DEFAULT_SHOW_RESULT = true
        const val EXTRA_DETAIL_CART_ITEM = "extra_detail_cart_item"
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
                .putExtra(EXTRA_SHOW_RESULT, showRecent)
                .setFlags(flag)
        }
    }
}

@BindingAdapter("app:hasProductName", "app:isShowRecent")
fun CardView.setRecentProductVisibility(
    productName: String?,
    showRecent: Boolean,
) {
    if (productName != null && showRecent) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}