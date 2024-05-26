package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.card.MaterialCardView
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.presentation.home.ProductQuantity

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    private val productId by lazy {
        intent.getLongExtra(EXTRA_PRODUCT_ID, DEFAULT_PRODUCT_ID)
    }
    private val lastlyViewedProductId by lazy {
        intent.getLongExtra(EXTRA_LASTLY_VIEWED, DEFAULT_PRODUCT_ID)
    }
    private val viewModel: DetailViewModel by viewModels {
        val application = application as ShoppingApplication
        DetailViewModelFactory(
            application.productRepository,
            application.cartRepository,
            application.productHistoryRepository,
            productId,
            lastlyViewedProductId,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBindingVariables()
        initializeToolbar()
        initializeOnBackPressedCallback()
        observeEvents()
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initializeBindingVariables() {
        binding.detailViewModel = viewModel
        binding.quantityListener = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeEvents() {
        viewModel.message.observe(this) { event ->
            if (event.hasBeenHandled) return@observe
            val quantity =
                ProductQuantity(
                    productId,
                    viewModel.productInformation.value?.quantity ?: -1,
                )
            setResult(
                RESULT_OK,
                Intent().putExtra(
                    "quantities",
                    arrayListOf(quantity),
                ),
            )
            showToastMessage(
                getString(
                    event.getContentIfNotHandled()?.stringResourceId ?: return@observe,
                ),
            )
        }
        viewModel.navigateToDetailEvent.observe(this) { event ->
            val data = event.getContentIfNotHandled()
            val intent =
                newIntent(
                    this,
                    data?.productId ?: return@observe,
                    data.lastlyViewedProductId,
                )
            viewModel.updateHistory(id = productId)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_exit -> navigateBackToMain()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateBackToMain() {
        viewModel.updateHistory(id = productId)
        finish()
    }

    private fun initializeOnBackPressedCallback() {
        val onBackPressedCallBack =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = navigateBackToMain()
            }
        onBackPressedDispatcher.addCallback(onBackPressedCallBack)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val EXTRA_LASTLY_VIEWED = "extra_lastly_viewed"
        private const val DEFAULT_PRODUCT_ID = -1L

        fun newIntent(
            context: Context,
            productId: Long,
            lastlyViewedProductId: Long?,
        ): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_PRODUCT_ID, productId)
                .putExtra(EXTRA_LASTLY_VIEWED, lastlyViewedProductId)
        }
    }
}

@BindingAdapter("shopping:lastProductAvailability")
fun MaterialCardView.setLastViewedProductAvailability(recentProduct: RecentProduct?) {
    visibility =
        if (recentProduct == null) {
            View.GONE
        } else {
            View.VISIBLE
        }
}
