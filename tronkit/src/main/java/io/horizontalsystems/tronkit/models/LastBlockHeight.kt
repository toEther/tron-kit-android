package io.definenulls.tronkit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LastBlockHeight(val height: Long, @PrimaryKey val id: String = "")
