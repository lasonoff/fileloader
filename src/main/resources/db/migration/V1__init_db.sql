CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `firstName` varchar(60) DEFAULT NULL,
  `lastName` varchar(60) DEFAULT NULL,
  `login` varchar(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8tbwelrnviudxdaggwr1kd9b` (`login`)
);

CREATE TABLE `file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `fileLocation` varchar(255) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actionType` int DEFAULT NULL,
  `fileId` bigint NOT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_file` (`fileId` ASC) VISIBLE,
  CONSTRAINT `fk_event_file`
    FOREIGN KEY (`fileId`)
    REFERENCES `file` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  INDEX `fk_event_user` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_event_user`
    FOREIGN KEY (`userId`)
    REFERENCES `user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);