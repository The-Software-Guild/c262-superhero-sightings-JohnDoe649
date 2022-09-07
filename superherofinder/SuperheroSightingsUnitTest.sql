DROP DATABASE if EXISTS testSuperTracker;
CREATE DATABASE testSuperTracker;

USE testSuperTracker;

CREATE TABLE supers(
id INT PRIMARY KEY auto_increment,
superName VARCHAR(50) NOT NULL,
superDescription VARCHAR(255),
superpower VARCHAR(50) NOT NULL
);

CREATE TABLE organizations(
id INT PRIMARY KEY auto_increment,
orgName VARCHAR(100) NOT NULL,
orgDescription VARCHAR(255),
orgAddress VARCHAR(255) NOT NULL
);

CREATE TABLE superOrganizations(
orgId INT NOT NULL,
superId INT NOT NULL,
PRIMARY KEY (orgId, superId),
FOREIGN KEY (orgId) REFERENCES organizations(id),
FOREIGN KEY (superId) REFERENCES supers(id)
);

CREATE TABLE locations(
id INT PRIMARY KEY auto_increment,
locName VARCHAR(100) NOT NULL,
locDescription VARCHAR(255),
locAddress VARCHAR(255) NOT NULL,
coordinates VARCHAR(20) NOT NULL
);

CREATE TABLE sights(
id INT PRIMARY KEY auto_increment,
locId INT NOT NULL,
superId INT NOT NULL,
date datetime NOT NULL,
FOREIGN KEY (locId) REFERENCES locations(id),
FOREIGN KEY (superId) REFERENCES supers(id)
)
