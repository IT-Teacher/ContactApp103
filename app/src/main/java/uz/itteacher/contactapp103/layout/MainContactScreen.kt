package uz.itteacher.contactapp103.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uz.itteacher.contactapp103.model.MyContact
import uz.itteacher.contactapp103.R


@Composable
fun MainContactScreen(navController: NavHostController, myContacts: List<MyContact>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (myContacts.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.box),
                contentDescription = "Box",
                modifier = Modifier.size(200.dp)
            )
        }
        else{
            LazyColumn(modifier = Modifier.padding(10.dp)){
                items(myContacts){
                    Row(modifier = Modifier.padding(10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "Person",
                            modifier = Modifier.size(50.dp).padding(18.dp)
                        )
                        Text(text = it.name)

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
    Row(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp, end = 20.dp),
        horizontalArrangement = Arrangement.End,) {
        FloatingActionButton(onClick = {
            navController.navigate("create")
        },
            modifier = Modifier.align(Alignment.Bottom)) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(48.dp)
                    .padding(10.dp)
            )

        }
    }

}