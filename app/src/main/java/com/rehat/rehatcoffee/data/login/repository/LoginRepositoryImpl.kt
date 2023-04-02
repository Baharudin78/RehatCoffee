package com.rehat.rehatcoffee.data.login.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.login.remote.api.LoginApi
import com.rehat.rehatcoffee.data.login.remote.dto.LoginRequest
import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import com.rehat.rehatcoffee.data.login.remote.mapper.toLoginEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.domain.login.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository{
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return flow {
            try {
                val response = loginApi.login(loginRequest)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    val login = body.data.let { it!!.toLoginEntity() }
                    emit(BaseResult.Success(login))
                } else {
                    val errorResponse = parseErrorResponse(response)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(WrappedResponse(message = e.message ?: "Error Occured", null)))
            }
        }
    }
}

private fun parseErrorResponse(response: Response<*>): WrappedResponse<LoginResponse> {
    val type = object : TypeToken<WrappedResponse<LoginResponse>>() {}.type
    val errorBody = response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
    val errorResponse = Gson().fromJson<WrappedResponse<LoginResponse>>(errorBody.charStream(), type)!!
    errorResponse.status = response.code()
    return errorResponse
}