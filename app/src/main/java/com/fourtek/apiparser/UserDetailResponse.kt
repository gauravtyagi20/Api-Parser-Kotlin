package com.fourtek.apiparser

import com.google.gson.Gson

/**
 * Created by fourtek on 28/2/18.
 */
class UserDetailResponse{
    private var code: Int = 0
    private var msg: String? = null
    private var info: Any? = null


    fun getInfo(): Any {
        return Gson().toJson(info)
    }

    fun setInfo(info: String) {
        this.info = info
    }






    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }





}