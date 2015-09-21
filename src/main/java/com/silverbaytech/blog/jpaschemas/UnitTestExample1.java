/*
 *  Copyright (c) 2015 Kevin Hunter
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */

package com.silverbaytech.blog.jpaschemas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.silverbaytech.blog.jpaschemas.model.Comment;
import com.silverbaytech.blog.jpaschemas.model.User;

public class UnitTestExample1
{
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	@Before
	public void setup()
	{
		entityManagerFactory = Persistence.createEntityManagerFactory("unit-test1");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@After
	public void tearDown()
	{
		if (entityManager != null)
		{
			if (entityManager.isOpen())
			{
				entityManager.close();
			}
		}
		
		if (entityManagerFactory != null)
		{
			if (entityManagerFactory.isOpen())
			{
				entityManagerFactory.close();
			}
		}
	}
	
	private long getCount(Class<?> clazz)
	{
		entityManager.getTransaction().begin();
		Long result = (Long)entityManager.createQuery("select count(*) from " + clazz.getName()).getSingleResult();
		entityManager.getTransaction().commit();
		return result;
	}
	
	@Test
	public void databaseStartsEmpty()
	{
		assertThat(getCount(User.class), equalTo(0L));
		assertThat(getCount(Comment.class), equalTo(0L));
	}
	
	@Test
	public void canInsertItems()
	{
		entityManager.getTransaction().begin();
		
		User user = new User();
		user.setName("user" + System.currentTimeMillis());
		entityManager.persist(user);
		
		Comment comment1 = new Comment();
		comment1.setText("A comment");
		comment1.setUser(user);
		user.addComment(comment1);
		
		Comment comment2 = new Comment();
		comment2.setText("Another comment");
		comment2.setUser(user);
		user.addComment(comment2);
		
		entityManager.persist(comment1);
		entityManager.persist(comment2);
		
		entityManager.getTransaction().commit();
		
		assertThat(getCount(User.class), equalTo(1L));
		assertThat(getCount(Comment.class), equalTo(2L));
	}
}

