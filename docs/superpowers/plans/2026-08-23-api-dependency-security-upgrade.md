# Jewix API Dependency Security Upgrade Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Remove the 23 open Dependabot findings in `Jewix-API/pom.xml` while preserving Java 8, Spring Boot 2.x, API contracts, authentication behavior, and development Swagger documentation.

**Architecture:** Keep the existing Spring Boot application structure and service code. Centralize dependency versions in the Maven parent/BOM, replace direct Fastjson usage with Jackson, upgrade Hutool and commons-lang3, and replace the Springfox configuration with Springdoc OpenAPI 1.x. Verification combines source-level dependency checks, Maven tests, clean packaging, and an application-context/OpenAPI smoke check.

**Tech Stack:** Java 8, Maven, Spring Boot 2.7.18, Springdoc OpenAPI 1.8.0, Jackson, Hutool 5.8.47, commons-lang3 3.20.0, JUnit 5.

---

### Task 1: Capture Maven baseline and dependency graph

**Files:**
- Read: `Jewix-API/pom.xml`
- Read: `Jewix-API/src/main/java/com/aomsir/jewixapi/config/Swagger2Configuration.java`
- Read: `Jewix-API/src/test/java/com/aomsir/jewixapi/JewixApiApplicationTests.java`

- [ ] **Step 1: Record the current toolchain and dependency graph**

Run:

```bash
cd Jewix-API
java -version
mvn -version
mvn dependency:tree -DoutputFile=target/dependency-tree-before.txt
```

Expected: the output confirms Java 8 compatibility and the graph contains Springfox, `fastjson:1.2.49`, Jackson `2.12.1`, Hutool `5.8.12`, and `commons-lang3:3.12.0`.

- [ ] **Step 2: Inventory direct Fastjson usage**

Run:

```bash
rg -n "com\.alibaba\.fastjson|JSON\.parse|JSONObject|JSONArray" src/main src/test || true
```

Expected: record every direct usage before choosing the replacement. The current inventory includes `OperateLogAspect` JSON serialization and `RedisConfiguration`'s `GenericFastJsonRedisSerializer`; both must be migrated to Jackson before Fastjson is removed.

- [ ] **Step 3: Commit the baseline only if it changes tracked files**

Do not commit generated `target/` output. Continue without a commit when the baseline is read-only.

### Task 2: Upgrade the Maven dependency baseline

**Files:**
- Modify: `Jewix-API/pom.xml:6-28,119-159,179-184`

- [ ] **Step 1: Update the parent and dependency properties**

Change the parent to Spring Boot `2.7.18`, retain `<java.version>1.8</java.version>`, set `hutool.version` to `5.8.47`, and set the explicit `commons-lang3` version to `3.20.0`. Do not add duplicate versions for modules already managed by the Spring Boot parent.

- [ ] **Step 2: Remove unsafe or obsolete declarations**

Remove after migrating the direct usages in Task 2 Step 3:

```xml
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.49</version>
</dependency>
```

Remove the explicit Jackson `2.12.1` version and keep Jackson aligned through the parent/BOM or one explicit Jackson BOM override. Remove both Springfox dependencies; Springdoc is added in Task 3.

- [ ] **Step 3: Migrate direct Fastjson usage to Jackson**

Replace `OperateLogAspect`'s `JSON.toJSONString` call with the application's Jackson `ObjectMapper` convention. Replace `GenericFastJsonRedisSerializer` in `RedisConfiguration` with Spring Data Redis' Jackson serializer, preserving the existing Redis template/key/hash/value wiring. Document that existing Fastjson-encoded Redis entries must expire or be flushed before rollout; do not add a compatibility serializer without a failing regression test.

- [ ] **Step 4: Resolve the dependency graph before compiling**

Run:

```bash
mvn -U dependency:tree -Dincludes=org.springframework.boot,com.fasterxml.jackson.core,cn.hutool,org.apache.commons,io.springfox,org.springdoc,com.alibaba:fastjson
```

Expected: no Springfox or Fastjson nodes; Jackson modules resolve to one version line; Hutool and commons-lang3 resolve to the selected secure versions.

- [ ] **Step 5: Compile the existing source and inspect failures**

Run:

```bash
mvn -DskipTests compile
```

Expected: compilation succeeds before the Springfox configuration migration, with no unrelated service or DTO API breakage.

