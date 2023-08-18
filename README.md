# java-multi-tenancy-benchmark

Simple spring boot app with load tests to compare different approaches to implement multi-tenancy in a java application.

## Table of contents

* [java-multi-tenancy-benchmark](#java-multi-tenancy-benchmark)
  * [Index](#index)
  * [Environment](#environment)
  * [Technologies](#technologies)
  * [First approach MTDS - Multi-tenant data source per application, one application serves all tenants](#first-approach-mtds---multi-tenant-data-source-per-application-one-application-serves-all-tenants)
  * [Second approach STDS - Single tenant data source per application, one application per tenant](#second-approach-stds---single-tenant-data-source-per-application-one-application-per-tenant)
  * [Scenarios](#scenarios)
    * [Scenario 1 - 5 tenants, 20 users per tenant, 100 requests per user](#scenario-1---5-tenants-20-users-per-tenant-100-requests-per-user)
  * [Results](#results)
    * [MTDS approach](#mtds-approach)
    * [STDS approach](#stds-approach)
  * [Conclusion](#conclusion)
  * [Next steps](#next-steps)

## Environment

- Docker is used as an execution environment
- Docker compose is used to configure deployments
- Tests are run on MacBook Pro M1 8 cores 2020 with 16GB RAM

## Technologies

- Java 17, Spring Boot 3.1.2, Spring Data JPA - for application
- YugabyteDB - for database
- Nginx - for load balancing and reverse proxying 
- K6 - for load testing

## First approach MTDS - Multi-tenant data source per application, one application serves all tenants

MTDS approach is implemented using `AbstractRoutingDataSource` and `ThreadLocal` to store tenant id.

All tenants share the same database and use different schemas to store data.

The application uses the header `tenant` to identify the tenant.

## Second approach STDS - Single tenant data source per application, one application per tenant

STDS approach uses environment variables to configure the database connection.

All tenants share the same database and use different schemas to store data.

A reverse proxy is used to route requests to different applications and it uses the header `X-Tenant` to identify tenants.

## Scenarios

### Scenario 1 - 5 tenants, 20 users per tenant, 100 requests per user

The scenario is implemented using the k6 load testing tool.

Script for the MTDS approach is located in `./jmtb-load-test/load-test-1.js`.
Script for the STDS approach is located in `./jmtb-load-test/load-test-1-stds.js`.

## Results

### MTDS approach
- [Scenario 1, MTDS approach, one application](https://htmlpreview.github.io/?https://github.com/vu-koman/java-multitenancy-benchmark/blob/main/results/load-test-1.html)
- [Scenario 1, MTDS approach, 3 applications, load balancer in front](https://htmlpreview.github.io/?https://github.com/vu-koman/java-multitenancy-benchmark/blob/main/results/load-test-1_3instance.html)

### STDS approach
- [Scenario 1, STDS approach, 5 applications, load balancer in front](https://htmlpreview.github.io/?https://github.com/vu-koman/java-multitenancy-benchmark/blob/main/results/load-test-1-stds.html)


## Conclusion

- STDS approach is faster than the MTDS approach
- MTDS approach has more timeouts than the STDS approach
- STDS does not require any additional programming, MTDS requires to implement `AbstractRoutingDataSource` and `ThreadLocal` to store tenant id

## Next steps

- Measure memory consumption
- Measure CPU consumption
- Measure disk consumption
- Measure network consumption


