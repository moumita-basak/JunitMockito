package com.infosys.junitmockito.repositories

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito

class ItemRepositoryTest {
    @Mock
    lateinit var repository: ItemRepository

    @Before
    fun setUp() {
    }

    @Test
    fun getData() {
        repository = Mockito.mock(ItemRepository::class.java)
        Mockito.`when`(repository.getData()).thenReturn("hello")
        assertEquals("hello",repository.getData())

//        Mockito.doReturn(repository.getData()).`when`(repository.getData())
//        assertEquals("hello",repository.getData())
    }
}