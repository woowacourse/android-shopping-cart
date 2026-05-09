package woowacourse.shopping.ui.common.component.recentlyviewed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LastViewedProductCard(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .size(height = 80.dp, width = 324.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp),
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFAAAAAA),
                    shape = RoundedCornerShape(5.dp),
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 18.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column {
            Text(
                text = "마지막으로 본 상품",
                color = Color(0xFF04C09E),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = name,
                color = Color(0xFF555555),
                fontSize = 18.sp,
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LastViewedProductCardPreview() {
    LastViewedProductCard(
        name = "PET보틀-정사각형(500ml)",
        onClick = {},
    )
}
