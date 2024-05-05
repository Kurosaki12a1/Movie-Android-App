package com.kuro.movie.domain.alarm_manager

import com.kuro.movie.domain.model.UpComingAlarmItem

interface UpComingAlarmScheduler {
    fun scheduleAlarm(item: UpComingAlarmItem)
    fun cancelAlarm(item: UpComingAlarmItem)
}