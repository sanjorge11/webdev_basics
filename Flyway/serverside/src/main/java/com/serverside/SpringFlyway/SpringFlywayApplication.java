package com.serverside.SpringFlyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFlywayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFlywayApplication.class, args);
	}
	
	//Flyway is a tool that allows users to initialize new databases and upgrade existing ones to 
	//newer versions. It is used to automate database changes that accompanies software releases. 
	//For example, if a new release of the software depends on the addition of a new column to a 
	//table, the Flyway library will manage the automatic execution of SQL scripts to upgrade the
	//database to the newer version. 
	
	//Notice that when adding the Flyway dependency in pom.xml -- the /db/migration folder was added 
	//to the resources folder of the project. 
	
	//Flyway scripts are atomic, they will either succeed or rollback if failed. Each Flyway script 
	//follows a naming convention: 
	//		V(version number)__(name).sql
	//		V1__initialize.sql
	//		V1_0_0__initialize.sql
	
	//Each Flyway script must have unique versions, you cannot have two scripts with the same version 
	//number. The Flyway library will sort them and execute them one by one. The way it works is 
	//the following: 
	// 1. If you have an empty database, Flyway first looks for a schema history table. 
	//	  As the database is empty, Flyway won't find it and will create it instead.
	//    You now have a database with a single empty table called flyway_schema_history by default. 
	//	  This table will be used to track the state of the database.
	//
	// 2. Flyway begins scanning the filesystem or the classpath of the application for migrations. 
	//    They can be written in either SQL or Java.
	//
	// 3. The migrations are then sorted based on their version number and applied in order.
	//    As each migration gets applied, the schema history table is updated accordingly. 
	//
	//   When you migrate the database to a new version, Flyway once again scans the migration 
	//   scripts. The migrations are checked against the schema history table. If their version 
	//   number is lower or equal to the one of the version marked as current, they are ignored.
	//   The other higher versions are executed to perform the database migration. If the database 
	//   migration was unsuccessful, then the app will not startup so that you can fix those issues.
	
	
	//When running the Spring Boot app, the Flyway migration is executed. 
	/*
	  .   ____          _            __ _ _
	  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	 ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	   '  |____| .__|_| |_|_| |_\__, | / / / /
	  =========|_|==============|___/=/_/_/_/
	  :: Spring Boot ::        (v2.3.0.RELEASE)

	 2020-06-08 23:32:25.073  INFO 49573 --- [           main] c.s.S.SpringFlywayApplication            : Starting SpringFlywayApplication on Jorges-MBP.carolina.rr.com with PID 49573 (/Users/sanjorge/Projects/webdev_basics/Flyway/serverside/target/classes started by sanjorge in /Users/sanjorge/Projects/webdev_basics/Flyway/serverside)
	 2020-06-08 23:32:25.075  INFO 49573 --- [           main] c.s.S.SpringFlywayApplication            : No active profile set, falling back to default profiles: default
	 2020-06-08 23:32:25.941  INFO 49573 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
	 2020-06-08 23:32:25.949  INFO 49573 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
	 2020-06-08 23:32:25.949  INFO 49573 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
	 2020-06-08 23:32:26.008  INFO 49573 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
	 2020-06-08 23:32:26.008  INFO 49573 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 896 ms
	 2020-06-08 23:32:26.157  INFO 49573 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
	 2020-06-08 23:32:26.311  INFO 49573 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 6.4.1 by Redgate
	 2020-06-08 23:32:26.317  INFO 49573 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
	 2020-06-08 23:32:26.410  INFO 49573 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
	 2020-06-08 23:32:26.430  INFO 49573 --- [           main] o.f.c.internal.database.DatabaseFactory  : Database: jdbc:postgresql://localhost:5432/Flyway (PostgreSQL 12.2)
	 2020-06-08 23:32:26.453  INFO 49573 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.010s)
	 2020-06-08 23:32:26.461  INFO 49573 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Creating Schema History table "public"."flyway_schema_history" ...
	 2020-06-08 23:32:26.483  INFO 49573 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "public": << Empty Schema >>
	 2020-06-08 23:32:26.486  INFO 49573 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version 0 - initialize
	 2020-06-08 23:32:26.494  INFO 49573 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema "public" (execution time 00:00.014s)
	 2020-06-08 23:32:26.568  INFO 49573 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
	 2020-06-08 23:32:26.583  INFO 49573 --- [           main] c.s.S.SpringFlywayApplication            : Started SpringFlywayApplication in 1.815 seconds (JVM running for 2.056)
	 */
	
	//If you look at the postgres database, you'll see that the database migrations were applied,
	//and the flyway_schema_history table was created.
	
	
	//If you ran the Spring Boot app before adding V1__addLastname.sql to the /db//migration folder, and then ran
	//the app again after adding the script, you would've noticed that there was no "table already exists" error. 
	//Again, this is because Flyway scans through the list of migration scripts and compares them to the most 
	//recent version that is in flyway_schema_history. It will not reapply migration scripts that are less than 
	// or equal to the most recent version. The newest versions are the pending migrations, those are applied.
	
	/*
	  .   ____          _            __ _ _
	 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	  '  |____| .__|_| |_|_| |_\__, | / / / /
	 =========|_|==============|___/=/_/_/_/
	 :: Spring Boot ::        (v2.3.0.RELEASE)
	
	2020-06-08 23:41:31.057  INFO 49693 --- [           main] c.s.S.SpringFlywayApplication            : Starting SpringFlywayApplication on Jorges-MBP.carolina.rr.com with PID 49693 (/Users/sanjorge/Projects/webdev_basics/Flyway/serverside/target/classes started by sanjorge in /Users/sanjorge/Projects/webdev_basics/Flyway/serverside)
	2020-06-08 23:41:31.059  INFO 49693 --- [           main] c.s.S.SpringFlywayApplication            : No active profile set, falling back to default profiles: default
	2020-06-08 23:41:32.008  INFO 49693 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
	2020-06-08 23:41:32.016  INFO 49693 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
	2020-06-08 23:41:32.016  INFO 49693 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
	2020-06-08 23:41:32.080  INFO 49693 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
	2020-06-08 23:41:32.080  INFO 49693 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 985 ms
	2020-06-08 23:41:32.238  INFO 49693 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
	2020-06-08 23:41:32.403  INFO 49693 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 6.4.1 by Redgate
	2020-06-08 23:41:32.408  INFO 49693 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
	2020-06-08 23:41:32.488  INFO 49693 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
	2020-06-08 23:41:32.514  INFO 49693 --- [           main] o.f.c.internal.database.DatabaseFactory  : Database: jdbc:postgresql://localhost:5432/Flyway (PostgreSQL 12.2)
	2020-06-08 23:41:32.544  INFO 49693 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 2 migrations (execution time 00:00.015s)
	2020-06-08 23:41:32.549  INFO 49693 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "public": 0
	2020-06-08 23:41:32.555  INFO 49693 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema "public" to version 1 - addLastname
	2020-06-08 23:41:32.568  INFO 49693 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema "public" (execution time 00:00.022s)
	2020-06-08 23:41:32.643  INFO 49693 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
	2020-06-08 23:41:32.655  INFO 49693 --- [           main] c.s.S.SpringFlywayApplication            : Started SpringFlywayApplication in 1.934 seconds (JVM running for 2.172)
	 */
	
	
	// Note that if you update a migration script that was already applied in a previous Spring Boot startup,
	// restarting the app will no apply those additions. As far as the flyway_schema_history table knows, 
	// you're database is already up to date -- you can see that in the log below. If you want to add new 
	// additions to database you will need to add it to a new migration script with a higher version number,
	// or you could delete the entire database for all the migration scripts to run from scratch again. 
	
	/*
		  .   ____          _            __ _ _
	 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	  '  |____| .__|_| |_|_| |_\__, | / / / /
	 =========|_|==============|___/=/_/_/_/
	 :: Spring Boot ::        (v2.3.0.RELEASE)
	
	2020-06-08 23:48:26.542  INFO 49788 --- [           main] c.s.S.SpringFlywayApplication            : Starting SpringFlywayApplication on Jorges-MBP.carolina.rr.com with PID 49788 (/Users/sanjorge/Projects/webdev_basics/Flyway/serverside/target/classes started by sanjorge in /Users/sanjorge/Projects/webdev_basics/Flyway/serverside)
	2020-06-08 23:48:26.544  INFO 49788 --- [           main] c.s.S.SpringFlywayApplication            : No active profile set, falling back to default profiles: default
	2020-06-08 23:48:27.360  INFO 49788 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
	2020-06-08 23:48:27.368  INFO 49788 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
	2020-06-08 23:48:27.368  INFO 49788 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
	2020-06-08 23:48:27.433  INFO 49788 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
	2020-06-08 23:48:27.433  INFO 49788 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 850 ms
	2020-06-08 23:48:27.596  INFO 49788 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
	2020-06-08 23:48:27.752  INFO 49788 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 6.4.1 by Redgate
	2020-06-08 23:48:27.757  INFO 49788 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
	2020-06-08 23:48:27.835  INFO 49788 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
	2020-06-08 23:48:27.860  INFO 49788 --- [           main] o.f.c.internal.database.DatabaseFactory  : Database: jdbc:postgresql://localhost:5432/Flyway (PostgreSQL 12.2)
	2020-06-08 23:48:27.889  INFO 49788 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 2 migrations (execution time 00:00.015s)
	2020-06-08 23:48:27.895  INFO 49788 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "public": 1
	2020-06-08 23:48:27.895  INFO 49788 --- [           main] o.f.core.internal.command.DbMigrate      : Schema "public" is up to date. No migration necessary.
	2020-06-08 23:48:27.958  INFO 49788 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
	2020-06-08 23:48:27.970  INFO 49788 --- [           main] c.s.S.SpringFlywayApplication            : Started SpringFlywayApplication in 1.725 seconds (JVM running for 1.97)
	 */
	
	//For more details, visit: https://flywaydb.org/getstarted/how
	

}
