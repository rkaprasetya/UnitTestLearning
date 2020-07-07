package com.raka.mockitotutorial.user

import com.raka.mockitotutorial.user.model.ApiUser

interface ApiHelper {
    suspend fun getUsers():List<ApiUser>
}