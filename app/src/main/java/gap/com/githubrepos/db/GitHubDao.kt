package gap.com.githubrepos.db

import androidx.lifecycle.LiveData
import androidx.room.*
import gap.com.githubrepos.entitiy.Item

@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item): Long

    @Delete
    suspend fun deleteUser(item: Item)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<Item>>

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE id = :id)")
    fun userExist(id: Int?): Boolean
}