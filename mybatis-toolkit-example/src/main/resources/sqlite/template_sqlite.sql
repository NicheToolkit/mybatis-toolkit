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
  `update_time` TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_TEMPLATE` PRIMARY KEY (`template_pk1`,`template_pk2`)
);

CREATE INDEX `IDX_NTR_TEMPLATE_TEMPLATE_PK1` ON `ntr_template` (`template_pk1`);

CREATE INDEX `IDX_NTR_TEMPLATE_TEMPLATE_PK2` ON `ntr_template` (`template_pk2`);

CREATE INDEX `IDX_NTR_TEMPLATE_LINK_ID1` ON `ntr_template` (`link_id1`);

CREATE INDEX `IDX_NTR_TEMPLATE_LINK_ID2` ON `ntr_template` (`link_id2`);

CREATE INDEX `IDX_NTR_TEMPLATE_TIME` ON `ntr_template` (`time`);

CREATE INDEX `IDX_NTR_TEMPLATE_OPERATE` ON `ntr_template` (`operate`);

CREATE INDEX `IDX_NTR_TEMPLATE_STATUS1` ON `ntr_template` (`status1`);

CREATE INDEX `IDX_NTR_TEMPLATE_STATUS2` ON `ntr_template` (`status2`);

CREATE INDEX `IDX_NTR_TEMPLATE_LOGIC` ON `ntr_template` (`logic`);

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
  `update_time` TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_TEMPLATE_DYNAMIC` PRIMARY KEY (`template_pk1`,`template_pk2`)
);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK1` ON `ntr_template_dynamic` (`template_pk1`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK2` ON `ntr_template_dynamic` (`template_pk2`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID1` ON `ntr_template_dynamic` (`link_id1`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID2` ON `ntr_template_dynamic` (`link_id2`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_TIME` ON `ntr_template_dynamic` (`time`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_OPERATE` ON `ntr_template_dynamic` (`operate`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_STATUS1` ON `ntr_template_dynamic` (`status1`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_STATUS2` ON `ntr_template_dynamic` (`status2`);

CREATE INDEX `IDX_NTR_TEMPLATE_DYNAMIC_LOGIC` ON `ntr_template_dynamic` (`logic`);

