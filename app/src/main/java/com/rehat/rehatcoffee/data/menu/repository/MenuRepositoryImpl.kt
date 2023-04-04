package com.rehat.rehatcoffee.data.menu.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.menu.remote.api.MenuApi
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.data.menu.remote.mapper.toImageMenuEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi
) : MenuRepository {
    override suspend fun getMenuFood(): Flow<BaseResult<List<MenuEntity>, WrappedListResponse<MenuResponse>>> {
        return flow {
            val response = menuApi.getMenuFood()
            if (response.isSuccessful){
                val body = response.body()!!
                val menuFood = mutableListOf<MenuEntity>()
                body.data?.forEach { menuResponse ->
                    menuFood.add(
                        MenuEntity(
                            menuResponse.id,
                            menuResponse.description,
                            menuResponse.images.map { it?.toImageMenuEntity() },
                            menuResponse.price,
                            menuResponse.productName
                        )
                    )
                }
                emit(BaseResult.Success(menuFood))
            }else{
                val type = object : TypeToken<WrappedListResponse<MenuResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<MenuResponse>>(response.errorBody()!!.charStream(), type)!!
                err.status = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }

    override suspend fun getMenuDrink(): Flow<BaseResult<List<MenuEntity>, WrappedListResponse<MenuResponse>>> {
        return flow {
            val response = menuApi.getMenuDrink()
            if (response.isSuccessful){
                val body = response.body()!!
                val menuDrink = mutableListOf<MenuEntity>()
                body.data?.forEach { menuResponse ->
                    menuDrink.add(
                        MenuEntity(
                            menuResponse.id,
                            menuResponse.description,
                            menuResponse.images.map { it?.toImageMenuEntity() },
                            menuResponse.price,
                            menuResponse.productName
                        )
                    )
                }
                emit(BaseResult.Success(menuDrink))
            }else{
                val type = object : TypeToken<WrappedListResponse<MenuResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<MenuResponse>>(response.errorBody()!!.charStream(), type)!!
                err.status = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }


}