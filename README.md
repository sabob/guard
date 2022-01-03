# guard
An Object validator library for Java

# Documentation

``` java
Violations violations = Guard.of("firstname")
.value("steve")
.constraint( new Required() )
.validate();

System.out.println(violations.isValid())); // true
System.out.println(violations.isValid())); // true

List<Violation> violationList = violations.getList("firstname");
Violation violation = violationList.get(0);
System.out.println("message: " +  violation.getMessage());
    
```

