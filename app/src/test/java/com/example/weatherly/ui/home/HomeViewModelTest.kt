package com.example.weatherly.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherly.MainDispatcherRule
import com.example.weatherly.model.FakeRepository
import com.example.weatherly.utils.ApiState
import kotlinx.coroutines.test.pauseDispatcher
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class HomeViewModelTest{

    @get:Rule
    val instance = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fakeRepo : FakeRepository

    @Before
    fun setupStatisticsViewModel(){
        //init repo
        fakeRepo = FakeRepository()
        //init view model
        homeViewModel = HomeViewModel(fakeRepo)
    }




    @Test
    fun getStateFlowProducts_dataStates(){
        //pause dispatcher so i can validate initial values
        mainDispatcherRule.pauseDispatcher()
        //when -> api state = loading that means that data is still downloading and progress bar is active
        val res = homeViewModel.stateFlow.value
        //assert that it's still loading
        assertThat(res,`is`(ApiState.Loading))


    }


}