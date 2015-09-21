/*
 * Copyright (c) 2015 Kevin Hunter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.silverbaytech.blog.jpaschemas;

import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.silverbaytech.blog.jpaschemas.model.Comment;
import com.silverbaytech.blog.jpaschemas.model.User;

public class DatabaseExample2
{
	private static final String DATABASE_FILE = "./target/databaseExample2.mv.db";
	
	public static void main(String[] args)
	{
		try
		{
			Map<String, Object> properties = new HashMap<>();
			
			Path databasePath = FileSystems.getDefault().getPath(DATABASE_FILE);
			if (!Files.exists(databasePath))
			{
				System.out.println("Database does not exist");
				properties.put("javax.persistence.schema-generation.database.action", "create");
				properties.put("javax.persistence.schema-generation.create-source", "script-then-metadata");
				properties.put("javax.persistence.schema-generation.create-script-source", "./src/main/sql/additionalCreate.sql");
			}
			else
			{
				System.out.println("Database already exists");
			}
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("database2", properties);
			
			EntityManager em = factory.createEntityManager();
			
			em.getTransaction().begin();
			
			User user = new User();
			user.setName("user" + System.currentTimeMillis());
			em.persist(user);
			
			Comment comment1 = new Comment();
			comment1.setText("A comment");
			comment1.setUser(user);
			user.addComment(comment1);
			
			Comment comment2 = new Comment();
			comment2.setText("Another comment");
			comment2.setUser(user);
			user.addComment(comment2);
			
			em.persist(comment1);
			em.persist(comment2);
			
			em.getTransaction().commit();
			
			em.getTransaction().begin();
			
			Query query1 = em.createQuery("select count(*) from " + User.class.getName());
			Long count1 = (Long)query1.getSingleResult();
			Query query2 = em.createQuery("select count(*) from " + Comment.class.getName());
			Long count2 = (Long)query2.getSingleResult();
			Query query3 = em.createNativeQuery("select count(*) from additional");
			BigInteger count3 = (BigInteger)query3.getSingleResult();
			
			em.getTransaction().commit();
			em.close();

			System.out.println(count1.toString() + " users");
			System.out.println(count2.toString() + " comments");
			System.out.println(count3.toString() + " additional entries");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
