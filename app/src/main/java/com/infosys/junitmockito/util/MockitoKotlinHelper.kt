package com.infosys.junitmockito.util

import org.mockito.ArgumentCaptor

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()