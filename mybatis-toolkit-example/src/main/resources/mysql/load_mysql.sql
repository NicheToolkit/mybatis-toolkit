DROP TABLE IF EXISTS `ntr_load`;
CREATE TABLE IF NOT EXISTS `ntr_load`
(
  `load_pk1`          VARCHAR(64) NOT NULL,
  `load_pk2`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `param_id`     VARCHAR(64),
  `link_id1`     VARCHAR(64),
  `link_id2`     VARCHAR(64),
  `time` TIMESTAMP,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_LOAD` PRIMARY KEY (`load_pk1`,`load_pk2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_LOAD_PK1 (`load_pk1`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_LOAD_PK2 (`load_pk2`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_PARAM_ID (`param_id`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_LINK_ID1 (`link_id1`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_LINK_ID2 (`link_id2`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_TIME (`time`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_OPERATE (`operate`);

ALTER TABLE `ntr_load` ADD INDEX IDX_NTR_LOAD_LOGIC (`logic`);

DROP TABLE IF EXISTS `ntr_load_link1`;
CREATE TABLE IF NOT EXISTS `ntr_load_link1`
(
  `id`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `param_id`     VARCHAR(64),
  `time` TIMESTAMP,
  `operate` TINYINT,
  `logic` TINYINT,
  `create_time` TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_LOAD_LINK1` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_load_link1` ADD INDEX IDX_NTR_LOAD_LINK1_PARAM_ID (`param_id`);

ALTER TABLE `ntr_load_link1` ADD INDEX IDX_NTR_LOAD_LINK1_TIME (`time`);

ALTER TABLE `ntr_load_link1` ADD INDEX IDX_NTR_LOAD_LINK1_OPERATE (`operate`);

ALTER TABLE `ntr_load_link1` ADD INDEX IDX_NTR_LOAD_LINK1_LOGIC (`logic`);

DROP TABLE IF EXISTS `ntr_load_link2`;
CREATE TABLE IF NOT EXISTS `ntr_load_link2`
(
    `id`          VARCHAR(64) NOT NULL,
    `name`        VARCHAR(128),
    `description` VARCHAR(256),
    `param_id`     VARCHAR(64),
    `time` TIMESTAMP,
    `operate` TINYINT,
    `logic` TINYINT,
    `create_time` TIMESTAMP,
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `PK_NTR_CF_LOAD_LINK2` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_load_link2` ADD INDEX IDX_NTR_LOAD_LINK2_PARAM_ID (`param_id`);

ALTER TABLE `ntr_load_link2` ADD INDEX IDX_NTR_LOAD_LINK2_TIME (`time`);

ALTER TABLE `ntr_load_link2` ADD INDEX IDX_NTR_LOAD_LINK2_OPERATE (`operate`);

ALTER TABLE `ntr_load_link2` ADD INDEX IDX_NTR_LOAD_LINK2_LOGIC (`logic`);
