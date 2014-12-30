package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Employee;
@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao implements EmployeeDao{

	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		persist(employee);
	}

	public List<Employee> findAllEmployees() {
		// TODO Auto-generated method stub
		Criteria criteria =getSession().createCriteria(Employee.class);
		
		return criteria.list();
	}

	public void deleteEmployeeBySsn(String ssn) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery("delete from Employee where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}

	public Employee findBySsn(String ssn) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Employee.class);
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Employee)criteria.uniqueResult();
	}

	public void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		getSession().update(employee);
	}

}
