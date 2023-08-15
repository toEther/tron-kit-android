package io.definenulls.tronkit.database

import androidx.room.*
import io.definenulls.tronkit.models.LastBlockHeight

@Dao
interface LastBlockHeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lastBlockHeight: LastBlockHeight)

    @Query("SELECT * FROM LastBlockHeight")
    fun getLastBlockHeight(): LastBlockHeight?
}
