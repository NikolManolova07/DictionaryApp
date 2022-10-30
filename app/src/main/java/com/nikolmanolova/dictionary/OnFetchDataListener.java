package com.nikolmanolova.dictionary;

import com.nikolmanolova.dictionary.models.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
