package com.example.weatherly.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherly.MainDispatcherRule
import com.example.weatherly.model.Current
import com.example.weatherly.model.WeatherModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LocalSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var localSource: ConcreteLocalSource
    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var current: Current
    private lateinit var  myWeatherModel : WeatherModel

    @Before
    fun setupDatabase() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        localSource = ConcreteLocalSource.getInstance(ApplicationProvider.getApplicationContext())
        current = Current(2, .5, 5, .5, 10, 10, 10, 10, .5, .5, 5, listOf(),5,.5,.5)
         myWeatherModel = WeatherModel(1,current = current, daily =  listOf(), hourly =  listOf(), lat = .5,lon=.5, timezone = "timeZone", timezone_offset = 5)

    }

    @After
    fun teardown(){
        weatherDatabase.close()
    }

    @Test
    fun itemInserted_itemRetrieved_itemExists() = runBlockingTest{
        //given
        val myWeatherModel = WeatherModel(1,current = current, daily =  listOf(), hourly =  listOf(), lat = .5,lon=.5, timezone = "timeZone", timezone_offset = 5)

        //when -> inserted
        localSource.insertOrUpdate(myWeatherModel)

        //then -> should be inserted and it matches the same id
        val returnedWeatherModel = localSource.getStoredLocationData(myWeatherModel.id)

        //then ->check the task isn't empty
        assertThat(returnedWeatherModel, notNullValue())
        //then -> assert it's the same task i inserted
        assertThat(returnedWeatherModel.id, CoreMatchers.`is`(myWeatherModel.id))
    }

    @Test
    fun itemInserted_itemDeleted_itemDoesntExist() = runBlockingTest {
        //when -> inserted then deleted
        localSource.insertOrUpdate(myWeatherModel)
        //assert it was inserted
        assertThat(localSource.getStoredLocationData(myWeatherModel.id), notNullValue())

        //then -> deleted it's not in the list
        val result = localSource.getStoredWeatherData().first()
        assertThat(result, not(contains(myWeatherModel)))
    }

    @Test
    fun updateTask_getById_taskUpdated() = runBlockingTest {
        //given -> inserted object
        localSource.insertOrUpdate(myWeatherModel)
        //assert it was inserted
        assertThat(localSource.getStoredLocationData(myWeatherModel.id), notNullValue())

        //when -> update that object
        val myTimeZone = "Summer time"
        myWeatherModel.timezone = myTimeZone
        localSource.insertOrUpdate(myWeatherModel)

        //then -> its values in database should be updated
        val result = localSource.getStoredLocationData(myWeatherModel.id)
        assertThat(result.timezone, `is`(myTimeZone) )
    }





}

