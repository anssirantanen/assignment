To run the application use "sbt run" command, this will start the server at localhost:8080
To run the test suite use "sbt test"

About the data format:
The current schedule format could be improved in two ways. The first one is to use a single event for an opening duration,
which includes the opening timestamp and duration of the opening. 
This would remove awkward closing events that can be spanned to another day.  

The second change that would improve the parsing of the code, would be switching the days from object keys to a list of objects,
where weekday information would be as key in the object.
This change would enable iterating between days instead of the current verbose structure with the specified weekdays. 