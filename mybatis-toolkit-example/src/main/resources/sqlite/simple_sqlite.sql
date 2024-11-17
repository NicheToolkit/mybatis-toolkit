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
  `update_time` TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_SIMPLE` PRIMARY KEY (`id`)
);

CREATE INDEX `IDX_NTR_SIMPLE_LINK_ID` ON `ntr_simple` (`link_id`);

CREATE INDEX `IDX_NTR_SIMPLE_TIME` ON `ntr_simple` (`time`);

CREATE INDEX `IDX_NTR_SIMPLE_STATUS` ON `ntr_simple` (`status`);

CREATE INDEX `IDX_NTR_SIMPLE_OPERATE` ON `ntr_simple` (`operate`);

CREATE INDEX `IDX_NTR_SIMPLE_LOGIC` ON `ntr_simple` (`logic`);

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
  `update_time` TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_SIMPLE_DYNAMIC` PRIMARY KEY (`id`)
);

CREATE INDEX `IDX_NTR_SIMPLE_DYNAMIC_LINK_ID` ON `ntr_simple_dynamic` (`link_id`);

CREATE INDEX `IDX_NTR_SIMPLE_DYNAMIC_TIME` ON `ntr_simple_dynamic` (`time`);

CREATE INDEX `IDX_NTR_SIMPLE_DYNAMIC_STATUS` ON `ntr_simple_dynamic` (`status`);

CREATE INDEX `IDX_NTR_SIMPLE_DYNAMIC_OPERATE` ON `ntr_simple_dynamic` (`operate`);

CREATE INDEX `IDX_NTR_SIMPLE_DYNAMIC_LOGIC` ON `ntr_simple_dynamic` (`logic`);
