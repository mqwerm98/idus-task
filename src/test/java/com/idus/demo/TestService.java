package com.idus.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.demo.dto.response.ResponseError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

@Service
public class TestService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isSuccess(MvcResult result) throws Exception {
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        return (Boolean) map.get("success");
    }

    public boolean isBadRequest(MvcResult result) throws Exception {
        ResponseError error = getResponseError(result);
        return error.getStatus() == HttpStatus.BAD_REQUEST.value();
    }

    public ResponseError getResponseError(MvcResult result) throws Exception {
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        return modelMapper.map(map.get("error"), ResponseError.class);
    }

    public Object getData(MvcResult result) throws Exception{
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        return map.get("data");
    }

}
