# Quarkus project for cgm - Andrew Book

The postgresql database was initialized with the script at /scripts/load-database.sql

Quarkus and Panache allow for a really quick development experience!

Changes made are immediately reflected in the development server

For example, to retrieve a 'Patient' by id, I'm only using two classes.

- A GET endpoint defined at PatientController.java
- an Entity defined at Patient.java
    - Queries can be defined at the entity level

These classes are accessed statically so there is no need for reflection like in spring boot.