# poi-data-validation
Implemention of checking Data Validation with Apache Poi library

Checks the sheets by using data validation rules of that sheet. 
All you need to call the following interface for each sheet:

List<ValidationResult> validateSheet(XSSFSheet sheet);


Programs (like Microsoft Excel) prevent users to enter invalid values to the cells, but cannot prevent users to copy&paste invalid values to the cells. Also some generators can cause such inconsistency. So one may need to check data validations of input files.
