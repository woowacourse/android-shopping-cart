package woowacourse.shopping.productdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.productlist.ChangedItemsId
import woowacourse.shopping.productlist.ProductListActivity
import woowacourse.shopping.util.ViewModelFactory

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1L)
        showProductDetail(productId)

        setAddedItemOnResult()
        setActionOnSuccess()
        setupToolbar()
    }

    private fun showProductDetail(productId: Long) {
        viewModel.loadProductDetail(productId)
    }

    private fun setAddedItemOnResult() {
        viewModel.addedItems.observe(this) { addedItems ->
            val intent = Intent(this, ProductListActivity::class.java)
            intent.putExtra(ChangedItemsId.KEY_CHANGED_ITEMS, ChangedItemsId(addedItems))
            setResult(Activity.RESULT_OK, intent)
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

    companion object {
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
