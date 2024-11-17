DROP TABLE IF EXISTS `ntr_template`;
CREATE TABLE IF NOT EXISTS `ntr_template`
(
  `template_pk1`          VARCHAR(64) NOT NULL,
  `template_pk2`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `link_id1`     VARCHAR(64),
  `link_id2`     VARCHAR(64),
  `time` TIMESTAMP,
  `status1` TINYINT,
  `status2` TINYINT,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_TEMPLATE` PRIMARY KEY (`template_pk1`,`template_pk2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_TEMPLATE_PK1 (`template_pk1`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_TEMPLATE_PK2 (`template_pk2`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_LINK_ID1 (`link_id1`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_LINK_ID2 (`link_id2`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_TIME (`time`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_OPERATE (`operate`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_STATUS1 (`status1`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_STATUS2 (`status2`);

ALTER TABLE `ntr_template` ADD INDEX IDX_NTR_TEMPLATE_LOGIC (`logic`);

DROP TABLE IF EXISTS `ntr_template_dynamic`;
CREATE TABLE IF NOT EXISTS `ntr_template_dynamic`
(
  `template_pk1`          VARCHAR(64) NOT NULL,
  `template_pk2`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `link_id1`     VARCHAR(64),
  `link_id2`     VARCHAR(64),
  `time` TIMESTAMP,
  `status1` TINYINT,
  `status2` TINYINT,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_TEMPLATE_DYNAMIC` PRIMARY KEY (`template_pk1`,`template_pk2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK1 (`template_pk1`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK2 (`template_pk2`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID1 (`link_id1`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID2 (`link_id2`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_TIME (`time`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_OPERATE (`operate`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_STATUS1 (`status1`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_STATUS2 (`status2`);

ALTER TABLE `ntr_template_dynamic` ADD INDEX IDX_NTR_TEMPLATE_DYNAMIC_LOGIC (`logic`);
