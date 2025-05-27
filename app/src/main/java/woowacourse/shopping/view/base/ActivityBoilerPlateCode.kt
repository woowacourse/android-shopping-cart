package woowacourse.shopping.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

interface ActivityBoilerPlateCode<T : ViewDataBinding> {
    var binding: T

    fun AppCompatActivity.initialize()

    fun AppCompatActivity.onUnexpectedError(message: String)
}
