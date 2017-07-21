# Spring Annotations

I'm trying to learn the key Annotations used by Spring Boot.

## Domain Objects

### Class Annotations

#### @Entity

#### @Table

Properties:

 - uniqueConstraints
 - indexes
 - schema

### Field/Method Annotations

It looks like putting these on the field getter methods works nicely.

#### @Id

Specifies the Entity's Primary Key

#### @GeneratedValue

Properties:

 - strategy - the strategy for generation
 - generator - the name of the generator

#### @Column

Properties:

 - unique - true iff the column is a unique key.
 - nullable - true iff the column can contain nulls.
 - updatable - true iff the column should be included in UPDATE statements
 - length (for string columns only)
 - precision (for decimal columns only)

#### @ManyToOne

Properties:

 - cascade - specifies how to cascade changes to the relation (defaults to no cascading).
 - fetch - specifies how the related resources should be fetched (defaults to EAGER)
 - optional - if false, a non-null relationship must always exist.

#### @JoinColumn

For specifying how to join the two entities.

Properties:

 - name - the name of the join column.
 - foreignKey - the ForeignKey provides a way to name the foreign key constraint.
 - updatable - true iff this column is included in UPDATE statements.
 - insertable - true iff this column is included in INSERT statements.
 - nullable - true iff the foreign key can be null (defaults to false)

#### @OnDelete

This can be useful to avoid foreign key constraint violations when deleting
entities that are in relationships.

Properties:

 - action - either CASCADE or NO_ACTION

#### @Column

Useful for specifying that a column cannot contain any nulls.

Properties:

 - name - the name of the column.
 - updatable - true iff this column is included in UPDATE statements.
 - insertable - true iff this column is included in INSERT statements.
 - nullable - true iff the foreign key can be null (defaults to false)
 - length (for string columns only)
 - precision (for decimal columns only)

# Repository Classes

## Class Level

### @Repository

Properties:

 - name

# Controller

## Class Level

### @CrossOrigin

 - origins
 - allowedHeaders
 - exposedHeaders
 - methods
 - allowCredentials
 - maxAge

### @RestController

## Method Level

### @RequestMapping

Properties:

 - path - the request's path (variables are written in curly braces like {id})
 - method - an array of HTTP methods (e.g. GET, POST, etc.)
 - params - used to map requests based on query string parameters (e.g. myParam=myValue or myParam!=myValue)
 - consumes - the media types that are supported
 - produces - the media types that the client has said they will accept (e.g. Accept header)

 ## Parameter Level

 ### @PathVariable

 Provides a way to access parts of the request's path as parameters.

 ### @RequestBody

 Provides a way to access the request payload as an Domain object.

# Test Classes

## Class Level

### @RunWith

Properties:

 - the runner to run the test fixtures with.

### @SpringBootTest

Properties:

 - webEnvironment - e.g. MOCK, RANDOM_PORT
 - properties - zero or more key value pairs

# Components

## Class Level

### @Component

Makes the annotated class injectable.
