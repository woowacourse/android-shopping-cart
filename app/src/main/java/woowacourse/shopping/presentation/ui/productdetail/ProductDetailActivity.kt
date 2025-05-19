package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.presentation.ui.base.BaseActivity
import woowacourse.shopping.presentation.viewmodel.productdetail.ProductDetailViewModel

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        removeSupportActionBarTitle()
        updateProductDetail()
        initViewBinding()
        viewModel.putProductFlag.observe(this) {
            Toast.makeText(this, getString(R.string.product_detail_cart_add_success), Toast.LENGTH_SHORT).show()
        }
        viewModel.finishFlag.observe(this) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_product_detail_close) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun removeSupportActionBarTitle() {
        supportActionBar?.title = null
    }

    private fun updateProductDetail() {
        viewModel.updateProductDetail(intent.getIntExtra(KEY_PRODUCT_ID, 0))
    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    companion object {
        private const val KEY_PRODUCT_ID = "PRODUCT_ID"

        fun newIntent(
            context: Context,
            id: Int,
        ): Intent =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_ID, id)
            }
    }
}
