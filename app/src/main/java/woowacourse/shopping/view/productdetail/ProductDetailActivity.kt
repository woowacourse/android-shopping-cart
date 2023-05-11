package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.BundleKeys
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.data.db.ProductDBHelper
import woowacourse.shopping.data.db.ProductDBRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.getSerializableCompat
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        setSupportActionBar(binding.tbProductDetail)

        val productData =
            intent.getSerializableCompat(BundleKeys.KEY_PRODUCT) ?: ProductUIModel.dummy

        val dbHelper = ProductDBHelper(this)
        val db = dbHelper.writableDatabase
        val repository = ProductDBRepository(db)
        repository.insert(productData)

        Glide.with(binding.root.context)
            .load(productData.url)
            .into(binding.ivProductImage)
        binding.tvProductName.text = productData.name
        binding.tvPrice.text = productData.price.toString()

        setOnClickAddToCart(productData)
    }

    private fun setOnClickAddToCart(productData: ProductUIModel) {
        binding.btAddToCart.setOnClickListener {
            val dbHelper = CartDBHelper(this)
            val db = dbHelper.writableDatabase
            val repository = ProductDBRepository(db)
            repository.insert(productData)

            startActivity(CartActivity.intent(binding.root.context))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, ProductDetailActivity::class.java)
        }
    }
}
