DROP TABLE IF EXISTS `ntr_simple`;
CREATE TABLE IF NOT EXISTS `ntr_simple`
(
  `id`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `link_id`     VARCHAR(64),
  `time` TIMESTAMP,
  `status` TINYINT,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_SIMPLE` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_simple` ADD INDEX IDX_NTR_SIMPLE_LINK_ID (`link_id`);

ALTER TABLE `ntr_simple` ADD INDEX IDX_NTR_SIMPLE_TIME (`time`);

ALTER TABLE `ntr_simple` ADD INDEX IDX_NTR_SIMPLE_STATUS (`status`);

ALTER TABLE `ntr_simple` ADD INDEX IDX_NTR_SIMPLE_OPERATE (`operate`);

ALTER TABLE `ntr_simple` ADD INDEX IDX_NTR_SIMPLE_LOGIC (`logic`);

DROP TABLE IF EXISTS `ntr_simple_dynamic`;
CREATE TABLE IF NOT EXISTS `ntr_simple_dynamic`
(
  `id`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `link_id`     VARCHAR(64),
  `time` TIMESTAMP,
  `status` TINYINT,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_SIMPLE_DYNAMIC` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_simple_dynamic` ADD INDEX IDX_NTR_SIMPLE_DYNAMIC_LINK_ID (`link_id`);

ALTER TABLE `ntr_simple_dynamic` ADD INDEX IDX_NTR_SIMPLE_DYNAMIC_TIME (`time`);

ALTER TABLE `ntr_simple_dynamic` ADD INDEX IDX_NTR_SIMPLE_DYNAMIC_STATUS (`status`);

ALTER TABLE `ntr_simple_dynamic` ADD INDEX IDX_NTR_SIMPLE_DYNAMIC_OPERATE (`operate`);

ALTER TABLE `ntr_simple_dynamic` ADD INDEX IDX_NTR_SIMPLE_DYNAMIC_LOGIC (`logic`);
