package woowacourse.shopping.study

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityStudyBinding
import woowacourse.shopping.presentation.base.BindingActivity

data class Item(val id: Long, val content: String)

class StudyActivity : BindingActivity<ActivityStudyBinding>(R.layout.activity_study) {
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val odooongItems = List(4) { index -> Item(index + 1L, "오둥이 ${index + 1}") }

        val kkosangItems = List(4) { index -> Item(index + 1L, "꼬상이 ${index + 1}") }

        concatAdapter =
            ConcatAdapter(
                ConcatAdapter.Config.Builder()
                    .setIsolateViewTypes(false)
                    .setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS)
                    .build(),
                FirstAdapter(odooongItems),
                SecondAdapter(kkosangItems),
            )
        // 로그: (getItemId, getItemViewType)

        // StableIdMode 와 상관 없음

        // SingleViewType Adapter(같은 ViewHolder 공유) 를 ConcatAdapter 에 추가할 때

        // setIsolateViewTypes(true)
        // 0, 0, 0, 0, 1, 1, 1, 1

        // setIsolateViewTypes(false)- childAdapter 에서 getItemViewType 재정의 X
        // 0, 0, 0, 0, 0, 0, 0, 0

        // setIsolateViewTypes(false)- childAdapter 에서 getItemViewType 재정의
        // 100, 100, 100, 100, 200, 200, 200, 200 - 이러면 재사용 안됨

        // SingleViewType Adapter(각각 다른 ViewHolder 사용) 를 ConcatAdapter 에 추가할 때

        // setIsolateViewTypes(true)
        // 0, 0, 0, 0, 1, 1, 1, 1

        // setIsolateViewTypes(false)
        // Crash 발생! casting 에러(ClassCastException)
        // multiViewType Adapter 를 ConcatAdapter 에 추가할 때

        // MultiViewType Adapter(같은 ViewHolder 공유) 를 ConcatAdapter 에 추가할 때
        // setIsolateViewTypes(true)
        //  0, 1, 0, 1, 2, 3, 2, 3, 2, 3

        // setIsolateViewTypes(false) - childAdapter 에서 getItemViewType 재정의
        // 100, 200, 100, 200, 100, 200, 100, 200, 100, 200

        // -----------------------------------------

        // NO_STABLE_IDS
        // id들 : -1 -1 -1 -1 -1 -1 -1 -1
        // SHARED_STABLE_IDS
        // 1, 2, 3, 4, 1, 2, 3, 4

        // ISOLATED_STABLE_IDS -
        // RecyclerView.Adapter.getItemId(int)의 값과 RecyclerView.ViewHolder.getItemId()의 값이 다를 수 있습니다.
        // stable id가 지정되지 않은 adapter를 추가할 경우 IllegalArgumentException이 발생!!
        // ISOLATED_STABLE_IDS - 각 item 마다 다른 id 사용할 경우
        // 0, 1, 2, 3, 4, 5, 6, 7
        // ISOLATED_STABLE_IDS - 오둥 어뎁터는 모두 100로 id 설정, 꼬상 어뎁터는 모두 200로 id 설정
        // 0, 0, 0, 0, 1, 1, 1, 1
        // ISOLATED_STABLE_IDS - 오둥 어뎁터는 모두 1, 2, 3, 4로 id 설정, 꼬상 어뎁터는 모두 1, 2, 3, 4로 id 설정
        // 0, 1, 2, 3, 4, 5, 6, 7
        binding.rvConcat.adapter = concatAdapter
        repeat(concatAdapter.itemCount) {
            Log.e(TAG, logMsg(it))
        }
    }

    private fun logMsg(position: Int): String {
        return "(${concatAdapter.getItemId(position)} , ${
            concatAdapter.getItemViewType(
                position,
            )
        })"
    }

    companion object {
        const val TAG = "StudyActivity"
    }
}
