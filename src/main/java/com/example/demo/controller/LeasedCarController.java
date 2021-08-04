package com.example.demo.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.ApiResult;
import com.example.demo.model.WorkingCarListRequest;
import com.example.demo.model.WorkingCarListViewModel;
import com.example.demo.service.WorkingCarListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class LeasedCarController {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WorkingCarListService service;

	@RequestMapping("/LeasedCar/GetWorkingCarList")
	public @ResponseBody String GetWorkingCarList(@RequestBody WorkingCarListRequest Req) throws IOException {
		ApiResult<WorkingCarListViewModel> ret = new ApiResult<WorkingCarListViewModel>();
		String strJson = "";
		try {

			WorkingCarListViewModel Data = service.getAllRepo(Req);
			strJson = mapper.writeValueAsString((Data == null ? ret.ApiNotFound() : ret.ApiSuccess(Data)));
		} catch (Exception ex) {
			strJson = mapper.writeValueAsString(ret.ApiOtherErr("500", "伺服器回傳錯誤"));
		}
		return strJson;
	}
}