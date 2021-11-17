# Object Oriented Design Database

This project is a simple key-value store database implementation in Java, following the OOD Design principles. The following patterns (will be / are) used for various things across the code-base.
- **Singleton**: The database is a singleton, and is accessed through the `Database` class.
- **Command**: Commands are used to both modify the state of the database and to distribute tasks like reading libs vs reading from the disk.
- **Observer**: The database is an observer, and is notified of changes to the database.