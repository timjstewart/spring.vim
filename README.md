# Spring Annotations

I'm trying to learn the key Annotations used by Spring Boot.

## Domain Objects

### Class Annotations

#### @Entity

Example:

    @Entity
    public class Report
    {
    }

#### @Table

Example:

    @Entity
    @Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {
        "car_uuid", "user_uuid"
    }))
    public class Rating {
    }

Properties:

 - uniqueConstraints
 - indexes
 - schema

### Field/Method Annotations

Example:

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    public UUID getUuid()
    {
        return uuid;
    }

It looks like putting these on the field getter methods works nicely.

#### @Id

Specifies the Entity's Primary Key

#### @GeneratedValue

Properties:

 - strategy - the strategy for generation
 - generator - the name of the generator

#### @Column

Useful for specifying that a column cannot contain any nulls.


Example:

    @Column(nullable = false)
    public int getStars()
    {
        return stars;
    }

Properties:

 - name - the name of the column.
 - updatable - true iff this column is included in UPDATE statements.
 - insertable - true iff this column is included in INSERT statements.
 - nullable - true iff the foreign key can be null (defaults to false)
 - length (for string columns only)
 - precision (for decimal columns only)

#### @ManyToOne

Example:

    @ManyToOne(optional = false, fetch = FetchType.LAZY)

Properties:

 - cascade - specifies how to cascade changes to the relation (defaults to no cascading).
 - fetch - specifies how the related resources should be fetched (defaults to EAGER)
 - optional - if false, a non-null relationship must always exist.

#### @JoinColumn

For specifying how to join the two entities.

Example:

    @JoinColumn(name = "car_uuid", foreignKey = @ForeignKey(name = "CAR_UUID_FK"))
    public Car getCar()
    {
        return car;
    }

Properties:

 - name - the name of the join column.
 - foreignKey - the ForeignKey provides a way to name the foreign key constraint.
 - updatable - true iff this column is included in UPDATE statements.
 - insertable - true iff this column is included in INSERT statements.
 - nullable - true iff the foreign key can be null (defaults to false)

#### @OnDelete

This can be useful to avoid foreign key constraint violations when deleting
entities that are in relationships.

Example:

    @OnDelete(action = OnDeleteAction.CASCADE)
    public Car getCar()
    {
        return car;
    }

Properties:

 - action - either CASCADE or NO_ACTION

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

 ### @RequestParam

 Provides a way of accessing query string parameters.

 Example:

    @RequestMapping(path = "/cars", method = RequestMethod.GET)
    public Page<Car> getMany(@RequestParam(name = "year", required = false) Integer year)
    {
        if (year == null)
        {
        ...
        }
    }

Properties:

 - name - the name of the query string parameter
 - required - whether or not the parameter is required
 - defaultValue - which value should be used if the parameter is not supplied

 ### @PathVariable

 Provides a way to access parts of the request's path as parameters.

 Example:

    @RequestMapping(path = "/blogs/{id}", method = RequestMethod.GET)
    public HttpEntity<Blog> getOne(@PathVariable UUID id)
    {
    ...
    }

 ### @RequestBody

 Provides a way to access the request payload as an Domain object.

 Example:

    @RequestMapping(path = "/blogs/, method = RequestMethod.POST)
    public HttpEntity<Blog> createOne(@RequestBody Blog blog)
    {
    ....
    }

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
