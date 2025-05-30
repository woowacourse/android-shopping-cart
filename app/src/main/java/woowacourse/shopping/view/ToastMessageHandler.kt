package woowacourse.shopping.view

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import woowacourse.shopping.R

interface ToastMessageHandler {
    val toastMessage: LiveData<Event<Unit>>

    fun observeToastMessage(owner: LifecycleOwner) {
        val context = (owner as? Context) ?: return
        toastMessage.observe(owner) {
            val text = context.getString(R.string.fail_access_data_message)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}
