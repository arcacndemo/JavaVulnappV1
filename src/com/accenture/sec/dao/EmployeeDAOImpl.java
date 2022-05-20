package com.accenture.sec.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accenture.sec.business.bean.EmployeeBean;
import com.accenture.sec.business.bean.EmployeeSearchBean;
import com.accenture.sec.entity.EmployeeEntity;

@Repository
//@Transactional(value="txManager")
public class EmployeeDAOImpl implements EmployeeDAO{

        @Autowired
        EntityManagerFactory entityManagerFactory;
        /*@PersistenceContext
        EntityManager em;*/
        @Override
        public EmployeeBean addEmployee(EmployeeBean bean) {
                EntityManager em=entityManagerFactory.createEntityManager();
                EmployeeEntity entity = new EmployeeEntity();
                BeanUtils.copyProperties(bean, entity);
                em.getTransaction().begin();
                em.persist(entity);
                em.getTransaction().commit();
                return bean;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Map<Integer, String> getDepartmentMap() throws Exception{

                Map<Integer, String> departmentMap = new HashMap<Integer, String>();

                EntityManager em=entityManagerFactory.createEntityManager();

                try {
                String hql ="SELECT id,name from DepartmentEntity";
                TypedQuery query = (TypedQuery) em.createQuery(hql);
                List<Object[]> res  =  query.getResultList();

                Iterator it = res.iterator();
                while(it.hasNext()){
                        Object[] dept = (Object[]) it.next();
                        departmentMap.put((Integer) dept[0], (String) dept[1]);
                }
                } catch(Exception e) {
                        throw e;
                }


                return departmentMap;
        }

        @Override
        public List<EmployeeBean> getEmployeeList(EmployeeSearchBean employeeSearch) {

                List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();

                EntityManager em=entityManagerFactory.createEntityManager();

                StringBuilder whereCond = new StringBuilder();
                HashMap<String,String> paramListIN00154 = new HashMap();
                String hql= null;
                boolean isCondCheck=false;

            Optional<EmployeeSearchBean> optSearch = Optional.ofNullable(employeeSearch);

                if(optSearch.isPresent() && employeeSearch.getEmpName()!=null && !employeeSearch.getEmpName().isEmpty()) {
                        isCondCheck = true;
                        // *******************************************************************************
                        // Changed by Accenture Auto-remediation service according to Scan: EmployeeVulnApp_S1 , 
                        // Vulnerability ID: IN00154
                        // Strategy in use: Parametrized query for Createquery(Query String) - concatenated string
                        // *******************************************************************************
                        hql ="SELECT empId,empName,city,gender,salary from EmployeeEntity where empName like '%" + " :remediation1" + "%'";
                        paramListIN00154.put("remediation1",employeeSearch.getEmpName());
                        //hql ="SELECT empId,empName,city,gender,salary from EmployeeEntity where empName like %"; //append where condition
                } else {
                        hql = "SELECT empId,empName,city,gender,salary from EmployeeEntity";
                }


                // *******************************************************************************
                // Changed by Accenture Auto-remediation service according to Scan: EmployeeVulnApp_S1 , 
                // Vulnerability ID: IN00154
                // Strategy in use: Parametrized query
                // *******************************************************************************
                EntityManager entityManagerIN00154 = null;
                entityManagerIN00154 = em;
                TypedQuery query = (TypedQuery) entityManagerIN00154.createQuery(hql);
                for(HashMap.Entry<String,String> entry : paramListIN00154.entrySet()){
                	query.setParameter(entry.getKey(), entry.getValue());
                }
                /*if(isCondCheck) {
                        query.setParameter("name", "%" +employeeSearch.getEmpName() +"%");
                }*/
                List<Object[]> res  =  query.getResultList();

                Iterator it = res.iterator();
                while(it.hasNext()){
                        Object[] emp = (Object[]) it.next();
                        EmployeeBean employeeDtl = new EmployeeBean();
                        employeeDtl.setEmpId((Integer)emp[0]);
                        employeeDtl.setEmpName((String)emp[1]);
                        employeeDtl.setCity((String)emp[2]);
                        employeeDtl.setGender((String)emp[3]);
                        employeeDtl.setSalary((Double)emp[4]);

                        employeeList.add(employeeDtl);
                }

                return employeeList;

        }


}
