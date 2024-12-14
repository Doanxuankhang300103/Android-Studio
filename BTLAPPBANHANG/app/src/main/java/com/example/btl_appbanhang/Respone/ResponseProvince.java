package com.example.btl_appbanhang.Respone;

public class ResponseProvince <T> {
//    error: 0,
//    error_text: "Lấy dữ liệu tỉnh thành thành công..!",
//    data_name: "Tỉnh thành Việt Nam",
//    data:
    private int error;
    private String error_text;
    private String data_name;
    private T data;

    public ResponseProvince() {
    }

    public ResponseProvince(int error, String error_text, String data_name, T data) {
        this.error = error;
        this.error_text = error_text;
        this.data_name = data_name;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getError_text() {
        return error_text;
    }

    public void setError_text(String error_text) {
        this.error_text = error_text;
    }

    public String getData_name() {
        return data_name;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
