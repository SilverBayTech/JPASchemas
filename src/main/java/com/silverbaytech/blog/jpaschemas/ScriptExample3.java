/*
 * Copyright (c) 2016 kevin
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

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;

public class ScriptExample3
{
	public static void main(String[] args)
	{
		try
		{
			Map<String, Object> properties = new HashMap<>();

			StringWriter create = new StringWriter();

			properties.put("javax.persistence.schema-generation.scripts.action", "create");
			properties.put("javax.persistence.schema-generation.scripts.create-target", create);

			Persistence.generateSchema("script", properties);

			System.out.println("Create script:");
			pretty(create.toString());

			System.out.println("Done");

			System.exit(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void pretty(String unformatted)
	{
		StringReader lowLevel = new StringReader(unformatted);
		BufferedReader highLevel = new BufferedReader(lowLevel);
		DDLFormatterImpl formatter = new DDLFormatterImpl();

		highLevel.lines().forEach(x -> {
			String formatted = formatter.format(x + ";");
			System.out.println(formatted);
		});
	}
}
