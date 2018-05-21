package br.com.rodrigocardoso.utils;

/**
 * Created by rodri on 20/05/2018.
 */
public class Response<T> {
    public Integer status;
    public String description;
    public T response;

    public Response(Integer status, String description, T response) {
        this.status = status;
        this.description = description;
        this.response = response;
    }

    public Response() { }

    public void set(Integer status, String description, T response) {
        this.status = status;
        this.description = description;
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public Response setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Response setDescription(String description) {
        this.description = description;
        return this;
    }

    public T getResponse() {
        return response;
    }

    public Response setResponse(T response) {
        this.response = response;
        return this;
    }
}
