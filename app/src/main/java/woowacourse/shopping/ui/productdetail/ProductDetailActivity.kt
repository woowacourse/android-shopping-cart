package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.ui.base.BaseActivity

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {
    val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = null
        viewModel.updateProductDetail(intent.getIntExtra(KEY_PRODUCT_ID, 0))
        binding.viewModel = viewModel
        binding.onAddCartProductClick = ::addCartProduct
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_product_detail_close) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun addCartProduct() {
        viewModel.addCartProduct()
        Toast.makeText(this, "성공적으로 장바구니에 담겼습니다!", Toast.LENGTH_SHORT).show()
        finish()
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
