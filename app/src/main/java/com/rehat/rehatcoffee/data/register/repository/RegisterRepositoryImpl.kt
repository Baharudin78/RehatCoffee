package com.rehat.rehatcoffee.data.register.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.register.remote.api.RegisterApi
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterRequest
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.data.register.remote.mapper.toRegisterEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.domain.register.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerApi: RegisterApi
) : RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>> {
        return flow {
            try {
                val response = registerApi.register(registerRequest)
                if (response.isSuccessful) {
                    val body = response.body()!!
                    val register = body.data.let { it!!.toRegisterEntity() }
                    emit(BaseResult.Success(register))
                } else {
                    val errorResponse = parseErrorResponse(response)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            null
                        )
                    )
                )
            }
        }
    }
}

private fun parseErrorResponse(response: Response<*>): WrappedResponse<RegisterResponse> {
    val type = object : TypeToken<WrappedResponse<RegisterResponse>>() {}.type
    val errorBody =
        response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
    val errorResponse =
        Gson().fromJson<WrappedResponse<RegisterResponse>>(errorBody.charStream(), type)!!
    errorResponse.status = response.code()
    return errorResponse
}