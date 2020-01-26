[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
# Concurrent Tasks Java Library
An easy-to-consume concurrency library allowing for "Tasks" to execute business logic in a thread safe manner. Presently, there are 2 types of Tasks: Retrievable and Non-Retrievable.

# Non-Retrievable Tasks
Once extended (NonRetrievableTask.java), this allows for simple concurrent execution where the business logic executed <i>does <b>not</b></i> need to return back an object. As a result, calling the getVal() method for a NonRetrievableTask throws an UnsupportedOperationException.

# Retrievable Tasks
Once extended (RetrieveableTask.java), this allows for Thread Safe (ThreadSafe.java) concurrent execution where business logic executed <i>does</i> need to return back an object. As a result, calling the getVal() method for a Retrievable task returns the object of type T (via use of Java generics) - which is blocked until all logic in the execute() method has terminated. Note that classes extending RetrievableTask.java should explicity specify object type in the class declaration to enable compile-type type checking. See example:<br/>
```java
  public class ExampleTask extends RetrievableTask<String>{
      @Overide
      protected void execute(){
        //do work, execute business logic
        this.obj = "Example task successfully completed";
      }
  }
```
