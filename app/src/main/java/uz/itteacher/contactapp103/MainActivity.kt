package uz.itteacher.contactapp103

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.itteacher.contactapp103.db.AppDataBase
import uz.itteacher.contactapp103.layout.CreateContactScreen
import uz.itteacher.contactapp103.layout.HistoryContactScreen
import uz.itteacher.contactapp103.layout.MainContactScreen
import uz.itteacher.contactapp103.layout.SearchScreen
import uz.itteacher.contactapp103.navigation.NavigationItem
import uz.itteacher.contactapp103.ui.theme.ContactApp103Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val appDataBase = AppDataBase.getInstance(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactApp103Theme {
                Column(modifier = Modifier.fillMaxSize().padding(top = 60.dp, bottom = 40.dp)) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = NavigationItem.Main.route){
                        composable(NavigationItem.Main.route){
                            MainContactScreen(navController, appDataBase)
                        }
                        composable(NavigationItem.History.route){
                            HistoryContactScreen(navController, appDataBase)
                        }
                        composable(NavigationItem.Create.route){
                            CreateContactScreen(navController,appDataBase)
                        }
                        composable(NavigationItem.Search.route){
                            SearchScreen(navController,appDataBase)
                        }
                    }
                }

            }
        }
    }
}



