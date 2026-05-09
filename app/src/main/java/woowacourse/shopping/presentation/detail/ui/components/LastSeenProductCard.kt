package woowacourse.shopping.presentation.detail.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.AndroidshoppingTheme
import woowacourse.shopping.ui.theme.Gray50
import woowacourse.shopping.ui.theme.Green40

@Composable
fun LastSeenProductCard(
    onClick: () -> Unit,
    name: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .border(1.dp, Gray50, RoundedCornerShape(4.dp)),
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.last_seen_product),
                fontSize = 12.sp,
                color = Green40,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = name,
                fontSize = 18.sp,
                color = Gray50,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LastSeenProductCardPreview() {
    AndroidshoppingTheme {
        LastSeenProductCard(
            onClick = {},
            name = "모카 프라푸치노",
        )
    }
}
