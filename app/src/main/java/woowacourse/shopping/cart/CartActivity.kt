package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.BundleKeys
import woowacourse.shopping.ProductClickListener
import woowacourse.shopping.R
import woowacourse.shopping.cart.list.CartRecyclerViewAdapter
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartDBRepository
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

class CartActivity : AppCompatActivity(), CartContract.View, ProductClickListener {
    private lateinit var binding: ActivityCartBinding
    private val adapter: CartRecyclerViewAdapter =
        CartRecyclerViewAdapter(this, ::clickDeleteButton)
    private lateinit var presenter: CartContract.Presenter
    private val repository: CartDBRepository by lazy { CartDBRepository(CartDBHelper(this).writableDatabase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        setToolBarBackButton()

        presenter = CartPresenter(this, repository)

        initCartList()
        initSetOnClickListener()

        binding.rvCartList.adapter = adapter
    }

    private fun initCartList() {
        presenter.getCartProducts()
        presenter.setPageNumber()
    }

    private fun initSetOnClickListener() {
        setPagePreviousClickListener()
        setPageNextClickListener()
    }

    override fun setCartProducts(newCartProducts: List<CartProductUIModel>) {
        adapter.initProducts(newCartProducts)
        adapter.notifyDataSetChanged()
    }

    private fun clickDeleteButton(cartProduct: CartProductUIModel, position: Int) {
        presenter.removeProduct(cartProduct, position)
    }

    private fun setPageNextClickListener() {
        binding.btNext.setOnClickListener {
            presenter.goNextPage()
        }
    }

    private fun setPagePreviousClickListener() {
        binding.btPrevious.setOnClickListener {
            presenter.goPreviousPage()
        }
    }

    override fun setPage(page: Int) {
        binding.tvCurrentPage.text = page.toString()
    }

    override fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int) {
        adapter.removeData(cartProductUIModel)
        adapter.notifyItemRemoved(position)
    }

    private fun setToolBarBackButton() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.close()
    }

    companion object {
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }

    override fun onProductClick(productUIModel: ProductUIModel) {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, productUIModel)
        startActivity(intent)
    }
}
