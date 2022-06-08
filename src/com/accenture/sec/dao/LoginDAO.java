package com.accenture.sec.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accenture.sec.business.bean.LoginBean;
import com.accenture.sec.business.bean.UserAccountBean;
import com.accenture.sec.business.bean.UserProfileForm;
import com.accenture.sec.entity.UserAccountEntity;
import com.accenture.sec.entity.UserProfileEntity;
import java.util.HashMap;

@Repository
public class LoginDAO {
        @Autowired
        EntityManagerFactory entityManagerFactory;
        private static final Logger logger = LoggerFactory.getLogger(LoginDAO.class);

        public String validateLogin(LoginBean loginBean){

                // *******************************************************************************
                // Changed by Accenture Auto-remediation service according to Scan: EmployeeVulnApp_S1 , 
                // Vulnerability ID: IN00096
                // Strategy in use: Comment the variable declaration that contains value never read.
                // *******************************************************************************
                //EntityManager em=entityManagerFactory.createEntityManager();
                

                String uName = loginBean.getUserName();
                String password = loginBean.getPassword();

        HashMap<String,String> paramListIN00153 = new HashMap();
        // *******************************************************************************
        // Changed by Accenture Auto-remediation service according to Scan: EmployeeVulnApp_S1 ,
        // Vulnerability ID: IN00153
        // Strategy in use: Parametrized query for Createquery(Query String) - concatenated string
        // *******************************************************************************
        String hql ="SELECT pwd,roleID from UserAccountEntity WHERE userName='" + " :remediation1" + "'";
        paramListIN00153.put("remediation1",uName);

//      String hql ="SELECT pwd,roleID from UserAccountEntity WHERE userName = :name"; // Approach1

        // Approach 2

                /*
                 * CriteriaBuilder cb = em.getCriteriaBuilder();
                 * CriteriaQuery<UserAccountEntity> cr =
                 * cb.createQuery(UserAccountEntity.class); Root<UserAccountEntity> root =
                 * cr.from(UserAccountEntity.class);
                 * cr.select(root).where(cb.equal(root.get("userName"), uName));
                 *
                 *
                 * TypedQuery<UserAccountEntity> query = em.createQuery(cr);
                 * List<UserAccountEntity> results = query.getResultList();
                 */



          // *******************************************************************************
          // Changed by Accenture Auto-remediation service according to Scan: EmployeeVulnApp_S1 ,
          // Vulnerability ID: IN00153
          // Strategy in use: Parametrized query
          // *******************************************************************************
          EntityManager entityManagerIN00153 = null;
          entityManagerIN00153 = em;
          TypedQuery query = (TypedQuery) entityManagerIN00153.createQuery(hql);
          for(HashMap.Entry<String,String> entry : paramListIN00153.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
          }
          //query.setParameter("name", uName);
          List<Object[]> results = query.getResultList();


                if (!results.isEmpty()) {
                        Iterator it = results.iterator();
                        while (it.hasNext()) {
                                Object[] user = (Object[]) it.next();

                                if (password.equals((String) user[0])) {
                                        loginBean.setRoleID((String) user[1]);
                                        return "success";
                                } else {
                                        return "failure";
                                }
                        }
                } else {
                        return "failure";
                }

                return "failure";
        }

        //Issue 148 & 24
        public List<UserAccountBean> listUser() throws SQLException {

                List<UserAccountBean> listUsers = new ArrayList<UserAccountBean>();

                EntityManager em=entityManagerFactory.createEntityManager();
                Connection conn = null;
                FileNotFoundException exception =null;


                try (InputStream input = LoginDAO.class.getResourceAsStream("/com/accenture/sec/resources/sec_conn.properties")) {
                //File absolute path given, absolute path should begin with /

                        Properties prop = new Properties();
                        prop.load(input);
                        String dbURL = prop.getProperty("sec_db_url");
                        String dbuser = prop.getProperty("sec_user");
                        String dbpwd = prop.getProperty("sec_password");

                        conn = DriverManager.getConnection(dbURL,dbuser , dbpwd);

                                Statement stmt = conn.createStatement();

                                // String hql ="SELECT userName,pwd,roleID from UserAccountEntity";
                                String sql = "SELECT Username, Password, RoleID from account";

                                ResultSet rs = stmt.executeQuery(sql);

                                while (rs.next()) {
                                        UserAccountBean userAccount = new UserAccountBean();

                                        userAccount.setUserName(rs.getString("Username"));
                                        userAccount.setPwd(rs.getString("Password"));
                                        userAccount.setRoleID(rs.getString("RoleID"));

                                        listUsers.add(userAccount);
                                }

                                conn.close();

                /*
                 * TypedQuery query = (TypedQuery) em.createQuery(hql); List<Object[]> res =
                 * query.getResultList();
                 *
                 * Iterator it = res.iterator(); while(it.hasNext()){ Object[] user = (Object[])
                 * it.next(); UserAccountBean userAccount = new UserAccountBean();
                 * userAccount.setUserName((String)user[0]);
                 * userAccount.setPwd((String)user[1]); userAccount.setRoleID((String)user[2]);
                 *
                 * listUsers.add(userAccount); }
                 */

                } catch (FileNotFoundException e) {
                        exception= e;
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                finally {

                        /*
                         * if (conn != null) { if(exception!=null) { try { conn.close(); } catch
                         * (SQLException e) {
                         * logger.debug("Exception occurred while closing connection."+e.getMessage());
                         * exception.addSuppressed(e); } } else { conn.close(); } }
                         */
                }


                return listUsers;
        }

        public UserAccountBean addUser(UserAccountBean userAccountBean) {

                EntityManager em=entityManagerFactory.createEntityManager();
                UserAccountEntity entity = new UserAccountEntity();
                BeanUtils.copyProperties(userAccountBean, entity);


                em.getTransaction().begin();
                em.persist(entity);
                em.getTransaction().commit();

                return userAccountBean;
        }


          public UserProfileForm addUserProfile(UserProfileForm userProfile) {

          EntityManager em=entityManagerFactory.createEntityManager();

          UserProfileEntity entity = new UserProfileEntity();
          BeanUtils.copyProperties(userProfile, entity);

          em.getTransaction().begin(); em.persist(entity);
          em.getTransaction().commit();

          return userProfile;
          }


}
