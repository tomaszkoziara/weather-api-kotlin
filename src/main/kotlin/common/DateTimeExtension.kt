package common

import org.joda.time.DateTime

fun DateTime.datesTo(endDate: DateTime): ArrayList<DateTime> {

    if (this.isAfter(endDate)) {
        return arrayListOf()
    }

    val output: ArrayList<DateTime> = arrayListOf()
    var currentDate = this
    do {
        output.add(currentDate)
        currentDate = currentDate.plusDays(1)
    } while (!currentDate.isAfter(endDate))

    return output

}