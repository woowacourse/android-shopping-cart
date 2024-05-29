package woowacourse.shopping.productdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.productlist.ChangedItemsId
import woowacourse.shopping.productlist.LatestViewedProductViewModel
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.productlist.RecentlyViewedProductsClickAction
import woowacourse.shopping.util.ViewModelFactory

class ProductDetailActivity : AppCompatActivity(), RecentlyViewedProductsClickAction {
    private lateinit var pendingIntent: Intent
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels {
        ViewModelFactory(
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.quantityControlClickListener = viewModel
        binding.recentProductClickListener = this

        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        val recentProductClicked = intent.getBooleanExtra(EXTRA_RECENT_PRODUCT_CLICKED, false)
        binding.isLatestViewedProductVisible = !recentProductClicked
        showProductDetail(productId, recentProductClicked)

        pendingIntent = Intent(this, ProductListActivity::class.java)
        setAddedItemOnResult()
        setLatestViewedProduct()
        setActionOnSuccess()
        setupToolbar()
    }

    private fun showProductDetail(
        productId: Long,
        recentProductClicked: Boolean,
    ) {
        viewModel.showLatestViewedProduct(recentProductClicked)
        viewModel.loadProductDetail(productId)
    }

    private fun setAddedItemOnResult() {
        viewModel.addedItems.observe(this) { addedItems ->
            pendingIntent.putExtra(ChangedItemsId.KEY_CHANGED_ITEMS, ChangedItemsId(addedItems))
            setResult(Activity.RESULT_OK, pendingIntent)
        }
    }

    private fun setLatestViewedProduct() {
        viewModel.productUi.observe(this) { presentProduct ->
            pendingIntent.putExtra(ProductListActivity.EXTRA_LATEST_VIEWED_PRODUCT_ID, presentProduct.id)
            setResult(Activity.RESULT_OK, pendingIntent)
        }
    }

    private fun setActionOnSuccess() {
        viewModel.isAddSuccess.observe(this) { success ->
            if (success) {
                showToastMessage(TOAST_MESSAGE_ON_ADD_SUCCESS)
                finish()
            } else {
                showToastMessage(TOAST_MESSAGE_ON_ADD_FAILURE)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarProductDetail as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_detail_close -> finish()
            else -> {}
        }
        return true
    }

    override fun onRecentProductClicked(id: Long?) {
        id?.let {
            val intent = newInstance(this, id)
            intent.putExtra(EXTRA_RECENT_PRODUCT_CLICKED, true)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_RECENT_PRODUCT_CLICKED = "recentProductClicked"
        private const val EXTRA_PRODUCT_ID = "productId"
        private const val TOAST_MESSAGE_ON_ADD_SUCCESS = "상품을 장바구니에 담았습니다!"
        private const val TOAST_MESSAGE_ON_ADD_FAILURE = "오류가 발생했습니다! 잠시 후에 다시 시도해주세요."

        fun newInstance(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).apply {
            this.putExtra(
                EXTRA_PRODUCT_ID,
                productId,
            )
        }
    }
}
