package views.ageusd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technomatesoftware.ergostats.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeUsdView(
    navController: NavController,
) {
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("AgeUSD View")
        }
    }


}

@Preview
@Composable
fun PreviewSearchView() {
    val navController = rememberNavController()
    AgeUsdView(navController = navController)
}