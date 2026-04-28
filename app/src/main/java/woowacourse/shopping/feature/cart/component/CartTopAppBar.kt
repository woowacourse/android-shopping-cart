package woowacourse.shopping.feature.cart.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.core.designsystem.component.BasicTopAppBar

@Composable
fun CartTopAppBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTopAppBar(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_left_arrow_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable(onClick = onClick)
            )

            Text(
                text = "Cart",
                color = Color.White,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
private fun CartTopAppBarPreview() {
    CartTopAppBar(onClick = {})
}
