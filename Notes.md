https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
https://docs.mongodb.com/manual/introduction/

Versions available: Community, Enterprise
Community version is open source, while enterprise version comes with features like authentication, monitoring etc.

MongoDB has shell interface as well as GUI interface called Compass.

Shell use:

We can run mongo shell without any command-line options to connect to a MongoDB instance running on localhost 
with default port 27017.
> mongo

To explicitly specify the port, include the --port command-line option. 
For example, to connect to a MongoDB instance running on localhost with a non-default port 28015:
> mongo --port 28015

MongoDB Instance on a Remote Host:
To explicitly specify the hostname and/or port,

We can specify a connection string. For example, to connect to a MongoDB instance running on a remote host machine:
> mongo mongodb://mongodb0.example.com:28015

We can use the command-line option --host <host>:<port>. For example, to connect to a MongoDB instance running on a remote host machine:
> mongo --host mongodb0.example.com:28015

We can use the --host <host> and --port <port> command-line options. For example, to connect to a MongoDB instance running on a remote host machine:
> mongo --host mongodb0.example.com --port 28015

MongoDB Instance with Authentication
We can specify the username, authentication database, and optionally the password in the connection string. For example, to connect and authenticate to a remote MongoDB instance as user alice:
> mongo --host mongodb://alice@mongodb0.examples.com:28015/?authSource=admin

We can use the --username <user> and --password, --authenticationDatabase <db> command-line options. For example, to connect and authenticate to a remote MongoDB instance as user alice:
> mongo --username alice --password --authenticationDatabase admin --host mongodb0.examples.com --port 28015

Mongo shell commands:
> db
It would display name of the current database you are in

Note: Although we may be in the 'test' database by default, the database does not actually get created until we insert 
a document into a collection in the database which will implicitly create the collection and the database.

> show dbs
It would display names of all the databases which actually exist.So it may not display 'test' even though first command 'db'
may display 'test', as 'test' may not have been created yet.

> use <db name>
It would switch to the data base.

**What is NoSQL data store?**
NoSQL is a generic term used to refer to any data store that does not follow the traditional RDBMS model—specifically,
the data is nonrelational and it generally does not use SQL as a query language. Most of the databases that are categorized
as NoSQL focus on **availability** and **scalability** in spite of atomicity or consistency.

Some common characteristics of such databases are:
1. **Data in many formats**: While most of the RDBMS databases store data in row, column format, NoSQL databases store data in varied formats,
   like documents, key-value pairs, graph databases and many more.
2. **Joinless**: NoSQL databses extract data using simple document-oriented interfaces, without using SQL joins.
3. **Schemaless data representation**: NoSQL implementations are based on a schemaless data representation, 
   with the notable exception of the Cassandra database. The advantage of this approach is that WE don't need to define 
   a data structure beforehand, which can thus continue to change over time, without those migrations or update scripts.
4. **Dsitributed**: Most NoSQL systems has ability to store database on multiple machines while maintaining high-speed
   performance.
   
In context of database operations, following are most common features needed:
• **Atomicity**: Everything in a transaction either succeeds or is rolled back
• **Consistency**: Every transaction must leave the database in a consistent state
• **Isolation**: Each transaction that is running cannot interfere with other transactions
• **Durability**: A completed transaction gets persisted, even after applications restart

But, these qualities sort of hamper availability and scalability. For eg, every time a user wants to alter state of a 
record in databsse, that part of database needs ot be locked so that other users dont't corrupt it. This may cause issues
in context of huge systems like amazon etc.

In context of distribute systems, CAP theorem is a better way of looking at things than ACID. CAP theorem states that at
a given time, we can only have two of consistency(C), availability(A) and partition tolerance(P). And as partition tolerance is must
in distributed systems, we need to choose between consistency and availability. This choice then becomes the deciding factor
in selection fo technology.

RDBMS systems fall in CA part.P is missing as generally RDBMS systems run on single nodes.
MongoDB is more CP. Its consistent by default, and uses replica set for partition tolerance.In a replica set, there exists
a single primary node that accepts writes, and asynchronously replicates a log of its operations to other secondary databases.
But not all NoSQL databases work on same philosophy. CouchDB, for instance, focuses on AP and promises **Eventual Consistency**.

**Living without transactions**
NoSQL databases like MongoDB don't support transactions. This is a trade-off for being simple, fast and scalable. So to achieve atomicity
complex documents(document within document) can be used. Updating a single document would thus update multiple documents atomically.
On the other hand, operations that includes multiple documents,are conversely not atomic.

So, to sum it up, if application's requirements can be met via document updates(also by using nested documents to provide an atomic update),
then this is a perfect use case for MongoDB, which will allow a much easier horizontal scaling of application.
On the other hand, if strict transaction semantics (such as a banking application) are required, then nothing can beat a relational database.
In some scenarios, we can combine both approaches (RDBMS and MongoDB) to get the best of both worlds, at the price of a more complex infrastructure to maintain.
   
**Managing read-write concurrency**

