package com.raka.mockitotutorial.login.model

import com.google.gson.annotations.SerializedName
import com.raka.mockitotutorial.login.model.DataLoginResponse

data class LoginResponse(

    @field:SerializedName("data")
	val dataLoginResponse: DataLoginResponse? = null

)