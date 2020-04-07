package com.gproduction.yuklapor.data

data class Resource<out T>(val status:Status,val data:T?,val message:String?,val exception: java.lang.Exception?){
    companion object{
        fun <T> success(data:T?):Resource<T>{
            return Resource(Status.SUCCESS,data,null, null)
        }

        fun<T> error(msg:String,data:T?) :Resource<T>{
            return Resource(Status.ERROR, data, msg, null)
        }

        fun<T> errorThrowable(exception: java.lang.Exception?, data:T?) :Resource<T>{
            return Resource(Status.ERRORTHROWABLE,data,null,exception)
        }
    }
}