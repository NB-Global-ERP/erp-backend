CREATE TABLE `companies` (
  `company_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `company_code` VARCHAR(4) NOT NULL,
  `company_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`company_id`)
);

CREATE TABLE `course_of_study` (
  `cource_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(1024) NOT NULL,
  `duration_in_days` INT NOT NULL,
  `price_per_person` DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`cource_id`)
);

CREATE TABLE `specification` (
  `id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `date` DATE NOT NULL,
  `number` INT NOT NULL,
  `companies_id` INT NOT NULL,
  `total_amount_excluding_vat` DECIMAL(12, 2) NOT NULL,
  `vat_amount_22_percent` DECIMAL(12, 2) NOT NULL,
  `total_amount_including_vat` DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_specification_1` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`company_id`)
);

CREATE TABLE `group_members` (
  `group_member_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `student_id` INT NOT NULL,
  `group_id` INT NOT NULL,
  `completion_percent` DECIMAL(3, 2) NOT NULL,
  PRIMARY KEY (`group_member_id`),
  CONSTRAINT `fk_group_member_2` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`),
  CONSTRAINT `fk_group_member_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
);

CREATE TABLE `group` (
  `group_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `course_id` INT NOT NULL,
  `data_begin` DATE NOT NULL,
  `data_end` DATE NOT NULL,
  `price_per_person` DECIMAL(12, 2) NOT NULL,
  `participant_count` INT NOT NULL DEFAULT '0',
  `group_price` DECIMAL(10, 2) NOT NULL,
  `training_status_id` INT NOT NULL,
  `average_progress` DECIMAL(5, 2) NOT NULL,
  `specification_id` INT NOT NULL,
  PRIMARY KEY (`group_id`),
  CONSTRAINT `fk_study_group_1` FOREIGN KEY (`course_id`) REFERENCES `course_of_study` (`cource_id`),
  CONSTRAINT `fk_study_group_3` FOREIGN KEY (`training_status_id`) REFERENCES `training_status` (`id`),
  CONSTRAINT `fk_study_group_2` FOREIGN KEY (`specification_id`) REFERENCES `specification` (`id`)
);

CREATE TABLE `students` (
  `student_id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `first_name` VARCHAR(255) NOT NULL,
  `middle_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `company_id` INT NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `fk_training_participant_1` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
);

CREATE TABLE `training_status` (
  `id` INT NOT NULL AUTO_INCREMENT UNIQUE,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);