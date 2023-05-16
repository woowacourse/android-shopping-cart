package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogCountBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.PriceFormatter
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productlist.ProductListActivity.Companion.ID
import woowacourse.shopping.view.productlist.ProductListActivity.Companion.RESULT_VIEWED

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var productDetailBinding: ActivityProductDetailBinding
    private lateinit var dialogBinding: DialogCountBinding
    private lateinit var dialog: AlertDialog
    private lateinit var presenter: ProductDetailContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpPresenter()
        setUpBinding()
        setContentView(productDetailBinding.root)
        val product = intent.getParcelableCompat<ProductModel>(PRODUCT)
        if (product == null) {
            forceQuit()
            return
        }
        setUpInitView(product)
        setUpDialog(product)
        setUpResult(product.id)
        presenter.updateRecentViewedProducts(product.id)
    }

    private fun setUpBinding() {
        productDetailBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        dialogBinding = DialogCountBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter =
            ProductDetailPresenter(this, CartDbRepository(this), RecentViewedDbRepository(this))
    }

    private fun forceQuit() {
        Toast.makeText(this, NOT_EXIST_DATA_ERROR, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun setUpInitView(product: ProductModel) {
        productDetailBinding.product = product
        productDetailBinding.presenter = presenter
        Glide.with(productDetailBinding.root.context).load(product.imageUrl).into(productDetailBinding.imgProduct)
        productDetailBinding.btnPutInCart.setOnClickListener {
            dialog.show()
        }
    }
    private fun setUpDialog(product: ProductModel) {
        setUpDialogBinding(product)
        dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
    }

    private fun setUpDialogBinding(product: ProductModel) {
        dialogBinding.presenter = presenter
        dialogBinding.product = product
    }

    override fun updateCount(count: Int) {
        dialogBinding.textCount.text = count.toString()
    }

    private fun setUpResult(id: Int) {
        intent.putExtra(ID, id)
        setResult(RESULT_VIEWED, intent)
    }

    override fun startCartActivity() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PRODUCT = "PRODUCT"
        private const val NOT_EXIST_DATA_ERROR = "데이터가 넘어오지 않았습니다."

        fun newIntent(context: Context, product: ProductModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT, product)
            return intent
        }
    }
}
