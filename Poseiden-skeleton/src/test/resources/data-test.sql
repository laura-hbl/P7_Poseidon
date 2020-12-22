DROP DATABASE IF EXISTS poseidon_test;

CREATE DATABASE poseidon_test CHARACTER SET utf8mb4;

USE poseidon_test;

CREATE TABLE users (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE bid_list (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE trade (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE curve_point (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  curve_id tinyint,
  as_of_date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_date TIMESTAMP ,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE rating (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  moodys_rating VARCHAR(125),
  stand_poor_rating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number tinyint,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE rule_name (
  id tinyint(4) AUTO_INCREMENT NOT NULL,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO users(id, fullname, username, password, role)
VALUES
(1,"Administrator", "admin", "$2a$10$/Oa835whQGgbbHEl4lyyDe0IWlREZb69.ikdiCzUco81yOwx3W07m", "ADMIN"),
(2, "User", "user", "$2a$10$/Oa835whQGgbbHEl4lyyDe0IWlREZb69.ikdiCzUco81yOwx3W07m", "USER");

INSERT INTO bid_list(id, account, type, bid_quantity)
VALUES
(1,"account1", "type1", 100),
(2, "account2", "type2", 300);

INSERT INTO rating
VALUES
(1,"moody1", "standP1", "fitch1", 3),
(2, "moody2", "standP2", "fitch2", 5);

INSERT INTO rule_name
VALUES
(1, "name1", "description1", "json1", "template1", "sqlStr1", "sqlPart1"),
(2, "name2", "description2","json2", "template2", "sqlStr2", "sqlPart2");

INSERT INTO trade(id, account, type, buy_quantity)
VALUES
(1, "account1", "type1", 200),
(2, "account2", "type2", 500);

INSERT INTO curve_point(id, curve_id, term, value)
VALUES
(1, 3, 10, 200),
(2, 5, 10, 300);