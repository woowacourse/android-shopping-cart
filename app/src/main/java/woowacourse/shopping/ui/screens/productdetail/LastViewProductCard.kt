package woowacourse.shopping.ui.screens.productdetail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LastViewProductCard(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Color(0xFFAAAAAA), shape = RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "마지막으로 본 상품",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF04C09E),
        )

        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF555555),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LastViewProductCardPreview() {
    LastViewProductCard(
        name = "포르쉐 911",
        onClick = { },
        modifier = Modifier.padding(10.dp),
    )
}
