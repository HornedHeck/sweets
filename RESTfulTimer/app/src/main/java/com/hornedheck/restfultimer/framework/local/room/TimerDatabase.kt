package com.hornedheck.restfultimer.framework.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hornedheck.restfultimer.framework.models.TimerRaw
import com.hornedheck.restfultimer.framework.models.TimerStep

@Database(entities = [TimerRaw::class, TimerStep::class], version = 4 , exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract val timerDao: TimerDao

}