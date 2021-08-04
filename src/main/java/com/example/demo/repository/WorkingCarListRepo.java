package com.example.demo.repository;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class WorkingCarListRepo
{
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Map<String, Object>> getAll(String Id,String sToday,String sPreMon) {
		String SqlQuery ="select Distinct "
				+"				CarStatus=Convert(int,Case when ( :Today between c.DateB And c.DateE ) And c.Status=0 then 0 else 1 end)"
				+"				,LicensePlate = r.LicensePlate"
				+"				,DriverID = d.IdNum"
				+"				,DriverName = d.Name"
				+"				,DriverPhone = d.ContactPhone"
				+"				,PrincipalName = cust.Contact"
				+"				,PrincipalPhone = Case when Replace(isnull(cust.ContactPhone, ''), ' ', '') = '' then Replace(isnull(cust.ContactPhone2, ''), ' ', '') else Replace(isnull(cust.ContactPhone, ''), ' ', '') end"
				+"		from dbo.Contract c"
				+"		inner join dbo.Orders o on c.OrdersId=o.OrdersId"
				+"		inner join dbo.Repository r on c.ContractId=r.ContractId"
				+"		inner join dbo.Driver d on o.OrdersId=d.OrdersId"
				+"		inner join dbo.Custom cust on c.CustomId=cust.CustomId"
				+"		where :PreMon <= (Case when c.Status in(1,2) then r.EditDate else c.DateE End)"
				+"		And :Today >= c.DateB"
				+"		And :ID = cust.IdNum"
				+"		And c.Status >=0"
				+"		And r.Status>=0"
				+"		And c.Kind in ('B')";
		Query nativeQuery=(Query) entityManager.createNativeQuery(SqlQuery)
				.setParameter("ID", Id)
				.setParameter("Today", sToday)
				.setParameter("PreMon", sPreMon);
	      nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	      List<Map<String, Object>> resultList=nativeQuery.getResultList();
	      return resultList;
	}
}