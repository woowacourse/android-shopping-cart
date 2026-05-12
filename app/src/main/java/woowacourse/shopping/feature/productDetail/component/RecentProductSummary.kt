package woowacourse.shopping.feature.productDetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.core.designsystem.theme.LightGreen

@Composable
fun RecentProductSummary(
    productName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "마지막으로 본 상품",
            fontSize = 12.sp,
            fontWeight = FontWeight.W700,
            color = Color.LightGreen,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = productName,
            fontSize = 18.sp,
            color = Color.Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentProductSummaryPreview() {
    RecentProductSummary(
        productName = "리자몽",
        onClick = {},
    )
}
