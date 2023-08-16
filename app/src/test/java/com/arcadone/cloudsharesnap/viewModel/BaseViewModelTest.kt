package com.arcadone.cloudsharesnap.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTest<VM : ViewModel> {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: VM

    abstract fun setupTest()

    protected val exception = Exception()

    inline fun <reified T : Any> verifyObserverNotCalled(observer: Observer<T>) {
        val captor = ArgumentCaptor.forClass(T::class.java)

        Mockito.verify(observer, Mockito.times(0)).onChanged(captor.capture())
    }

    inline fun <reified T : Any> verifyObserver(
        observer: Observer<T>,
        vararg values: T
    ): T {
        val valuesToCheck: List<T> = values.toList()

        val captor = ArgumentCaptor.forClass(T::class.java)

        Mockito.verify(
            observer,
            Mockito.times(valuesToCheck.size)
                .description("Captor values ${captor.allValues}")
        ).onChanged(captor.capture())

        valuesToCheck.forEachIndexed { index, value ->
            assertThat(captor.allValues[index], equalTo(value))
        }

        return captor.allValues.last()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        setupTest()
    }
}
