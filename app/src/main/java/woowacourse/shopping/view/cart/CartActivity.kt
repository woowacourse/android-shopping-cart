package woowacourse.shopping.view.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.data.repository.CartDbRepository
import woowacourse.shopping.domain.repository.ProductRemoteRepository
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.domain.data.MockServer
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.view.productlist.ProductListActivity

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var mockWebServer: MockServer
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpMockServer()
        setUpPresenter()
        setUpBinding()
        setContentView(binding.root)
        setUpActionBar()
        presenter.fetchProducts()
    }

    private fun setUpMockServer() {
        val thread = Thread { mockWebServer = MockServer() }
        thread.start()
        thread.join()
    }

    private fun setUpBinding() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.presenter = presenter
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpPresenter() {
        presenter = CartPresenter(this, CartDbRepository(this), ProductRemoteRepository(mockWebServer.url))
    }

    override fun showProducts(items: List<CartViewItem>) {
        binding.recyclerCart.adapter = CartAdapter(
            items,
            object : CartAdapter.OnItemClick {
                override fun onRemoveClick(id: Int) {
                    presenter.removeProduct(id)
                }

                override fun onNextClick() {
                    presenter.fetchNextPage()
                }

                override fun onPrevClick() {
                    presenter.fetchPrevPage()
                }

                override fun onUpdateCount(id: Int, count: Int) {
                    presenter.updateCartProductCount(id, count)
                }

                override fun onSelectProduct(product: CartProductModel) {
                    presenter.checkProduct(product)
                }
            }
        )
    }

    override fun showChangedItems() {
        binding.recyclerCart.adapter?.notifyDataSetChanged()
    }

    override fun showChangedItem(position: Int) {
        binding.recyclerCart.adapter?.notifyItemChanged(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(ProductListActivity.RESULT_VISIT_CART, intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CartActivity::class.java)
    }
}
