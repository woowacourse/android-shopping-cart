package woowacourse.shopping.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.cart.list.CartRecyclerViewAdapter
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.datas.CartDBHelper
import woowacourse.shopping.datas.CartDBRepository
import woowacourse.shopping.uimodel.CartProductUIModel

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private val adapter: CartRecyclerViewAdapter =
        CartRecyclerViewAdapter(::clickProduct, ::clickDeleteButton)
    private lateinit var presenter: CartContract.Presenter
    private val repository: CartDBRepository by lazy { CartDBRepository(CartDBHelper(this).writableDatabase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        setToolBarBackButton()

        val repository = CartDBRepository(CartDBHelper(this).writableDatabase)
        presenter = CartPresenter(this, repository)
        adapter =
            CartRecyclerViewAdapter(
                repository.getAll().take(CART_UNIT_SIZE),
                repository,
                presenter.setOnClickRemove()
            )

        setPagePreviousClickListener()
        setPageNextClickListener(repository)

        binding.rvCartList.adapter = adapter
    }

    private fun setPageNextClickListener(repository: CartDBRepository) {
        binding.btNext.setOnClickListener {
            val currentPage = binding.tvCurrentPage.text.toString().toInt()
            val nextPage = currentPage + 1
            if (repository.getSize() + CART_UNIT_SIZE < CART_UNIT_SIZE * nextPage) return@setOnClickListener
            adapter.changePage(nextPage)
            binding.tvCurrentPage.text = nextPage.toString()
        }
    }

    private fun setPagePreviousClickListener() {
        binding.btPrevious.setOnClickListener {
            val currentPage = binding.tvCurrentPage.text.toString().toInt()
            if (currentPage == 1) return@setOnClickListener
            adapter.changePage(currentPage - 1)
            binding.tvCurrentPage.text = (currentPage - 1).toString()
        }
    }

    private fun setToolBarBackButton() {
        setSupportActionBar(binding.tbCart)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun removeAdapterData(cartProductUIModel: CartProductUIModel, position: Int) {
        adapter.remove(cartProductUIModel)
        adapter.notifyItemChanged(position)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val CART_UNIT_SIZE = 5
        fun intent(context: Context) = Intent(context, CartActivity::class.java)
    }
}
