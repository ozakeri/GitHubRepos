package gap.com.githubrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import gap.com.githubrepos.entitiy.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun getGitHubDao():GitHubDao

}