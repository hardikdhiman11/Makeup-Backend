CREATE TABLE `user_authorities` (
  `user_id` bigint NOT NULL,
  `authorities_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`authorities_id`)
--  KEY `FKnfeejqie0sy3gxxil7dn3plsw` (`authorities_id`),
--  CONSTRAINT `FKmj13d0mnuj4cd8b6htotbf9mm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
--  CONSTRAINT `FKnfeejqie0sy3gxxil7dn3plsw` FOREIGN KEY (`authorities_id`) REFERENCES `authorities` (`id`)
)
