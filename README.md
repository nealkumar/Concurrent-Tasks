[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
# Concurrent Tasks Java Library
An easy-to-consume concurrency library allowing for "Runnable Tasks" to execute business logic in a thread safe manner. Presently, there are 2 types of Tasks: Retrievable and Non-Retrievable.
## Non-Retrievable Tasks
Once <code>NonRetrievableTask</code> has been extended, this allows for simple concurrent execution where the business logic executed <i>does <b>not</b></i> need to return back an object. As a result, calling the getVal() method for a NonRetrievableTask throws an UnsupportedOperationException. 
</br></br>
Example usages: initializers, message dispatchers, or any standalone time-consuming task which you would like to execute concurrently.
## Retrievable Tasks
Once <code>RetrievableTask</code> is extended, this allows for <code>@ThreadSafe</code> concurrent execution where business logic executed <i>does</i> need to return back an object. As a result, calling the getVal() method for a Retrievable task returns the object of type T (via use of Java generics) - which is blocked until all logic in the execute() method has terminated. 
<br/><br/>
Example usages: Api calls, I/O, or any other situation warranting concurrent parallel processing.
<br/><br/>
Note that classes extending RetrievableTask.java should explicity specify object type in the class declaration to enable compile-type type checking. See example:
```java
  public class ExampleTask extends RetrievableTask<String>{}
```

Finally to return the value, one simple has to set the value for <code>obj</code> or <code>this.obj</code>, as such:

```java
  public class ExampleTask extends RetrievableTask<String>{
      @Overide
      protected void execute(){
        //do work, execute business logic
        
        //then set the return value for ExampleTasks's object
        this.obj = "Example task successfully completed";
      }
  }
```
# Client Usage / Use-Cases
Regardless on the type of <code>Task</code> needed, each respective <code>Task</code> should be wrapped within with a <code>Thread</code> and will begin execution upon calling of the <code>start()</code> or <code>run()</code> methods. The <code>start()</code> method executes the <code>Task</code> in a new thread, while the <code>run()</code> method executes the <code>Task</code> in the same thread.
<br/><br/>Below is an example of a <code>RetrievableTask</code> and <code>NonRetrievable</code> executing business logic in new threads, and then printing out status messages - based on the designated <code>Task</code> type.
```java
  public class Main{
  
    private Task nonRetrievable, retrievable;
    
    public static void main(String[] args){
      nonRetrievable = new NonRetrievableTask();
      retrieveable = new RetrievableTask();
      //start the Retrievable Task
      Thread t = new Thread(retrievable);
      t.start();
      System.out.println(retrievable.getVal());
      //start the Non-Retrievable Task using an anonymous Thread
      new Thread(nonRetrievable).start();
    }
    
    private class FullTask extends RetrievableTask<String>{
      @Override
      protected void execute(){
        //do work, execute business logic
        //...    
        this.obj = "Finished with Retrievable task!";
      }
    }
    
    private class EmptyTask extends NonRetrievableTask{
      @Override
      protected void execute(){
        //do work, execute business logic
        //...
        System.out.println("Finished with Non-Retrievable task!");
      }
    }    
  }
```
