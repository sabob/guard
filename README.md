# guard
Guard is a simple and easy-to-use, general purpose, validation library for Java.

The idea behind Guard is to guard objects, in most cases, the individual fields of an object.

Constraints are applied to the values of the object and all violations of the constraint are added
to a list. Once all the constraints are applied, you can retrieve the list of violations and 
then throw an exception containing this list or branch your code depending on a violation.  

You can set Guard to fail-fast if a constraint is violated, meaning it will throw an exception 
immediately. Or run leniently and wait until all constraints have been applied before retrieving
the list of violations.

### Why not use Bean Validation or a different Validation library?

[Bean Validation](https://beanvalidation.org/) is great
for static validation of classes, it doesn't cater for runtime
validation of objects.

Bean Validation shines when applying constraints on data
structures on the boundary of the application eg.
Data Transfer Objects (DTO) that is often used as input
to REST requests.

But Bean Validation isn't intended for dynamic validations
at runtime.

For example, given a REST endpoint to add users we must first check for an existing
user in the database with the same username before adding.

In the above scenario Bean Validation isn't the tool for the job.

This is where an Object / Runtime validator can be used.

There are other Java validation libraries available, but I found
them too sophisticated for my needs.

Guard is complimentary to Bean Validation and is often used on the boundary of the application,
eg. REST endpoints,  where data is added or updated.
Guard uses the familiar concepts of Constraints and Violations
and ships with basic validations such is NotNull, Empty, Size, Max etc.

## Highlights 
* Easy to learn.
* Extendable. Implement the Constraint interface to create a custom
* validator
* Provides basic validators out of the box. ( Most the validators
* found in [Bean Validation](https://beanvalidation.org/))
* are also provided by Guard.
* Constraints can be composed to create new constraints.

## Basic Usage
The idea behind Guard is to guard objects, in most cases, the individual fields of an object.

A Guard must have a **name**. 

The name should be unique and all violations that occur while applying constraints, will be
added under this name.

For example:
```
Guard guard = new Guard("some_name")
.value("") // empty value will violate the Required constraint
.constraint( new Required());

// a Violation will be added for "some_name".
 
// Now if we change the name
guard.of("another_name")
.value("") // empty value will violate the Required constraint
.constraint( new Required());
// a Violation will be added for "another_name".

// One violation will be registered for each name
// Hence the following Assertions will be true
Assertions.assertTrue( violations.getList( FIRSTNAME ).size() == 1 );
Assertions.assertTrue( violations.getList( LASTNAME ).size() == 1 );
```


will be used as the name of the Violations that are 
created when constraints are applied to the values of the object
that is guarded.

Below is a quick example:

``` java
Guard guard = new Guard("firstname");
guard.value("steve");
giard.constraint( new Required() );
Violations violations = guard.validate();

System.out.println(violations.isValid())); // true
System.out.println(violations.isValid())); // true

List<Violation> violationList = violations.getList("firstname");
Violation violation = violationList.get(0);
System.out.println("message: " +  violation.getMessage());
```

This can be read as "Create a Guard for the firstname Field"

### Fluid API
The above example can be written a bit more fluently as most
methods return the guard instance:

```
Violations violations = new Guard("firstname")
.value("steve")
.constraint( new Required() )
 .validate()
```

### Multiple Constraints
We can apply more than one Constraint on a value:

```
Violations violations = new Guard("firstname")
.value("steve")
.constraint( new NotNull() )
.constraint( new NotEmpty() )
.constraint( new Size(2, 20) )
 .validate()
```

### Multiple Values
The same Guard instance can be applied to different values
for the same Object or Field.
Just change the **value** and apply another Constraint.
```
Violations violations = new Guard("firstname")
.value("Steve")
.constraint( new Size().max(10) )
.value("Mary")
.constraint( new Size().max(5) )
 .validate()
```

### Multiple Names
The same Guard instance can be applied to different Names
, generally when we want to validate a different Field or
Object.
Just change the **name** through the **of()** method.
This can be read as "This is a Guard **of** the firstname Field".
The word, for, would have been better, but it's a keyword in Java,
so we settle for the next best thing.
```
Violations violations = new Guard("firstname")
.value("Steve")
.constraint( new NotNull() )
.constraint( new Size().max(5) )

.of("lastname")
.value("Sanders")
.constraint( new Size().max(10) )
 .validate()
```

### Context
When a Guard instance is created a GuardContext is created
for the name of that Guard.

A GuardContext has a name, a value and a 
list of violations that was created as constraints were applied 
against the current context.

### Context Switching
New GuardContexts are created when the **name** is changed
through the **of()** method.

```
Violations violations = new Guard("firstname")
.value("")
.constraint( new Required() )
.of("lastname")
.value(null)
.constraint( new Required() )
of("age")
.value( 200 )
.constraint( new Max(  120 ) )
 .validate()
 
 Assertions.assertTrue( violations.getList("firstname").size() == 1 );
 Assertions.assertTrue( violations.getList("lastname").size() == 1 );
 Assertions.assertTrue( violations.getList("age").size() == 1 );
```
 
All new constraints will be applied against the current 
GuardContext.

NOTE: A Guard instance keeps a list of all Violations of all
GuardContexts that was created.

So even when the context is switched from "firstname" to "lastname",
all "firstname" Violations are still present in the list of
Violations returned by the "validate()" method.

```
Violations violations = new Guard("firstname")
    .value(null)
    .constraint( new NotNull() )
    .of("lastname")
    .value("Sanders")
    .constraint( new Size().max( 5 ) )
    .validate();

    Assertions.assertTrue( violations.getList().size() == 2 ); 
```

We can apply multiple constraints on a Guard with the same
name.
```
Violations violations = new Guard( "client phone number" )
                .value( "abc123" )
                .constraint( new Size().max(3) ) // value length cannot be greater than 3
                .constraint( new NumberOnly( ) ) // value must consist of numbers only
                .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList().size() == 2 );
```
This will result in a Violation for each Constraint.

### Composition
As Java is object-oriented, we can assemble more complex constraints from 
others.

The AtLeast constraint is a good example of this approach.

### isValid() / isInvalid() utility methods
The constraint interface exposes the __isValid( Object value )__  and
__isInvalid( Object value )__ methods that can be used as utility methods
to validate values.
```
List list = new ArrayList();
Size size = new Size( 1, 5 );
if (size.isInvalid(list)) {
    System.out.println("List must contain contain at least 1 
    but less than 5 items");
```

