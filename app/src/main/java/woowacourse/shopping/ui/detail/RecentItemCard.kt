package woowacourse.shopping.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.ui.theme.Gray40
import woowacourse.shopping.ui.theme.Green40

@Composable
fun RecentItemCard(
    name: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .border(
                    border = BorderStroke(width = 1.dp, color = Gray40),
                    shape = RoundedCornerShape(16.dp),
                ).padding(horizontal = 18.dp, vertical = 16.dp),
    ) {
        Text(
            text = "마지막으로 본 상품",
            color = Green40,
            fontWeight = FontWeight.W700,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            lineHeight = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecentItemCardPreview() {
    RecentItemCard(
        name = "Test",
    )
}
