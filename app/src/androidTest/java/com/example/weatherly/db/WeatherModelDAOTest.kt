package com.example.weatherly.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherly.model.Current
import com.example.weatherly.model.WeatherModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherModelDAOTest {

    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var dao: WeatherModelDAO
    private lateinit var current: Current
    private lateinit var weatherModel: WeatherModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = weatherDatabase.getWeatherModelDao()
        current = Current(2, .5, 5, .5, 10, 10, 10, 10, .5, .5, 5, listOf(),5,.5,.5)
        weatherModel = WeatherModel(current = current, daily =  listOf(), hourly =  listOf(), lat = .5,lon=.5, timezone = "timeZone", timezone_offset = 5)
    }

    @After
    fun teardown() {
        weatherDatabase.close()
    }

    @Test
    fun itemInserted_itemRetrieved_itemExists() = runBlockingTest{
        //when
        weatherDatabase.getWeatherModelDao().insertOrUpdate(weatherModel)

        val returnedWeatherModel = weatherDatabase.getWeatherModelDao().getLocationWeather(weatherModel.id)

//then ->check the task isn't empty
        assertThat(returnedWeatherModel, CoreMatchers.notNullValue())
        //then -> assert it's the same task i inserted
        assertThat(returnedWeatherModel.id, `is`(weatherModel.id))
    }

    @Test
    fun twoItems_dontHaveTheSameId_true() = runBlockingTest{
        val myWeatherModel = WeatherModel( current = current, daily = listOf(), hourly = listOf(), lat = .5, lon = .5, timezone = "something", timezone_offset = 5)

        weatherDatabase.getWeatherModelDao().insertOrUpdate(weatherModel)
        weatherDatabase.getWeatherModelDao().insertOrUpdate(myWeatherModel)

        assertThat(myWeatherModel.id, not(weatherModel.id))
    }





//    @Test
//    fun itemInserted_itemUpdated
}