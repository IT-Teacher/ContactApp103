package uz.itteacher.contactapp103.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.itteacher.contactapp103.model.MyContact

@Database(entities = [MyContact::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getMyContactDao(): MyContactDAO

    companion object{
        const val DB_NAME = "my_db"
        var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase{
            if (instance == null){
                instance = Room.databaseBuilder(context,
                    AppDataBase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!

        }
    }



}