### Task 3: Replace Springfox Swagger 2 with Springdoc OpenAPI

**Files:**
- Modify: `Jewix-API/pom.xml:119-131`
- Rename/modify: `Jewix-API/src/main/java/com/aomsir/jewixapi/config/Swagger2Configuration.java` to `OpenApiConfiguration.java`

- [ ] **Step 1: Add the Springdoc 1.x UI starter**

Add the Maven dependency compatible with Spring Boot 2.x:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-ui</artifactId>
  <version>1.8.0</version>
</dependency>
```

Use Springdoc `1.8.0`, the latest 1.x release available from Maven Central. Do not use Springdoc 2.x.

- [ ] **Step 2: Rewrite the development-only configuration**

Replace Springfox imports and `Docket` with `io.swagger.v3.oas.models.info.Info`, `io.swagger.v3.oas.models.info.Contact`, and `org.springdoc.core.models.GroupedOpenApi` or the Springdoc 1.x equivalent. Preserve `@Profile("dev")`, the title, description, version, contact, and `/.*` path coverage. Use the actual package API provided by the selected Springdoc version.

- [ ] **Step 3: Verify the configuration compiles and is discoverable**

Run:

```bash
mvn -DskipTests compile
rg -n "springfox|Swagger2Configuration|fastjson" pom.xml src || true
```

Expected: compilation passes and the only possible `Swagger2Configuration` match is absent because the configuration has been renamed; no Springfox/Fastjson source or POM references remain.

### Task 4: Add focused regression coverage

**Files:**
- Create: `Jewix-API/src/test/java/com/aomsir/jewixapi/config/OpenApiConfigurationTest.java`

- [ ] **Step 1: Add a test for the OpenAPI configuration metadata**

Use a focused unit test to instantiate `OpenApiConfiguration` and assert the `OpenAPI` and `GroupedOpenApi` beans expose:

```text
group = WebAPI
title = Jewix博客系统-API文档
description = Jewix博客系统,新一代博客系统
version = 1.0
```

Keep the test independent of MySQL and Redis by testing the configuration methods directly; do not load the full application context for this assertion.

- [ ] **Step 2: Add a JSON regression assertion for existing Jackson behavior**

Use an existing small DTO such as `LoginVo` or `WebInfo` and assert that the application’s current serializer/deserializer still maps its documented fields after the Jackson upgrade. Do not enable polymorphic default typing.

- [ ] **Step 3: Run focused tests before the full build**

Run:

```bash
mvn -Dtest=OpenApiConfigurationTest,JewixApiApplicationTests test
```

Expected: all selected tests pass without requiring production database or Redis services.

### Task 5: Full verification and Dependabot refresh

**Files:**
- Verify: `Jewix-API/pom.xml`
- Verify: `Jewix-API/src/main/java/com/aomsir/jewixapi/config/OpenApiConfiguration.java`

- [ ] **Step 1: Run the full Maven test suite**

Run:

```bash
mvn clean test
```

Expected: `BUILD SUCCESS` and all tests pass.

- [ ] **Step 2: Run the production package build**

Run:

```bash
mvn clean package
```

Expected: `BUILD SUCCESS` and a new `target/*.jar` is produced.

- [ ] **Step 3: Audit the final resolved graph**

Run:

```bash
mvn dependency:tree -Dverbose -DoutputFile=target/dependency-tree-after.txt
rg -n "springfox|fastjson|jackson-databind:2\.12|hutool.*5\.8\.12|commons-lang3:3\.12\.0" target/dependency-tree-after.txt pom.xml || true
```

Expected: no replaced dependency or vulnerable old version appears in the final graph.

- [ ] **Step 4: Verify OpenAPI endpoints when the application can start**

Start the API with the existing development configuration, then request:

```bash
curl -fsS http://localhost:<api-port>/v3/api-docs
curl -fsS -I http://localhost:<api-port>/swagger-ui.html
```

Expected: OpenAPI JSON returns HTTP 200 and Swagger UI returns HTTP 200 or a documented redirect. If external MySQL/Redis services are unavailable, record that limitation and rely on the focused configuration test plus package verification.

- [ ] **Step 5: Check the working tree and commit the implementation**

Run:

```bash
git diff --check
```

Commit only the backend dependency and test changes with a concise Chinese Conventional Commit message. Push only after the user requests it.
