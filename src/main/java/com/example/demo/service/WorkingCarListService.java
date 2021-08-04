package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.WorkingCarListRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.CarList;
import com.example.demo.model.Principal;
import com.example.demo.model.WorkingCarListRequest;
import com.example.demo.model.WorkingCarListViewModel;
import com.example.demo.Lib;

@Service
public class WorkingCarListService {

	@Autowired
	Lib lib;

	@Autowired
	WorkingCarListRepo repo;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	public WorkingCarListService(WorkingCarListRepo repo) {
		this.repo = repo;
	}

	public WorkingCarListViewModel getAllRepo(WorkingCarListRequest Req) {

		// 取得SqlQuery資料放到MapList裡
		String sId = Req.ID;
		String sPhone = lib.GetOnlyNumber(Req.PhoneNumber);
		String sToday = lib.GetTWDate(LocalDate.now());
		String sPreMon = lib.GetTWDate(LocalDate.now().minusMonths(1L));
		List<Map<String, Object>> d = repo.getAll(sId, sToday, sPreMon);
		
		// 宣告ViewModelCalss
		WorkingCarListViewModel vm = new WorkingCarListViewModel();

		// 檢查電話=駕駛人或聯絡人，才有資料，否則回傳null
		if (d.stream().filter(w -> lib.GetOnlyNumber(w.get("PrincipalPhone").toString()).equals(sPhone)
				|| lib.GetOnlyNumber(w.get("DriverPhone").toString()).equals(sPhone)).count() > 0) {
			// Mapping資料到ViewModel
			// 角色分類
			vm.Role = (lib.GetOnlyNumber(d.get(0).get("PrincipalPhone").toString()).equals(sPhone)) ? 1 : 0;
			// 負責人(聯絡人)
			Principal p = new Principal();
			p.Name = d.get(0).get("PrincipalName").toString();
			p.PhoneNumber = d.get(0).get("PrincipalPhone").toString();
			vm.Principal = p;
			// 駕駛人
			List<CarList> cl = new ArrayList<>();
			for (Map<String, Object> m : d) {
				CarList c = new CarList();
				c.LicensePlate = m.get("LicensePlate").toString();
				c.DriverID = m.get("DriverID").toString();
				c.DriverName = m.get("DriverName").toString();
				c.DriverPhone = m.get("DriverPhone").toString();
				c.CarStatus = Integer.parseInt(m.get("CarStatus").toString());
				cl.add(c);
			}
			vm.CarList = cl;
		} else {
			vm = null;
		}
		return vm;
	}
}