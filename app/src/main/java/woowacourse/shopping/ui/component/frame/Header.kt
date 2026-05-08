package woowacourse.shopping.ui.component.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun Header(
    modifier: Modifier = Modifier,
    headerContent: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFF555555),
                )
                .padding(26.dp, 16.dp),
    ) {
        headerContent()
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    Header(Modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Shopping")
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "shopping icon",
            )
        }
    }
}
