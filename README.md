# ex 183 practice
## Intro

This is an example project created while walking through the objectives listed for the  Red Hat Certified Enterprise Application Developer exam (EX 183).  See the [Red Hat documentation] for further information.

## Prerequisites
* Java 1.8 JDK
* Maven 3.6.3 or higher
* PostgreSQL 12.4 or greater
* Red Hat JBoss Enterprise Application Platform 7.3

## Installation and Configuration

Note this was installed on Fedora 33

### Postgres

1. install
```sh
sudo dnf install postgresql-server
```
2. initialize
```sh
sudo /usr/bin/postgresql-setup --initdb
```
3. enable
```sh
sudo systemctl start postgresql
 ```
4. verify
```sh
sudo systemctl status postgresql
 ```
5. create user
```sh
sudo -u postgres createuser <username>
```
6. create database
```sh
sudo -u postgres createdb <dbname>
```
7. grant user privileges on the database
```sh
psql=# grant all privileges on database <dbname> to <username>;
```
8. create password for username
```sh
psql=# alter user <username> with encrypted password '<password>';
```
9. allow password access
```sh
vim /var/lib/pgsql/data/pg_hba.conf
```
ensure the following is set
```sh
# TYPE  DATABASE        USER            ADDRESS                 METHOD
local   all              postgres                               peer
local   all              all                                    md5
# IPv4 local connections:
host    all              all            127.0.0.1/32            md5
# IPv6 local connections:
host    all              all            ::1/128                 md5
```
10. run create_tables.psql
```sh
psql --username=<username> --dbname=<dbname>
--file=./create_tables.sql
```
11.   download JDBC driver
```sh
https://jdbc.postgresql.org
```
### JBoss EAP
1. Install [JBoss EAP]
2. Ensure user system user has write access to the standalone directory
```sh
$EAP_HOME/standalone
```
3. add admin user to EAP
```sh
$EAP_HOME/bin/add-user.sh
```
4. start the server in eclipse - ensure configuration is standalone-full.xml
5. add successQueue
    Configuration -> Subsystems -> Messaging -> Server -> default -> Destinations -> View -> JMS Queue -> Add
Name: successQueue
   Entries: java:/jms/queue/successQueue
6. add successTopic
    Configuration -> Subsystems -> Messaging -> Server -> default -> Destinations -> View -> JMS Topic -> Add
Name: successTopic
Entries: java:/jms/topic/successTopic
7. install JDBC driver
    Deployments -> Upload Deployment -> 

8. add datasource - note the datasource JNDI Name must match what is in the persistence.xml jta-data-source
Configuration -> Subsystems -> Datasources -> Add Datasource

## Testing

Below is a list of available REST API calls, the required headers, and example bodies

### Hello World
HTTP Verb: GET
URL: http://localhost:8080/library/catalog/helloWorld

### Add Book
HTTP VERB: PUT
URL: http://localhost:8080/library/catalog/addBook
HEADER: Content-Type=application/json
EXAMPLE BODY:
```JSON
{
    "isbn": 1234,
    "title": "The Last Entry",
    "authorBean": {
        "firstName": "John",
        "lastName": "Smith"
    }
}
```

### Remove Book
HTTP VERB: DELETE
URL: http://localhost:8080/library/catalog/removeBook?isbn=<isbn_to_remove>

### Show Books
HTTP VERB: GET
URL: http://localhost:8080/library/catalog/showBooks

### Show Authors
HTTP VERB: GET
URL: http://localhost:8080/library/catalog/showAuthors

### Search by Author
HTTP VERB: GET
URL: http://localhost:8080/library/catalog/searchByAuthor?firstName=<first_name>&lastName=<last_name>

### Search by Title
HTTP VERB: GET
URL: http://localhost:8080/library/catalog/searchByTitle?title=<title>
    
   [Red Hat documentation]: https://www.redhat.com/en/services/training/ex183-red-hat-certified-enterprise-application-developer-exam?section=Objectives
   [JBoss EAP]:  https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.3/html/installation_guide/product_installation