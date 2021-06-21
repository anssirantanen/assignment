package com.time

trait OpeningData {
  lazy val baseJson = """{
"monday" : [],
"tuesday" : [
{
"type" : "open",
"value" : 36000
},
{
"type" : "close",
"value" : 64800
}
],
"wednesday" : [],
"thursday" : [
{
"type" : "open",
"value" : 37800
},
{
"type" : "close",
"value" : 64800
}
],
"friday" : [
{
"type" : "open",
"value" : 36000
}
],
"saturday" : [
{
"type" : "close",
"value" : 3600
},
{
"type" : "open",
"value" : 36000
}
],
"sunday" : [
{
"type" : "close",
"value" : 3600
},
{
"type" : "open",
"value" : 43200
},
{
"type" : "close",
"value" : 75600
}
]
}"""
  lazy val unsorted = """{
"monday" : [],
"tuesday" : [
{
"type" : "open",
"value" : 36000
},
{
"type" : "close",
"value" : 64800
}
],
"wednesday" : [],
"thursday" : [
{
"type" : "open",
"value" : 37800
},
{
"type" : "close",
"value" : 64800
}
],
"friday" : [
{
"type" : "open",
"value" : 36000
}
],
"saturday" : [
{
"type" : "open",
"value" : 36000
},
{
"type" : "close",
"value" : 3600
}
],
"sunday" : [
    {
"type" : "open",
"value" : 43200
},
{
"type" : "close",
"value" : 3600
},

{
"type" : "close",
"value" : 75600
}
]
}"""
  lazy val baseResponse = """Monday: Closed
Tuesday: 10 AM - 6 PM
Wednesday: Closed
Thursday: 10:30 AM - 6 PM
Friday: 10 AM - 1 AM
Saturday: 10 AM - 1 AM
Sunday: 12 PM - 9 PM""".stripMargin
  lazy val unclosed = """{
"monday" : [],
"tuesday" : [],
"wednesday" : [],
"thursday" : [],
"friday" : [],
"saturday" : [],
"sunday" : [
{
"type" : "open",
"value" : 43200
}]
}"""
  lazy val wrongSequence = """{
"monday" : [],
"tuesday" : [],
"wednesday" : [],
"thursday" : [],
"friday" : [],
"saturday" : [],
"sunday" : [
    "type" : "close",
"value" : 42200
},
{
"type" : "open",
"value" : 43200
}]
}"""
  lazy val sunday = """{
"monday" : [{
"type" : "close",
"value" : 3600
}],
"tuesday" : [],
"wednesday" : [],
"thursday" : [],
"friday" : [],
"saturday" : [],
"sunday" : [
{
"type" : "open",
"value" : 43200
}]
}"""
  lazy val multiple = """{
"monday" : [],
"tuesday" : [],
"wednesday" : [],
"thursday" : [],
"friday" : [],
"saturday" : [],
"sunday" : [
{
"type" : "open",
"value" : 36000
},
{
"type" : "open",
"value" : 64800
},{
"type" : "close",
"value" : 43200
},{
"type" : "close",
"value" : 75600
}
]
}"""
  lazy val multipleResp = """Monday: Closed
Tuesday: Closed
Wednesday: Closed
Thursday: Closed
Friday: Closed
Saturday: Closed
Sunday: 10 AM - 12 PM, 6 PM - 9 PM""".stripMargin
}
