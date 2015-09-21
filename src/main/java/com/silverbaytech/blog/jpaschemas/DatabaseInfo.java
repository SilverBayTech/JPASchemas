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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;


public class DatabaseInfo
{
	public static void main(String[] args)
	{
		System.out.println("Getting metadata for : " + args[0]);
		
		try
		{
			Connection connection = DriverManager.getConnection(args[0]);
			DatabaseMetaData metaData = connection.getMetaData();
			
			System.out.println("javax.persistence.database-production-name=" + metaData.getDatabaseProductName());
			System.out.println("javax.persistence.database-major-version=" + metaData.getDatabaseMajorVersion());
			System.out.println("javax.persistence.database-minor-version=" + metaData.getDatabaseMinorVersion());
			
			connection.close();
		}
		catch(Exception e)
		{
		}
		
		System.exit(0);
	}
}
