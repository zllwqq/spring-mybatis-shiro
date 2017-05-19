SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS province;
CREATE TABLE province (
  id            int,
  name           varchar(16),
  province_code      varchar(8),
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS city;
CREATE TABLE city (
  id           int,
  city_code       varchar(8),
  name          varchar(32),
  province_code   varchar(8),
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS area;
CREATE TABLE area (
  id  int,
  name varchar(32),
  area_code varchar(8),
  city_code varchar(8),
  PRIMARY KEY (id)
);