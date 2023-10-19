package com.adelvanchik.mydays.Data.day

import com.adelvanchik.mydays.Domain.entity.Day


class DayMapper {

    fun entityDayToDayDBModel(myDay: Day): DayDBModel = DayDBModel(
        data = myDay.data,
        day = myDay.day,
        month = myDay.month,
        year = myDay.year,
        dataStringFormat = myDay.dataStringFormat,
        durationOfSleep = myDay.durationOfSleep,
        health = myDay.health,
        percOfCompletedTasks = myDay.percOfCompletedTasks,
        countOfCompletedTasks = myDay.countOfCompletedTasks,
        countOfPlannedTasks = myDay.countOfPlannedTasks,
        amountOfWater = myDay.amountOfWater,
        achievements = myDay.achievements,
        thoughts = myDay.thoughts,
        training = myDay.training,
    )

    fun entityDayDBModelToDay(myDay: DayDBModel): Day = Day(
        data = myDay.data,
        day = myDay.day,
        month = myDay.month,
        year = myDay.year,
        dataStringFormat = myDay.dataStringFormat,
        durationOfSleep = myDay.durationOfSleep,
        health = myDay.health,
        percOfCompletedTasks = myDay.percOfCompletedTasks,
        countOfCompletedTasks = myDay.countOfCompletedTasks,
        countOfPlannedTasks = myDay.countOfPlannedTasks,
        amountOfWater = myDay.amountOfWater,
        achievements = myDay.achievements,
        thoughts = myDay.thoughts,
        training = myDay.training,
    )

    fun listDayDBModelToDay(list: List<DayDBModel?>): List<Day> = list.filterNotNull().map {
        entityDayDBModelToDay(it)
    }

}