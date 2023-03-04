package com.technomatesoftware.ergostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.technomatesoftware.ergostats.navigation.NavGraph
import com.technomatesoftware.ergostats.ui.theme.ErgoStatsTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            ErgoStatsTheme {
                // A surface container using the 'background' color from the theme
                NavGraph(navController = navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val navController = rememberNavController()
    ErgoStatsTheme {
        NavGraph(navController = navController)
    }
}