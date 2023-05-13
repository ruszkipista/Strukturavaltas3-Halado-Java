run the application with
```bash
mvn clean package jetty:run
```
check the created web page on URL
```URL
http://localhost:8080/
```

result:

![Hello World <TimeStamp>](/doc/hello-world.png)

Terminal output:
```log
$ mvn clean package jetty:run
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< microservices.training:employees-spring >---------------
[INFO] Building employees-spring 1.0-SNAPSHOT
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ employees-spring ---
[INFO] Deleting .../demo01-spring/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ employees-spring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory .../demo01-spring/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ employees-spring ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 3 source files to .../demo01-spring/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ employees-spring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory .../demo01-spring/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ employees-spring ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ employees-spring ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-war-plugin:3.3.2:war (default-war) @ employees-spring ---
[INFO] Packaging webapp
[INFO] Assembling webapp [employees-spring] in [.../demo01-spring/target/employees-spring-1.0-SNAPSHOT]
[INFO] Processing war project
[INFO] Building war: .../demo01-spring/target/employees-spring-1.0-SNAPSHOT.war
[INFO] 
[INFO] >>> jetty-maven-plugin:11.0.14:run (default-cli) > test-compile @ employees-spring >>>
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ employees-spring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory .../demo01-spring/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ employees-spring ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ employees-spring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory .../demo01-spring/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ employees-spring ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] <<< jetty-maven-plugin:11.0.14:run (default-cli) < test-compile @ employees-spring <<<
[INFO] 
[INFO] 
[INFO] --- jetty-maven-plugin:11.0.14:run (default-cli) @ employees-spring ---
[INFO] Configuring Jetty for project: employees-spring
[INFO] Classes = .../demo01-spring/target/classes
[INFO] Context path = /
[INFO] Tmp directory = .../demo01-spring/target/tmp
[INFO] web.xml file = null
[INFO] Webapp directory = .../demo01-spring/target/webapp-synth
[INFO] Web defaults = org/eclipse/jetty/webapp/webdefault.xml
[INFO] Web overrides =  none
[INFO] jetty-11.0.14; built: 2023-02-22T23:41:48.575Z; git: 4601fe8dd805ce75b69c64466c115a162586641b; jvm 17.0.4+11-LTS-179
[INFO] 1 Spring WebApplicationInitializers detected on classpath
[INFO] Session workerName=node0
[INFO] Initializing Spring DispatcherServlet 'dispatcher'
[INFO] Initializing Servlet 'dispatcher'
[INFO] Completed initialization in 440 ms
[INFO] Started o.e.j.m.p.MavenWebAppContext@6de43bc1{/,[file://.../demo01-spring/target/webapp-synth/],AVAILABLE}{file://.../demo01-spring/target/webapp-synth/}
[INFO] Started ServerConnector@1eb6037d{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
[INFO] Started Server@6cbb7a7d{STARTING}[11.0.14,sto=0] @3767ms
[INFO] Automatic redeployment disabled, see 'mvn jetty:help' for more redeployment options
```