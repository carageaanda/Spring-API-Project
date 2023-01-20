# Spring-Project

A system that keeps track of both the musicians and the music they produce. The system implements CRUD operation on entities, , ability to handle individual errors. Rest endpoint for all the main features are defined. Unit tests with 85% coverage and the POJO classes are validated. To create this project I used Spring Web, Mockito, MySQLWorkbench, JPA. 
DB:
OneToMany: 
A Band has multiple Albums, An Album has multiple Songs
OneToOne: 
An Album has a price and a quantity
ManyToMany:
A Song has multiple Genres and a Genre can belong to multiple Songs.
A Shop has multiple Albums and an Album can belong to multiple Shops. 
