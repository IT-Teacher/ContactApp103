package uz.itteacher.contactapp103.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.itteacher.contactapp103.R
import uz.itteacher.contactapp103.db.AppDataBase


@Composable
fun SearchScreen(navController: NavController, appDataBase: AppDataBase){
    var search_name by remember { mutableStateOf("") }
    val myContacts = appDataBase.getMyContactDao().getAllContacts().sortedBy { it.name }
        .filter { it.name.contains(search_name, ignoreCase = true) }
    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navController.navigate("main")
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
            Text("Qidirish", fontSize = 25.sp, modifier = Modifier.padding(start = 20.dp, top = 10.dp))
        }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        value = search_name,
        onValueChange = {
            search_name = it
        },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        placeholder = {
            Text(text = "Qidirish")
        })

        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp)){
                items(myContacts){
                    Card(
                        Modifier.fillMaxWidth().padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(modifier = Modifier.padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.person),
                                contentDescription = "Person",
                                modifier = Modifier.size(70.dp).padding(18.dp)
                            )
                            Text(text = it.name, fontSize = 20.sp)

                        }
                    }
                }
            }
        }
    }
}