package data

import java.util.*

data class Temperature(val temp: Double, val date: Date)

data class Windspeed(val north: Double, val west: Double, val date: Date)

data class Weather(val temp: Double, val north: Double, val west: Double, val date: Date)