package woowacourse.shopping.presentation.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import woowacourse.shopping.databinding.DialogProductDetailBinding
import woowacourse.shopping.presentation.common.CounterContract
import woowacourse.shopping.presentation.model.ProductModel

class ProductDetailDialog(
    private val productModel: ProductModel,
    private val presenter: ProductDetailContract.Presenter,
) : DialogFragment() {

    private lateinit var counter: CounterContract.Presenter
    private lateinit var binding: DialogProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        initView()
    }

    private fun setUpBinding() {
        binding.lifecycleOwner = this
        binding.productModel = productModel
        binding.presenter = presenter
        counter = binding.counterDialogCartProduct.presenter
    }

    private fun initView() {
        counter.updateCount(presenter.productInfo.value.count)
        minusClick()
        plusCLick()
        addCartButtonClick()
    }

    private fun minusClick() {
        binding.counterDialogCartProduct.minusButton.setOnClickListener {
            counter.minusCount()
            presenter.updateProductCount(counter.counter.value.value)
        }
    }

    private fun plusCLick() {
        binding.counterDialogCartProduct.plusButton.setOnClickListener {
            counter.plusCount()
            presenter.updateProductCount(counter.counter.value.value)
        }
    }

    private fun addCartButtonClick() {
        binding.buttonDialogPutCart.setOnClickListener {
            presenter.saveProductInRepository(counter.counter.value.value)
            dismiss()
        }
    }
}
