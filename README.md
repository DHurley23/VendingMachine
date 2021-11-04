Main DAO classes - Includes two DAO interfaces, one for the Audit DAO and one for the File I/O.

Change class - Makes use of the CoinValues Enum.

Two custom Exception classes - InsufficientFundException is a runtime exception, NoItemInventoryException is a compiletime exception.

SyncLogger class - used to log to a logger file.

Item class - constructor generates key/id values for each items to simulate vending machine codes.

JUnit tests - tests to check the import functionality, the vendingMachineServiceImpl class and the change class for correct funcitonality