package com.carry.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;


public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)   
	        throws BeansException {   
	            try {   
	               String username = props.getProperty("jdbc.username");   
	                if (username != null) {   
	                    props.setProperty("user", new String(AESUtils.decrypt(Base64Utils.decode(username), AESUtils.KEY)));
	                    //props.setProperty("user", username);  
	                }   
	                String password = props.getProperty("jdbc.password");   
	               if (password != null) {   
	                    props.setProperty("password", new String(AESUtils.decrypt(Base64Utils.decode(password), AESUtils.KEY))); 
	                    //props.setProperty("password", password); 
	                }   
	                String url = props.getProperty("jdbc.url");   
	               if (url != null) {   
	                    props.setProperty("url", new String(AESUtils.decrypt(Base64Utils.decode(url), AESUtils.KEY)));  
	                   // props.setProperty("url", url); 
	                }   
	                String driverClassName = props.getProperty("jdbc.driverClassName");   
	               if(driverClassName != null){   
	                    props.setProperty("driver", new String(AESUtils.decrypt(Base64Utils.decode(driverClassName), AESUtils.KEY)));   
	                   // props.setProperty("driver", driverClassName); 
	               }   
	               String dialect = props.getProperty("hibernate.dialect");
	               if(dialect != null){
	            	   props.setProperty("dialect", new String(AESUtils.decrypt(Base64Utils.decode(dialect), AESUtils.KEY)));   
	               }
	               String show_sql = props.getProperty("hibernate.show_sql");
	               if(show_sql != null){
	            	   props.setProperty("show_sql", new String(AESUtils.decrypt(Base64Utils.decode(show_sql), AESUtils.KEY)));   
	               }
	                super.processProperties(beanFactory, props);   
	           } catch (Exception e) {   
	                throw new BeanInitializationException(e.getMessage());   
	            }   
	        }
}