MonngoDB uses read/write locks to handle concurrent execution of database operations.
• There can be an unlimited number of simultaneous readers on a database
• There can only be one writer at a time on any collection in any one database
• The writers block out the readers once a write request comes in; all the
readers are blocked until the write completes (which is also known as writer-greedy)
Since version 3.0, write lock is now restricted to document.
In order to store information about locks, MongoDB relies on a storage engine, which is a part of the database and is responsible for
managing how data is stored on the disk. In particular, MongoDB 3.0 comes with two storage engines:
1. **MMAPv1**: This is the default storage engine, which uses collection-level locking
2. **WiredTiger**: This is the new storage engine, which ships with document level
   locking and compression (only available for the 64-bit version)
   
**Building blocks**   

**Database**: This is the top-level element. A Mongo Database is a physical container of a structure called a collection.
Each database has its own set of files on the filesystem. A single MongoDB server typically has multiple databases.

**Collection**: This is a set of MongoDB documents. A collection is the equivalent of an RDBMS table. There can be only one 
collection with that name on the database but obviously multiple collections can coexist in a database.

• **Documents**: This is the most basic unit of data in MongoDB. Basically, it is composed by a set of key-value pairs. 
Documents have a dynamic schema, which means documents that are part of the same collection do not need to have the same set of 
fields. And in same way, the fields contained in a document may hold different data types.
Document is analogous to row in RDBMS. Critical difference is that we don't have a restrictive enforced schema, instead a dynamic schema.
So, document structure can change with time without those migrations or update scripts.
Document structure is closely related to JSON structure.
person = {
  _id: "jo",
  name: "Jo Bloggs",
  age: 34,
  address: {
    street: "123 Fake St",
    city: "Faketon",
    state: "MA",
    zip: &#x201C;12345&#x201D;
  }
  books: [ 27464, 747854, ...]
}

Primary features:
1. Documents are structures of field-value pairs where value can be one of the primitives.
2. A document can contain another document(address is a subdocument inside person)
3. Document can contain array of values(books)

MongoDB stores data records as BSON documents. BSON is a binary representation of JSON documents, and it contains more data types than
JSON.

**Document Stucture**
A MongoDB document consists of field-value pairs\
{\
   field1: value1,\
   field2: value2,\
   field3: value3,\
   ...\
   fieldN: valueN\
}

The value of a field can be any of the BSON data types, including other documents, arrays, and arrays of documents
For example, the following document contains values of varying types:\
{\
       _id: ObjectId("5099803df3f4948bd2f98391"),\
        name: { first: "Alan", last: "Turing" },\
        birth: new Date('Jun 23, 1912'),\
        death: new Date('Jun 07, 1954'),\
        contribs: [ "Turing machine", "Turing test", "Turingery" ],\
        views : NumberLong(1250000)\
}

**Field Names**
Field names are strings.
Documents have the following restrictions on field names: 
1. The field name _id is reserved for use as a primary key; its value must be unique in the collection, is immutable, 
   and may be of any type other than an array.
2. Field names cannot contain null character(\0).
3. Top-level field names cannot start with the dollar sign ($) character.

MongoDB is both type-sensitive and case sensitive.
For example, these documents are distinct:
{"age" : 18}
{"age" : "18"}
{"Age" : 18}

**Document Size Limit**
The maximum BSON document size is **16 megabytes**.
The maximum document size helps ensure that a single document cannot use excessive amount of RAM or, during transmission,
excessive amount of bandwidth. To store documents larger than the maximum size, MongoDB provides the GridFS API.

**The _id Field**
In MongoDB, each document stored in a collection requires a unique _id field that acts as a primary key.
If an inserted document omits the _id field, the MongoDB driver automatically generates an ObjectId for the _id field.
This also applies to documents inserted through update operations with upsert: true.
The _id field has the following behavior and constraints:
1. By default, MongoDB creates a unique index on the _id field during the creation of a collection.
2. The _id field is always the first field in the documents. If the server receives a document that does not have the 
   _id field first, then the server will move the field to the beginning.
3. The _id field may contain values of any BSON data type, other than an array.

**Data types accepted in document**
1. String
2. Integer
3. Double
4. Boolean
5. Arrays
6. Timestamp
7. Object
8. Null
9. Date
10. Object ID: To store document id
11. Binary data
12. Regular expressions

**MongoClient**
The MongoClient is our route in to MongoDB, from this we’ll get our database and collections to work with.
The instance of MongoClient will ordinarily be a singleton in your application. However, if we need to connect via different credentials (different user names and passwords) we’ll want a MongoClient per set of credentials.

It is important to limit the number of MongoClient instances in the application, hence why we suggest a singleton.
MongoClient is effectively the connection pool, so for every new MongoClient, we are opening a new pool.
Using a single MongoClient (and optionally configuring its settings) will allow the driver to correctly manage your connections to the server. 
This MongoClient singleton is safe to be used by multiple threads.
One final thing we need to be aware of: we want our application to shut down the connections to MongoDB when it finishes running.
Always make sure application or web server calls MongoClient.close() when it shuts down.

**Where are tables?**

MongoDB doesn’t have tables, rows, columns, joins etc.
There are some new concepts.While we still have the concept of a database, the documents are stored in collections, rather than 
database being made up of tables of data. But it can be helpful to think of documents like rows and collections like tables in a traditional database. 
And collections can have indexes like we’d expect.