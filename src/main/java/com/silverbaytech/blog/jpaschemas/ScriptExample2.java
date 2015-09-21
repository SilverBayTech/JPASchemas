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

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

public class ScriptExample2
{
	public static void main(String[] args)
	{
		try
		{
			Map<String, Object> properties = new HashMap<>();

			StringWriter create = new StringWriter();
			StringWriter drop = new StringWriter();

			properties.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
			properties.put(	"javax.persistence.schema-generation.scripts.create-target",
							create);
			properties.put(	"javax.persistence.schema-generation.scripts.drop-target",
							drop);

			Persistence.generateSchema("script", properties);
			
			System.out.println("Create script:");
			System.out.println(create.toString());
			
			System.out.println("Drop script:");
			System.out.println(drop.toString());

			System.out.println("Done");

			System.exit(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			;
		}
	}
}
