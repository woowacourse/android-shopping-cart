package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
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

class CartActivity :
    AppCompatActivity(),
    CartContract.View,
    ProductClickListener,
    ItemClickListener,
    OnCheckedChangedListener {
    private lateinit var binding: ActivityCartBinding
    private val adapter: CartRecyclerViewAdapter =
        CartRecyclerViewAdapter(this, this, this)
    private lateinit var presenter: CartPresenter
    private val repository: CartDBRepository by lazy { CartDBRepository(CartDBHelper(this).writableDatabase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setToolBarBackButton()
        initPresenter()
        initCartList()
        initSetOnClickListener()
        showTotalPrice()
        showTotalPickedProductsCount()
        initAdapter()
        setOnAllCheckedChangeListener()
    }

    private fun showTotalPickedProductsCount() {
        presenter.totalPickedProductsCount.observe(this) {
            binding.btOrder.text = getString(R.string.cart_order_button_text, it)
        }
    }

    private fun setOnAllCheckedChangeListener() {
        val onAllCheckedChange = ::onChangedIsAllPicked
        binding.onCheckedChangedListener = onAllCheckedChange
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
    }

    private fun setToolBarBackButton() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPresenter() {
        presenter = CartPresenter(this, repository)
    }

    private fun initCartList() {
        presenter.getCartProducts()
        presenter.setPageNumber()
    }

    private fun initSetOnClickListener() {
        setPagePreviousClickListener()
        setPageNextClickListener()
    }

    private fun initAdapter() {
        binding.rvCartList.adapter = adapter
    }

    override fun setCartProducts(newCartProducts: List<CartProductUIModel>) {
        adapter.initProducts(newCartProducts)
        adapter.notifyDataSetChanged()
    }

    override fun clickDeleteButton(cartProduct: CartProductUIModel, position: Int) {
        presenter.removeProduct(cartProduct, position)
    }

    override fun onMinusClick(cartProduct: CartProductUIModel, count: Int, countView: TextView) {
        presenter.updateCartProductCount(cartProduct, count)
        if (count <= 0) return
        countView.text = count.toString()
    }

    override fun onPlusClick(cartProduct: CartProductUIModel, count: Int, countView: TextView) {
        presenter.updateCartProductCount(cartProduct, count)
        countView.text = count.toString()
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

    override fun showPageNumber(page: Int) {
        binding.tvCurrentPage.text = page.toString()
    }

    override fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int) {
        adapter.removeData(cartProductUIModel)
        adapter.notifyItemRemoved(position)
    }

    override fun refreshAllChecked(isChecked: Boolean) {
        binding.cbAllChecked.isChecked = isChecked
    }

    private fun showTotalPrice() {
        presenter.totalPrice.observe(this) {
            binding.tvTotalPrice.text = getString(R.string.formatted_price, it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClick(productUIModel: ProductUIModel) {
        val intent = ProductDetailActivity.intent(this)
        intent.putExtra(BundleKeys.KEY_PRODUCT, productUIModel)
        startActivity(intent)
    }

    override fun onChangedIsPicked(cartProductUIModel: CartProductUIModel, isPicked: Boolean) {
        presenter.updateProductIsPicked(cartProductUIModel, isPicked)
    }

    private fun onChangedIsAllPicked(isAllChecked: Boolean) {
        presenter.updateIsPickAllProduct(isAllChecked)
        presenter.getCartProducts()
    }

    companion object {
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
