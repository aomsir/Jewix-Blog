# Jewix API Dependency Security Upgrade Design

## Goal

Remove the 23 open GitHub Dependabot alerts reported for `Jewix-API/pom.xml`, including Critical and High findings, while preserving the API's Java 8 runtime and existing HTTP contracts.

## Scope

- Upgrade the Spring Boot 2.x baseline from `2.5.6` to `2.7.18`, the final Spring Boot 2 release line compatible with Java 8.
- Remove the manually pinned Jackson `2.12.1` dependency version and use a consistent secure Jackson BOM/version compatible with the selected Spring Boot line.
- Upgrade Hutool to a current secure 5.x release while retaining `hutool-core` and `hutool-json`, which are used by application code.
- Remove the unused legacy `com.alibaba:fastjson` dependency.
- Replace Springfox Swagger 2 and its bundled Swagger UI with `org.springdoc:springdoc-openapi-ui` 1.x, retaining OpenAPI documentation and the existing Swagger UI access path where practical.
- Upgrade `commons-lang3` to a secure compatible release.
- Resolve any newly exposed vulnerable transitive dependencies without broad unrelated refactoring.

Out of scope:

- Spring Boot 3, Jakarta namespace migration, or Java 17 migration.
- Changes to database schema, authentication semantics, API payloads, or frontend API calls.
- Suppressing, dismissing, or manually closing Dependabot alerts without addressing the dependency.

## Dependency Strategy

The POM will prefer managed versions from Spring Boot where possible. Explicit versions will be used only for dependencies that are outside the selected Boot BOM or require a security override. Jackson modules must remain on one aligned version line to avoid runtime linkage errors.

The existing source search shows no direct `fastjson` usage. JSON serialization currently uses Jackson and Hutool JSON APIs, so removing `fastjson` does not require an application-code replacement. Hutool remains because `BeanUtil`, `JSONUtil`, `UUID`, `StrUtil`, and related APIs are used in production code.

## Swagger Migration

`Swagger2Configuration` will be migrated from Springfox types and annotations to Springdoc OpenAPI types. The configuration will continue to describe the API and expose the generated documentation. Springdoc 1.x is selected because it supports Spring Boot 2.x and Java 8; Springdoc 2.x is intentionally excluded because it targets Spring Boot 3.

The migration must verify:

- Application context starts successfully.
- OpenAPI JSON endpoint is available.
- Swagger UI is available through the expected documented URL or an explicitly documented equivalent.
- Existing controller mappings and security behavior are unchanged.

## Error Handling and Compatibility

Dependency upgrades must not weaken request validation, authentication, authorization, or JSON deserialization safeguards. If a library upgrade changes a default, the implementation will preserve the current application behavior explicitly or add a focused regression test. Any unavoidable compatibility adjustment will stay inside the affected configuration or adapter.

## Verification

From `Jewix-API`:

1. Run Maven tests.
2. Run a clean Maven package/build with tests.
3. Inspect the resolved dependency tree for old Jackson, Springfox, Fastjson, Hutool, and vulnerable duplicate versions.
4. Start the application context or targeted integration test and verify OpenAPI endpoints.
5. Confirm the final POM and generated dependency graph no longer contain the replaced libraries.

The final report will distinguish local Maven results from GitHub Dependabot refresh timing. GitHub may need several minutes after the dependency manifest is pushed before its alert count reflects the new graph.
